

#include <stdio.h>
#include <stdlib.h>

typedef struct{
    long divider,reminder,dividend,quotient;
}division;

int main() {
    division x;
    printf("Enter a dividend: ");
    scanf("%ld",&x.dividend);
    printf("Enter a divider: ");
    scanf("%ld",&x.divider);
    
    x.quotient=x.dividend/x.divider;
    x.reminder=x.dividend%x.divider;
    
    printf("Quotient: %ld",x.quotient);
    printf("\nReminder: %ld",x.reminder);
    return 0;
}

