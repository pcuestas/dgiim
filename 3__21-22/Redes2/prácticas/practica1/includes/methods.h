#ifndef _METHODS_H
#define _METHODS_H

#include "server_response_build.h"
#include "server_sck_interaction.h"

#define INTERPRETER_PYTHON "python3"
#define INTERPRETER_PHP    "php"


/**
 * @brief responde una petición 
 * @param sock descriptor del socket por donde enviar la respuesta
 * @param request petición
 * @return -1 en caso de error, >0 en otro caso
 */ 
int mth_respond(int sock, struct ssk_req *request);

/**
 * @brief método get - funciṕn genérica para get, post y head
 * 
 * @param sock socket
 * @param request petición
 * @param headers headers a mandar
 * @param send_body para decidir si mandar o no el cuerpo 
 * (SEND_BODY ó DONT_SEND_BODY)
 * @return 0 en caso de éxito, -1 en caso de error
 */
int mth_get(int sock, 
            struct ssk_req *request, 
            struct srb_headers *headers,
            int send_body);

/* Funciones de los métodos
 * Todas ellas son de la siguiente forma:
 * 
 * @param sock socket
 * @param request request
 * @param headers headers
 * @return 0 en caso de éxito, -1 en caso de error
 */

int mth_post(int sock, 
            struct ssk_req *request, 
            struct srb_headers *headers);

int mth_head(int sock, 
            struct ssk_req *request, 
            struct srb_headers *headers);

int mth_options(int sock, 
            struct ssk_req *request, 
            struct srb_headers *headers);

/**
 * @brief mandar error
 * 
 * @param socket 
 * @param err_code código 
 * @param err_mssg mensaje
 * @param headers headers
 * @return 0 en caso de éxito, -1 en caso de error
 */
int mth_error(int sock, int err_code, char* err_mssg, 
            struct srb_headers *headers);

#endif /*_METHODS_H*/