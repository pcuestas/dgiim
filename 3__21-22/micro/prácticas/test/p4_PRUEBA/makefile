all: p4a.com p4b.exe p4C.com p4D.exe

p4a.com: p4a.obj
 tlink /t p4a
p4a.obj: p4a.asm
 tasm /zi p4a.asm ,, p4a.lst

p4b.exe: p4b.obj
 tlink /v p4b
p4b.obj: p4b.asm
 tasm /zi p4b.asm ,, p4b.lst

p4C.com: p4C.obj
 tlink /t p4C
p4C.obj: p4C.asm
 tasm /zi p4C.asm ,, p4C.lst

p4D.exe: p4D.obj
 tlink /v p4D
p4D.obj: p4D.asm
 tasm /zi p4D.asm ,, p4D.lst