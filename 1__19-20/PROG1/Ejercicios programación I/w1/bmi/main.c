
/* 
 * File:   main.c
 * Author: pablocuesta
 * Body mass index
 * Created on 10 de septiembre de 2019, 12:07
 */

#include <stdio.h>

int main() {
    double height, weight;
    double bmi;
    
    printf("Enter your height in cm: \n");
    scanf("%lf", &height);
    
    printf("Enter your weight in kg: \n");
    scanf("%lf", &weight);
    
    bmi=10000*weight/(height*height);
    
    printf("Your body mass index is %.2lf", bmi);
    
    return 0;
}

