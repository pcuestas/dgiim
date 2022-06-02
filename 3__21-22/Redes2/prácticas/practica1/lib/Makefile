# Author: Pablo Cuesta Sierra

CFLAGS=-pedantic -Wall -g -Wdeclaration-after-statement
CC=gcc

OBJ_DIR=../obj
SRC_DIR=../srclib
INCLUDES_DIR=../includes

SRC_FILES=$(wildcard $(SRC_DIR)/*.c)
OBJ_FILES=$(SRC_FILES:${SRC_DIR}/%.c=$(OBJ_DIR)/%.o)

SOCKET_LIB=lib_socket.a
HTTP_PARSER_LIB=lib_picohttpparser.a
URL_PARSER_LIB=lib_yuarel.a

LIB=$(SOCKET_LIB) $(HTTP_PARSER_LIB) $(URL_PARSER_LIB)

##################################################

all: ${LIB}

./%.a : $(OBJ_DIR)/%.o $(INCLUDES_DIR)/%.h 
	@echo "# -------------------------------"
	@echo "# Compiling $@"
	ar rcs $@ $<

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c
	@echo "# -------------------------------"
	@echo "# Compiling $<"
	mkdir -p $(OBJ_DIR)
	$(CC) $(CFLAGS) -o $@ -c $<

clean:
	rm -f $(OBJ_FILES) $(LIB)

