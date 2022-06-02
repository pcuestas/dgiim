import sys

print("Welcome to the server:")

try:
    print(sys.argv[1].split("=")[1])
except: 
    print("No name given")

