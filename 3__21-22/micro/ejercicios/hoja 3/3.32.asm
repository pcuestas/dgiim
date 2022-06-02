pin proc 
    push ax dx es

    mov ax, 0
    mov es, ax 
    mov dx, es:[040Ch]
    inc dx 
    in al, dx 
    test al, 00001000b
    jz cero
        mov ax, 1193182/256
        jmp send
    cero:
        mov ax, 1193182/1024
    send: 
        out 42h, al
        mov al, ah
        out 42h, al 
    pop es dx ax
    ret 
pin endp 
