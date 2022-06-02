
/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 24 de septiembre de 2019, 11:07
 */

#include <stdio.h>
#include <math.h>

int main() {
    int x[3],y[3],z[3];
    
    printf("Enter the first component of the first vector: ");
    scanf("%d",&x[0]);
    printf("Enter the second component of the first vector: ");
    scanf("%d",&x[1]);
    printf("Enter the third component of the first vector: ");
    scanf("%d",&x[2]);
    printf("Enter the first component of the second vector: ");
    scanf("%d",&y[0]);
    printf("Enter the second component of the second vector: ");
    scanf("%d",&y[1]);
    printf("Enter the third component of the second vector: ");
    scanf("%d",&y[2]);
    
    z[0]=x[0]+y[0];
    z[1]=x[1]+y[1];
    z[2]=x[2]+y[2];    
    
    printf("The sum vector is: (%d, %d, %d)\n",z[0],z[1],z[2]);
    printf("The norm of the sum is: %lg",sqrt(z[0]*z[0]+z[1]*z[1]+z[2]*z[2]));
    
    
    return 0;
}

