code SEGMENT
ASSUME cs : code
ORG 256

I_DIR equ 1Ch*4

start: 
    call is_installed
    cmp ax, 0
    je do_installation
        mov dx, offset s_installed
        mov ah, 9
        int 21h
        call uninstaller
        
        call is_installed
        cmp ax, 0
        je do_installation 
            ;failure
            mov dx, offset s_installed
            mov ah, 9
            int 21h
            ret 

    do_installation: jmp installer 


; Global variables
info DB "This is the driver info",13,10,"$"
s_installed db "INSTALLED",13,10,"$"
s_not_installed db "NOT INSTALLED",13,10,"$"
timer_tick db 0
signature db "a"

; Interrupt service routine
isr PROC FAR
    inc timer_tick
iret
isr ENDP

is_installed proc 
    push es bx 
        mov ax, 0 
        mov es, ax
        les bx, es:[I_DIR]
        cmp byte ptr es:[bx-1], "a"
        je yes
            mov ax, 0
            jmp ii_end
        yes:    mov ax, 1

    ii_end: pop bx es 
    ret 
is_installed endp 

uninstaller PROC 
    push ax bx cx ds es
    mov cx, 0
    mov ds, cx ; Segment of interrupt vectors
    mov es, ds:[ I_DIR+2 ] ; Read ISR segment
    mov bx, es:[ 2Ch ] ; Read segment of environment from ISR’s PSP. 
    mov ah, 49h
    int 21h ; Release ISR segment (es)
    mov es, bx
    int 21h ; Release segment of environment variables of ISR; Set vector of interrupt 40h to zero
    cli
    mov ds:[ I_DIR ], cx ; cx = 0
    mov ds:[ I_DIR+2 ], cx
    sti
    pop es ds cx bx ax
    ret
uninstaller ENDP


installer PROC
    mov ax, 0
    mov es, ax
    mov ax, OFFSET isr
    mov bx, cs
    cli
    mov es:[ I_DIR ], ax
    mov es:[ I_DIR+2 ], bx
    sti
    mov dx, OFFSET installer
    int 27h ; Terminate and stay resident; PSP, variables, isr routine.
installer ENDP
code ENDS
END start