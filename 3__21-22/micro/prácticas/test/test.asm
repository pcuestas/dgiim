;**************************************************************************
; name...
;**************************************************************************
; DATA SEGMENT DEFINITION
DATA SEGMENT
    A DB -20,0,1,3,0,0,5,1,1
org 20h
    num DB ?
org 100h 
    string DB 20 dup(?)
    request DB "Enter number: $"
    ten db 10
    aux2 db 20 dup(?)
    aux1 db "      $"
    auxA db " |A|= $"
    sep db "  $"
    
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
    ; BEGINNING OF THE 
    
    mov bx, offset A
    call print_mat 
    call print_crlf
    
    mov dx, offset request 
    mov ah, 9 
    int 21h 
    
    mov ah, 0Ah 
    mov dx, offset string 
    mov string, 20 ; max length
    int 21h 
    call print_crlf 
     
    call readnum  ;ax contains the number 
    
    mov si, 0 
    mov bx, ax 
    bucle:
        mov al, A[si] 
        call extend_al 
        mul bx 
        mov A[si], al 
        inc si
        cmp si, 9 
        jne bucle 

        
    mov bx, offset A
    call print_mat 
    call print_crlf

    ; END OF THE PROGRAMME
    MOV AX, 4C00H
    INT 21H
BEGIN ENDP

extend_al proc 
    mov ah, 0
    cmp al, 0
    jge extend 
    mov ah, 0FFh 
    extend:
    ret 
extend_al endp 

print_mat proc 
    mov dx, offset aux1 
    mov ah, 9 
    int 21h 
    
    call print_row 
    call print_crlf 
    add bx, 3 

    mov dx, offset auxA 
    mov ah, 9 
    int 21h 
    
    call print_row 
    call print_crlf 
    add bx, 3 
    
    mov dx, offset aux1 
    mov ah, 9 
    int 21h 
    
    call print_row 
    call print_crlf    
    
    ret 
print_mat endp 

al_ascii proc
    mov aux2[0], ' '
    mov aux2[1], ' '
    mov aux2[2], ' '
    mov aux2[4], '$'
    mov si, offset aux2[3]
    mov cx, 0 
    cmp al, 0 
    jge ascii_conv 
        neg al 
        mov cx, 1 
    ascii_conv:
        mov ah, 0 
        div ten 
        add ah, '0' 
        mov [si], ah 
        dec si 
        cmp al, 0 
        jne ascii_conv 
    jcxz endascii 
        mov byte ptr [si], '-'
    endascii: 
        mov ax, offset aux2
    ret 
al_ascii endp

print_al proc
    push dx 
    
    call al_ascii 
    mov dx, ax 
    mov ah, 9 
    int 21h

    pop dx 
    ret 
print_al endp

print_row proc 
    
    mov ah, 2
    mov dl, '|' 
    int 21h 

    mov al, [bx]
    call print_al 
    mov dx, offset sep 
    mov ah, 9 
    int 21h
    mov al, [bx+1]
    call print_al 
    mov dx, offset sep 
    mov ah, 9 
    int 21h
    mov al, [bx+2]
    call print_al
    
    mov ah, 2
    mov dl, '|' 
    int 21h 
    ret 
print_row endp 

readnum proc
    mov bl, string[1]
    mov bh, 0
    mov string[bx+2], ' ' ; end of string 
    mov bx, offset string[2]
    mov ax, 0
    mov cx, 0    
    cmp byte ptr [bx], '-'
    jne readn 
        inc bx
        mov cx, 1 
    readn:
        mov ch, [bx]
        sub ch, '0' 
        mul ten 
        add al, ch
        inc bx 
        cmp byte ptr [bx], ' '
        jne readn 
    mov ch, 0
    jcxz readend 
        neg ax 
    readend:    
    ret 
readnum endp 

print_crlf proc 
    push ax 
    push dx
    mov ah, 2
    mov dl, 13 
    int 21h 
    mov dl, 10 
    int 21h

    pop dx 
    pop ax 
    ret 
print_crlf endp 



; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN