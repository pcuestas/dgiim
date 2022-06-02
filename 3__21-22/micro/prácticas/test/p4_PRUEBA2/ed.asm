;**************************************************************************
; MBS 2021. BASIC STRUCTURE OF AN ASSEMBLER PROGRAMME
;**************************************************************************
; DATA SEGMENT DEFINITION
DATA SEGMENT
msg db "Enter string: $"
read db 100 dup (0)
DATA ENDS
;**************************************************************************
; STACK SEGMENT DEFINITION
STACKSEG SEGMENT STACK "STACK"
DB 40H DUP (0) ; initialization example, 64 bytes initialize to 0
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
MOV SP, 64 ; LOAD A STACK POINTER WITH THE HIGHEST VALUE
; END OF INITIALIZATIONS
; BEGINNING OF THE PROGRAMME

mov ax, 0 
mov es, ax
les bx, es:[ 1Ch*4 ]
mov dl, "a"

main_l:
    cmp byte ptr es:[bx-2], 182/30
    jb main_l
    
        in al, 21h 
        or al, 00000001b
        out 21h, al 

        mov byte ptr es:[bx-2], 0

        mov ah, 2
        int 21h 

        inc dl 

        in al, 21h 
        and al, 11111110b
        out 21h, al 

        cmp dl, "z"
        jbe main_l
main_end:

; END OF THE PROGRAMME
MOV AX, 4C00H
INT 21H
BEGIN ENDP

is_quit proc 
    cmp byte ptr read[2], "q"
    jne no 
    cmp byte ptr read[3], "u"
    jne no 
    cmp byte ptr read[4], "i"
    jne no 
    cmp byte ptr read[5], "t"
    jne no 
    cmp byte ptr read[6], "$"
    jne no 
        mov ax, 1 
        jmp end_is_quit
    no:
        mov ax, 0    
    end_is_quit:
    ret 
is_quit endp 

; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN 