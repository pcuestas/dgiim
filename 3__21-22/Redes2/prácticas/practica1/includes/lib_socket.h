#ifndef _LIB_SOCKET_H_
#define _LIB_SOCKET_H_

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

/**
 * @brief establece timeout en la recepción del socket de tiempo
 * determinado por la suma de los segundos y los microsegundos
 *
 * @param sockval socket
 * @param seconds segundos del timoeut
 * @param useconds microsegundos del timeout
 * @return -1 en caso de error. 0 en otro caso
 */
int sckt_set_rcvtimeout(int sockval, time_t seconds, suseconds_t useconds);

/**
 * @brief abre un socket TCP llamando a las funciones
 * socket, bind, listen.
 *
 * @param in_port puerto de recepción
 * @param max_queue_len maximum length to which the  queue
 *        of  pending  connections for the socket may grow
 *
 * @return el descriptor de socket o -1 en caso de error
 */
int sckt_create_tcp_socket(uint16_t in_port, int max_queue_len);

/**
 * @brief acepta una conexión a un socket y crea un nuevo socket para
 * procesar la peticiones del cliente. Si el socket no es bloqueante lo 
 * pone en modo bloqueante. Por tanto, esta función siempre es bloqueante.
 *
 * @param sockval descriptor del socket al que se conectan los clientes
 *
 * @return descriptor del socket creado para atender las peticiones del cliente
 * o -1 en caso de error
 */
int sckt_accept(int sockval);

/**
 * @brief cierra un socket
 * @return 0 en caso de éxito, -1 en caso de error
 */
int sckt_close(int sockval);

#endif