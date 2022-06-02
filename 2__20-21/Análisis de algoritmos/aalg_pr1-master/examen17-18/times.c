/**
 *
 * Descripcion: Implementation of time measurement functions
 *
 * Fichero: times.c
 * Autor: Carlos Aguirre Maeso
 * Version: 1.0
 * Fecha: 16-09-2019
 *
 */

#include "times.h"
#include "sorting.h"
#include "search.h"

#include <time.h>
#include <errno.h>
#include <sys/time.h>



void int_pp_free(int **p, int  n){
    int i;

    for (i=0;i<n;i++){
        free(p[i]);
    }
    free(p);
}

/***************************************************/
/* Function: average_sorting_time Date: 2020/09/30 */
/* 
 * This function stores the data received from 
 * sorting n_perms permutations of size N
 *
 * method: sorting method to be used
 * n_perms: represents the number of permutations to be generated
 *      and sorted by the used method (in this case insertion method), 
 * N: is the size of each permutation 
 * time: pointer to a type structure TIME AA that at the exit of the function will contain:
 * the number of permutations averaged in the n elems field,
 * the size of the permutations in the N field,
 * the average execution time (in seconds) in the time field,
 * the average number of times the OB was executed in the average ob field,
 * the minimum number of times the OB was executed in the min ob field
 * the maximum number of times the OB was executed in the max ob field
 *
 **************************************************/
short average_sorting_time(pfunc_ordena metodo,int n_perms,int N, PTIME_AA ptime)
{   
/*    struct timeval start, end;
    long micro;
    double elapsed;
*/  
    clock_t begin, end;
    
    int **perms=NULL, i=0, min, max;
    unsigned long c_total=0, c_aux=0;

    if(!metodo||n_perms<=0||N<=0||!ptime){
        return ERR;
    }
    if(!(perms=generate_permutations(n_perms, N))){
        return ERR;
    }
    
    begin=clock();

    for (i=0;i<n_perms && c_aux!=-1;i++){
        c_aux=(unsigned long)metodo(perms[i], 0, N-1);

        if(!i){
            min=max=c_aux;
        }else{
            min = c_aux<min?c_aux:min;
            max = c_aux>max?c_aux:max;
        }
        c_total+=c_aux;
    }
    
    
    end=clock();

    int_pp_free(perms, n_perms);

    if(c_aux==-1){
        return ERR;
    }

    ptime->time = (double)(end-begin)/(double)(CLOCKS_PER_SEC*n_perms);
    ptime->average_ob = (double)c_total/(double)n_perms;
    ptime->max_ob = max;
    ptime->min_ob = min;
    ptime->N = N;
    ptime->n_elems = n_perms;

    return OK;
}


/***************************************************/
/* Function: generate_sorting_times Date: 2020/09/30
 * 
 * this function calls average_sorting_time to test 
 * and then print the information that said function provides 
 * for permutations of sizes from num_min to num_max (+incr)
 * 
 * method: function used to sort
 * file: name of the file where to print the information
 * num_min: minimum size of the permutations
 * num_max: maximum size of the permutations
 * incr: increment of the size
 * n_perms: number of permutations of each size                  
 *
 * ***************************************************/
short generate_sorting_times(pfunc_ordena method, char* file, int num_min, int num_max, 
    int incr, int n_perms)
    {   
    int N, i, n_iter;
    TIME_AA *ptime=NULL;
    
    if(!method||!file||num_min<=0||num_max<=num_min||incr<=0||n_perms<=0)
        return ERR;
    
    n_iter=(num_max-num_min)/incr+1;

    if (!(ptime = calloc(n_iter, sizeof(TIME_AA))))
        return ERR;

    for (i=0, N=num_min; i<n_iter; N=num_min+(i)*incr){
        if(average_sorting_time(method, n_perms, N, ptime+i)==ERR){
            free(ptime);
            return ERR;
        }
        printf("Num %d\n",N);
        i++;
    }
    
    if (save_time_table(file, ptime, n_iter) == ERR){
        free(ptime);
        return ERR;
    }
    free(ptime);
    
    return OK;
 
}


