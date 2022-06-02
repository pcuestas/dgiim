#include "../includes/server_response_build.h"
#include "../includes/server_sck_interaction.h"
#include "../includes/server_configuration.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <syslog.h>
#include <time.h>


#define MAX_BUF_LEN 1024


extern int pclose(FILE *stream);
extern int close(int fd);


/*****************AUX FUNCTIONS*********************************/

char *srb_response_status_line_build(struct srb_status_line l)
{
    char *s;

    if((s = calloc(MAX_BUF_LEN, sizeof(char))) == NULL)
    {
        syslog(LOG_ERR,"Error allocating for srb_response_status_line_build");
        return NULL;
    }

    snprintf(s, MAX_BUF_LEN, "HTTP/1.1 %d %s\r\n", l.code, l.phrase);
    return s;
}

int srb_header_total_len(struct srb_header *h)
{   
    /*name: valueCRLF*/
    return strlen(h->name)+strlen(h->value) + 4; /*4 -> : SP \r\n*/
}

int srb_headers_total_len(struct srb_headers* h)
{
    int len = 0;
    int i;

    for(i = 0;i < h->num; i++)
    {
        len += srb_header_total_len(h->headers + i);
    }
    return len + 2; /* len + len(CRLF)*/
}

char *srb_response_headers_build(struct srb_headers* h)
{
    char *s;
    int i;
    int written = 0;
    int total_len;

    total_len = srb_headers_total_len(h) + 1;

    if((s = calloc(total_len, sizeof(char))) == NULL)
    {
        syslog(LOG_ERR,"Error allocating for srb_response_headers_build");
        return NULL;
    }
    *s = 0; 

    for(i = 0; i < h->num; i++)
    {
        written += snprintf(s + written, 
                    total_len - written,
                    "%s: %s\r\n", 
                    h->headers[i].name, 
                    h->headers[i].value);
    }

    strcat(s, "\r\n");
    return s;
}

/**
 * @brief construye la respuesta a partir de la status_line y los headers.
 * No incluye el cuerpo. Reserva memoria para el puntero devuelto.
 * 
 * @param status_line 
 * @param h 
 * @param size contiene la longitud de la respuesta construida
 * @return char* puntero con la respuesta, NULL en caso de error
 */
char *srb_response_build(struct srb_status_line status_line, 
                        struct srb_headers* h, 
                        int *size)
{
    int hdrs_len;
    char *hstr;
    char *lstr;
    char *response;

    if((hstr = srb_response_headers_build(h)) == NULL)
        return NULL;
    if((lstr = srb_response_status_line_build(status_line)) == NULL)
    {
        free(hstr);
        return NULL;
    }
    
    hdrs_len = strlen(hstr);
    *size = strlen(lstr) + hdrs_len;

    if((response = calloc(*size + 1, sizeof(char))) == NULL)
    {
        free(hstr);
        free(lstr);
        syslog(LOG_ERR, "Error allocating for srb_response_build");
        return NULL;
    }

    snprintf(response, *size, "%s", lstr);
    free(lstr);
    
    strncat(response, hstr, hdrs_len);
    free(hstr);

    return response;
}

/**
 * @brief envía el fichero apuntado por f desde la posición 
 * a la que apunta inicialmente
 * 
 * @param sock socket en el que se envía el fichero 
 * @param f puntero al fichero a enviar
 * @return int -1 en caso de error. El tamaño escrito en el 
 * socket en caso contrario
 */
int ssk_send_file_from_fp(int sock, FILE *f)
{
    char output[MAX_BUF_LEN];
    int ret = 0;
    int total = 0;

    while((ret = fread(output, 1, MAX_BUF_LEN, f)) > 0)
    {
        total += ret;
        ssk_send(sock, output, ret);
    }
    if (ret == -1)
    {
        syslog(LOG_ERR, "Error freading (ssk_send_file_from_fp)");
        return -1;
    }
    return total;
}

/**
 * @brief envía el fichero al que apunta body
 * 
 * @param sock socket por el que enviarlo
 * @param body estructura del cuerpo a enviar
 * @return int -1 en caso de error. 0 en caso contrario
 */
int srb_send_file(int sock, struct srb_body *body)
{
    if(!(body->send)) 
        return 0; /*el fichero no debe enviarse*/

    if (body->fpoin == NULL) 
        return ssk_send_file(sock, body->fdesc, body->fsize);
    else
        return ssk_send_file_from_fp(sock, body->fpoin);
}

/**
 * @brief cierra los ficheros pertinentes 
 * 
 * @param b 
 */
void body_close(struct srb_body *b)
{
    if (b->fpoin)
    {
        pclose(b->fpoin); /*si se abrió con popen*/
    }
    else if (b->fdesc != -1)
    {
        close(b->fdesc);   /*si se abrió con open, solo el descriptor*/
    }
}

/**************************.H FUNCTIONS**********************************/


void srb_body_init(struct srb_body *b)
{
    b->fdesc = -1;
    b->fpoin = NULL;
    b->fsize = 0;
    b->send = 0;
}

void srb_status_line_set(struct srb_status_line *line, int _code, char *_phrase)
{
    line->code =_code;
    
    strncpy(line->phrase, _phrase, PHRASE_MAX_LEN);
}

int srb_response_send(int sock, 
                      struct srb_status_line status_line, 
                      struct srb_headers* h, 
                      struct srb_body *body)
{
    int total_size;
    char *resp = srb_response_build(status_line, h, &total_size);

    if(resp == NULL)
        return -1;

    /*enviar respuesta sin cuerpo*/
    ssk_send(sock, resp, total_size); 
    free(resp);

    /*enviar solo el cuerpo, si procede*/
    total_size = srb_send_file(sock, body); 

    body_close(body);
    return 0;
}



