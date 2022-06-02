#include <stdio.h>

int main() {

    char p1, p2;
    double h1,h2,w1,w2,w;

    printf("Please enter the initial of the first person: ");
    scanf("%c",&p1);
    printf("Please enter the height of the first person: ");
    scanf("%lg",&h1);

    printf("Please enter the initial of the second person: ");
    scanf("\n%c",&p2);
    printf("Please enter the height of the second person: ");
    scanf("%lg",&h2);

    w1= h1-100-(h1-150)/4;
    w2= h2-100-(h2-150)/4;
    w=(w1+w2)/2;

    printf("The ideal weight of %c is %.1lf kg.\n",p1,w1);
    printf("The ideal weight of %c is %.1lf kg.\n",p2,w2);
    printf("The average of both ideal weights is %.1lf kg.",w);

    return 0;
}