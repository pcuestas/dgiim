#ifndef S_THREAD_H
#define S_THREAD_H

#include <pthread.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>

/**
 * @brief estructura de un hilo
 * 
 */
typedef struct s_thread_ {
    pthread_t tid;
    int sockval;
} Thread;

/***************************************************************/
/* Variables globales para todos los threads, incluido el padre*/
Thread *thrds;  /* array de threads; 
                 * calloc'ed por el server*/
pid_t server_pid;       /*el pid del server*/
int listenfd; 
pthread_mutex_t mlock;  /*mutex para el accept*/
/***************************************************************/

/**
 * @brief crea los threads. Aloca memoria en el puntero thrds
 * 
 * @return -1 en caso de error (en este caso libera memoria).
 *          0 en otro caso
 */
int create_threads();

/**
 * @brief termina todos los hilos, mandando una señal 
 * (pthread_kill) y esperando (pthread_join) su finalización
 * 
 * @return 0 en caso de éxito
 */
int end_threads();

/**
 * @brief función principal de cada hilo
 * 
 * @param i el índice de este hilo en el 
 * array thrds (se castea a int) 
 * @return NULL por defecto
 */
void *thread_main(void *i);

int set_signal_handler(int sig, void sig_handler(int));

#endif /*S_THREAD_H*/