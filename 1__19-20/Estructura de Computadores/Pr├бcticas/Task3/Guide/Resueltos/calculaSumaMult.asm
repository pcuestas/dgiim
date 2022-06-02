######################################################################
## File: calculaSumaMult.asm
## Description: Progam which calculates the sum of two operators, then
## multiply by 2, everything with a function
## Date:

## Authors: Pablo Cuesta
## Subject: E.C. 1� grado
## Laboratory group: 2151 (couple 10)
## Theory group: 215
## Task: 3
## Exercise: 1
######################################################################

.text

main:		
lw $a0, X	#Reading of X variable and save it in a register, which will pass like parameter

	
lw $a1, Y	#Reading of Y variable and save it in a register, which will pass like parameter
	
		
jal calculaSumaMult	#Function call
	
	
sw $v0, R	#Saving the return in the R variable of memory
	
	
loop: j loop	#Infinitive loop
	
	
calculaSumaMult:
		
add $v0, $a0, $a1	#Suma of two parameters
	
	
sll $v0, $v0, 1	#Multiply by two
	
	
jr $ra	#Return to main
	

	

.data
X: 10
Y: 4
R:
