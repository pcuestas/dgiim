#define MAX_LEVEL 100

#include "menu_functions.h"
#include "utils.h"
#include <string.h>

#define STR_MAX_SIZE 512


extern char*strndup(const char*, size_t);

int ShowMainMenu(){
    int sel = 0;
    char buf[16];

    do{
        printf("Select an option:\n");

        printf("\t(1) Use.\n"
               "\t(2) Insert.\n"
               "\t(3) Print.\n"
               "\t(4) Exit.\n\n"

               "Enter a number that corresponds to your choice > ");

        (void)fflush(stdout);
        (void)fflush(stdin);
        if (scanf("%s", buf) == 0){
            sel = 0;
        }else{
            sel = atoi(buf);
        }if ((sel < 1) || (sel > 4)){
            printf("You entered an invalid choice. Please try again.\n\n");
        }
    } while ((sel < 1) || (sel > 4));

    return sel;
}
 
bool use(char *TableName){

    int i=0,l=0, error=0;
    char auxname[STR_MAX_SIZE];

    if(!TableName)
        return false;
    
    printf("Enter the name of a table (\"tablename.dat\"): ");
    scanf("%s",auxname);

    l=strlen(auxname);

    /*Check format of the tablename*/
    for(i=0;auxname[i]!=0 && auxname[i]!='.';i++);
    
    if(i+4==l){
        if(auxname[i+1]!='d'||auxname[i+2]!='a'||auxname[i+3]!='t'){
            error=1;
        }
    }else{
        error=1;
    }

    if(error==1){
        printf("Invalid format of file.\n");
        return false;
    }

    strncpy(TableName, auxname, STR_MAX_SIZE);

    return createTable(TableName);
}

bool insert(char *TableName){
    
    Book book;
    bool ret = true;
    char IndexName[STR_MAX_SIZE],book_id[STR_MAX_SIZE],title[STR_MAX_SIZE];

    if(!TableName)
        return false;
    
    replaceExtensionByIdx(TableName,IndexName);

    
    printf("Enter key of the book: "); 

    scanf("%s", book_id);

    if(strlen(book_id)!=PK_SIZE){
        printf("Invalid size of key. It must have a size of %d characters.\n", (int)PK_SIZE);
        return true;
    }

    memcpy(book.book_id, book_id, PK_SIZE);
   
    
    printf("\nEnter title of the book: ");
    fflush(stdout);

    fflush(stdin);
    while((getchar())!='\n'); /*To clean all \n so fgets works properly*/
    fgets(title, sizeof(title), stdin);
    /*remove '\n' fro the title*/
    title[strlen(title)-1]=0;

    book.title = strndup(title, STR_MAX_SIZE);
    if(book.title==NULL){
        printf("Error allocating memory\n");
        return false;
    }
    
    book.title_len=strlen(title);

    ret = addTableEntry(&book,TableName,IndexName);
    
    free(book.title);

    return ret;
}

bool print(char *TableName){

    char IndexName[512];

    if(!TableName)
        return false;

    replaceExtensionByIdx(TableName, IndexName);

    printf("------Printing tree------\n");
    printTree(MAX_LEVEL,IndexName);
    printf("\n");

    return true;
}