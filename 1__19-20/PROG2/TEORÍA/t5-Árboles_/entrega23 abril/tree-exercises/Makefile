CC=gcc
CFLAGS=-Wall -g
CFLAGSOPT=-O2
IFLAGS=-I./
LDFLAGS=-L./
LDLIBS=-ltree

all: test_tree


######################################################################
# $@ es el item que aparece a la izquierda de ':'
# $< es el primer item en la lista de dependencias
# $^ son todos los archivos que se encuentran a la derecha de ':'
########################################################################

test_tree: test_tree.c file_utils.c elements.c libtree.a
	$(CC) -o test_tree $(CFLAGS) test_tree.c file_utils.c elements.c $(LDFLAGS) $(LDLIBS)
clean:
	rm  -f test_tree
