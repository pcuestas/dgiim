######################################################################
## Fichero: calculaSumaMult.asm
## Descripci�n: Programa que calcula la suma de dos operandos y posteriormente multiplica por 2, todo a trav�s de una funci�n
## Fecha �ltima modificaci�n:

## Autores: 
## Asignatura: E.C. 1� grado
## Grupo de Pr�cticas:
## Grupo de Teor�a:
## Pr�ctica: 3
## Ejercicio: 2
######################################################################

.text

main:		
	#Lectura de X y guardado en un registro que pasaremos como par�metro
	lw $a0, X
	
	#Lectura de Y y guardado en un registro que pasaremos como par�metro
	lw $a1, Y	
		
	#Llamada a funci�n
	jal calculaSumaMult
	
	#Guardado del retorno en la variable R de memoria
	sw $v0, R
	
	#Bucle infinito
	fin: j fin
	
calculaSumaMult:
		
	#Suma de los dos par�metros
	add $t2, $a0, $a1
	
	#Multiplica por dos
	sll $v0, $t2, 1
	
	#Retorno a main
	jr $ra	

.data
X: 10
Y: 4
R:
