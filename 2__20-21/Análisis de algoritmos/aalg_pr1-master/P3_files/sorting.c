/**
 *
 * Descripcion: Implementation of sorting functions
 *
 * Fichero: sorting.c
 * Autor: Carlos Aguirre
 * Version: 1.0
 * Fecha: 16-09-2019
 *
 */


#include "sorting.h"

/***************************************************/
/* Function: InsertSort    Date:   2020/10/14      */
/* Implementation of the algorithm InsertSort      */
/* *table: array to be sorted                      */
/* ip: first index of the array                    */
/* iu: last index of the array                     */
/***************************************************/
int InsertSort(int* table, int ip, int iu)
{ int i = ip+1, j, num, count=0;

  if(!table||ip<0||iu<ip)
    return -1;
  
  while (i<=iu){
    num=table[i];

    for(j = i-1;j>=ip && num<table[j]; j--){
      count++;
      table[j+1] = table[j];
    }
    if(j>=ip){
      count++;
    }

    table[j+1]=num;
    i++;
  }

  return count;
}

/***************************************************/
/* Function: InsertSortInv    Date:   2020/10/14   */
/* Implementation of the algorithm InsertSort to   */
/* sort an array in descending order               */
/* *table: array to be sorted                      */
/* ip: first index of the array                    */
/* iu: last index of the array                     */
/***************************************************/
int InsertSortInv(int* table, int ip, int iu)
{ int i = ip+1, j, num, count=0;

  if(!table||ip<0||iu<ip)
    return -1;
  
  while (i<=iu){
    num=table[i];

    for(j = i-1;j>=ip && num>table[j]; j--){
      count++;
      table[j+1] = table[j];
    }
    if(j>=ip){
      count++;
    }

    table[j+1]=num;
    i++;
  }

  return count;
}

