/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 17 de septiembre de 2019, 11:25
 */

#include <stdio.h>

int main() {
    int age, condition;
    
    printf("Enter your age: ");
    scanf("%d",&age);
    
    condition = (age >= 18) && (age%3 != 0);
    
    printf("You are above the legal age and your age is not a multiple of 3: ");
    printf("%d",condition);
    
    
    return 0;
}
