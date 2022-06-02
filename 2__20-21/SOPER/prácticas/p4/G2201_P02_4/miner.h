/**
 * @file miner.h 
 * Fichero proporcionado como parte del enunciado.
 * 
 * Hemos añadido campos a la estructura NetData, 
 * además de los #ifndef, #define, #endif.
 * - Pablo Cuesta Sierra
 * 
 */
#ifndef MINER_H
#define MINER_H

#include <unistd.h>
#include <sys/types.h>
#include <semaphore.h>

#define OK 0
#define MAX_WORKERS 10

#define SHM_NAME_NET "/netdata"
#define SHM_NAME_BLOCK "/block"

#define PRIME 99997669
#define BIG_X 435679812
#define BIG_Y 100001819

#define MAX_MINERS 200


typedef struct _Block {
    int wallets[MAX_MINERS];
    long int target;
    long int solution;
    int id;
    int is_valid;
    struct _Block *next;
    struct _Block *prev;
} Block;

typedef struct _NetData {
    pid_t miners_pid[MAX_MINERS];
    char voting_pool[MAX_MINERS];
    int last_miner;
    int total_miners;
    pid_t monitor_pid;
    pid_t last_winner;

    int num_active_miners; /*número de mineros activos*/
    int num_voters; /*número de votantes*/
    int time_out; /*flag que señaliza que los mineros han finalizado por tiempo agotado*/
    sem_t sem_round_begin;
    sem_t sem_round_end; 
    sem_t sem_scrutiny;
    sem_t sem_votation_done;
    sem_t sem_start_voting;
} NetData;

long int simple_hash(long int number);

void print_blocks(Block * plast_block, int num_wallets);

#endif