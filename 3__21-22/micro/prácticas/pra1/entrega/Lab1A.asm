;**************************************************************************
; Lab1A.asm file
; AUTHOR: PABLO CUESTA SIERRA
; GROUP: 2292
;**************************************************************************
; DATA SEGMENT DEFINITION
DATA SEGMENT
    
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
    ; Load in BX the content of the memory address that is 10 bytes 
    ; over the address pointed by BP
    MOV BX, [BP+10]
    
; END OF THE PROGRAMME
    MOV AX, 4C00H
    INT 21H
BEGIN ENDP
; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN
