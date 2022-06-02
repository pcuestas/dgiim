N=test

all: $(N).exe

$(N).exe: $(N).obj
	tlink /v $* 

$(N).obj: $(N).asm
	tasm /zi $*.asm,, $*.lst

clean:
	del *.exe 
	del *.obj 
	del *.lst 