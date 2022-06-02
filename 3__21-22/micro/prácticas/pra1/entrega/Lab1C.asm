;**************************************************************************
; Lab1C.asm file
; AUTHOR: PABLO CUESTA SIERRA
; GROUP: 2292
;**************************************************************************
; DATA SEGMENT DEFINITION
DATA SEGMENT
    LONGLIVE_MBS        DB ?
    DRINK               DW 0CAFEH
    TABLE300            DB 300 DUP(?)
    TOTALERROR2         DB "This programme always crashes"
DATA ENDS
;**************************************************************************
; STACK SEGMENT DEFINITION
STACKSEG SEGMENT STACK "STACK"
    DB 40H DUP (0) 
STACKSEG ENDS
;**************************************************************************
; EXTRA SEGMENT DEFINITION
EXTRA SEGMENT
    
EXTRA ENDS
;**************************************************************************

; CODE SEGMENT DEFINITION
CODE SEGMENT
ASSUME CS: CODE, DS: DATA, ES: EXTRA, SS: STACKSEG
; BEGINNING OF MAIN PROCEDURE
BEGIN PROC
; INITIALIZE THE SEGMENT REGISTER WITH ITS VALUE
    MOV AX, DATA
    MOV DS, AX
    MOV AX, STACKSEG
    MOV SS, AX
    MOV AX, EXTRA
    MOV ES, AX
    MOV SP, 64 ; LOAD A STACK POINTER WITH THE HIGHEST VALUE
; END OF INITIALIZATIONS
; BEGINNING OF THE PROGRAMME
    
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
    
; END OF THE PROGRAMME
    MOV AX, 4C00H
    INT 21H
BEGIN ENDP
; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN 