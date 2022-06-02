/**
 *
 * Description: Implementation of functions for search
 *
 * File: search.c
 * Author: Carlos Aguirre and Javier Sanz-Cruzado
 * Version: 1.0
 * Date: 14-11-2016
 *
 */

#include "search.h"

#include <stdlib.h>
#include <math.h>

#define MIN(a, b) ((a)<(b))?(a):(b)

/**
 *  Key generation functions
 *
 *  Description: Receives the number of keys to generate in the n_keys
 *               parameter. The generated keys go from 1 to max. The
 * 				 keys are returned in the keys parameter which must be 
 *				 allocated externally to the function.
 */
  
/**
 *  Function: uniform_key_generator
 *               This function generates all keys from 1 to max in a sequential
 *               manner. If n_keys == max, each key will just be generated once.
 */
void uniform_key_generator(int *keys, int n_keys, int max)
{
  int i;

  for(i = 0; i < n_keys; i++) keys[i] = 1 + (i % max);

  return;
}

/**
 *  Function: potential_key_generator
 *               This function generates keys following an approximately
 *               potential distribution. The smaller values are much more 
 *               likely than the bigger ones. Value 1 has a 50%
 *               probability, value 2 a 17%, value 3 the 9%, etc.
 */
void potential_key_generator(int *keys, int n_keys, int max)
{
  int i;

  for(i = 0; i < n_keys; i++) 
  {
    keys[i] = .5+max/(1 + max*((double)rand()/(RAND_MAX)));
  }

  return;
}


PDICT init_dictionary (int size, char order)
{
	PDICT pd=NULL;
    /*arguments check*/
    if((order!=SORTED && order!=NOT_SORTED)||(size<=0)){
        return NULL;
    }
    /*memory allocation of the dictionary*/
    if(!(pd=(PDICT)calloc(1, sizeof(DICT)))){
        return NULL;
    }
    /*memory allocation of the table that contains the data*/
    if(!(pd->table=(int*)calloc(size, sizeof(int)))){
        free(pd);
        return NULL;
    }
    /*initialize the data of the structure*/
    pd->size = size ;
    pd->n_data = 0 ;
    pd->order = order ;

    return pd;
}

void free_dictionary(PDICT pdict)
{
	if(pdict!=NULL){
        free(pdict->table);
        free(pdict);
    }
    return ;
}

/*Auxiliary function to resize the table of a dictionary to the new_size */
int resize_dictionary(PDICT pdict, int new_size){  
    int *aux = NULL;

    if(pdict==NULL){
        return ERR;
    }

    if(new_size<=(pdict->size)){
        return OK; /*no need to realloc*/
    }

    if((aux = (int *)realloc(pdict->table, new_size))==NULL){
        /*there is an error, no memory left, but the 
        data is not lost because we used an auxiliary pointer*/
        return ERR; 
    }else{
        pdict->size = new_size ;
        pdict->table = aux;
        return OK;
    }
}

int insert_dictionary(PDICT pdict, int key){
    int j = 0, BO = 0;

	if(pdict==NULL)
        return ERR;
    
    /*resize if needed*/
    if((pdict->size)<(pdict->n_data + 1)){
        if(resize_dictionary(pdict, (int)(2*(pdict->size)))==ERR){
            /*no data was lost, but there is not memory enough to insert a new key*/
            return ERR;
        }
    }
    if(pdict->order==NOT_SORTED){
        /*insert at the end of the table*/
        pdict->table[pdict->n_data] = key;
        (pdict->n_data)++;
    }else{
        j = pdict->n_data;
        /*add it at the end if the table */
        pdict->table[j] = key;
        (pdict->n_data)++;
        j--;
        /*insert the key in its place: */
        while(j>=0 && pdict->table[j]>key){
            pdict->table[j+1] = pdict->table[j];
            j--;
        }
        BO=MIN((pdict->n_data)-j, (pdict->n_data)-1);
        /**this is the number of KC comparisons made:MIN
         * used in case j==0, because there are only
         * (pdict->n_data) elements, so the key can be
         * compared only to (pdict->n_data)-1
         **/
        pdict->table[j+1] = key;
    }
    return BO;
}

int massive_insertion_dictionary (PDICT pdict,int *keys, int n_keys){   
    int i=0, ret=OK, BO=0;
    
	if(!pdict||!keys||n_keys<=0)
        return ERR;
    
    for(i=0; i<n_keys && ret!=ERR; i++){
        ret=insert_dictionary(pdict,keys[i]);
        BO+=ret;
    }

    if(ret==ERR)
        return ERR;
    
    return BO;
}

int search_dictionary(PDICT pdict, int key, int *ppos, pfunc_search method){   

    if(!method)
        return ERR;
    
    return method(pdict->table, 0, (pdict->n_data)- 1, key, ppos);
}


/* Search functions of the Dictionary ADT */
int bin_search(int *table,int F,int L,int key, int *ppos){   
    int M=0, BO=0;

    (*ppos)=NOT_FOUND;

	if(!table||!ppos||F<0){
        return ERR;
    }

    /*non recursive*/

    while(F<=L){
        M=(F+L)/2;
        BO++;
        if(table[M]<key){
            F=M+1;
        }
        else if(table[M]>key){
            BO++;
            L=M-1;
        }
        else{
            BO++;
            (*ppos)=M;
            break;
        }
    }
    
    return BO; 
}

int lin_search(int *table, int F, int L, int key, int *ppos){
    int i, bo = 0;

    if (!table || !ppos || F > L || F < 0)
        return ERR;

    for (i = F; i <= L; i++){
        bo++;
        if (table[i] == key){
            *ppos = i;
            return bo;
        }
    }
    (*ppos)=NOT_FOUND;

    return bo;
}

int lin_auto_search(int *table, int F, int L, int key, int *ppos)
{
    int i, bo = 0;

    if (!table || !ppos || F > L || F < 0)
        return ERR;

    for (i = F; i <= L; i++){
        bo++;
        if (table[i] == key){
            if (i != F){
                table[i] = table[i - 1];
                table[i - 1] = key;
                *ppos = i - 1;
            }
            else{
                *ppos = i; /*We return the position where the element ends after the searc*/
            }

            return bo;
        }
    }

    *ppos=NOT_FOUND;
    return bo;
}
