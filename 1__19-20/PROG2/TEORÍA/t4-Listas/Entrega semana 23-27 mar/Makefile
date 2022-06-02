main_list : main_list.o 
	gcc -no-pie -o main_list main_list.o -L. -llist-linux

main_list.o : main_list.c 
	gcc -c -Wall -ansi main_list.c

clean :
	rm main_list main_list.o

