

/* 
 * File:   main.c
 * Author: Usuario
 * Pablo Cuesta
 * Created on 13 de septiembre de 2019, 9:51
 */


#include <stdio.h>

int main() {
    char a, b;
    printf("Type a letter: ");
    scanf("%c",&a);
    
    printf("Type another letter: ");
    scanf("\n%c",&b);
    
    printf("The distance between \'%c\' and \'%c\' is %hhu.",a,b,b-a);
    
   
    return 0;
}

