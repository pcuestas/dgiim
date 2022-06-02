#include <stdio.h>
#include <string.h>

int main() {
    char s[20],t[20];
    int value;
    printf ("Enter a string: ");
    gets(s);
    printf ("Enter another string: ");
    gets(t);
    value=strcmp(s,t);
    printf("The value returned by the comparison of the strings is: %d",value);
    return 0;
}

