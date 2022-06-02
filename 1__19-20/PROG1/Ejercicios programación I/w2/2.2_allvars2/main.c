#include <stdio.h>

int main() {
    char c;
    unsigned char uc;
    int i;
    unsigned int ui;
    short s;
    unsigned short us;
    long l;
    unsigned long ul;
    float f;
    double d;
    
    printf("Write the value of the char variable: ");
    scanf("%hhu",&c);
    printf("Write the value of the unsigned char variable: ");
    scanf("%hhu",&uc);
    
    printf("Write the value of the int variable: ");
    scanf("%i",&i);
    printf("Write the value of the unsigned int variable: ");
    scanf("%u",&ui);
    
    printf("Write the value of the short variable: ");
    scanf("%hi",&s);
    printf("Write the value of the unsigned short variable: ");
    scanf("%hu",&us);
    
    printf("Write the value of the long variable: ");
    scanf("%li",&l);
    printf("Write the value of the unsigned long variable: ");
    scanf("%lu",&ul);
    
    printf("Write the value of the float variable: ");
    scanf("%f",&f);
    printf("Write the value of the double variable: ");
    scanf("%lf",&d);
    
    
    printf("\nThe value of the char variable is %hhu.\n", c);
    printf("The value of the unsigned char variable is %hhu.\n\n", uc);
    
    printf("The value of the int variable is %i.\n", i);
    printf("The value of the unsigned int variable is %u.\n\n", ui);
    
    printf("The value of the short variable is %hi.\n", s);
    printf("The value of the unsigned short variable is %hu.\n\n", us);
    
    printf("The value of the long variable is %li.\n", l);
    printf("The value of the unsigned long variable is %lu.\n\n", ul);
    
    printf("Float: %f. Scientific notation: %e. Shorter: %g\n\n",f,f,f);
    
    printf("Double: %lf. Scientific notation: %le. Shorter: %lg\n\n",d,d,d);
  return 0;
}
