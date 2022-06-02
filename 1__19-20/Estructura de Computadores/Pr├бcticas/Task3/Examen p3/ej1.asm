######################################################################
## Fichero: ej1.asm
## Autor: PABLO CUESTA SIERRA
## Asignatura: E.C. 1� grado
## Grupo de Pr�cticas: 2151
## Grupo de Teor�a: 215
## Pr�ctica: 3
## Modelo A
## Ejercicio: 1
######################################################################

.text
main: 	lw $s0, N($0) # $s0 <= N

	addi $s1, $0, 0 # R = 0;
	
	#initialization of the loop (i=0)
	addi $s2, $0, 0 
	
	#condition: (if they are equal, it'll jump to the endloop label)
loop: 	beq $s2, $s0, endloop

	#body of the loop: (R = R + i)
	add $s1, $s1, $s2

	#loop operation: (i++)
	addi $s2, $s2, 1

	#back to the loop condition
	j loop

endloop: sw $s1, R($0)

end: j end

.data
N: 5
R:
