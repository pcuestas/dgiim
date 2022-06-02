/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 21 de septiembre de 2019, 16:42
 */

#include <stdio.h>

int main() {
  	unsigned char l;

	printf("Type a letter: ");
	scanf("%c",&l);

	if ((l>='A' && l<='Z')||(l>='a' && l<='z')) {
		
		if (l=='A'||l=='E'||l=='I'||l=='O'||l=='U'||l=='a'||l=='e'||l=='i'||l=='o'||l=='u') {
			printf("'%c' is a vowel.",l);
		}
		else {
			printf("'%c' is not a vowel.",l);
		}
	}
	else {
		printf("You did not type a letter.");
	}

  return 0;
}
