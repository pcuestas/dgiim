set title "Slow-Fast Execution Time"
set ylabel "Execution time (s)"
set xlabel "Matrix Size (N)"
set key left top Left
set grid
set term png
set output "../outputs/out1/slow_fast_time.png"
plot "../outputs/out1/slow_fast_time.dat" using 1:2 with lines lw 2 title "slow", \
     "../outputs/out1/slow_fast_time.dat" using 1:3 with lines lw 2 title "fast"
replot
quit