/**
 *
 * Descripcion: Implementation of sorting functions
 *
 * Fichero: sorting.c
 * Autor: Pabo Cuesta Sierra
 * Version: 1.0
 * Fecha: 2020/10/14
 *
 */


#include "sorting.h"


/**
 * The following function swaps values of the two pointers:
*/
void swap(int *a, int *b){
    int aux = *(a);
    (*(a))=(*(b));
    (*(b))=aux;
}

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

/***************************************************/
/* Function: MergeSort    Date:    2020/10/21      */
/* Implementation of the algorithm Mergesort to    */
/* ip: first index of the array                    */
/* iu: last index of the array                     */
/***************************************************/

int mergesort(int *table, int ip, int iu){

  int im, count=0, ret=OK;

  if(!table||ip>iu)
    return ERR;
  
  if(ip==iu)
    return OK; 
  
  im=(ip+iu)/2;

  if((ret=mergesort(table,ip,im))!=ERR){
    count+=ret;
    if((ret=mergesort(table,im+1,iu))!=ERR){
      count+=ret;
      if((ret=merge(table,ip,iu,im))!=ERR){
        count+=ret;
        return count;
      }
    }
  }
  return ERR;
}

/***************************************************/
/* Function: Merge        Date:     2020/10/21     */
/* Merges two ordered tables into one.             */
/*                                                 */
/* ip: first index of the array                    */
/* iu: last index of the array                     */
/* imiddle: index of the                           */
/***************************************************/

int merge(int *table, int ip, int iu, int imiddle){

  int* taux=NULL;
  int i,j,k,count=0;

  if(!table||ip>imiddle||imiddle>iu)
    return ERR;

  if(!(taux=(int*)calloc(iu-ip+1,sizeof(int))))
    return ERR;
  
  for(i=ip,j=imiddle+1,k=0;i<=imiddle && j<=iu;k++,count++){
    if(table[i]<table[j]){
      taux[k]=table[i];
      i++;
    }else{
      taux[k]=table[j];
      j++;
    }
  }

  if(i>imiddle){
    while(j<=iu){
      taux[k]=table[j];
      j++;
      k++;
    }
  }else{
    while (i<= imiddle){
      taux[k] = table[i];
      i++;
      k++;
    }
  }

  for(i=ip,k=0;i<=iu;i++,k++){
    table[i]=taux[k];
  }

  free(taux);
  return count;
}


/**
 * Function: QuickSort        Date: 2020-10-21
 * 
 * This routine returns ERR in case of error 
 * or the number of basic operations in case 
 * the table is ordered rightly, where table
 * is the table to sort, ip is the first element 
 * of the table and iu is the last element of the table.
 * 
 * functions: partition and median 
 * are used to implement this function
******************************************************/
int quicksort(int* table, int ip, int iu){
    int im, count=0, ret=OK;
    if(ip > iu || table==NULL){
        return ERR;
    }else if(ip==iu){
        return OK;
    }else{
        count=partition(table, ip, iu, &im);
        if(ip < im-1){
            if((ret=quicksort(table, ip, im-1))==ERR){
                return ERR;
            }
            count+=ret;
        }
        if(im+1 < iu){
            if((ret=quicksort(table, im+1, iu))==ERR){
                return ERR;
            }
            count+=ret;
        }
    }
    return count;
}
int partition(int* table, int ip, int iu, int *pos){
    int im, key, i, count=0;

    median(table, ip, iu, &im);

    key=table[im];

    swap(table+ip, table+im);

    im=ip;

    for(i=ip+1;i<=iu;i++){
        count++;
        if(table[i]<key){
            im++;
            if(i!=im){
                swap(table+i, table+im);
            }
        }
    }
    swap(table+ip, table+im);

    (*(pos))=im;

    return count;
}
/**
 * @brief median
 * This function selects the first index and 
 * assigns it to the value pointed by pos
 * */
int median(int *table, int ip, int iu,int *pos){
    (*(pos))=ip;
    return 0;
}
/**
 * QuickSort implementation with the 
 * removal of the tail recursion
*/
int quicksort_ntr(int* table, int ip, int iu){
    int im, count=0, ret=OK;
    
    if(ip > iu || table==NULL){
        return ERR;
    }
    if(ip == iu){
        return OK;
    }
    while(ip<iu){
        count+=partition(table, ip, iu, &im);
        /*partition will not return ERR*/
        if(ip < im-1){
            if((ret=quicksort_ntr(table, ip, im-1))==ERR){
                return ERR;
            }
            count+=ret;
        }
        ip=im+1;
    }
    return count;
}