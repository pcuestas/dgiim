#ifndef _REQUEST_H_
#define _REQUEST_H_

#include "lib_picohttpparser.h"
#include "lib_yuarel.h"
#include "server_response_build.h"

#define BUF_LEN 4096
#define HEADER_LEN 100
#define MAX_PARAMS_QUERY 1024
#define QUERY_DELIMITER ('&')

/*para evitar quejas del compilador*/
extern int snprintf(char *str, size_t size, const char *format, ...);


struct ssk_req {
    char buf[BUF_LEN];
    size_t request_len;
    const char *method;
    size_t method_len;
    const char *path;
    size_t path_len;
    char *query_str;
    size_t query_len;
    struct yuarel_param params[MAX_PARAMS_QUERY];
    size_t num_params;
    int minor_version;
    struct phr_header headers[HEADER_LEN];
    size_t num_headers;
} ;

/**
 * @brief procesa una petición y responde si fuera pertinente
 * 
 * @param sock descriptor devuelto por sckt_accept
 * @return -1 en caso de error, 0 en otro caso
 */
int ssk_request_process(int sock);

/**
 * @brief lee una petición del socket. No reserva memoria.
 * 
 * @param sock descriptor del socket
 * @param mssg estructura donde se secribe el mensaje
 * @return -1 en caso de error. 0 en caso de éxito.
 */
int ssk_request_read(int sock, struct ssk_req *mssg);

/**
 * @brief manda un mensaje por un socket
 * @param socket socket descriptor
 * @param msg mensaje a mandar
 * @param nbytes número de bytes
 * @return número de bytes enviados o negativo en caso de error
 */ 
int ssk_send(int socket, char* msg, int nbytes);

/**
 * @brief escribe un fichero en el socket
 * 
 * @param socket 
 * @param fd descriptor del fichero
 * @param fsize tamaño del fichero a enviar
 * @return -1 en caso de error. El tamaño mandado en caso 
 *          contrario
 */
int ssk_send_file(int socket, int fd, int fsize);

#endif