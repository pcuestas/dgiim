#include <stdio.h>

int main() {
    char c=128;
    unsigned char uc=255;
    int i =2147483648;
    unsigned int ui=4294967295;
    short s=32767;
    unsigned short us=65535;
    long l=-2147483648;
    unsigned long ul=4294967295;
    float f1 = 3879, f2 = 34.980E31, f3 = 2.0032E-21;
    double d1 = -2349432324, d2 = 2.0003033243E89, d3 = 1.00000000001E-32;
    
    printf("The value of the char variable is %hhu.\n", c);
    printf("The value of the unsigned char variable is %hhu.\n\n", uc);
    
    printf("The value of the int variable is %i.\n", i);
    printf("The value of the unsigned int variable is %u.\n\n", ui);
    
    printf("The value of the short variable is %hi.\n", s);
    printf("The value of the unsigned short variable is %hu.\n\n", us);
    
    printf("The value of the long variable is %li.\n", l);
    printf("The value of the unsigned long variable is %lu.\n\n", ul);
    
    printf("The value of the first float variable is %f.\n", f1);
    printf("The value of the second float variable is %g.\n", f2);
    printf("The value of the third float variable is %e.\n\n", f3);
    
     printf("The value of the first double variable is %lf.\n", d1);
    printf("The value of the second double variable is %lg.\n", d2);
    printf("The value of the third double variable is %le.\n", d3);
  return 0;
}
