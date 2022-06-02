/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 18 de septiembre de 2019, 9:15
 */


typedef enum {dollar=1,yen,pound} Currency;

int main() {
    double e,c;
    Currency o;
    
    printf("Select an option: \n");
    printf(" 1. Dollar\n 2. Yen\n 3. Pound\n");
    scanf("%d",&o);
   
    switch(o){
        case dollar: 
            printf("Type the ammount in euros: ");
            scanf("\n%lf",&e);
            c=1.10*e;
            printf("That ammount corresponds to %.2lf dollars.",c);
            break;
            
        case yen:
            printf("Type the ammount in euros: ");
            scanf("\n%lf",&e);
            c=119.20*e;
            printf("That ammount corresponds to %.2lf yens.",c);
            break;
            
        case pound:
            printf("Type the ammount in euros: ");
            scanf("\n%lf",&e);
            c=0.89*e;
            printf("That ammount corresponds to %.2lf pounds.",c);
            break;  
            
        default: printf("You entered an incorrect option. Please enter 1, 2 or 3.");               
    }   
    
    
    return 0;
}

