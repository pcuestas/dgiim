/***********************************************/
/* Program: exercise1     Date: 2020/09/23     */
/* Authors: Álvaro Zamanillo and Pablo Cuesta  */
/*                                             */
/* Program that generates two random nunmbers  */
/* between two given numbers                   */
/*                                             */
/* Input: Command Line                         */
/* -limInf: lower limit                        */
/* -limSup: upper limit                        */
/* -numN: ammount of numbers                   */
/* Output: 0: OK, -1: ERR                      */
/***********************************************/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include "permutations.h"

int main(int argc, char** argv)
{
  int i;
  unsigned int inf, sup, num, j;
  FILE *pf=NULL;

  srand(time(NULL));

  if (argc != 7) {
    fprintf(stderr, "Input parameter error:\n\n");
    fprintf(stderr, "%s -limInf <int> -limSup <int> -numN <int>\n", argv[0]);
    fprintf(stderr, "Where:\n");
    fprintf(stderr, " -limInf : Lower limit.\n");
    fprintf(stderr, " -limSup : Upper limit.\n");
    fprintf(stderr, " -numN : ammout of mumbers to generate.\n");
    exit(-1);
  }
  printf("Practice no 1, Section 1\n");
  printf("Done by: Álvaro Zamanillo and Pablo Cuesta\n");
  printf("Grupo: 1251\n");

  /* check command line */
  for(i = 1; i < argc; i++) {
    if (strcmp(argv[i], "-limInf") == 0) {
      inf = atoi(argv[++i]);
    } else if (strcmp(argv[i], "-limSup") == 0) {
      sup = atoi(argv[++i]);
    } else if (strcmp(argv[i], "-numN") == 0) {
      num = atoi(argv[++i]);
    } else {
      fprintf(stderr, "Wrong Parameter %s \n", argv[i]);
    }
  }

  if(!(pf=fopen("exercise1.log", "w")))
    return -1;

  /* print data */
  for(j = 0; j < num; j++) { 
    fprintf(pf,"%d\n", aleat_lin_num(inf, sup));
  }

  fclose (pf);

  return 0;
}

