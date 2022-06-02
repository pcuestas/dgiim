#!/bin/bash

# Authors: Pablo Cuesta Sierra and Álvaro Zamanillo Sáez
# Plays $REP matches with each color using the 2 heuristics input as argument
# Example: ./compare_2_heuristic.sh Solution1 Solution2

RES_DIR="../results/"

REP=1 # 30

mkdir -p ${RES_DIR}

COMMAND="python3 ../scripts/demo_tournament.py"
OUTPUT="${RES_DIR}comp.dat"

touch $OUTPUT


echo -e "Running $1 vs $2 -- $REP rounds"

t1=0
t2=0
for ((j = 0 ; j < REP ; j += 1));do
	
    op1=( $(${COMMAND} $1 $2 | grep 'opt1' | awk '{print $2}') )
    op2=$((2-$op1))


    t1=$(echo $(($t1+$op1)) )
    t2=$(echo $(($t2+$op2)) )

    echo -e " $j Result $1 vs $2: $op1 : $op2  Total so far: $t1 : $t2 "
done

echo -e "$1 vs $2:  $t1 $t2 " >> $OUTPUT
echo -e "   " >> $OUTPUT

