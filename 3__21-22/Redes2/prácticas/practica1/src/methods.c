#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <syslog.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>

#include "../includes/lib_yuarel.h"
#include "../includes/methods.h"
#include "../includes/server_response_build.h"
#include "../includes/server_configuration.h"

#define _XOPEN_SOURCE  

extern int close(int fd);
extern char *strptime(const char *restrict s, const char *restrict format,
                      struct tm *restrict tm);


/***************************************************************/
/* Variables globales de la configuración del servidor         */
extern char *server_root;
/***************************************************************/

#define RET_MOVED_PERMANENTLY -2
#define RET_NOT_MODIFED -3

#define GET_NAME "GET"
#define POST_NAME "POST"
#define HEAD_NAME "HEAD"
#define OPTIONS_NAME "OPTIONS"
#define PUT_NAME "PUT"
#define PATCH_NAME "PATCH"
#define CONNECT_NAME "CONNECT"
#define DELETE_NAME "DELETE"
#define PUT_NAME "PUT"
#define TRACE_NAME "TRACE"

#define MAX_URL_LEN 4096
#define MAX_COMM_LEN 512

#define SEND_BODY 1
#define DONT_SEND_BODY 0
/*#define ROOT_DIR "./build/www"*/

#define IF_MODIFIED_HEADER "If-Modified-Since"


/** Handle error and return*/
#define HANDLE_ERR_AND_RETURN(msg) \
    do                             \
    {                              \
        syslog(LOG_ERR, msg);      \
        return -1;                 \
    } while (0)
/*do{ syslog(LOG_ERR, msg); return -1; }while(0)*/



/** funciones auxiliares **/

/**
 * @brief comrpueba el path, si es un directorio le concatena index.html al nuevo path.
 * No reserva memoria.
 * @param path ruta original
 * @param path_len longitud de la ruta original
 * @param actual_path ruta real
 */
void actual_path(const char *path, int path_len, char *actual_path)
{
    if (path[path_len - 1] == '/')
        sprintf(actual_path, "%s%.*s%s", server_root, (int)path_len, path, "index.html");
    else
        sprintf(actual_path, "%s%.*s", server_root, (int)path_len, path);
}

/**
 * @brief ejecuta el comando 
 * 
 * @param command comando terminado en '\0'
 * @param max_read tamaño máximo a leer en el buffer
 * @param sread tamaño del bufer leido 
 * @param output bufer donde se escribe lo retornado por el comando
 * @return -1 en caso de error. 0 en caso contrario
 */
int command_output(const char* command, int max_read, int *sread, char *output)
{
    int ret;
    int read = 0;
    FILE *f = popen(command, "r");

    if(!f)
        HANDLE_ERR_AND_RETURN("Error executing command (popen in command_output)");

    while((ret = fread(output + read, 1, max_read - read, f)) > 0)
        read += ret;
    *sread = read;
    fclose(f);
    return 0;
}

/**
 * @brief ejecuta un comando y devuelve (en fp) el puntero al 
 * fichero con el resultado
 * 
 * @param command comando 
 * @param fp puntero al fichero que termina apuntando al fichero 
 * que devuelve la ejecución con popen
 * @param fd descriptor del fichero al que apunta fp (no queda 
 * claro que esto luego se pueda utilizar, popen puede dar problemas
 * al tomar el descriptor del fichero que devuelve)
 * @return 0 en caso de error. -1 en otro caso
 */
int command_output_to_file(char *command, FILE **fp, int *fd)
{
    (*fp) = popen(command, "r");

    if(!(*fp))
        HANDLE_ERR_AND_RETURN("Couldn't execute command successfully (popen in command_output_to_file)");
    
    (*fd) = fileno(*fp); /*asignar el descriptor de fichero correspondiente*/

    if( (*fd) == -1)
        HANDLE_ERR_AND_RETURN("Couldn't open output file successfully (command_output_to_file)");

    return 0;
}

/**
 * @brief comprueba si se ha movido un archivo. Si es el caso
 * completa la linea de status y el header location.
 * @param path path del recurso
 * @param headers headers de la respuesta
 * @return -1 si no existe un archivo con ese nombre. 
 * RET_MOVED_PERMANENTLY si existe en otra localización. 
 */
int has_been_moved(const char *path, struct srb_headers *headers)
{
    int read;
    char new_path[MAX_URL_LEN];
    char command[MAX_COMM_LEN];
    char *beggining = strrchr(path, '/');
    
    if(!beggining)
        beggining = (char*)path;

    snprintf(command, sizeof(command), "find %s | grep %s | head -1", server_root, beggining + sizeof(char));
    
    command_output(command, MAX_URL_LEN, &read, new_path);
    new_path[read-1] = 0; /*quitar el \n final del comando*/

    if (!read) /*No existe tal fichero*/
        return -1;
    
    srb_hdr_add_location(headers, new_path + strlen(server_root));
    
    return RET_MOVED_PERMANENTLY; /*Para el get_body */
}

