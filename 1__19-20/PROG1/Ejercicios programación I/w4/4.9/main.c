
#include <stdio.h>
#include <string.h>
#define tam1 5
#define tam2 4



int main() {
    char s[tam1],t[tam1];
    int x[tam2];
    
    printf("Type a string of four characters: ");
    gets(s);
    printf("Type another string of four characters: ");
    gets(t);
    x[0]=s[0]-t[0];
    x[1]=s[1]-t[1];
    x[2]=s[2]-t[2];
    x[3]=s[3]-t[3];
    printf("The difference is: %d, %d, %d and %d.",x[0],x[1],x[2],x[3]);
    return 0;
}