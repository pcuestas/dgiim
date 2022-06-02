
/* 
 * File:   main.c
 * Author: Usuario
 * Pablo Cuesta
 * Created on 13 de septiembre de 2019, 9:38
 */

#include <stdio.h>


int main() {
    char character, integer, result;
    printf("Type a character: ");
    scanf("%c", &character);
    printf("Type an integer: ");
    scanf("%hhu", &integer);
    
    result=character+integer;  
    
    printf("The ASCII numeric code for the typed character is %hhu\n", character);
    printf("The character obtained by adding the integer to the typed character ");
    printf("is \"%c\"\n", result);
    printf("Its ASCII numeric code is %hhu", result);
            
    return 0;
}

