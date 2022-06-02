
/* 
 * File:   main.c
 * Author: Usuario
 * Pablo Cuesta
 * Created on 13 de septiembre de 2019, 16:09
 */

#include <stdio.h>

int main() {
    int age, max;
    double upper, lower;
    printf("Type your age: ");
    scanf("%d", &age);

    max = 220 - age;
    lower = max*0.65;
    upper = max*0.85;

    printf("Your cardiovascular range is between %.0lf and %.0lf.", lower, upper);
    
   
    return 0;
}


