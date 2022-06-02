/*/*
 * File:   main.c
 * Author: Usuario
 *
 * Created on 17 de septiembre de 2019, 11:13
 */

#include <stdio.h>

int main() {
    int age, condition;
    
    printf("Enter your age: ");
    scanf("%d",&age);
    
    condition = (age >= 18) || (age%3 != 0);
    
    printf("You are of age or your age is not a multiple of 3: ");
    printf("%d",condition);
    
    
    return 0;
}
