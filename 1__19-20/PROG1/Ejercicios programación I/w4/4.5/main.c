
#include <stdio.h>
#include <stdlib.h>


int main() {
    char s[20]="Hello world.";
    printf("%s\n",s);
    s[5]=0;
    printf("%s",s);
    
    return 0;
}

