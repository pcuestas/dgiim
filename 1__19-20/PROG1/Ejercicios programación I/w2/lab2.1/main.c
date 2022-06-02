
/* 
 * File:   main.c
 * Author: Usuario
 * Pablo Cuesta
 * Created on 13 de septiembre de 2019, 16:37
 */


#include <stdio.h>

int main() {
    char a,b;
    printf("Please, enter a character: ");
    scanf("%c",&a);
    
    printf("Please, enter another character: ");
    scanf("\n%c",&b);

    printf("If we add the ASCII code of the character \'%c\' that is %hhu with the ASCII code of the character \'%c\' that is %hhu we get the number %hhu.",a,a,b,b,a+b);

   return 0;
}


