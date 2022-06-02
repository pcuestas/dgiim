/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 21 de septiembre de 2019, 16:40
 */

#include <stdio.h>

int main() {
    int a,b,c;

    printf("Enter three integer numbers, separated by spaces: ");
	scanf("%d %d %d",&a,&b,&c);

	if (a<=b && a<=c) {
		printf("Of the three, %d is the smallest.",a);
	} else if (b<=a && b<=c) {
		printf("Of the three, %d is the smallest.",b);
	} else {
		printf("Of the three, %d is the smallest.",c);
	}

  return 0;
}