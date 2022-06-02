#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <stdint.h>

/**
 * @file conc_cycle.c
 * @authors Pablo Cuesta Sierra, Álvaro Zamanillo Sáez
 * 
 * @brief Programa descrito en el ejercicio 10 de la 
 *  práctica 2 (SOPER).
 * 
 */

#define SEM_NAME "/sem_cycles"
#define SECS 10

static volatile sig_atomic_t got_sigusr1 = 0;
static volatile sig_atomic_t got_end = 0;

/**
 * @brief manejador - para cada señal actualiza la 
 *  bandera correspondiente
 */
void manejador(int sig){
    if (sig == SIGUSR1){
        got_sigusr1 = 1;
    }
    else{ /*SIGINT, SIGALRM o SIGTERM*/
        got_end = 1;
    }
}

/**
 * @brief Inicializa la acción que se va a utilizar para 
 *  capturar las señales. Bloquea las señales que 
 *  pueden recibir los procesos durante los ciclos para
 *  evitar que se pierdan. Ya que sigsuspend reestablece
 *  la máscara tras la ejecución del manejador: si no se
 *  bloquean estas señales en el manejador podríamos 
 *  'perderlas' (solo atendemos a una de las variables y el proceso
 *  se bloquea esperando a otra señal cuando queda una sin atender).
 *
 * @param act puntero a la estructura de sigaction que
 *  se va a configurar.
 */


/*NOT            USED*/
void set_act(struct sigaction *act){
    /*bloqueamos las señales dentro del manejador 
    para evitar perderlas*/
    sigemptyset(&(act->sa_mask));
    sigaddset(&(act->sa_mask), SIGALRM);
    sigaddset(&(act->sa_mask), SIGINT);
    sigaddset(&(act->sa_mask), SIGTERM);
    sigaddset(&(act->sa_mask), SIGUSR1);
    
    act->sa_handler = manejador;
    act->sa_flags = 0;
}

/**
 * @brief Wrapper de kill con control de error. 
 * En caso de error, termina el proceso e imprime 
 * el error obtenido.
 *
 * @param pid pid igual que la función kill(2)
 * @param sig sig igual que la función kill(2)
 */
void kill_(pid_t pid, int sig){
    if(kill(pid,sig)==-1){
        perror("kill");
        exit(EXIT_FAILURE);
    }
}

void signal_and_print(sem_t *sem, int cycle_number, pid_t next, pid_t this_pid){
    while(sem_wait(sem) != 0); /*para evitar que devuelva y no sea por el sem*/
    
    kill_(next,SIGUSR1);
    printf("Ciclo: %d (PID=%jd)\n", cycle_number, (intmax_t)this_pid);
    
    sem_post(sem);
}

void cycles(int first_cycle_number, pid_t next_proc, pid_t first_proc, sem_t *sem, sigset_t old_set){
    int i = first_cycle_number;
    int term = 0;
    pid_t this_pid = getpid();

    while( !term ){
        sigsuspend(&old_set);/*bloquea todas las señales menos las que espera cada proceso*/
      
        if (got_sigusr1){
            got_sigusr1 = 0;
            signal_and_print(sem, i, next_proc, this_pid);
            i++;
        }
        else if(got_end){
            got_end = 0;

            if(next_proc != first_proc){
                kill_(next_proc, SIGTERM);
            }
            term = 1;
        }
    }
}

pid_t child(int j, int NUM_PROC, pid_t first_proc, sigset_t old_set, sem_t *sem){
    pid_t nextp;

    if(j < NUM_PROC){ /*si no es el último, crea el siguiente proceso*/
    
        nextp = fork();

        if(nextp < 0){
            perror("fork");
            exit(EXIT_FAILURE);    
        }
        else if(nextp == 0){
            /*función del proceso j+1*/
            child(j+1, NUM_PROC, first_proc, old_set, sem);
        }
    }
    else{/*el último proceso manda señal al primero en los ciclos*/
        nextp = first_proc;
    }

    cycles(0, nextp, first_proc, sem, old_set);

    sem_close(sem);
    wait(NULL);
    exit(EXIT_SUCCESS);
}

int main(int argc, char *argv[]) {
    int NUM_PROC, i, term;
    pid_t nextp, first_proc = getpid(); /*pid del proceso primero*/
    
    struct sigaction act;
    sigset_t mask, old_mask;
    sem_t *sem = NULL;

    if (argc != 2 || (NUM_PROC = atoi(argv[1]))<=1) {
        fprintf(stderr, "Uso:\t%s <NUM_PROC>\n\tCon NUM_PROC > 1\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    if (alarm(SECS)){
        fprintf(stderr, "Existe una alarma previa establecida\n");
    }

    /* inicializar el semáforo a 1 */
    if ((sem = sem_open(SEM_NAME, O_CREAT | O_EXCL, S_IRUSR | S_IWUSR, 1)) == SEM_FAILED) {
		perror("sem_open");
		exit(EXIT_FAILURE);
	}
    sem_unlink(SEM_NAME);

    /*bloquear las señales antes de llamar a sigaction (para evitar perderlas)*/
    sigemptyset(&mask);
    sigaddset(&mask, SIGUSR1);
    sigaddset(&mask, SIGINT);
    sigaddset(&mask, SIGALRM);
    sigaddset(&mask, SIGTERM);
    sigprocmask(SIG_BLOCK, &mask, &old_mask); /* guardamos old_mask para luego usarlo en sigsuspend */

    /*establecer act*/
    //set_act(&act);
    act.sa_mask = mask; /*para que dentro del manejador se bloqueen las señales*/
    act.sa_handler = manejador;
    act.sa_flags = 0;
    
    /*establecer el manejador para las señales que pueden recibir*/
    if (sigaction(SIGUSR1, &act, NULL) < 0) {
        perror("sigaction SIGUSR1");
        exit(EXIT_FAILURE);
    }
    if (sigaction(SIGINT, &act, NULL) < 0) {
        perror("sigaction SIGINT");
        exit(EXIT_FAILURE);
    }
    if (sigaction(SIGALRM, &act, NULL) < 0){
        perror("sigaction SIGALRM");
        exit(EXIT_FAILURE);
    }
    if (sigaction(SIGTERM, &act, NULL) < 0) {
        perror("sigaction SIGTERM");
        exit(EXIT_FAILURE);
    }


    /*creación del proceso 2*/
    if((nextp=fork()) < 0){
        perror("fork");
        exit(EXIT_FAILURE);    
    }else if(nextp == 0){
        /*evitamos que los procesos que no son el 1 reciban SIGINT*/
        sigaddset(&old_mask, SIGINT);
        
        //    sigaddset(&old_mask, SIGALRM);//no hace falta

        child(2, NUM_PROC, first_proc, old_mask, sem); /*función del proceso segundo -- llama a exit()*/
    }
    /* proceso 1 */

        //    sigaddset(&old_mask, SIGTERM);//no hace falta

    signal_and_print(sem, 0, nextp, first_proc);/*ciclo inicial-primera señal a su hijo*/

    cycles(1, nextp, first_proc, sem, old_mask);

    sem_close(sem);
    wait(NULL);
    exit(EXIT_SUCCESS);
}
