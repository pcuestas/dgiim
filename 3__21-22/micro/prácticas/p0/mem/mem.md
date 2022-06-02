---
title:
- MICRO. Practice 0, Exercises.
author:
- Pablo
geometry:
- margin=2.5cm
header-includes: |
    \usepackage[T1]{fontenc}
    \usepackage{mathpazo}
---

# Exercise 1: Factorial calculation

The changes necessary to calculate the requested results are to be done in the first part of the code, in the 'datos' segment, where `DATO_1` and `DATO_2` are declared:

```asm
DATOS SEGMENT 

DATO_1  DB     2
DATO_2  DB     3
```

`FACT_DATO_1` contains the result of the first factorial calculated at the end of execution.

Results obtained:

1. $4!\times 5! =$ 0B40h = 2880

```asm
DATOS SEGMENT 

DATO_1  DB     4
DATO_2  DB     5
```
 
![](../img/e1.1)


2. $8! =$ 9D80h = 40320

```asm
DATOS SEGMENT 

DATO_1  DB     8
DATO_2  DB     1
```
 
![](../img/e1.2)

3. $9! =$ 8980h = 35200. 

Although this is the obtained result, it is not correct, $9!=$ 58980h, which is bigger than what can be represented with 2 bytes. The overflow happens before the last multiplication of the factorial function, so the data in `DX` that appears when there is an overflow is not used in the following multiplications, so the result is not correct. A possible solution to this would be to reverse the order in which the factorial function multiplies (increasing the counter instead of decreasing). This only works because 8! is still less than $2^{16}$.
 
 ```asm
DATOS SEGMENT 

DATO_1  DB     9
DATO_2  DB     1
```
 
![](../img/e1.3)

4. $8!\times 7! =$ C800h = 51200.

Although this is the obtained result, it is not correct, $8!\times 7!$ is bigger than what can be represented with 2 bytes. However, the overflow doesn't happen until the last operation, thus it is saved in the `DX` register, and as can be seen in the image, the result is not lost:
$8!\times 7! =$ 0C1CC800h = 203212800.
 
```asm
DATOS SEGMENT 

DATO_1  DB     8
DATO_2  DB     7
```
 
![](../img/e1.4)

# Exercise 2: "Factor" Program Modification

This is the code:

```asm
    ;COMIENZO DEL PROGRAMA 
    MOV AL, DATO_1 			  ; AX = DATO_1
	MOV BL, DATO_2			  ;	BX = DATO_2
	MUL BX					  ; AX = AX * BX
	MOV CX, AX
    CALL FACTOR 			  ; EN AX ESTA EL RESULTADO DEL FACTORIAL

    ; ALMACENA EL RESULTADO 
    MOV RESULT, AX 
    MOV RESULT+2, DX
```

1. $(2!\times 3!)=$ 02D0h = 720

2. $(2!\times 3!)=$ 9D80h = 40320

3. $(3\times 3)!$

4. $(2\times 5)!$

The last two (3. and 4.) are just like the exercise 1, point 3. There is overflow and the data is lost during execution.

# Exercise 3: "Alumno" Program Modification 