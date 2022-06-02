---
title:
- \textbf{\texttt{report1B.pdf}}
author:
- "Pablo Cuesta Sierra. Group 2292."
geometry:
- margin=2.5cm
header-includes: |
    \usepackage[T1]{fontenc}
    \usepackage{mathpazo}
---

# MICRO. Assignment 1, Task 2.

Register values (my ID card starts with digits 54): 

\begin{center}
\texttt{DS = 0354h,    ES = 0300h,    BX = 0210h,    DI = 1011h}.
\end{center}

## Determine the memory address by following the instructions:

a) 
```asm 
        MOV AL,DS:[0211h]
```

Memory address `= 03540h + 0211h = 03751h`. After this instruction, `AL` contains the content of address `03751h`.

b) 
```asm 
        MOV AX,[BX]
```

Memory address (predetermined stack register is `DS`) 
`= 03540h + 0210h = 03750h`. After this instruction, `AX` contains the content of address `03750h`.

c) 
```asm
        MOV [DI],AL
```

Memory address (predetermined stack register is `DS`) 
`= 03540h + 1011h = 04551h`. After this instruction, memory address `04551h` contains the byte from `AL`.

## Complete the code so you can access the same values in the same positions, as in the corresponding previous instructions:

a1) 
```asm
        MOV AL,ES:[0751h]
```

Expected address `= 03751h = 03000h + 0751h `.

b1) 
```asm
        MOV SI, 0750h
        MOV AX,ES:[SI]
```

Expected address `= 03750h = 03000h + 0750h `.

c1) 
```asm
        MOV ES:[1551h],AL
```

Expected address `= 04551h = 03000h + 1551h `.
