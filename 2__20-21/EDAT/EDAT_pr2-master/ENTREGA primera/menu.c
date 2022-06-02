#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "menu_functions.h"




/**
 * @file menu.c
 * @author Pablo Cuest and Álvaro Zamanillo
 * @date 25 Oct 2020
 * @brief File containing the requested menu system with submenus.
 */


int main(void){
    int choice=0;

    do{
        choice = ShowMainMenu();
        switch (choice){
            case 1: {
                ShowProductsMenu();
            }
                break;
            
            case 2:{
                ShowOrdersMenu();
            }
                break;
            
            case 3:{
                ShowCustomersMenu();
            }
                break;

            case 4:{
                printf("\nExiting...\n\n");
            }
                break;
        }
    }while (choice != 4);
    
    return 0;
}
