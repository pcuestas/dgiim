##Prefix to infix:
	
* Read the Prefix expression in reverse order (from right to left):

- If the symbol is an operand, then push it onto the Stack
- If the symbol is an operator:
		pop two operands from the Stack
	Create a string by concatenating the two operands and the operator between them.
	*string = (operand1 + operator + operand2)*
    	And push the resultant string back to Stack

- Repeat the above steps until end of Prefix expression, then the output infix expression will be the only string left in the stack.

##Prefix to Postfix:

* Read the Prefix expression in reverse order (from right to left):

		If the symbol is an operand, then push it onto the Stack
		If the symbol is an operator: 
			pop two operands from the Stack
			Create a string by concatenating the two operands and the operator after them.
				*string = operand1 + operand2 + operator*
			Push the resultant string back to Stack
- Repeat the above steps until end of Prefix expression, then the output infix expression will be the only string left in the stack.


##Infix to prefix

- invert the string: eg. ( a - b * c ) / c + d <-> d + c / ( c * b - a )

- make conversion from infix to postfix

- invert the postfix expression

##Infix to postfix

* Read the Infix expression from left to right:

		If the symbol is an operand, write it in the output
		If the symbol is a '(':
				push it into the stack
		If the symbol is an operator:
			pop and add to the output until the top has a lower precedence than the read operator
			then push the read operator into the stack
		If the symbol is a ')':
			pop and add to the output until a '(' is encountered
			pop the '(' (but do not add it to the output)

- Repeat the above steps until end of Infix expression.

##Postfix to infix:
	
* Read the Postfix expression from left to right:

			If the symbol is an operand, then push it onto the Stack
			If the symbol is an operator:
				pop two operands from the Stack
				Create a string by concatenating the two operands and the operator between them.
					*string = (operand2 + operator + operand1)*
				Push the resultant string back to Stack

- Repeat the above steps until end of Prefix expression, then the output infix expression will be the only string left in the stack.

##Postfix to prefix

* Read the Postfix expression from left to right:

		If the symbol is an operand, then push it onto the Stack
		If the symbol is an operator: 
			pop two operands from the Stack
			Create a string by concatenating the two operands and the operator before them.
				*string = operator + operand2 + operand1*
			Push the resultant string back to Stack
- Repeat the above steps until end of Prefix expression, then the output infix expression will be the only string left in the stack.
