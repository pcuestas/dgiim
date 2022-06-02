######################################################################
## File: Vectores.asm
## Description: Program to...
## Date: 

## Authors: 
## Subject: E.C. 1� grado
## Laboratory group: 2151 (couple 10)
## Theory group: 215
## Task: 3
## Exercise: 2
######################################################################

.text

main:		
	#Reading N variable
	lw $a0, N
	
	#Adaptation of N (x4)
	sll $a0, $a0, 2
	
	#Initialization the for loop
	add $t0, $zero, $zero	
	
	#Checking the loop 
loop:	slt $t1, $t0, $a0	#t1 is 1 iff t0 (i*4) is less than a0 (N*4)
	beq $t1, $zero, end	

	#Reading of A(i) --in t2
	lw $t2, A($t0)
	
	#Reading of B(i) --in t3
	lw $t3, B($t0)
	
	#Multiplication by 4 of B(i) --in t4
	sll $t4, $t3, 2
	
	#Addition t5=t2+t4
	add $t5, $t2, $t4
	
	#Writing in C(i)
	sw $t5, C($t0)
	
	#Loop opeartion
	addi $t0, $t0, 4
	
	#Jump to the comparison
	j loop
	
	#Infinitive loop
end: j end

.data 		# Start of user memory data
A: 2,2,4,6,5,6,7,8,9,10
B: -1,-5,4,10,1,-2,5,10,-10,0
C: .space 40 			#.space reserve space in bytes.
N: .word 10

