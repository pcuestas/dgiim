;**************************************************************************
; name...
;**************************************************************************
; DATA SEGMENT DEFINITION
DATA SEGMENT
    A DB -120,0,1,3,0,0,5,1,1
    B DB 1,0,1,1,2,1,1,1,0
    C DB 9 dup(?)
org 100h
    STRING DB 100 dup(?)
    aux1 DB "        $"
    auxA DB "  |A|=  $"
    auxB DB "  |B|=  $"
    auxC DB "  |C|=  $"
    oper DB "(C)=(A)-(B)$"
    SEP  DB "  $"
    TEN  DB 10
    
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
    
    mov bx, offset A
    mov di, offset auxA
    call print_matrix
    call print_crlf

    mov bx, offset B 
    mov di, offset auxB
    call print_matrix 
    call print_crlf 
    
    mov dx, offset oper 
    call print_string 
    call print_crlf
    
    call calculate_C
    
    mov bx, offset C
    mov di, offset auxC
    call print_matrix
    ; END OF THE PROGRAMME
    MOV AX, 4C00H
    INT 21H
BEGIN ENDP

calculate_C proc 
    mov si, 9
    calc_c: 
        mov al, A[si-1]
        sub al, B[si-1]
        mov C[si-1], al
        dec si 
        jnz calc_c
    ret 
calculate_C endp 

; prints $ ended string pointed by dx
print_string proc 
    push ax
    mov ah, 9h 
    int 21h  
    pop ax 
    ret
print_string endp 

print_crlf proc 
    push ax 
    push dx 

    mov ah, 2h
    mov dl, 13
    int 21h 
    mov dl, 10 
    int 21h 

    pop dx
    pop ax 
    ret
print_crlf endp 

print_matrix proc 
    mov dx, offset aux1 
    call print_string
    
    call print_row      
    call print_crlf 
    add bx, 3 
     
    mov dx, di
    call print_string 
    call print_row       
    call print_crlf  
    add bx, 3 
    
    mov dx, offset aux1 
    call print_string
     
    call print_row      
    call print_crlf 
    ret
print_matrix endp 

print_row proc 
    push bx

    mov ah, 2h 
    mov dl, '|' 
    int 21h 
     
    mov al, [bx] 
    inc bx 
    call print_al
    mov dx, offset SEP 
    call print_string     
    
    mov al, [bx] 
    inc bx 
    call print_al
    mov dx, offset SEP 
    call print_string   

    mov al, [bx] 
    inc bx 
    call print_al

    mov ah, 2h 
    mov dl, '|' 
    int 21h 

    pop bx
    ret 
print_row endp 

print_al proc 
    mov si, offset STRING[3]
    mov STRING[0],' '
    mov STRING[1],' '
    mov STRING[2],' '
    mov STRING[3],' '
    mov STRING[4],'$'
    mov cx, 0
    cmp al, 0 
    jge cont 
        mov cx, 1
        neg al
    cont:
        mov ah, 0
        div TEN ; AH=AX%10, AL=AX/10
        add ah, '0'
        mov [si], ah
        dec si
        cmp al, 0 
        jne cont 
    cmp cx,0 
    je fin 
        mov BYTE PTR [si], '-'
    fin: 
    mov dx, offset STRING 
    call print_string
    ret  
print_al endp 

; END OF THE CODE SEGMENT
CODE ENDS
; END OF THE PROGRAMME POINTING OUT WHERE THE EXECUTION BEGINS
END BEGIN