CODE SEGMENT
ASSUME CS:CODE 

ORG 256 

ROUTINE_DIR EQU 1CH*4

START:
	CMP BYTE PTR CS:[83H], "I"
	JE GO_INSTALL
	CMP BYTE PTR CS:[83H], "D"
	JE GO_UNINSTALL

	CALL IS_INSTALLED 
	CMP AX, 0
	JE P_NOT_I
		MOV DX, OFFSET INSTALLED
		JMP END_START
	P_NOT_I: MOV DX, OFFSET NOT_INSTALLED

	END_START:
	MOV AH, 9 
	INT 21H

	MOV DX, OFFSET INFO 
	INT 21H
RET

GO_INSTALL: JMP INSTALLER
GO_UNINSTALL: JMP UNINSTALLER


; Global variables
INFO DB "THIS IS THE DRIVER INFORMATION",13,10,"$"
INSTALLED DB "INSTALLED", 13,10,"$"
NOT_INSTALLED DB "NOT INSTALLED", 13,10,"$"
TIMER_TICK DB 0 
SIGNATURE DB "V"
; Interrupt service routine
isr PROC FAR

INC TIMER_TICK
iret
isr ENDP


IS_INSTALLED PROC 
	PUSH ES BX
	MOV AX, 0
	MOV ES, AX    
	LES BX, ES:[ROUTINE_DIR]
	CMP BYTE PTR ES:[BX-1], "V"
	JE YES
		MOV AX, 0
		JMP END_IS_INSTALLED

	YES: MOV AX, 1 
	
	END_IS_INSTALLED: 
	POP BX ES
	RET 
IS_INSTALLED ENDP

UNINSTALLER PROC
push ax bx cx ds es

CALL IS_INSTALLED
CMP AX, 0
JE U_NOT_INSTALLED

mov cx, 0
mov ds, cx ; Segment of interrupt vectors
mov es, ds:[ ROUTINE_DIR + 2 ] ; Read ISR segment
mov bx, es:[ 2Ch ] ; Read segment of environment from ISR’s PSP. 
mov ah, 49h
int 21h ; Release ISR segment (es)
mov es, bx
int 21h ; Release segment of environment variables of ISR; Set vector of interrupt 40h to zero
cli
mov ds:[ ROUTINE_DIR  ], cx ; cx = 0
mov ds:[ ROUTINE_DIR + 2 ], cx
sti
JMP END_UNINSTALLER

U_NOT_INSTALLED:
MOV DX, CS
MOV DS, DX
MOV DX, OFFSET NOT_INSTALLED
MOV AH,  9 
INT 21H

END_UNINSTALLER:

pop es ds cx bx ax
ret
UNINSTALLER ENDP

INSTALLER PROC
	mov ax, 0
	mov es, ax
	mov ax, OFFSET isr
	mov bx, cs
	cli
	mov es:[ ROUTINE_DIR ], ax
	mov es:[ ROUTINE_DIR + 2 ], bx
	sti
	mov dx, OFFSET installer
int 27h ; Terminate and stay resident; PSP, variables, isr routine
INSTALLER ENDP 

CODE ENDS
END START