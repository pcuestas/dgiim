; Lab1C.asm file
; AUTHOR: PABLO CUESTA SIERRA. GROUP: 2292.

; DATA SEGMENT
DATA SEGMENT 
    LONGLIVE_MBS        DB ?
    DRINK               DW 0CAFEH
    TABLE300            DB 300 DUP(?)
    TOTALERROR2         DB "This programme always crashes"
DATA ENDS 

; STACK SEGMENT
PILA    SEGMENT STACK "STACK" 
    DB   100H DUP (0) 
PILA ENDS 

; CODE SEGMENT
CODE    SEGMENT 
    ASSUME CS:CODE, DS:DATA, SS:PILA 

MAIN PROC 
    ;INIT SEG REGISTERS 
    MOV AX, DATA 
    MOV DS, AX 
    MOV AX, PILA 
    MOV SS, AX 
    ; STACK POINTER 
    MOV SP, 100H 

    ;START PROGRAM 

; Copy the third character of the string TOTALERROR2 
; in the position 63H of TABLE300.
    MOV AL, TOTALERROR2[2]
    MOV TABLE300[63H], AL

; Copy the content of the variable DRINK starting in 
; the position 4 of TABLE300.
    MOV AX, DRINK
    MOV WORD PTR TABLE300[4], AX

; Copy the most significant byte of DRINK in the 
; variable LONGLIVE_MBS. 
    MOV LONGLIVE_MBS, AH

    ; END PROGRAM 
    MOV AX, 4C00H 
    INT 21H 

MAIN ENDP 

CODE ENDS 

END MAIN 
