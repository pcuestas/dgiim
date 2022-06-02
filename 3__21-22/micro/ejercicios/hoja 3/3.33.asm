config proc near 
    push ax dx es    
    mov ax, 0
    mov es, ax
    mov dx, es:[040Ah]
    inc dx
    in al, dx 
    and al 01111000b
    mov cl, 5
    shl ax, cl 
    mov al, 0Ah
    out 70h, al
    in al, 71h 
    and al, 0F0h
    or ah, al
    mov al, 0Ah
    out 70h, al 
    mov al, ah
    out 71h, al 

    pop es dx ax

    ret 
config endp 
