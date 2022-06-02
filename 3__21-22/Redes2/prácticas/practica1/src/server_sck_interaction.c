#include "../includes/server_sck_interaction.h"
#include "../includes/lib_picohttpparser.h"
#include "../includes/lib_socket.h"
#include "../includes/server_response_build.h"
#include "../includes/methods.h"
#include "../includes/lib_yuarel.h"
#include "../includes/server_configuration.h"

#include <errno.h>
#include <syslog.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <sys/sendfile.h>
#include <sys/time.h>

#define STRING "HTTP/1.1 200 OK\r\n\r\n Hola\r\n\r\n"

#define HANDLE_ERR_AND_RETURN(msg) \
    do                             \
    {                              \
        syslog(LOG_ERR, msg);      \
        return -1;                 \
    } while (0)

int ssk_request_process(int sock)
{
    struct ssk_req request;

    if(ssk_request_read(sock, &request) == -1)
        return -1;

    return mth_respond(sock, &request);
}

/**
 * @brief igual que strchr, pero no se comprueban más de _n posiciones
 * 
 * @param s string en la que se busca el char
 * @param c char que se busca
 * @param _n número de posiciones que se comprueban
 * @return el puntero a la primera ocurrencia. 
 * NULL en caso de no encontrarla.
 */
char *util_strnchr(const char *s, char c, int _n)
{
    char *p;
    for(p = (char*)s; (*p != c) && (p < s + _n); p++);
    return (*p == c) ? p : NULL;
}

/**
 * @brief comprueba si es el método
 * 
 * @param mssg request
 * @param method método a comprobar
 * @return 1 si es ese método, 0 si no
 */
int is_method (struct ssk_req *mssg, char *method)
{
    return strncmp(mssg->method, method, mssg->method_len) ? 0 : 1;
}


int ssk_set_get_query(struct ssk_req *mssg, int path_len)
{
    if ((mssg->query_str = util_strnchr(mssg->path, '?', mssg->path_len)))
    {
        *(mssg->query_str)++ = '\0';
        path_len = mssg->query_str - mssg->path;
        mssg->query_len = mssg->path + mssg->path_len - mssg->query_str;
        mssg->path_len = path_len;
        mssg->query_str[mssg->query_len] = 0; /*terminar a 0 la query*/
    }
    else 
        mssg->query_len = 0;
    
    if (!is_method(mssg, "GET"))
        return 0; /*no hace falta parsear los parámetros*/

    if(mssg->query_len) 
    {
        if((mssg->num_params = 
                    yuarel_parse_query(mssg->query_str, 
                                       QUERY_DELIMITER, 
                                       mssg->params, 
                                       MAX_PARAMS_QUERY)) == -1)
            HANDLE_ERR_AND_RETURN("Error parsing query");
    }
    else
        mssg->num_params = 0;

    return 0;
}

/**
 * @brief busca el header de content-length y devuelve el 
 * valor de la longitud
 * 
 * @param mssg request
 * @return el tamaño en bytes del content-length. 0 si no 
 * existe el header.
 */
int content_len(struct ssk_req *mssg)
{
    int i;
    int clen = 0;
    for(i = 0; i < mssg->num_headers; i++)
    {
        if(!strncmp(mssg->headers[i].name, "Content-Length", strlen("Content-Length")))
        {
            clen = atoi(mssg->headers[i].value);
            break;
        }
    }
    return clen;
}

int ssk_set_post_query(struct ssk_req *mssg, int buflen)
{
    int clen;
    if (!is_method (mssg, "POST"))
    {
        return 0; /*no es post, no hacer nada*/
    }

    if ( (clen = content_len (mssg)) < 0 )
        return -1;

    mssg->query_len = clen;
    mssg->query_str = mssg->buf + buflen - clen; /*inicio del cuerpo*/

    if (mssg->query_len)
    {
        mssg->query_str[mssg->query_len] = 0; /*terminar a 0 la query*/
        if (-1 == (mssg->num_params =
                       yuarel_parse_query(mssg->query_str,
                                          QUERY_DELIMITER,
                                          mssg->params,
                                          MAX_PARAMS_QUERY)))
            HANDLE_ERR_AND_RETURN("Error parsing query (ssk_set_get_query)");
    }
    else
        mssg->num_params = 0;

    return 0;

}

/**
 * @brief lee una petición del socket
 *
 * @param sock descriptor del socket
 * @param mssg req_s donde se escribe la petición
 * @return -1 en caso de error. 0 si todo va bien
 */
int ssk_request_read(int sock, struct ssk_req *mssg)
{
    int pret;
    int plen = 0;
    size_t buflen = 0;
    size_t prevbuflen = 0;
    ssize_t rret;

    if (sckt_set_rcvtimeout(sock, SEC_TIMEOUT, 0) == -1)
        return -1;

    do
    { /* leer petición */
        while ((rret = read(sock, mssg->buf + buflen, BUF_LEN - buflen)) == -1 
               && errno == EINTR)
            ;

        /* si read sale por timeout, errno = EAGAIN or EWOULDBLOCK,
           pero rret será -1 en cualquier caso*/

        if (rret <= 0)
            HANDLE_ERR_AND_RETURN(
                (errno == EAGAIN || errno == EWOULDBLOCK) ? 
                    ("Read has timedout")
                : ("Error while reading request")
            );

        prevbuflen = buflen;
        buflen += rret;
        
        /* parsear petición */
        mssg->num_headers = sizeof(mssg->headers) / sizeof(mssg->headers[0]);
        pret = phr_parse_request(mssg->buf, buflen,
                                 &(mssg->method), &(mssg->method_len),
                                 &(mssg->path), &(mssg->path_len),
                                 &(mssg->minor_version), (mssg->headers),
                                 &(mssg->num_headers), prevbuflen);
        if (pret == -1)
            HANDLE_ERR_AND_RETURN("Error while parsing request");
        if (buflen == BUF_LEN)
            HANDLE_ERR_AND_RETURN("Error: too long request");
    } while (pret == -2);

    mssg->request_len = pret;


    /* parsear la posible cadena de query */
    if ((ssk_set_get_query(mssg, plen) == -1) || (ssk_set_post_query(mssg, buflen) == -1))
        return -1;

    return 0;
}

int ssk_send(int socket, char *msg, int nbytes)
{
    int size_written = 0;
    int total_size_written = 0;
    do
    {
        size_written = write(socket, ((char *)msg) + total_size_written, nbytes - total_size_written);
        if (size_written == -1 && errno != EINTR)
        {
            HANDLE_ERR_AND_RETURN("write");
        }
        else if (size_written != -1)
            total_size_written += size_written;
    } while (total_size_written != nbytes);

    return total_size_written;
}

int ssk_send_file(int socket, int fd, int fsize)
{
    int size_sent;
    int total_size_sent = 0;
    char errbuf[BUF_LEN];
    do
    {
        size_sent = sendfile(socket, fd, NULL, fsize - total_size_sent);
        if(size_sent == -1)
        {
            strerror_r(errno, errbuf, BUF_LEN);
            syslog(LOG_ERR, "sendfile: %s", errbuf);
            return -1;
        }
        total_size_sent += size_sent; 
    } while (total_size_sent != fsize);
    return total_size_sent;
}
