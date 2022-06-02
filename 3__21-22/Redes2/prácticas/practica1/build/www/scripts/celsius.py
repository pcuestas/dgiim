import sys

try:
    celsius=float(sys.argv[1].split("=")[1])
    farenheit=celsius*1.8+32
    print("{} celsius degrees are {} farenheit degrees".format(celsius, farenheit))
except: 
    print("Please enter a valid temperature")


