do proc far 
    push es dx cx ax 
    mov al, ah
    mov ah, 0
    push ax
    call _BinaryToGray 
    add sp, 2
    mov cl, 12
    shl ax, cl ; ah = bits0000
    mov cx, 0
    mov es, cx
    mov dx, es:[040Ah]
    in al, dx 
    and al, 0Fh
    or al, ah
    out dx, al 
    pop ax cx dx es
    ret 
do endp 
