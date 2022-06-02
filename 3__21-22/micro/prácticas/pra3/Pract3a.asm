; Practice 3

DGROUP GROUP _DATA, _BSS ; Data segments are grouped

_DATA SEGMENT WORD PUBLIC 'DATA' ; Public data segment
_DATA ENDS

_BSS SEGMENT WORD PUBLIC 'BSS' ; Public data segment
_BSS ENDS


_TEXT SEGMENT BYTE PUBLIC 'CODE' ; Definition of the code segment
    ASSUME CS:_TEXT, DS:DGROUP

PUBLIC _OddPositive ; Make function accessible from C
PUBLIC _DigitComputation 
PUBLIC _NextPrime 

_OddPositive PROC FAR ; int OddPositive(int num)
    PUSH BP    ; For using BP in order to address the stack,
    MOV BP, SP ; it is loaded with pointer to the top
     
    mov ax, [bp + 6]
    test ax, 8000h ; check if ax is negative
    jnz negative
        and ax, 1  ; if it is positive, check odd or even
        jmp end_odd_positive
    negative:
        xor ax, ax 
    end_odd_positive:
    POP BP ; Restore BP
    RET ; Return to calling procedure
_OddPositive ENDP

_DigitComputation PROC FAR ; int DigitComputation (unsigned int num, unsigned int pos)
    PUSH BP    ; For using BP in order to address the stack,
    MOV BP, SP ; it is loaded with pointer to the top
    push cx bx dx
    
    mov ax, [bp + 6] ; num
    mov cx, [bp + 8] ; pos
    xor dx, dx ; for 16 bits division
    mov bx, 10 ; divisor
    jcxz digit 
    digit_computation:
        div bx ; divide ax by ten 
        xor dx, dx
        dec cx 
        jnz digit_computation

    digit:
        div bx
        mov ax, dx

    pop dx bx cx
    POP BP ; Restore BP
    RET ; Return to calling procedure
_DigitComputation ENDP

_isPrime PROC NEAR
    push bp 
    mov bp, sp 

    push dx bx cx si

    mov bx, [bp + 4]
    ; some base cases: 
    cmp bx, 2
    je prime 
    cmp bx, 3
    je prime 
     
    ; the divisor to check:
    mov cx, 2 

    mov ax, bx 
    div cx 
    inc ax
    mov si, ax ; si now holds number/2+1 - the maximum number to check divisibility
    is_prime:
        mov ax, bx 
        xor dx, dx 
        div cx 
        inc cx 
        cmp dx, 0
        je not_prime 
        cmp cx, si 
        je prime 
        jmp is_prime 
    
    not_prime:
        mov ax, 0
        jmp is_prime_end
    prime:
        mov ax, 1

    is_prime_end:
    pop si cx bx dx  
    
    pop bp 
    RET 
_isPrime ENDP 

_NextPrime PROC FAR ; unsigned int NextPrime (unsigned int n)
    PUSH BP    ; For using BP in order to address the stack,
    MOV BP, SP ; it is loaded with pointer to the top
    
    push bx

    mov bx, [bp + 6]
    next_prime:
        inc bx 
        push bx 
        call _isPrime
        pop bx 
        cmp ax, 0
        je next_prime

    mov ax, bx 

    pop bx 

    POP BP ; Restore BP
    RET ; Return to calling procedure
_NextPrime ENDP




_TEXT ENDS
END