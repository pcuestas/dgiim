######################################################################
## Fichero: calculaSumaMult.asm
## Descripción: Programa que calcula la suma de dos operandos y posteriormente multiplica por 2, todo a través de una función
## Fecha última modificación:

## Autores: 
## Asignatura: E.C. 1º grado
## Grupo de Prácticas:
## Grupo de Teoría:
## Práctica: 3
## Ejercicio: 2
######################################################################

.text

main:		
	#Lectura de X y guardado en un registro que pasaremos como parámetro
	lw $a0, X
	
	#Lectura de Y y guardado en un registro que pasaremos como parámetro
	lw $a1, Y	
		
	#Llamada a función
	jal calculaSumaMult
	
	#Guardado del retorno en la variable R de memoria
	sw $v0, R
	
	#Bucle infinito
	fin: j fin
	
calculaSumaMult:
		
	#Suma de los dos parámetros
	add $t2, $a0, $a1
	
	#Multiplica por dos
	sll $v0, $t2, 1
	
	#Retorno a main
	jr $ra	

.data
X: 10
Y: 4
R:
