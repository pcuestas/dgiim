

/* 
 * File:   main.c
 * Author: Usuario
 *
 * Created on 17 de septiembre de 2019, 12:10
 */

#include <stdio.h>

int main() {
    long d1,d2,m1,m2,y1,y2;
    
    printf("Type a date in the form: day month year: ");
    scanf("%ld %ld %ld",&d1,&m1,&y1);
    printf("Type another date in the form: day month year: ");
    scanf("%ld %ld %ld",&d2,&m2,&y2);
    
    if ((y1<y2) || ((y1==y2)&&(m1<m2)) || ((y1==y2)&&(m1==m2)&&(d1<d2))){
        printf("The date %ld/%ld/%ld is before %ld/%ld/%ld",d1,m1,y1,d2,m2,y2);
    } else if ((y1==y2)&&(m1==m2)&&(d1==d2)) {
        printf("Both dates are the same.");
    } else {
        printf("The date %ld/%ld/%ld is before %ld/%ld/%ld",d2,m2,y2,d1,m1,y1);
    }
    
    return 0;
}

