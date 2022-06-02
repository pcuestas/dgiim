
/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 17 de septiembre de 2019, 11:55
 */

#include <stdio.h>
#include <stdlib.h>


int main() {
    double w,h,bmi;
    
    printf("Type your height in cm: ");
    scanf("%lf",&h);    
    printf("Type your weight in kg: ");
    scanf("\n%lf",&w);
    
    bmi = 10000 * w / (h * h);
    
    if (bmi<21) {
        printf("You are underweight.");
    } else if (bmi<=25) {
        printf("Your weight is normal.");
    } else {
        printf("You are overweight.");
    }
    printf("(bmi = %lg)",bmi);
    
    return 0;
}

