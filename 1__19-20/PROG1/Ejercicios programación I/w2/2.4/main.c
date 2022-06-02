
/* 
 * File:   main.c
 * Author: Usuario
 * Pablo Cuesta
 * Created on 13 de septiembre de 2019, 9:10
 */

#include <stdio.h>


int main() {
    
    printf("A variable of type char is %ld bytes\n", sizeof(char));
    printf("A variable of type unsigned char is %ld bytes\n", sizeof(unsigned char));
    printf("A variable of type int is %ld bytes\n", sizeof(int));
    printf("A variable of type unsigned int is %ld bytes\n", sizeof(unsigned int));
    printf("A variable of type short is %ld bytes\n", sizeof(short));
    printf("A variable of type unsigned short is %ld bytes\n", sizeof(unsigned short));
    printf("A variable of type long is %ld bytes\n", sizeof(long));
    printf("A variable of type unsigned long is %ld bytes\n", sizeof(unsigned long));
    printf("A variable of type float is %ld bytes\n", sizeof(float));
    printf("A variable of type double is %ld bytes", sizeof(double));

    
    
    return 0;
}

