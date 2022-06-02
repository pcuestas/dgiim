imprimir_ASCIIZ proc far 
    push ax bx dx es
    mov ax, 0 
    mov es, ax
    mov dx, es:[0408h]
    inc dx ; dx state
    iterate:
        cmp byte ptr [bx], 0
        je end
        
        bsy1:
            in al, dx 
            test al, 10000000b
            jz bsy1
            ;send data byte 
            dec dx ; dx data 
            mov al, [bx]
            out dx, al 
            inc bx 
            ;rise strobe
            add dx, 2 ; dx control 
            in al, dx 
            or al, 00000001b
            out dx, al
            dec dx
        bsy2:
            in al, dx 
            test al, 10000000b
            jnz bsy2

            ;fall strobe 
            inc dx 
            in al, dx 
            and al, 11111110b
            out dx, al
            jmp iterate

end:
    pop es dx bx ax
    ret 
imprimir_ASCIIZ endp 
