all: eb.exe ea.com ed.exe ec.com

eb.exe: eb.obj
 tlink /v eb
eb.obj: eb.asm
 tasm /zi eb.asm,,eb.lst

ea.com: ea.obj
 tlink /t ea
ea.obj: ea.asm
 tasm /zi ea.asm,,ea.lst

ed.exe: ed.obj
 tlink /v ed
ed.obj: ed.asm
 tasm /zi ed.asm,,ed.lst

ec.com: ec.obj
 tlink /t ec
ec.obj: ec.asm
 tasm /zi ec.asm,,ec.lst
