
 

;**************************************************************************
; MBS 2021. BASIC STRUCTURE OF AN ASSEMBLER PROGRAMME
;**************************************************************************

ROW_LEN EQU 6
; DATA SEGMENT DEFINITION
DATA SEGMENT 
    table db "A B C D E F", 13, 10
          db "G H I J K L", 13, 10
          db "M N O P Q R", 13, 10
          db "S T U V W X", 13, 10
          db "Y Z 0 1 2 3", 13, 10
          db "4 5 6 7 8 9", 13, 10, "$"
    msg db "POLYBIUS$"
    coded_msg db "34 33 26 51 12 23 43 41$"
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
    MOV SP, 100h ; LOAD A STACK POINTER WITH THE HIGHEST VALUE
    ; END OF INITIALIZATIONS
    ; BEGINNING OF THE PROGRAMME
    
    mov dx, offset table
    call print_str
    
    mov dx, offset msg 
    mov bx, seg msg 
    mov ds, bx
    mov ah, 10h 
    int 57h

    mov dx, offset coded_msg 
    mov bx, seg coded_msg 
    mov ds, bx
    mov ah, 11h 
    int 57h

    ; END OF THE PROGRAMME
    MOV AX, 4C00H
    INT 21H
BEGIN ENDP

print_str proc 
    push ax 
    mov ah, 9
    int 21h
    pop ax 
    ret 
print_str endp 

    ; prints char in al 
    print_char PROC 
        push ax dx
        mov dl, al 
        mov ah, 2h 
        int 21h
        pop dx ax 
        ret 
    print_char ENDP

FUNCT PROC 
        push ax bx cx dx 
        
        mov bx, dx 
        mov cx, ROW_LEN

        cmp ah, 10h
        je codification 
        ; decodification 
        cmp byte ptr [bx], '$'
        je end_FUNCT 
            decodification_loop:
                mov dx, [bx]
                add dl, -1-'0' 
                add dh, -1-'0'
                mov al, dl 
                mul cl     
                add al, dh 
                cmp al, 'Z'-'A'
                jg decodification_number 
                ; letter 
                    add al, 'A'
                    jmp decodification_print
                decodification_number:
                    sub al, 'Z'-'A'+1-'0'
                decodification_print:
                    call print_char            
                cmp byte ptr [bx+2], '$'
                je end_FUNCT
                add bx, 3
                jmp decodification_loop            

        codification:
            codification_loop:
                mov al, [bx]
                cmp al, '$'
                je end_FUNCT
                cmp al, 'A'
                jge codification_letter 
                    ; it is a number 
                    add al, 'Z'-'A'+1-'0'
                    jmp codification_operation
                codification_letter:
                    sub al, 'A'    
                codification_operation:
                    mov ah, 0 
                    div cl ; ax /= 6
                    add al, 1+'0'
                    call print_char  
                    add ah, 1+'0'
                    mov al, ah 
                    call print_char
                    mov al, ' '
                    call print_char
                    inc bx 
                    jmp codification_loop

            end_FUNCT:
            
            mov al, 13 
            call print_char
            mov al, 10 
            call print_char 

            pop dx cx bx ax 
            ret 
        FUNCT ENDP 

; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN 