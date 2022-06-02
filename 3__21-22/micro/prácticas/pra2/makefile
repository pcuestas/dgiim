# AUTHOR: PABLO CUESTA SIERRA
# GROUP: 2292

NAME1=Lab2
NAME2=Lab1C

all: $(NAME1).exe


$(NAME1).exe: $(NAME1).obj
	tlink /v $*

$(NAME1).obj: $(NAME1).asm
	tasm /zi $*.asm,, $*.lst

clean:
	del *.exe
	del *.obj
	del *.lst 
	del *.map
	