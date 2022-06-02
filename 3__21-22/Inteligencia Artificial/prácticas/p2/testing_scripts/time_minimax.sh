#!/bin/bash

## Authors: Pablo Cuesta Sierra and Álvaro Zamanillo Sáez
## Timing alpha-beta vs minimax using reversi

DEPTHS=(2 3 4)
REP=10
TAB="\t"
DECIMALS=4

DAT_DIR="../results"
MEM_DIR="../mem"

TIME_SCRIPT="../scripts/time_minimax.py"
EXECUTE="python3 ${TIME_SCRIPT}"

TMP_FILE="${DAT_DIR}/time_tmp.dat"
TMP_FILE2="${DAT_DIR}/time_tmp2.dat"
OUT_FILE="${DAT_DIR}/time_minimax_output.dat"
LATEX_TABLE_FILE="${MEM_DIR}/aditional/times_table.tex"
EXEC_LATEX_TABLE_SCRIPT="python3 latex_table.py"

mkdir -p ${DAT_DIR}
rm -f ${TMP_FILE} ${TMP_FILE2} ${OUT_FILE}

echo "Running minimax vs alpha-beta timing: ${REP} repetitions for depths {${DEPTHS[@]}}"

for depth in ${DEPTHS[@]}; do
    echo "  Doing depth $depth"
    for ((j = 1; j <= REP ; j += 1));do
        echo "    Iteration $j"
        time_minimax=$(${EXECUTE} $depth "minimax"    | grep -i "time" | awk '{print $2}' )
        time_alpha_beta=$(${EXECUTE} $depth "alpha-beta" | grep -i "time" | awk '{print $2}' )
        echo "minimax $depth $time_minimax" >> ${TMP_FILE}
        echo "alpha_beta $depth $time_alpha_beta" >> ${TMP_FILE}
    done
done

cat ${TMP_FILE} | tr "." "," > ${TMP_FILE2} # this is because awk needs ',' instead of "."

# calculate the means and the ratio
echo -e "Max depth${TAB}Minimax mean${TAB}Alpha-beta mean${TAB}Speedup" >> ${OUT_FILE}

for depth in ${DEPTHS[@]}; do
    minimax_mean=$(grep -w "minimax $depth" ${TMP_FILE2} | awk '{ times += $3; n++ } END { printf "%s", times/n }')
    alpha_beta_mean=$(grep -w "alpha_beta $depth" ${TMP_FILE2} | awk '{ times += $3; n++ } END { printf "%s", times/n }')
    minimax_mean=$(echo $minimax_mean | tr "," ".")
    alpha_beta_mean=$(echo $alpha_beta_mean | tr "," ".")
    speedup=$(echo "scale=${DECIMALS};$minimax_mean/$alpha_beta_mean" | bc)
    echo -e "${depth}${TAB}${minimax_mean}${TAB}${alpha_beta_mean}${TAB}${speedup}" >> ${OUT_FILE}
done

rm -f ${TMP_FILE2}

echo "Writing latex table to file ${LATEX_TABLE_FILE}..."
${EXEC_LATEX_TABLE_SCRIPT} ${OUT_FILE} >> ${LATEX_TABLE_FILE} 