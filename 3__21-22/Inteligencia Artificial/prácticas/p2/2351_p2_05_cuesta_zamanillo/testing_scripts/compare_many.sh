#!/bin/bash

# Authors: Pablo Cuesta Sierra and Álvaro Zamanillo Sáez
# Script to compare several heuristics with each other using the compare_2_heuristic.sh script

./compare_2_heuristic.sh Solution2 Solution3
./compare_2_heuristic.sh Solution1 Solution2
./compare_2_heuristic.sh Solution1 Solution3