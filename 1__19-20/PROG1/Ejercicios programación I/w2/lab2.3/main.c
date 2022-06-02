
/* 
 * File:   main.c
 * Author: Usuario
 * Pablo Cuesta
 * Created on 13 de septiembre de 2019, 17:31
 */


#include <stdio.h>

int main() {

  double height, weight,age;
  char name;

  printf("Please enter the height in cm: ");
  scanf("%lf",&height);
  printf("Please enter the age in years: ");
  scanf("%lf",&age);
  printf("Type the initial of the name: ");
  scanf("\n%c",&name);
  
  weight= height - 100 + (9*age)/100.0;
  
  printf("\nThe ideal weight of %c, who measures %lg cm and is %.0lf years old, is %.3lf kg.",name,height,age,weight);
  
  return 0;
}