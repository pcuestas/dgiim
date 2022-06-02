_GetASCIICursor proc far
    push bx
    mov ah, 8 
    mov bh, 0
    int 10h 
    mov ah, 0
    pop bx 
    ret 
_GetASCIICursor endp 
