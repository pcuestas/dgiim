# AUTHOR: PABLO CUESTA SIERRA
# GROUP: 2292

NAME1=p4a
NAME2=p4b
NAME3=p4ac
NAME4=p4c

all: $(NAME3).com $(NAME4).exe $(NAME1).com $(NAME2).exe

# .com
$(NAME1).com: $(NAME1).obj
	tlink /t $*

$(NAME1).obj: $(NAME1).asm
	tasm /zi $*.asm,, $*.lst

$(NAME3).com: $(NAME3).obj
	tlink /t $*

$(NAME3).obj: $(NAME3).asm
	tasm /zi $*.asm,, $*.lst


# .exe
$(NAME2).exe: $(NAME2).obj
	tlink /v $*

$(NAME2).obj: $(NAME2).asm
	tasm /zi $*.asm,, $*.lst

$(NAME4).exe: $(NAME4).obj
	tlink /v $*

$(NAME4).obj: $(NAME4).asm
	tasm /zi $*.asm,, $*.lst

clean:
	del *.exe
	del *.obj
	del *.lst 
	del *.map
	