#!/bin/bash

QUERIES="queries.txt"
OUTPUT="ouptut.txt"
CODE="../code/code_2351_05_cuesta_zamanillo.pl"

echo -e "\nBEGIN TESTING\n" >> ${OUTPUT}

while read -r line
do 
    echo -e ${line} >> ${OUTPUT}
    echo -e ${line} | swipl -f ${CODE} >> ${OUTPUT}
done < ${QUERIES}

echo -e "\nEND TESTING\n" >> ${OUTPUT}