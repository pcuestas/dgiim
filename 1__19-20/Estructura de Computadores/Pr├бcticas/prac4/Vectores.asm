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
	add $a0, $a0, $a0 
	add $a0, $a0, $a0	
	
	#Initialization the for loop
	add $t0, $0, $0	
	
	#Checking the loop 
loop:	beq $a0, $t0, end	

	#Reading of A(i) --in t2
	lw $t2, A($t0)
	
	#Reading of B(i) --in t3
	lw $t3, B($t0)
	
	#Multiplication by 4 of B(i) --in t4
	add $t3, $t3, $t3
	add $t3, $t3, $t3

	#Addition t3=t2+t3
	add $t3, $t2, $t3
	
	#Writing in C(i)
	sw $t3, C($t0)
	
	#Loop opeartion
	addi $t0, $t0, 4
	
	#Jump to the comparison
	j loop
	
	#Infinitive loop
end: j end

.data 		# Start of user memory data
N: 6
A: 2,4,6,8,10,12
B: -1,-5,4,10,1,-5
C: .space 24 			#.space reserve space in bytes.


