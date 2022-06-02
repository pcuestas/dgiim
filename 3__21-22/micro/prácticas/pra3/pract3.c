#include <stdio.h>
#include <string.h>

#define BUFLEN 1024

extern int OddPositive(int);
extern int DigitComputation(unsigned int , unsigned int );
extern unsigned int NextPrime(unsigned int);
extern int SubstringFinder(char*, char*);
extern int strcmp2(char*, char*);
extern unsigned int SecondComputDC (char*);

void check_OddPositive()
{
    int n;
    int ret;

    printf("Check OddPositive...\n");

    printf("Input a number: ");
    scanf("%d", &n);
    ret = OddPositive(n);
    printf("The number %d is %s.\n\n", n, (ret ? "odd" : "even or negative"));
}

void check_DigitComputation()
{
    unsigned int num;
    unsigned int pos;
    int ret;
    
    printf("Check DigitComputation...\n");

    printf("Input a number: ");
    scanf("%u", &num);
    printf("Input a position: ");
    scanf("%u", &pos);
    ret = DigitComputation(num, pos);
    printf("The digit %u of the number %u is %d.\n\n", pos, num, ret);
}

void check_NextPrime()
{
    unsigned int n;
    unsigned int ret;

    printf("Check NextPrime...\n");

    printf("Input a number: ");
    scanf("%u", &n);
    ret = NextPrime(n);
    printf("The next prime after %u is %u.\n\n", n, ret);
}

void check_SubstringFinder()
{
    char str[BUFLEN], substr[BUFLEN];
    int ret;

    printf("Check SubstringFinder...\n");

    printf("Input a string: ");
    scanf("%s", str);
    printf("Input the substring to search: ");
    scanf("%s", substr);
    ret = SubstringFinder(str, substr);
    printf("The output is %d.\n\n", ret);
}

void check_SecondComputDC()
{
    unsigned int ret;
    char str[BUFLEN];
    printf("Check SecondComputDC...\n");

    printf("Input a 10 digit number: ");
    scanf("%s",str);
    ret = SecondComputDC(str);
    printf("Second control digit: %u\n", ret);    
}

int main()
{
    int option;

    printf("Options:\n" \
            "\t1) OddPositive\n"\
            "\t2) DigitComputation\n"\
            "\t3) NextPrime\n"\
            "\t4) SubstringFinder\n"\
            "\t5) SecondComputDC\n"\
        "Select an option: ");
    scanf("%d", &option);

    switch(option)
    {
        case 1:
            check_OddPositive(); break;
        case 2:
            check_DigitComputation(); break;
        case 3:
            check_NextPrime(); break;
        case 4:
            check_SubstringFinder(); break;
        default:
            check_SecondComputDC();
    }

    return 0;
}