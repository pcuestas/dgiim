; Lab1A.asm file
; AUTHOR: PABLO CUESTA SIERRA

; DATA SEGMENT
DATA SEGMENT 

    

DATA ENDS 


; STACK SEGMENT
PILA    SEGMENT STACK "STACK" 
    DB   100H DUP (0) 
PILA ENDS 


; EXTRA SEGMENT
EXTRA     SEGMENT 
    RESULT    DW 0,0                 ; 2 PALABRAS ( 4 BYTES ) 
EXTRA ENDS 


; CODE SEGMENT
CODE    SEGMENT 
    ASSUME CS:CODE, DS:DATA, ES: EXTRA, SS:PILA 

MAIN PROC 
    ;INIT SEG REGISTERS 
    MOV AX, DATA 
    MOV DS, AX ;!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    MOV AX, PILA 
    MOV SS, AX 
    MOV AX, EXTRA 
    MOV ES, AX
    ; STACK POINTER 
    MOV SP, 100H 

    ;START PROGRAM 

; Load 8BH in AX
    MOV AX, 8BH                 
; Load CFH in BL
    MOV BL, 0CFH                
; Load 3412H in DX
    MOV DX, 3412H               
; Load 11001011b in DS
    MOV AX, 11001011B
    MOV DS, AX                  
; Load the content of DX in CX
    MOV CX, DX
; Load in BH the content of the memory position
; 51223H and in BL the content of the position 51222H
    MOV AX, 5122H
    MOV ES, AX                  
    MOV BX, ES:[2]     
; Load in the memory position 60006H the content of DH.
    MOV AX, 6000H
    MOV ES, AX                  
    MOV ES:[6], DH
; Load in AX the content of the memory address pointed by SI.
    MOV AX, [SI]
; Load in BX the content of the memory address that is 10 bytes over the address
; pointed by BP
    MOV BX, [BP+10]

    ; END PROGRAM 
    MOV AX, 4C00H 
    INT 21H 

MAIN ENDP 

FACTOR PROC NEAR 
    MOV AX, 1 
    XOR CH,CH 
    CMP CX, 0 
    JE FIN 
IR: 
    MUL CX 
    DEC CX 
    JNE IR 
FIN: 
    RET 
FACTOR ENDP 

; FIN DEL SEGMENTO DE CODIGO 
CODE ENDS 
; FIN DEL PROGRAMA INDICANDO DONDE COMIENZA LA EJECUCION 
END MAIN 