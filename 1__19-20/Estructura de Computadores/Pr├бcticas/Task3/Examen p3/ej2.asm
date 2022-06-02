######################################################################
## Fichero: ej2.asm
## Autor: PABLO CUESTA SIERRA
## Asignatura: E.C. 1� grado
## Grupo de Pr�cticas: 2151
## Grupo de Teor�a: 215
## Pr�ctica: 3
## Modelo: A
## Ejercicio: 2
######################################################################

.text

main:	lw $a0, A($0) #load from the memory the argument of the function
	
	jal funcionAux #jump to the function
	
	sw $v0, R($0) #store the result of the function in R
	
end: j end
	
funcionAux: sll $v0, $a0, 1 #multiply x2 the argument and keep it in $v0
	
	jr $ra #go back to the main
	

.data
A: 6
R:
