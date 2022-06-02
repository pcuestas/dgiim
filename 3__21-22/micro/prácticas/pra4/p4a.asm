INTERR_ADDR EQU 57h
ROW_LEN EQU 6 

code SEGMENT
    ASSUME cs:code 

    ORG 256 
    start: 
        cmp byte ptr cs:[83h], "I"
        jne next_check1
        jmp installer 
        next_check1:
        cmp byte ptr cs:[83h], "D"
        jne next_check2
        jmp uninstaller
        next_check2:

        call installed_ax 
        cmp ax, 0
        je print_not 
            mov dx, OFFSET INSTALLED
            jmp print_installed
        print_not:
            mov dx, OFFSET NOT_INSTALLED
        print_installed:
        mov bx, cs
        mov ds, bx
        mov ah, 9h
        int 21h 

        mov ah, 9h
        mov dx, OFFSET POLYBIUS
        int 21h

        mov ax, 4C00h
        int 21h 

    ; variables
    POLYBIUS DB "Driver info.",13,10,"$"
    INSTALLED_MSG DB "Driver already installed",13,10,"$"
    NOT_INSTALLED_MSG DB "Driver not installed",13,10,"$"
    INSTALLED DB "INSTALLED", 13, 10, "$"
    NOT_INSTALLED DB "NOT INSTALLED", 13, 10, "$"
    signature db "k"

    isr PROC FAR
        push ax bx cx dx 
        
        mov bx, dx 
        mov cx, ROW_LEN

        cmp ah, 10h
        je codification 
        ; decodification 
        cmp byte ptr [bx], '$'
        je end_FUNCT 
            decodification_loop:
                mov dx, [bx]
                add dl, -1-'0' 
                add dh, -1-'0'
                mov al, dl 
                mul cl     
                add al, dh 
                cmp al, 'Z'-'A'
                jg decodification_number 
                ; letter 
                    add al, 'A'
                    jmp decodification_print
                decodification_number:
                    sub al, 'Z'-'A'+1-'0'
                decodification_print:
                    call print_char            
                cmp byte ptr [bx+2], '$'
                je end_FUNCT
                add bx, 3
                jmp decodification_loop            

        codification:
            codification_loop:
                mov al, [bx]
                cmp al, '$'
                je end_FUNCT
                cmp al, 'A'
                jge codification_letter 
                    ; it is a number 
                    add al, 'Z'-'A'+1-'0'
                    jmp codification_operation
                codification_letter:
                    sub al, 'A'    
                codification_operation:
                    mov ah, 0 
                    div cl ; ax /= 6
                    add al, 1+'0'
                    call print_char  
                    add ah, 1+'0'
                    mov al, ah 
                    call print_char
                    mov al, ' '
                    call print_char
                    inc bx 
                    jmp codification_loop

        end_FUNCT:
        
        mov al, 13 
        call print_char
        mov al, 10 
        call print_char 

        pop dx cx bx ax 
        iret 
    isr ENDP 

    ; prints char in al 
    print_char PROC 
        push ax dx
        mov dl, al 
        mov ah, 2h 
        int 21h
        pop dx ax 
        ret 
    print_char ENDP 

    installed_ax proc near
        push bx cx es ds
        mov cx, 0
        mov ds, cx ; Segment of interrupt vectors
        les bx, ds:[ INTERR_ADDR*4 ]
        cmp byte ptr es:[bx-1], "k"
        jne not_installed_ax
            mov ax, 1
            jmp end_installed_ax
        not_installed_ax:
            mov ax, 0
        end_installed_ax:
        pop ds es cx bx
        ret 
    installed_ax endp 

    uninstaller PROC 
        push ax bx cx ds es
        mov cx, 0
        mov ds, cx ; Segment of interrupt vectors
        mov es, ds:[ INTERR_ADDR*4+2 ] ; Read ISR segment
        mov bx, ds:[ INTERR_ADDR*4 ]
        cmp byte ptr es:[bx-1], "k"
        jne not_installed_uninstaller
        mov bx, es:[ 2Ch ] ; Read segment of environment from ISR’s PSP. 
        mov ah, 49h
        int 21h ; Release ISR segment (es)
        mov es, bx
        int 21h ; Release segment of environment variables of ISR; Set vector of interrupt 40h to zero
        cli
        mov ds:[ INTERR_ADDR*4 ], cx ; cx = 0
        mov ds:[ INTERR_ADDR*4+2 ], cx
        sti
        jmp end_uninstaller

        not_installed_uninstaller:
            mov bx, cs
            mov ds, bx
            mov dx, offset NOT_INSTALLED_MSG
            mov ah, 9 
            int 21h

        end_uninstaller:
        pop es ds cx bx ax
        ret
    uninstaller ENDP 

    installer PROC
        mov ax, 0 
        mov es, ax 
        mov ax, OFFSET isr 
        mov bx, cs 

        cli 
        mov es:[INTERR_ADDR*4], ax 
        mov es:[INTERR_ADDR*4+2], bx
        sti 
        mov dx, OFFSET installer  
        int 27h 
    installer ENDP 

code ENDS 
END start 
