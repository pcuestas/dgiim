
#include <stdio.h>
#include <math.h>

typedef struct {
    double a[3],b[3],c[3];
    }vector;
int main() {
    vector x;
    printf("Insert the first coordinate of the first vector: ");
    scanf("%lf",&x.a[0]);
    printf("Insert the second coordinate of the first vector: ");
    scanf("%lf",&x.a[1]);
    printf("Insert the third coordinate of the first vector: ");
    scanf("%lf",&x.a[2]);
    printf("Insert the first coordinate of the second vector: ");
    scanf("%lf",&x.b[0]);
    printf("Insert the second coordinate of the second vector: ");
    scanf("%lf",&x.b[1]);
    printf("Insert the third coordinate of the second vector: ");
    scanf("%lf",&x.b[2]);
    
    x.c[0]=x.a[0]+x.b[0];
    x.c[1]=x.a[1]+x.b[1];
    x.c[2]=x.a[2]+x.b[2];
    
    printf ("This is the sum of the two vectors: (%lg, %lg, %lg)\n",x.c[0],x.c[1],x.c[2]);
    printf("And this is its norm: %lg",sqrt(x.c[0]*x.c[0]+x.c[1]*x.c[1]+x.c[2]*x.c[2]));
    
    return 0;
}

