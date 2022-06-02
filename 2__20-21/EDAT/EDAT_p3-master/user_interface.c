#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "menu_functions.h"


/**
 * @file menu.c
 * @author Pablo Cuesta and Álvaro Zamanillo
 * @date 25 Oct 2020
 * @brief File containing the requested menu.
 */


int main(){
    int choice=0;
    char tableName[512]="";
    bool entered_table=false;

    do{
        choice = ShowMainMenu();
        switch (choice){
            case 1: {
                if(use(tableName)==false){
                    printf("Error creating table.\n");
                }else{
                    printf("Table entered succesfully\n");
                    entered_table=true;
                }
            }
                break;
            
            case 2:{
                if(entered_table==false){
                    printf("No table selected. Choose 'Use' to select table\n\n");
                }else{
                    /*this function may return false if the entered key is in the able already*/
                    insert(tableName);
                }
            }
                break;
            
            case 3:{
                if (entered_table == false){
                    printf("No table selected. Choose 'Use' to select table\n\n");
                }else{
                    print(tableName);
                }
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