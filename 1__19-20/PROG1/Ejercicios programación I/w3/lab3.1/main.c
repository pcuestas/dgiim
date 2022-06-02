/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 21 de septiembre de 2019, 16:37
 */

#include <stdio.h>

int main() {

    int y;

    printf("Enter a year: ");
    scanf("%d",&y);

    if (y%4 != 0) {
        printf ("That is NOT a leap year");
    }

    else if (y%400 !=0){
        printf ("That is NOT a leap year");
    }

    else {
      printf("That IS a leap year.");
    }

  return 0;
}