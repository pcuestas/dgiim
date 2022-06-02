/* 
 * File: main_list.c
 * Author: PABLO CUESTA SIERRA
 *
 * Created on 23 de marzo de 2020, 12:58
 */
 
#include <stdio.h>
#include <stdlib.h>

#include "list.h"
#include "types.h"


void free_all_memory(List* l1, List* l2, List* l3);

void int_free(void *i);

void* int_copy(const void* i);

int int_print(FILE* f, const void*i);

int main(int argc, char **argv){
    List *l1=NULL, *l2=NULL, *l3=NULL, *aux=NULL;
    Status st=OK;
    int i;
    int *element=NULL;
    
    
    /*list 1:*/
    fprintf(stdout, "Creating list 1:... ");
    
    if(!(l1=list_new(int_free, int_copy, int_print))) {
        free_all_memory(l1, l2, l3);
        return EXIT_FAILURE;
    }
    
    fprintf(stdout, "Size = %d\n", list_size(l1));
    
    fprintf(stdout, "Inserting elements into list...\n");
    
    for(i=0; i<=10 && st==OK; i+=2)
        st = list_pushFront(l1, &i);
        
    if(st==ERROR){
        free_all_memory(l1, l2, l3);
        return EXIT_FAILURE;
    }
    
    fprintf(stdout, "Printing list 1: \n");
    fprintf(stdout, "Size = %d. Elements: ", list_size(l1));
    list_print(stdout, l1);
    
    /*list 2: */
    fprintf(stdout, "\n\nCreating list 2:... ");
    
    if(!(l2=list_new(int_free, int_copy, int_print))){
        free_all_memory(l1, l2, l3);
        return EXIT_FAILURE;
    }
    
    fprintf(stdout, "Size = %d\n", list_size(l2));
    
    fprintf(stdout, "Inserting elements into list...\n");
    
    for(i=1; i<=9 && st==OK; i+=2)
        st = list_pushFront(l2, &i);
        
    if(st==ERROR){
        free_all_memory(l1, l2, l3);
        return EXIT_FAILURE;
    }
    
    fprintf(stdout, "Printing list 2: \n");
    fprintf(stdout, "Size = %d. Elements: ", list_size(l2));
    list_print(stdout, l2);
    
    /*list 3: */
    fprintf(stdout, "\n\nCreating list 3:... ");
    
    if(!(l3=list_new(int_free, int_copy, int_print))){
        free_all_memory(l1, l2, l3);
        return EXIT_FAILURE;
    }
    
    fprintf(stdout, "Size = %d\n", list_size(l3));
    
    fprintf(stdout, "Combining elements of list 1 and list 2 into list 3: \n");
    
    while((list_isEmpty(l1) == FALSE && list_isEmpty(l2) == FALSE) && st==OK){
        element = list_popFront(l1);
        fprintf(stdout, "Extracted element from list 1: %d\n", *element);
        st = list_pushFront(l3, element);
        free(element);
     
        element = list_popFront(l2);
        fprintf(stdout, "Extracted element from list 2: %d\n", *element);
        if(st==OK) st = list_pushFront(l3, element);
        free(element);
    }
    
    
    if(st==ERROR){
        free_all_memory(l1, l2, l3);
        return EXIT_FAILURE;
    }
    
    /*now one or both of the lists are empty*/
    
    aux = l1;
    i=1;
    
    if(list_isEmpty(l1) == TRUE){
        aux = l2;
        i=2;
    }
    
    /*so we empty the one that is not: */
    while(list_isEmpty(aux)==FALSE && st == OK){
        element = list_popFront(aux);
        fprintf(stdout, "Extracted element from list %d: %d\n", i, *element);
        st = list_pushFront(l3, element);
        free(element);
    }
    
    aux = NULL;

    fprintf(stdout, "\nPrinting list 1: \n");
    fprintf(stdout, "Size = %d. Elements: \n", list_size(l1));
    list_print(stdout, l1);
    
    fprintf(stdout, "\nPrinting list 2: \n");
    fprintf(stdout, "Size = %d. Elements: \n", list_size(l2));
    list_print(stdout, l2);
    
    fprintf(stdout, "\nPrinting list 3: \n");
    fprintf(stdout, "Size = %d. Elements: \n", list_size(l3));
    list_print(stdout, l3);
    
    fprintf(stdout, "\nFreeing memory...\n");
    
    free_all_memory(l1, l2, l3);

    return EXIT_SUCCESS;
}

void free_all_memory(List* l1, List* l2, List* l3){
    if(l1 != NULL){
        list_free(l1);
        l1 = NULL;
    }
    if(l2 != NULL){
        list_free(l2);
        l2 = NULL;
    }
    if(l3 != NULL){
        list_free(l3);
        l3 = NULL;
    }
}

void* int_copy(const void* i){
    int *j=NULL;

    if(!i) return NULL;

    if(!(j=malloc(sizeof(int)))) return NULL;

    (*j) = (*(int*)i);
    return j;
}

void int_free(void *i){
    if(i) free(i);
}

int int_print(FILE* f, const void*i){
    if(!i||!f) return -1;

    return fprintf(stdout, "%d ", *(int*)i);
}