/**
 * @brief comprueba si la petición tiene el header if_modified y 
 * si ha sido modificado
 * @param request http resquest
 * @param path path del recurso
 * @return 1 si hace falta mandar 304 "Not Modifed"
 */ 
int if_modified_header(struct ssk_req *request, char * path){
    int fd, i,header_inclued=0;
    struct stat attr;
    time_t request_time, file_time;
    struct tm aux_tm;

    for(i=0;i<request->num_headers;i++){
        if (!(strncmp(IF_MODIFIED_HEADER,request->headers[i].name,strlen(IF_MODIFIED_HEADER))))
            header_inclued=i;
    }

    if(header_inclued){
        fd = open(path, O_RDONLY);
        fstat(fd, &attr);
        file_time=attr.st_mtime;
        close(fd);

        memset (&aux_tm, 0, sizeof(struct tm));
        strptime(request->headers[header_inclued].value, "%a, %d %b %Y %H:%M:%S %Z", &aux_tm);
        request_time=mktime(&aux_tm);

        return difftime(file_time,request_time)<0;/*True when the file has not been modified*/
    }

    return 0;
}

/**
 * @brief construye el comando que ejecuta un script
 * 
 * @param req petición
 * @param comm string donde escribir el comando
 * @param max_len máxima longitud del comando
 * @param script_type tipo de script
 * @param actual_path path del script
 */
void build_script_command(struct ssk_req *req, char *comm, int max_len, int script_type, char *actual_path)
{
    char *interpreter;
    int swrit = 0;
    int i;

    if (script_type == ST_PYTHON)  
        interpreter = INTERPRETER_PYTHON;
    else /*script_type == ST_PHP*/ 
        interpreter = INTERPRETER_PHP;

    swrit += snprintf(comm + swrit, max_len - swrit, 
             "%s %s", interpreter, actual_path);

    for (i = 0; i < req->num_params; i++)
    {
        swrit += snprintf(comm + swrit, max_len - swrit, 
                " \"%s=%s\"", req->params[i].key, req->params[i].val);
    }
}

/**
 * @brief ejecuta el script y lo deja refenciado en el body
 * 
 * @param req petición
 * @param actual_path path del sript
 * @param body cuerpo donde se referenciará el resultado
 * @param script_type tipo de script
 * @return 0 en caso de éxito, -1 en caso de error
 */
int write_output_to_body(struct ssk_req *req, char *actual_path, struct srb_body *body, int script_type)
{
    char comm[MAX_COMM_LEN];

    build_script_command(req, comm, MAX_COMM_LEN, script_type, actual_path);

    if (command_output_to_file(comm, &(body->fpoin), &(body->fdesc)) == -1)
    {
        return -1;
    }
    return 0;
}

/**
 * @brief comprueba si el método existe y por tanto no es soportado
 * @return 1 si existe, 0 si no
 */
int unsupported_method(struct ssk_req *request)
{
    return (!strncmp(PUT_NAME, request->method, strlen(PUT_NAME)) ||
        !strncmp(DELETE_NAME, request->method, strlen(DELETE_NAME)) ||
        !strncmp(CONNECT_NAME, request->method, strlen(CONNECT_NAME)) ||
        !strncmp(TRACE_NAME, request->method, strlen(TRACE_NAME)) ||
        !strncmp(PATCH_NAME, request->method, strlen(PATCH_NAME)));
}   

/**
 * @brief reserva memoria para *buf y lee el fichero
 * al que apunta request->path en el buffer
 *
 * @param request petición
 * @param buf puntero al bufer
 * @return -1 en caso de error. 0 en caso contrario
 */
int mth_get_body(struct ssk_req *request, struct srb_body *body, struct srb_headers *headers)
{
    char path[MAX_URL_LEN];
    int content_type;
    int fd;

    actual_path(request->path, request->path_len, path);
    srb_hdr_add_contenttype(path, headers, &content_type);

    //_printf("path:%s\n",path);

    fd = open(path, O_RDONLY);
    if (fd == -1)
        return has_been_moved(path, headers);

    if (content_type != ST_NONE)
    {
        close(fd);
        if (write_output_to_body(request, path, body, content_type) == -1)
        {
            return -1;
        }
    }
    else /*no es un script*/
    {   
        body->fdesc = fd;

        if(if_modified_header(request,path))
            return RET_NOT_MODIFED;
    }

    srb_hdr_add_file_info(headers, body);

    return 0;
}


