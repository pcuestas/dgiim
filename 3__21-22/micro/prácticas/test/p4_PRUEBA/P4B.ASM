;**************************************************************************
; MBS 2021. BASIC STRUCTURE OF AN ASSEMBLER PROGRAMME
;**************************************************************************
; DATA SEGMENT DEFINITION
DATA SEGMENT
MSG DB "ESTO ES UN MENSAJE ABCDEFG$"
STRR DB 100 DUP(0)
DATA ENDS
;**************************************************************************
; STACK SEGMENT DEFINITION
STACKSEG SEGMENT STACK "STACK"
DB 100H DUP (0) ; initialization example, 64 bytes initialize to 0
STACKSEG ENDS
;**************************************************************************
; EXTRA SEGMENT DEFINITION
EXTRA SEGMENT
RESULT DW 0,0 ; initialization example. 2 WORDS (4 BYTES)
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
MOV SP, 100H ; LOAD A STACK POINTER WITH THE HIGHEST VALUE
; END OF INITIALIZATIONS
; BEGINNING OF THE PROGRAMME

MAIN_L:
MOV AH, 0Ah ; Capture keyboard function.
MOV DX, OFFSET STRR; Memory space reserved named NAME
MOV STRR[0], 60 ; Maximum number of characters is 60
INT 21h ; Software interruption 

MOV DL, 10
MOV AH, 2
INT 21H

MOV BL, STRR[1]
MOV BH, 0
CMP BX, 0 
JE MAIN_E
MOV BYTE PTR STRR[BX+2], "$"
MOV BX, OFFSET STRR[2]
INT 57H
JMP MAIN_L

MAIN_E:
; END OF THE PROGRAMME
MOV AX, 4C00H
INT 21H
BEGIN ENDP
; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN