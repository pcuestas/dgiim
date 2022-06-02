_ConfigurarRTC  proc far 
    push ax
    mov al, 0Ah
    out 70h, al
    mov al, 24h
    out 71h, al 

    in al, 0A1h
    and al, 11111110b
    out al, 0A1h
    in al, 021h
    and al, 11111011b
    out al, 021h 
    pop ax 
    ret 
_ConfigurarRTC endp 

_InstalarInterrupcionRTC proc far 
    push ax es
    mov ax, 0 
    mov es, ax
    cli
    mov es:[70h*4], OFFSET _RutServRTC
    mov es:[70h*4+2], SEG _RutServRTC
    sti 
    
    mov al, 0Bh
    out 70h, al
    in al, 71h 
    or al, 01000000b
    mov ah, al
    mov al, 0Bh
    out 70h, al
    mov al, ah
    out 71h, al 
    pop es ax
    ret 
_InstalarInterrupcionRTC endp 
_DesinstalarInterrupcionRTC proc far 
    push ax es
    mov ax, 0 
    mov es, ax
    cli
    mov es:[70h*4], 0ffffh
    mov es:[70h*4+2], 0ffffh
    sti 
    
    mov al, 0Bh
    out 70h, al
    in al, 71h 
    and al, 10111111b
    mov ah, al
    mov al, 0Bh
    out 70h, al
    mov al, ah
    out 71h, al 
    pop es ax
    ret 
_DesinstalarInterrupcionRTC endp 

_RutServRTC proc far 
    sti
    push ax cx dx es 

    mov al, 0Ch
    out 70h, al
    in al, 71h 

    mov ax, 0 
    mov es, ax  
    mov dx, es:[0408h]
    add dx, 2

    cmp _transmitiendo, 0
    je end
        mov cl, _posicion
        cmp cl, 9 
        je send_S
        cmp cl, 0 
        je initial
        dec cl
        mov ah, _dato
        shr ah, cl 
        and ah, 00000001b
        inc _posicion  
        jmp transmit
initial:
    mov ah, 0
    jmp tansmit 
send_S:     
    mov ah, 00000001b 
    mov _posicion, 0 
    mov _transmitiendo, 0 
transmit:
    in al, dx 
    test ah, 00000001b
    jz t0
        or al, 1 
        jmp trns
    t0: and al, 0FEh
    trns:
    out al, dx 
    
end:
    mov al, 20h
    out 20h, al
    out 0A0h, al
    pop es dx cx ax 
    iret 
_RutServRTC endp  
