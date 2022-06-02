
#include <stdio.h>
#include <string.h>


int main() {
    char s[20];
    int length;
    printf("Type a string: ");
    gets(s);
    s[strlen(s)-1]=0;
    printf("%s",s);
    return 0;
}

