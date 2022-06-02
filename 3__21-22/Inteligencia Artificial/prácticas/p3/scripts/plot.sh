#!/bin/bash

QUERIES="queries.txt"
OUTPUT="log.txt"
OUTPUT2="log2.txt"
CODE="../code/p3_2351_05_cuesta_zamanillo.pl"
fPNG='graph.png'

rm -f ${OUTPUT} ${OUTPUT2}

for ((k=1;k<150;k=k+1))
do 
    query="clasifica_patrones('../code/iris_patrones.csv','../code/iris_etiquetas.csv', ${k}, Tasa_aciertos)."
    V=$( echo -e ${query} | swipl -f ${CODE} | awk '{printf $3}' )
    echo "$k $V" >> ${OUTPUT}
done 

sed -z 's/\.\n/\n/g' ${OUTPUT} > ${OUTPUT2}

gnuplot << END_GNUPLOT
set title "Hit rate depending on K"
set ylabel "Hit rate"
set xlabel "K"
set key right top
set grid
set term png
set output "$fPNG"
plot "${OUTPUT2}" using 1:2 lw 1 title "Hit rate"
replot
quit
END_GNUPLOT