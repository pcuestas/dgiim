
/* 
 * File:   main.c
 * Author: Usuario
 * Pablo Cuesta
 * Created on 13 de septiembre de 2019, 17:46
 */

#include <stdio.h>

int main(void) {
    char e,c,d;

    printf("Please enter a number (between 1 and 5): ");
    scanf("%hhu",&e);

    printf("Please enter a character: ");
    scanf("\n%c",&c);

    d=e+c;

    printf("\nThe character \'%c\' with ASCII code %hhu,",c,c);
    printf(" if it is encrypted with the number %hhu, ",e);
    printf("would become the character \'%c\' with the ASCII code %hhu.",d,d);



  return 0;
}
