import sys
from tabulate import tabulate

'''
Convert file with data to latex table using:
	https://github.com/astanin/python-tabulate

Authors: Pablo Cuesta Sierra and Álvaro Zamanillo Sáez
'''

file = sys.argv[1]

A=[]
f=open(file)
for line in f:
	A+=[line.split("\t")]

print(
	tabulate(
		A, 
		headers='firstrow', 
		tablefmt='latex',
		# floatfmt=".3f" # to print only 3 decimal places
	)
)