/***************************************************/
/* Function: save_time_table Date:   2020/09/30    
 *
 * This is the function called by generate_sorting_times 
 * to print the information stored in the array ptime      
 * n_times is the size of the array and file, the name 
 * of the file where the data is to be printed
 *                     
 ****************************************************/
short save_time_table(char* file, PTIME_AA ptime, int n_times)
{
   FILE* pf;
   int i;

   if(!ptime||n_times<1||!file)
        return -1;
    
    if(!(pf=fopen(file,"a")))
        return -1;
    
    for(i=0;i<n_times;i++){
        fprintf(pf, "%i\t%.12f\t%f\t%i\t%i\n", ptime[i].N, ptime[i].time,
        ptime[i].average_ob, ptime[i].max_ob,ptime[i].min_ob);
    }

    fclose(pf);

    return OK;
    
}


short generate_search_times(pfunc_search method, pfunc_key_generator generator, int order, char* file, int num_min, int num_max, int incr, int n_times){

    int N, i, n_iter;
    TIME_AA *ptime = NULL;

    if (!method || !file || num_min <= 0 || num_max <= num_min || incr <= 0 || !generator || (order != SORTED && order != NOT_SORTED)||n_times<1)
        return ERR;

    n_iter = (num_max - num_min) / incr + 1;

    if (!(ptime = calloc(n_iter, sizeof(TIME_AA))))
        return ERR;

    for (i = 0, N = num_min; i < n_iter; N = num_min + (i)*incr){
        if (average_search_time(method, generator,order, N, n_times, ptime + i) == ERR){
            free(ptime);
            return ERR;
        }
        
        printf("Num %d\n", N); /*To check that the program is running*/
        i++;
    }

    if (save_time_table(file, ptime, n_iter) == ERR){
        free(ptime);
        return ERR;
    }
    
    free(ptime);

    return OK;
}

short average_search_time(pfunc_search metodo, pfunc_key_generator generator, int order, int N, int n_times, PTIME_AA ptime){

    int *keys,*perm,i,n_keys,ppos,min,max;
    long c_aux=0,c_total=0;
    PDICT dict=NULL;
    clock_t begin, end;


    if(!metodo||!generator||(order!=SORTED && order!=NOT_SORTED)||N<0||!ptime){
        return ERR;
    }
    /*Allocate dictionary, permutations and fill the dictionary*/
    if(!(dict=init_dictionary(N,(char)order)))
        return ERR;
    
    if(!(perm=generate_perm(N))){
        free_dictionary(dict);
        return ERR;
    }

    if(massive_insertion_dictionary(dict,perm,N)==ERR){
        free_dictionary(dict);
        free(perm);
        return ERR;
    }

    /*Allocate keys array and fill it with key_generator function*/
    n_keys=N*n_times;

    if(!(keys=(int*)calloc(n_keys,sizeof(int)))){
        free_dictionary(dict);
        free(perm);
        return ERR;
    }

    generator(keys,n_keys,N);

    begin = clock();
    
    for(i=0;i<n_keys;i++){
        c_aux=metodo(dict->table,0,N-1,keys[i],&ppos);
        
        if(!i){
            min = max = c_aux;
        }else{
            min = c_aux < min ? c_aux : min;
            max = c_aux > max ? c_aux : max;
        }
        c_total += c_aux;
    }
    end = clock();
    
    ptime->time = (double)(end - begin) / (double)(CLOCKS_PER_SEC*n_keys);
    ptime->average_ob = (double)c_total / (double)n_keys;
    ptime->max_ob = max;
    ptime->min_ob = min;
    ptime->N = N;
    ptime->n_elems = n_keys;

    /*Free memory*/
    free_dictionary(dict);
    free(perm);
    free(keys);

    return OK;
}