/** fin de funciones auxiliares **/

int mth_respond(int sock, struct ssk_req *request)
{
    struct srb_headers headers;

    srb_hdr_init(&headers);

    if (!strncmp(GET_NAME, request->method, strlen(GET_NAME)))
    {
        //_printf("METODO GET DISPONIBLE. Lo compruebo \n");
        return mth_get(sock, request, &headers, SEND_BODY);
    }
    if (!strncmp(HEAD_NAME, request->method, strlen(HEAD_NAME)))
    {
        //_printf("METODO HEAD DISPONIBLE. Lo compruebo \n");
        return mth_head(sock, request, &headers);
    }
    if (!strncmp(POST_NAME, request->method, strlen(POST_NAME)))
    {
        //_printf("METODO POST DISPONIBLE. Lo compruebo \n");
        return mth_post(sock,request,&headers);
    }
    if (!strncmp(OPTIONS_NAME, request->method, strlen(OPTIONS_NAME)))
    {
        //_printf("METODO OPTIONS DISPONIBLE. Lo compruebo \n");
        return mth_options(sock, request, &headers);
    }

    if (unsupported_method(request))
        return mth_error(sock, CODE_NOT_IMPLEMENTED,MSG_NOT_IMPLEMENTED,&headers);

    return mth_error(sock, CODE_BAD_REQUEST, MSG_BAD_REQUEST, &headers);
}


int mth_get(int sock, struct ssk_req *request, struct srb_headers *headers, int send_body)
{
    struct srb_body body;
    struct srb_status_line line;
    int sret;

    srb_body_init(&body);

    sret = mth_get_body(request, &body, headers);
    body.send = send_body; /*get debe mandar el fichero*/

    if (sret == -1)
    {
        //_printf("Voy a mandar 404\n");
        return mth_error(sock, CODE_NOT_FOUND, MSG_NOT_FOUND, headers);
    }
    else if (sret == RET_MOVED_PERMANENTLY)
    {
        return mth_error(sock, CODE_MOVED_PERMANENTLY, MSG_MOVED_PERMANENTLY, headers);
    }
    else if (sret == RET_NOT_MODIFED)
    {
        return mth_error(sock, CODE_NOT_MODIFIED, MSG_NOT_MODIFIED,headers);
    }

    srb_status_line_set(&line, CODE_OK, MSG_OK);

    sret = srb_response_send(sock, line, headers, &body);

    return sret;
}

int mth_post(int sock, struct ssk_req *request,
             struct srb_headers *headers)
{
    return mth_get(sock, request, headers, SEND_BODY);
}

int mth_head(int sock, struct ssk_req *request,
             struct srb_headers *headers)
{
    return mth_get(sock, request, headers, DONT_SEND_BODY);
}

int mth_options(int sock, struct ssk_req *request, 
            struct srb_headers *headers)
{
    char path[MAX_URL_LEN];
    int allow;
    int fd;

    if (*(request->path) == '*'){
        srb_hdr_add_allow(headers, 4, "OPTIONS", "GET", "POST", "HEAD");
    }
    else
    {
        actual_path(request->path, request->path_len, path);

        if((fd=(open(path,O_RDONLY))==-1)){
            close(fd);
            return mth_error(sock, CODE_NOT_FOUND,MSG_NOT_FOUND,headers);
        }

        close(fd);
        srb_hdr_process_content(path, NULL, &allow, NULL);

        if (allow == ALLOW_GET)
            srb_hdr_add_allow(headers, 3, "OPTIONS", "GET", "HEAD");
        else
            srb_hdr_add_allow(headers, 4, "OPTIONS", "GET", "POST", "HEAD");
    }

    return mth_error(sock, CODE_NO_CONTENT, MSG_NO_CONTENT, headers);
}

/**
 * @brief mandar error
 * 
 * @param socket 
 * @param err_code código 
 * @param err_mssg mensaje
 * @param headers headers
 * @return 0 en caso de éxito, -1 en caso de error
 */
int mth_error(int socket, int err_code, char *err_mssg, struct srb_headers *headers)
{
    struct srb_status_line line;
    struct srb_body body;
    int ret;
    char buf[BUF_LEN];

    srb_body_init(&body);
    body.send = 0;

    srb_status_line_set(&line, err_code, err_mssg);

    ret = srb_response_send(socket, line, headers, &body);

    if (ret == -1) 
        return -1;

    if (err_code == CODE_NOT_FOUND)
    {
        snprintf(buf,BUF_LEN, "%d %s\n",err_code,err_mssg);
        ret = ssk_send(socket, buf, strlen(buf));
    }
    return ret;
}
