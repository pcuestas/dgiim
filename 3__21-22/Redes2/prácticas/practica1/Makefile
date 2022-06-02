# Autor: Pablo Cuesta Sierra

CFLAGS=-pedantic -Wall -g -Wdeclaration-after-statement -Wint-to-pointer-cast -Wpointer-to-int-cast
CC=gcc
LIB=-pthread -lconfuse#other libraries

BUILD_DIR=build
OBJ_DIR=obj
SRC_DIR=src
INCLUDES_DIR=includes
MY_LIB_DIR=lib

EXE=$(BUILD_DIR)/server
MY_LIB=$(MY_LIB_DIR)/lib_socket.a $(MY_LIB_DIR)/lib_picohttpparser.a $(MY_LIB_DIR)/lib_yuarel.a

INCLUDE_FILES=$(wildcard $(INCLUDES_DIR)/*.h)
SRC_FILES=$(wildcard $(SRC_DIR)/*.c)
OBJ_FILES=$(SRC_FILES:${SRC_DIR}/%.c=$(OBJ_DIR)/%.o)

##################################################

all: library $(EXE)

run: all
	./$(EXE)

$(EXE): $(OBJ_FILES) $(MY_LIB) 
	@echo "# -------------------------------"
	@echo "Linking $@"
	mkdir -p $(BUILD_DIR)
	$(CC) $^ $(LIB) -o  $@

$(OBJ_FILES): $(OBJ_DIR)/%.o: $(SRC_DIR)/%.c $(INCLUDE_FILES)
	@echo "# -------------------------------"
	@echo "# Compiling $<"
	mkdir -p $(OBJ_DIR)
	$(CC) $(CFLAGS) -o $@ -c $<

#$(MY_LIB):
library:
	+$(MAKE) all -C $(MY_LIB_DIR)

clean:
	rm -f $(OBJ_FILES) $(EXE)
	+$(MAKE) clean -C $(MY_LIB_DIR)

stop_server:
	kill -SIGINT $(shell pgrep server)
	