######################################################################
## Fichero: Vectores.asm
## Descripción: Programa que...
## Fecha última modificación: 

## Autores: 
## Asignatura: E.C. 1º grado
## Grupo de Prácticas:
## Grupo de Teoría:
## Práctica: 3
## Ejercicio: 3
######################################################################

.text

main:		
	#Lee la variable N
	lw $4, N # Se carga N, que es el número de elementos del vector
	
	#Adaptación de N
	sll $4, $4, 2 # Por 4, y en vez de utilizar i se utiliza i*4, que es el offset dentro del vector
	
	#Inicializa el bucle for
	xor $5, $5, $5 # Inicializa $5 a 0
	
	#Comprobación del bucle
	inicioBucle: 	beq $4,$5, fin
	
	#Lectura de A(i)
	lw $1, A($5) # Carga del dato a[$5] en $1
	
	#Lectura de B(i)
	lw $2, B($5) # Carga del dato b[$5] en $2
	
	#Multiplicación por 4 de B(i)
	sll $2, $2, 2 # b[i]*4 en $3
	
	#Suma 
	add $3, $1, $2 # Ahora $4 vale a[i]+b[i]*4
	
	#Escritura en C(i)
	sw $3, C($5) # Se guarda el resultado en c[$5] 
	
	#Operación del bucle
	addi $5, $5, 4
	
	#Salto a la comparación
	j inicioBucle
	
	#Bucle infinito
	fin: j fin

.data # Comienzo de seccion de memoria de datos
A: 2,2,4,6,5,6,7,8,9,10
B: -1,-5,4,10,1,-2,5,10,-10,0
C: .space 40 #.space reserva el espacio determinado en bytes.
N: .word 10

