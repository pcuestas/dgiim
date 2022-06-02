#include "../includes/server_thread.h"
#include "../includes/lib_socket.h"
#include "../includes/server_configuration.h"

#include <signal.h>
#include <unistd.h>
#include <confuse.h>
#include <syslog.h>
#include <sys/types.h>
#include <sys/stat.h>

/***************************************************************/
/* Variables globales de los threads, incluido el padre        */
extern Thread *thrds;  /* array de threads 
                        * calloc'ed por el server*/
extern pid_t server_pid;       /*el pid del server*/
extern int listenfd; 
extern pthread_mutex_t mlock;  /*mutex para el accept*/
extern long int nthrds;             /*num. de threads*/
/***************************************************************/


static volatile sig_atomic_t got_sigint = 0;

void handler_sigint(int sig)
{
    got_sigint = 1;
}

/**
 * @brief el servidor abre socket, lanza hilos y espera a 
 * que le manden señal de terminar. Cuando recibe dicha señal, 
 * termina todos los hilos y libera todos los recursos que 
 * haya reservado/abierto
 * 
 * @param listen_port puerto de escucha de peticiones
 * @param max_clients max. núm. de clientes 
 * @return int -1 en caso de error. 0 en caso contrario
 */
int server_provide_service(long int listen_port, long int max_clients)
{
    server_pid = getpid();
    
    listenfd = sckt_create_tcp_socket(listen_port, max_clients);
    if(listenfd == -1) 
        return -1;

    if(pthread_mutex_init(&mlock, NULL))
    {
        sckt_close(listenfd);
        return -1;
    }

    if(   set_signal_handler(SIGINT, handler_sigint) 
       || set_signal_handler(SIGPIPE, SIG_IGN)  
       || create_threads())
    {
        pthread_mutex_destroy(&mlock);
        sckt_close(listenfd);
        return -1;
    }

    while(!got_sigint) 
        pause();

    end_threads();
    pthread_mutex_destroy(&mlock);
    return sckt_close(listenfd);
}

/**
 * @brief demoniza el servidor
 * 
 */
void do_daemon()
{
    pid_t pid = fork();
    if (pid < 0 ) exit(EXIT_FAILURE);
    if (pid > 0 ) exit(EXIT_SUCCESS);

    umask(0);                       /* Change the file mode mask*/
    setlogmask(LOG_UPTO(LOG_INFO)); /* Open logs here */
    openlog("Server system messages:", LOG_CONS | LOG_PID | LOG_NDELAY, LOG_LOCAL3);
    syslog(LOG_ERR, "Initiating new server.");
    if (setsid() < 0)
    { /* Create a new SID for the child process */
        syslog(LOG_ERR, "Error creating a new SID for the child process.");
        exit(EXIT_FAILURE);
    }

    syslog(LOG_INFO, "Closing standard file descriptors");
    close(STDIN_FILENO);
    close(STDOUT_FILENO);
    close(STDERR_FILENO);
    return;
}


int main(int argc, char **argv )
{
    long int max_clients, listen_port;

    do_daemon(); // comentar esto para evitar que se lance como un demonio

    scfg_configure(&max_clients,&listen_port);

    server_provide_service(listen_port, max_clients);

    scfg_free(); /*liberar variables de la configuración del servidor*/

    syslog(LOG_INFO, "Server terminates");
    return 0;
}