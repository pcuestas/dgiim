rtcrsi proc far
    sti 
    push ax bx dx
    ; read C reg
    mov al, 0Ch
    out 70h, al
    in al, 71h ;

    mov al , 10000000b
    out 43h, al
    in al, 42h
    mov bl, al
    in al, 42h 
    mov bh, al ; bx = count2_value
    ; currentHz = cte_rara / count2_value
    mov ax, 34DEh
    mov dx, 0012h 
    div bx ; ax = currentHz
    cmp ax, 1000
    jae end
        add ax, 100
        mov bx, ax 
        mov ax, 34DEh
        mov dx, 0012h
        div bx
        out 42h, al
        mov al, ah
        out 42h, al

    
end:
    mov al, 20h 
    out 20h, al 
    out 0A0h, al 
    pop dx bx ax  
    iret 
rtcrsi endp 
