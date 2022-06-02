/**
 *
 * Descripcion: Implementation of function that generate permutations
 *
 * File: permutations.c
 * Autor: Carlos Aguirre
 * Version: 1.0
 * Fecha: 21-09-2019
 * 
 */


#include "permutations.h"




/***************************************************/
/* Function: random_num Date:   2020/10/16         */
/* Authors: Álvaro Zamanillo and Pablo Cuesta      */
/*                                                 */
/* Rutine that generates a random number           */
/* between two given numbers                       */
/*                                                 */
/* Input:                                          */
/* int inf: lower limit                            */
/* int sup: upper limit                            */
/* Output:                                         */
/* int: random number                              */
/***************************************************/
int random_num(int inf, int sup){ 
 double t;
 
 if(inf>sup){
  return ERR;
 } 
  
 t=(double)rand() / ((double)RAND_MAX+1.);
 
 return inf+((int)(t*(sup-inf+1)));
}

/***************************************************/
/* Function: generate_perm Date:  2020/09/23       */
/* Authors: Álvaro Zamanillo and Pablo Cuesta      */
/*                                                 */
/* Rutine that generates a random permutation      */
/*                                                 */
/* Input: Pablo Cuesta, Álvaro Zamanillo           */
/* int n: number of elements in the permutation    */
/* Output:                                         */
/* int *: pointer to integer array                 */
/* that contains the permitation                   */
/* or NULL in case of error                        */
/***************************************************/


int* generate_perm(int N){

  int *perm=NULL;
  int i,temp, num;

  if(N<=0)
    return NULL;

  if(!(perm=(int*)calloc(N,sizeof(int))))
    return NULL;
  
  for(i=0;i<N;i++){
    perm[i]=i+1;
  }

  for(i=0;i<N;i++){
    num = aleat_lin_num(i,N-1);
    temp=perm[i];
    perm[i]=perm[num];
    perm[num]=temp;
  }

  return perm;
}

/***************************************************/
/* Function: generate_permutations Date: 2020/09/23*/
/* Authors: Pablo Cuesta, Álvaro Zamanillo         */
/*                                                 */
/* Function that generates n_perms random          */
/* permutations with N elements                    */
/*                                                 */
/* Input:                                          */
/* int n_perms: Number of permutations             */
/* int N: Number of elements in each permutation   */
/* Output:                                         */
/* int**: Array of pointers to integer that point  */
/* to each of the permutations                     */
/* NULL en case of error                           */
/***************************************************/


int** generate_permutations(int n_perms, int N)
{

  int **perm=NULL;
  int i,j;

  if(n_perms<=0||N<=0)
    return NULL;

  if(!(perm=(int**)calloc(n_perms,sizeof(int*))))
    return NULL;
  
  for(i=0;i<n_perms;i++){
    perm[i]=generate_perm(N);
    if(!perm[i]){
      for(j=0;j<i;j++)
        free(perm[j]);
      free(perm);
      return NULL;
    }
  }
  
  return perm; 
}
  
  

int aleat_lin_num(int inf,int sup){
    int x,y,i,test=1;
    i=0;
    while(test && i<10){
        i++;
        x=random_num(inf,sup);
        y=random_num(inf,sup);
        if(y<=x)
            test=0;
    }
    return x;
}

int* generate_perm_lin(int N){

  int *perm=NULL;
  int i;

  if(N<=0)
    return NULL;

  if(!(perm=(int*)calloc(N,sizeof(int))))
    return NULL;
  
  for(i=0;i<N;i++){
    perm[i]=aleat_lin_num(1,N);
  }

  return perm;
}


int** generate_permutations_lin(int n_perms, int N)
{

  int **perm=NULL;
  int i,j;

  if(n_perms<=0||N<=0)
    return NULL;

  if(!(perm=(int**)calloc(n_perms,sizeof(int*))))
    return NULL;
  
  for(i=0;i<n_perms;i++){
    perm[i]=generate_perm_lin(N);
    if(!perm[i]){
      for(j=0;j<i;j++)
        free(perm[j]);
      free(perm);
      return NULL;
    }
  }
  
  return perm; 
}
  