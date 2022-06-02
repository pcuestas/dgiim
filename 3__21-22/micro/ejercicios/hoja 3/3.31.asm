rsi proc far
    sti
    push ax dx es

    mov ax, 0
    mov es, ax
    mov dx, es:[040Ah]
    add dx, 2 ; dx control 
    
    in al, dx           ; write bdir
    or al, 00100000b
    out dx, al

    sub dx, 2 ;  dx datos 
    in al, dx
    push ax 
    mov ax, 2
    push ax
    call _Process
    add sp, 4

    out dx, al ; send the data 
    add dx, 2  ; dx control
    in al, dx
    or al, 1b
    out dx, al ; strobe
    dec dx; dx state reg 
bsy: in al, dx 
    test al, 10000000b
    jnz bsy

    inc dx 
    in al, dx
    and al, 11111110b
    out dx, al ; not strobe     

endrsi:
    mov al, 20h 
    out 20h, al
    pop es dx ax
    iret 
rsi endp 
