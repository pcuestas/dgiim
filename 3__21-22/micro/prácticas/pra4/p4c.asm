
;**************************************************************************
; MBS 2021. BASIC STRUCTURE OF AN ASSEMBLER PROGRAMME
;**************************************************************************

MAX_TIMER EQU 18

; DATA SEGMENT DEFINITION
DATA SEGMENT 
    msg db "hola",13,10,"$"
    read_keyboard_str db 100 dup('$')
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

    mov ax, 0 
    mov es, ax 
    
    les bx, es:[ 1Ch*4 ]   ; es:[bx-2] = timer_tick 
    mov cx, 5 ; 5 iterations 
    mov dx, offset msg

    iter:
        cmp BYTE PTR es:[bx-2], MAX_TIMER
        jb iter 
            ; disable timer interrupts 
                in al, 21h ; read master IMR 
                or al, 00000001b 
                out 12h, al ; write new mask

            mov BYTE PTR es:[bx-2], 0

            ; read from keyboard 
            call read_keyboard
            cmp ax, 0
            je nothing_read
                mov ah, 10h
                mov dx, offset read_keyboard_str
                INT 57h
            nothing_read:
            ;enable timer interrupts 
                in al, 21h ; read master IMR 
                and al, 11111110b 
                out 12h, al ; write new mask
            
            dec cx
            jnz iter 

    ; END OF THE PROGRAMME
    MOV AX, 4C00H
    INT 21H
BEGIN ENDP

read_keyboard proc 
    push bx 
    mov bx, 0 
    read_letter:
        mov ah, 1
        int 16h
        jz end_read_keyboard
            mov ah, 0
            int 16h
            mov read_keyboard_str[bx], al
            inc bx 
            jmp read_letter 
            
    end_read_keyboard:
    mov read_keyboard_str[bx], '$'
    mov ax, bx 
    pop bx 
    ret 
read_keyboard endp 

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

; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN 
