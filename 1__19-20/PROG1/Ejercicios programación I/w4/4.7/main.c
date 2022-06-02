
#include <stdio.h>
#include <string.h>


int main() {
    int s[20],t[20];
    printf("Type a string: ");
    scanf("%s",s);
    strcpy(t,s);
    printf("%s",t);
    printf("\nThe value of the comparison is %d",strcmp(s,t));
    return 0;
}

