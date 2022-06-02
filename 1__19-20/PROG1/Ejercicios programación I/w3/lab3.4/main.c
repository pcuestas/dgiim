/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 21 de septiembre de 2019, 16:41
 */

#include <stdio.h>

int main(void) {
	char l;
	printf("Enter a letter: ");
	scanf("\n%c",&l);

	if (l>='A' && l<='Z') {
		l=l+('a'-'A');
		printf("Its lowercase letter is ");
	} 
	else if (l>='a' && l<='z') {
		l=l-('a'-'A');
		printf("Its capital letter is ");
	} 
	else {
		printf("You did not type a letter");
		return 0;
	}
 
	printf("'%c'.",l);
  return 0;
}