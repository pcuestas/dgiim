rsi proc far 
    sti
    push 
    
    mov al, 0Ch
    out 70h, al
    in al, 71h 

    cmp ds:_Transmit, 0 
    je endrsi
    
    mov bx, ds:_Index
    cmp ds:_Buffer[bx], 0
    je reset

    cmp ds:_Buffer[bx], '0'
    je deactivate 
        in al, 61h
        or al, 00000011b
        out 61h, al
        jmp next
    deactivate:
        in al, 61h
        and al, 11111100b
        out 61h, al
    next: ; IMPORTANTE!!!!!!
        inc ds:_Index
        jmp endrsi
reset:
    mov ds:_Index, 0
    mov ds:_Transmit, 0

endrsi:
    pop 
    iret 
rsi endp 
