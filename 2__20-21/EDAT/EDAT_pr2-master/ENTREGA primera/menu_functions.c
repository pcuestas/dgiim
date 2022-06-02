#include "menu_functions.h"

/*Prints the options of the ProductsMenu and returns the user's choice*/
static int ShowProductsSubMenu(){
    int sel=0;
    char buf[16];

    do{
        printf("This is the products sub-menu, select an option:\n");

        printf( "\t(1) Stock.\n"
                "\t(2) Find.\n"
                "\t(3) Back.\n"

            "Enter a number that corresponds to your choice > ");

        (void)fflush(stdout);
        if(scanf("%s", buf)==0){
                    sel = 0;
                }
        else{
            sel = atoi(buf);
        }

        if((sel<1) || (sel>3)){
            printf("You entered an invalid choice. Please try again.\n\n");
        }
    }while ((sel<1) || (sel>3));

    return sel;
}

/*Prints the options of the OrdersMenu and returns the user's choice*/
static int ShowOrdersSubMenu(){
    int sel=0;
    char buf[16];

    do{
        printf("This is the orders sub-menu, select an option:\n");

        printf( "\t(1) Open.\n"
                "\t(2) Range.\n"
                "\t(3) Detail.\n"
                "\t(4) Back.\n\n"

            "Enter a number that corresponds to your choice > ");

        (void)fflush(stdout);
        if(scanf("%s", buf)==0){
                    sel = 0;
                }
        else{
            sel = atoi(buf);
        }

        if((sel<1) || (sel>4)){
            printf("You entered an invalid choice. Please try again.\n\n");
        }
    }while ((sel<1) || (sel>4));

    return sel;
}

/*Prints the options of the CustomersMenu and returns the user's choice*/
static int ShowCustomersSubMenu(){
    int sel=0;
    char buf[16];

    do{
        printf("This is the customers sub-menu, select an option:\n");

        printf( "\t(1) Find.\n"
                "\t(2) List products.\n"
                "\t(3) Balance.\n"
                "\t(4) Back.\n\n"

            "Enter a number that corresponds to your choice > ");

        if(scanf("%s", buf)==0){
                    sel = 0;
                }
        else{
            sel = atoi(buf);
        }

        if((sel<1) || (sel>4)){
            printf("You entered an invalid choice. Please try again.\n\n");
        }
    }while ((sel<1) || (sel>4));

    return sel;
}

/*Shows the main menu and returns the user's choice*/
int ShowMainMenu(){
    int sel=0;
    char buf[16];

    do{
        printf("Select an option:\n");

        printf( "\t(1) Products.\n"
                "\t(2) Orders.\n"
                "\t(3) Customers.\n"
                "\t(4) Exit.\n\n"

            "Enter a number that corresponds to your choice > ");

        (void)fflush(stdout);
        if(scanf("%s", buf)==0){
                    sel = 0;
                }
        else{
            sel = atoi(buf);
        }

        if((sel<1) || (sel>4)){
            printf("You entered an invalid choice. Please try again.\n\n");
        }
    }while ((sel<1) || (sel>4));

    return sel;
}

/*Calls the Products submenu and, depending on the choice of the user, calls the functions that make the queries*/
void ShowProductsMenu(){
    int choice=0;

    do{
        choice = ShowProductsSubMenu();
        switch (choice){
            case 1: {
                if(ProductsStock()==EXIT_FAILURE){
                    printf("Error in ProductsStock\n");
                }
            }
                break;
            
            case 2:{
                if (ProductsFind() == EXIT_FAILURE){
                    printf("Error in ProductsStock\n");
                }
            }
                break;
            
            case 3:{
                printf("\nBack to the main menu...\n\n");
            }
                break;
        }
    }while (choice != 3);

}

/*Calls the Orders submenu and, depending on the choice of the user, calls the functions that make the queries*/
void ShowOrdersMenu(){
    int choice=0;

    do{
        choice = ShowOrdersSubMenu();
        switch (choice){
            case 1: {
                if(OrdersOpen()==EXIT_FAILURE){
                    printf("Error in OrdersOpen");
                }
            }
                break;
            
            case 2:{
                if (OrdersRange() == EXIT_FAILURE){
                    printf("Error in OrdersRange");
                }
            }
                break;
                
            case 3:{
                if (OrdersDetails() == EXIT_FAILURE){
                    printf("Error in ordersdetails");
                }
            }
                break;
            
            case 4:{
                printf("\nBack to the main menu...\n\n");
            }
                break;
        }
    }while (choice != 4);

}

/*Calls the Customers submenu and, depending on the choice of the user, calls the functions that make the queries*/
void ShowCustomersMenu(){
    int choice=0;

    do{
        choice = ShowCustomersSubMenu();
        switch (choice){
            case 1: {
                if (CustomersFind() == EXIT_FAILURE){
                    printf("Error in CustomersFind");
                }
            }
                break;
            
            case 2:{
                if (CustomersListProducts() == EXIT_FAILURE){
                    printf("Error in CustomersListProducts");
                }
            }
                break;
                
            case 3:{
                if (CustomersBalance() == EXIT_FAILURE){
                    printf("Error in CustomersBalance");
                }
            }
                break;
            
            case 4:{
                printf("\nBack to the main menu...\n\n");
            }
                break;
        }
    }while (choice != 4);

}

