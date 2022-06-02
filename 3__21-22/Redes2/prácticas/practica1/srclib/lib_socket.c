#include <stdio.h>
#include <stdlib.h>
#include <syslog.h>
#include <netinet/in.h>
#include <strings.h>
#include <unistd.h>
#include <sys/time.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>

#include "../includes/lib_socket.h"

#define BUF_LEN 1024

#define HANDLE_ERR_AND_RETURN(msg) \
    do                             \
    {                              \
        syslog(LOG_ERR, msg);      \
        return -1;                 \
    } while (0)

/**
 * @brief función auxiliar para cambiar el modo de bloqueo o no bloqueo
 * de un socket
 * 
 * @param socket descriptor del socket 
 * @param is_blocking 1 para que sea bloqueante, 0 para que no lo sea
 */
void set_blocking_mode(int socket, int is_blocking)
{
    const int flags = fcntl(socket, F_GETFL, 0);
    if ((flags & O_NONBLOCK) && !is_blocking) 
        return ; 
        
    if (!(flags & O_NONBLOCK) && is_blocking) 
        return ; 

    fcntl(socket, F_SETFL, is_blocking ? flags ^ O_NONBLOCK : flags | O_NONBLOCK);
}

int sckt_set_rcvtimeout(int sockval, time_t seconds, suseconds_t useconds)
{
    struct timeval timeout = {
        .tv_sec = seconds,
        .tv_usec = useconds};
    if (setsockopt(sockval, SOL_SOCKET, SO_RCVTIMEO, &timeout, sizeof(timeout)) < 0)
    {
        HANDLE_ERR_AND_RETURN("Error setting timeout");
    }
    return 0;
}

int sckt_create_tcp_socket(uint16_t in_port, int max_queue_len)
{
    int sockval;
    struct sockaddr_in addr;

    if ((sockval = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        HANDLE_ERR_AND_RETURN("Error creating socket");
    }

    syslog(LOG_INFO, "Setting socket options");
    /* Hacerlo reutilizable y añadiendo el timeout */
    if (setsockopt(sockval, SOL_SOCKET, SO_REUSEADDR, &(int){1}, sizeof(int)) < 0)
    {
        HANDLE_ERR_AND_RETURN("Error setting reusable socket");
    }


    addr.sin_family = AF_INET;
    addr.sin_port = htons(in_port);
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    bzero((void *)&(addr.sin_zero), 8);

    syslog(LOG_INFO, "Binding socket");
    if (bind(sockval, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    {
        HANDLE_ERR_AND_RETURN("Error binding socket");
    }

    syslog(LOG_INFO, "Listening connections");
    if (listen(sockval, max_queue_len) < 0)
    {
        HANDLE_ERR_AND_RETURN("Error listenining");
    }

    return sockval;
}

int sckt_accept(int sockval)
{
    int desc;
    socklen_t len;
    struct sockaddr connection;
    char errbuf[BUF_LEN];

    len = sizeof(connection);

    while ((desc = accept(sockval, &connection, &len)) < 0)
    {
        if(errno == EAGAIN || errno == EWOULDBLOCK)
        {   // esto evita que el accept salga incontables veces porque el socket no es bloqueante:
            set_blocking_mode(sockval,1);
        }
        else
        {
            strerror_r(errno, errbuf, BUF_LEN);
            syslog(LOG_ERR, "accept: %s", errbuf);
            return -1;
        }
        
    }

    return desc;
}

int sckt_close(int socket)
{
    syslog(LOG_INFO, "Closing socket");
    shutdown(socket, SHUT_RDWR);
    return close(socket);
}
