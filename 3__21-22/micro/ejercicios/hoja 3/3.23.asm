VUmeter proc near
    push ax cx dx es
    mov cl, ah
    mov ax, 0FF00h
    rol ax, cl 
    
    mov cx, 0
    mov es, cx
    mov dx, es:[0408h]
    out dx, al

    pop es dx cx ax 
    ret 
VUmeter endp 
