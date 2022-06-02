#include "../includes/server_thread.h"
#include "../includes/lib_socket.h"
#include "../includes/server_sck_interaction.h"
#include "../includes/server_configuration.h"
#include <syslog.h>
/***************************************************************/
/* Variables globales de los threads, incluido el padre        */
extern Thread *thrds;          /* array de threads */
extern pid_t server_pid;       /*el pid del server*/
extern int listenfd; 
extern pthread_mutex_t mlock;  /*mutex para el accept*/
extern long int nthrds;             /*num. de threads*/
/***************************************************************/

static volatile sig_atomic_t got_sigusr1 = 0;

#define LOG_INFO_AND_RETURN(msg, ret) \
    do                                \
    {                                 \
        syslog(LOG_INFO, (msg));      \
        return (ret);                 \
    } while (0);

/**
 * @brief sigusr1 handler
 * 
 * @param sig 
 */
static void handler_sigusr1(int sig)
{
    pthread_exit(0);
}

/**
 * @brief función llamada por los threads para procesar una request
 * 
 * @param sock socket donde se recibe la respuesta
 * @return int -1 en caso de error, 0 en otro caso
 */
static int thread_request_process(int sock)
{
    return ssk_request_process(sock);
}

/**
 * @brief función principal de cada hilo
 * 
 * @param i el índice de este hilo en el 
 * array thrds (se castea a int) 
 * @return NULL por defecto
 */
void *thread_main(void* i)
{
    long tindex = (long)i;

    set_signal_handler(SIGUSR1, handler_sigusr1);
    
    syslog(LOG_INFO,"Thread %ld begins",tindex);
    
    for ( ; ; ) /*hasta ser parado por el padre con sigusr1*/
    {
        pthread_mutex_lock(&mlock);
        thrds[tindex].sockval = sckt_accept(listenfd);
        pthread_mutex_unlock(&mlock);
        
        thread_request_process(thrds[tindex].sockval);
        syslog(LOG_INFO,"Thread %ld answered a request",tindex);
        
        sckt_close(thrds[tindex].sockval);
        thrds[tindex].sockval = -1;
    }
    return NULL;
}

/**********CREATE AND END THREADS*****************************/
 
/**
 * @brief Crea el i-ésimo thread
 * 
 * @param i 
 * @return 0 en caso de éxito. En caso contrario, los 
 * mismos errores que pthread_create
 */
static int create_thread(long i)
{
    thrds[i].sockval = -1;
    return pthread_create(
        &(thrds[i].tid), NULL,
        &thread_main, (void *)i);
}

/**
 * @brief crea los threads. Aloca memoria en el puntero thrds.
 * 
 * @return -1 en caso de error (en este caso libera memoria).
 *          0 en otro caso
 */
int create_threads()
{
    long i;
    int ret;

    if((thrds = calloc(nthrds, sizeof(Thread))) == NULL)
        return -1;
    
    for (i = 0; i < nthrds; i++)
    {
        if ((ret = create_thread(i)))
        {
            free(thrds);
            return ret;
        }
    }
    return 0;
}

/**
 * @brief termina todos los hilos, mandando una señal y 
 * esperando (pthread_join) su finalización
 * 
 * @return 0 en caso de éxito
 */
int end_threads()
{
    int i;
    void *ret;

    for (i = 0; i < nthrds; i++)
    {
        pthread_kill(thrds[i].tid, SIGUSR1);
        pthread_join(thrds[i].tid, &ret);
        if(thrds[i].sockval >= 0) 
            sckt_close(thrds[i].sockval);
    }
    free(thrds);
    return 0;
}

/******************SET SIGNAL HANDLER*****************************/

/**
 * @brief Establece un handler a una señal 
 * 
 * @param sig señal
 * @param sig_handler manejador 
 * @return int 0 en caso de éxito, -1 en otro caso
 */
int set_signal_handler(int sig, void sig_handler(int))
{
    struct sigaction act;

    sigemptyset(&(act.sa_mask));   
    act.sa_handler = sig_handler;
    act.sa_flags = 0;

    if (sigaction(sig, &act, NULL) < 0)
    {
        LOG_INFO_AND_RETURN("sigaction",-1);
    }
    return 0;
}