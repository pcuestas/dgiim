# comment
set title "Comparison (Bubblesort): aleat\\\_lin\\\_num() vs random\\\_num()"
#set xrange [0:10];
#set yrange [0:10];
set xlabel "N (size of permutations)";
set ylabel "avgBO";
plot "bs1.log" u 1:3 w lp title "random\\\_num() - BS avgBO", "bs2.log" u 1:3 w lp title "aleat\\\_lin\\\_num() - BS avgBO";
