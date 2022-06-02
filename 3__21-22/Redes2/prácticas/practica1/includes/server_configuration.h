#ifndef SERVER_CONFIG_H_
#define SERVER_CONFIG_H_

#define SEC_TIMEOUT 10
#define DEFAULT_PORT 11000
#define CONF_FILE "./build/simple.conf"

/*Variables globales de la configuración del servidor*/
char *server_name;
char *server_root;
long int nthrds;             /*num. de threads*/

/**
 * @brief establece las variables del servidor 
 * con los parámetros del CONF_FILE. Reserva memoria, 
 * liberar al terminar de usar las variables llamando a scfg_free():
 *  - server_name (nombre del servidor)
 *  - server_root (path del directorio root www)
 * 
 * @param max_clients número máximo de clientes en cola
 * @param listen_port puerto del servidor
 */
void scfg_configure(long int *max_clients, long int *listen_port);

/**
 * @brief libera la memoria reservada por scfg_configure()
 * 
 */
void scfg_free();

#endif