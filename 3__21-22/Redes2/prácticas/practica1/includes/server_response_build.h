#ifndef _RESPONSE_H
#define _RESPONSE_H

#include <stdio.h>
#include <stdarg.h>

#define PHRASE_MAX_LEN 100
#define STATUS_LINE_MAX_SIZE (sizeof(int) + PHRASE_MAX_LEN)

#define CODE_NOT_IMPLEMENTED 501 /*deberia ser 501 para ser exactos*/
#define MSG_NOT_IMPLEMENTED "Not Implemented"
#define CODE_OK 200
#define MSG_OK "OK"
#define CODE_NO_CONTENT 204
#define MSG_NO_CONTENT "No Content"
#define CODE_BAD_REQUEST 400
#define MSG_BAD_REQUEST "Bad Request"
#define CODE_NOT_FOUND 404
#define MSG_NOT_FOUND "Not Found"
#define CODE_MOVED_PERMANENTLY 301 
#define MSG_MOVED_PERMANENTLY "Moved Permanently"
#define CODE_NOT_MODIFIED 304
#define MSG_NOT_MODIFIED "Not Modified"

/** SCRIPT TYPES */
#define ST_NONE   0
#define ST_PYTHON 1
#define ST_PHP    2

#define ALLOW_GET 1
#define ALLOW_POST 2


#define MAX_HEADERS 100
#define HDR_BUF_LEN 100




struct srb_status_line { /*asumimos que siempre utilizamos HTTP/1.1*/
    int code;                    /*response status code*/
    char phrase[PHRASE_MAX_LEN]; /*reason prase*/  
};

struct srb_header {
    char name[HDR_BUF_LEN];
    char value[HDR_BUF_LEN];
};

struct srb_headers{
    int _max; 
    int num;     /* primera posición libre del array */
    struct srb_header headers[MAX_HEADERS];
};

struct srb_body{
    FILE *fpoin;   /*posible file pointer*/
    int fdesc;     /*file desc*/
    int fsize;     /*tamaño del fichero*/
    int send;      /*1 si hay que mandar el cuerpo*/
};

/**
 * @brief construye y envía la respuesta HTTP al socket
 * 
 * @param sock socket por el que enviar
 * @param status_line línea inicial de la respuesta
 * @param h headers de la respuesta
 * @param body cuerpo de la respuesta
 * @return int -1 en caso de error. 0 en caso contrario
 */
int srb_response_send(int sock, 
                      struct srb_status_line status_line, 
                      struct srb_headers* h, 
                      struct srb_body *body);

/**
 * @brief establece los atributos de la estructura srb_status_line
 * @param line puntero a la estructura
 * @param _code status code
 * @param _phrase reason phrase
 */ 
void srb_status_line_set(struct srb_status_line *line, 
                        int _code, char *_phrase);

/**
 * @brief inicializa la estructura body
 * 
 * @param b estructura body
 */
void srb_body_init(struct srb_body *b);


/**************HEADERS (funciones en server_response_build_hdrs)***********/

/**
 * @brief inicializa los headers por defecto 
 * 
 * @param h estructura de headers a inicializar
 */
void srb_hdr_init(struct srb_headers *h);

/**
 * @brief fills a new header for a response
 * @param headers 
 * @param name
 * @param value
 * @return -1 in case of error.
 */ 
int srb_hdr_add(struct srb_headers *headers, char *name, char*value);

/**
 * @brief añade el header de content type. Además, guarda información en 
 * script_type sobre el tipo de script: ver macros definidas como ST_--- 
 * 
 * @param path path del fichero
 * @param headers estructura de los headers donde se añade
 * @param script_type guarda el tipo de ST_--- correspondiente al terminar la llamada
 * @return int -1 en caso de error, 0 en otro caso
 */
int srb_hdr_add_contenttype(char *path, struct srb_headers *headers, int *script_type);

/**
 * @brief sets the last modified and content length headers
 * and stores into body the size of the file
 * 
 * @param headers 
 * @param body 
 * @return int 
 */
int srb_hdr_add_file_info(struct srb_headers *headers, struct srb_body *body);

/**
 * @brief añade el header de location
 * 
 * @param headers 
 * @param new_path el path del fichero a escribir en el header location 
 * @return int -1 en caso de error, 0 en otro caso
 */
int srb_hdr_add_location(struct srb_headers *headers, char *new_path);


/**
 * @brief añade los métodos permitidos. Variadic function
 * @param n número de métodos permitidos
 * @param ... strings con los nombres de los mátodos permitidos 
 */ 
int srb_hdr_add_allow(struct srb_headers *headers, int n, ...);


/**
 * @brief añade el header de content type, rellena las flags siguientes, en caso de no ser null
 * @param path path del fichero
 * @param content_type pointer to an already alocated string 
 * @param allowed para OPTIONS, métodos permitidos
 * @param script para el tipo de script a ejecutar
 */
int srb_hdr_process_content(char *path, char *type_extension, int *allowed, int *script_type);


#endif