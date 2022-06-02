.text
main: 	lw $a0, N($0)
	jal factorial
	sw $v0, F($0)
	
factorial: add $sp, $sp, -8
	sw $ra, 4($sp) 
	sw $a0, 0($sp) 
	
	bne $a0, $0, noteq
	addi $v0, $0, 1
	lw $a0, 0($sp) 
	lw $ra, 4($sp)
	add $sp, $sp, 8
	jr $ra
	
noteq: addi $a0, $a0, -1
	jal factorial
	lw $ra, 4($sp) 
	lw $a0, 0($sp)
	add $sp, $sp, 8
	mul $v0, $a0, $v0
	jr $ra
	
	
	

.data
N: 5
F: