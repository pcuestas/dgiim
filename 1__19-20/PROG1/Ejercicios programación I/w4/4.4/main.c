

#include <stdio.h>
#include <stdlib.h>

#define tam 3
int main() {
    int x[tam][tam];
    printf("Insert a day in the following form: day month year: ");
    scanf("%d %d %d",&x[0][0],&x[0][1],&x[0][2]);
    printf("Insert another day: ");
    scanf("%d %d %d",&x[1][0],&x[1][1],&x[1][2]);
    
    x[2][0]=x[0][0]-x[1][0];
    x[2][1]=x[0][1]-x[1][1];
    x[2][2]=x[0][2]-x[1][2];
    printf("The difference in days is: %d\n",x[2][0]);
    printf("The difference in months is: %d\n",x[2][1]);
    printf("The difference in years is: %d\n",x[2][2]);
    return 0;
}

