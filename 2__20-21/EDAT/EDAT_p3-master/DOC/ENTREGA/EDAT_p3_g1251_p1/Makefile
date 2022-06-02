CC = gcc -g
CFLAGS = -Wall -Wextra -pedantic -ansi
# LDLIBS = -lodbc

# recompile if this header changes
HEADERS = utils.h

EXE = tester
OBJ = $(EXE).o utils.o checkUtils.o checkCreateTable.o checkReplaceExtensionByIdx.o \
checkCreateIndex.o checkPrint.o checkFindKey.o checkAddIndexEntry.o checkAddTableEntry.o

all : $(EXE)

%.o: %.c $(HEADERS)
	@echo Compiling $<...
	$(CC) $(CFLAGS) -c -o $@ $<

$(EXE): $(DEPS) $(OBJ)
	@echo $@...
	$(CC) -o $(EXE) $(OBJ) $(LDLIBS)

clean :
	rm -f *.o core $(EXE)

run: $(EXE)
	@echo Running $<...
	./$(EXE)


