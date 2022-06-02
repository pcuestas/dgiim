/* 
 * File:   main.c
 * Author: temporal
 * average
 * Created on 10 de septiembre de 2019, 14:25
 */

#include <stdio.h>


int main() {
    double P1, P2, P3, P4, P5, C;
    
    printf("Insert your grade in the first partial exam: ");
    scanf("%lf",&P1);
    
    printf("Insert your grade in the second partial exam: ");
    scanf("%lf",&P2);
    
    printf("Insert your grade in the third partial exam: ");
    scanf("%lf",&P3);
    
    printf("Insert your grade in the fourth partial exam: ");
    scanf("%lf",&P4);
    
    printf("Insert your grade in the fifth partial exam: ");
    scanf("%lf",&P5);
    
    C = (P1 + 2*P2 + 2*P3 + 2*P4 + 3 * P5 ) / 10;
    
    printf("Your final grade is %.2lf",C);
    return 0;
}

