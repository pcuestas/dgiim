Sound_500Hz proc far
    push ax
        mov ax, 1193182 / 500
        out 42h, al
        mov al, ah
        out 42h, al 

        in al, 61h 
        or al, 00000011b
        out 61h, al
    pop ax
    ret 

Sound_500Hz endp 
