_porcentaje_vocales proc near
    push bp 
    mov pb, sp 
    push bx cx dx si 

    mov bx, [bp+4]
    mov si, 0
    mov ax, 0 
    mov cl, [bx][si]
  while:
    cmp cl, 0 
    je end_while
    cmp cl, 'A'
    je vocal
    cmp cl, 'E'
    je vocal
    cmp cl, 'I'
    je vocal
    cmp cl, 'O'
    je vocal
    cmp cl, 'U'
    jne no_vocal
    
  vocal: inc ax 
  no_vocal: inc si
    mov cl, [bx][si]
    jmp while
  end_while:
    mov dx, 100
    mul dx
    div si 

    pop si dx cx bx
    ret 
_porcentaje_vocales endp 
