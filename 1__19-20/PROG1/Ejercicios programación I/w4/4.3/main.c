
#include <stdio.h>
#include <stdlib.h>


int main() {
        int x[3][3]={1,2,1,1,1,2,3,0,3};
        
        int det,a,b,c;
        
        a=x[0][0]*(x[1][1]*x[2][2]-x[1][2]*x[2][1]);
        b=x[0][1]*(x[1][0]*x[2][2]-x[2][0]*x[1][2]);
        c=x[0][2]*(x[1][0]*x[2][1]-x[1][1]*x[2][0]);
        det = a-b+c;
        printf("Determinant: %d",det);
    return 0;
}