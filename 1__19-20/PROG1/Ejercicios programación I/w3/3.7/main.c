/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 17 de septiembre de 2019, 12:40
 */

#include <stdio.h>


int main() {
    double e,c;
    int o;
    
    printf("Select an option: \n");
    printf(" 1.Dollar\n 2.Yen\n 3.Pound\n");
    scanf("%i",&o);
   
    switch(o){
        case 1: 
            printf("Type the ammount in euros: ");
            scanf("\n%lf",&e);
            c=1.10*e;
            printf("That ammount corresponds to %.2lf dollars.",c);
            break;
            
        case 2:
            printf("Type the ammount in euros: ");
            scanf("\n%lf",&e);
            c=119.20*e;
            printf("That ammount corresponds to %.2lf yens.",c);
            break;
            
        case 3:
            printf("Type the ammount in euros: ");
            scanf("\n%lf",&e);
            c=0.89*e;
            printf("That ammount corresponds to %.2lf pounds.",c);
            break;  
            
        default: printf("You entered an incorrect option. Please enter D, Y or P.");               
    }   
    
    
    return 0;
}

