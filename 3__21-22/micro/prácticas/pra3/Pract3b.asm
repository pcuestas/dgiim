; Practice 3

DGROUP GROUP _DATA, _BSS ; Data segments are grouped

_DATA SEGMENT WORD PUBLIC 'DATA' ; Public data segment
_DATA ENDS

_BSS SEGMENT WORD PUBLIC 'BSS' ; Public data segment
_BSS ENDS


_TEXT SEGMENT BYTE PUBLIC 'CODE' ; Definition of the code segment
    ASSUME CS:_TEXT, DS:DGROUP

PUBLIC _SubstringFinder 
PUBLIC _strcmp2 
PUBLIC _SecondComputDC
EXTRN _strlen:FAR


_SecondComputDC PROC FAR ; unsigned int SecondComputDC (char* numCuenta)
    push bp 
    mov bp, sp 

    push bx si ds di dx

    lds bx, [bp + 6]
    push ds bx 
    call _strlen 
    add sp, 4
    cmp ax, 10 ; if there are not 10 digits, return -1
    jne second_compute_fail
    mov si, 9
    mov di, 0               ; di == sum = 0 
    second_compute_loop:
        shl di, 1 ; sum *= 2  
        mov ah, 0 
        mov al, [bx][si]
        sub ax, '0'
        add di, ax ; sum += next_digit
        dec si 
        jns second_compute_loop 

    mov dx, 0
    mov ax, di 
    mov si, 11
    div si          ; dx = sum%11
    sub si, dx      ; si = 11-sum%11
    mov ax, si
    cmp ax, 10 
    jne second_compute_end
        mov ax, 1
    jmp second_compute_end
    second_compute_fail:
        mov ax, -1 
    second_compute_end:
    pop dx di ds si bx
    pop bp 
    ret 
_SecondComputDC ENDP

; this function checks if str1 is exactly the beginning of str2 
; strcmp2("asd", "asdasdfdf") returns 1
; strcmp2("asd", "aaaasdf") returns 0
_strcmp2 PROC FAR; int strcmp2(char*str1, char*str2)
    push bp 
    mov bp, sp 

    push ds es bx

    lds bx, [bp+6]
    les bp, [bp+10]

    dec bx 
    dec bp

    compare_letter:
        inc bx
        inc bp 
        mov al, [bx]
        cmp al, 0
        je strcmp2_success
        cmp al, es:[bp]
        je compare_letter

    ; not equal
    mov ax, 0
    jmp strcmp2_end

    strcmp2_success:
        mov ax, 1

    strcmp2_end:
    pop bx es ds 
    pop bp 
    ret 
_strcmp2 ENDP 

_SubstringFinder PROC FAR ;  int SubstringFinder (char* str, char* substr)
    push bp 
    mov bp, sp 

    push ds es bx si di 
    
    lds bx, [bp+6]; ds:bx := str 
    les bp, [bp+10]
    mov si, 0

    check_letter:
        cmp BYTE PTR [bx][si], 0 
        je not_found
        
        mov di,bx 
        add di,si
        push ds
        push di 
        push es
        push bp
        call _strcmp2
        add sp, 8
        test ax, 1
        jnz found
        inc si 
        jmp check_letter

    found:
        mov ax, si 
        jmp end_finder

    not_found:
        mov ax, -1
    
    end_finder:
    pop di si bx es ds
    pop bp 
    ret 
_SubstringFinder ENDP

_TEXT ENDS
END