#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <unistd.h>

/* manejador : rutina de tratamiento de la señal SIGINT . */
void manejador(int sig) {
    printf("Señal número %d recibida \n", sig);
    fflush(stdout);
}

int main(void) {
    struct sigaction act;

    act.sa_handler = manejador;
    sigemptyset(&(act.sa_mask));
    act.sa_flags = 0;

    int i;
    // capturamos todas las señales de [1..31]
    for(i=1;i<31;i++){
        if (sigaction(i, &act, NULL) < 0) {
            printf("%d",i);
            perror("sigaction");
            exit(EXIT_FAILURE);
        }
    }

    while(1) {
        printf("En espera de Ctrl+C (PID = %d)\n", getpid());
        sleep(9999);
    }
}
