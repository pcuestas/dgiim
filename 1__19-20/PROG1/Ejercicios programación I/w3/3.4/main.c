/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 17 de septiembre de 2019, 11:50
 */

#include <stdio.h>

int main() {
    int age;
    printf("Enter your age: ");
    scanf("%d",&age);
    
    if (age<18){
        printf("You are underage.\n");
    } 
    else if (age%3) {
        printf("You are of age and your age is not a multiple of three.\n");
    }
    else{
         printf("You are of age and your age is a multiple of three.\n");
    }
    
    return 0;
}
