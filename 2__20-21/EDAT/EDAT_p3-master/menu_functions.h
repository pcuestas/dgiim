#ifndef MENU_FUNCTIONS_H
#define MENU_FUNCTIONS_H

#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <stdio.h>


/**
 * @brief 
*/
int ShowMainMenu();
/**
*@brief The system ask for the name of the table to use and call the CreateTable fucntion
*/
bool use(char *TableName);

/**
 * @brief The system ask for the key and title to store.
 */
bool insert(char *TableName);

/**
 * @brief Shows the binary search tree on screen. 
 */
bool print(char*TableName);



#endif