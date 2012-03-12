#
# Makefile for the Mulle Software
#
# This makefile is common for all projects using the Mulle Hardware Platform.
# Project specific settings, sources, and include directories are specified 
# in the file named "SOURCES", which should be located in this folder.
#
#



# Specify CPU flag
CPU = -mcpu=m16c
PREFIX=m32c-elf

# Specify compiler to be used
CC = $(PREFIX)-gcc

# Specify Assembler to be used
AS = $(PREFIX)-as

# Specify linker to be used
LD = $(PREFIX)-ld

# Read sources and include dirs.
include SOURCES.mk

#DEBUG_FLAGS=-gdwarf-2

# Common compiler flags
CFLAGS = -nostartfiles -Wpacked -Wall -falign-functions=2

CFLAGS:=$(CFLAGS) $(INCLUDES)

ALL_FLAGS = $(CFLAGS) $(DEBUG_FLAGS) $(CPU)

$(EXECUTABLE).mot : $(EXECUTABLE).out
	$(PREFIX)-objcopy -O srec $(EXECUTABLE).out $@

#$(EXECUTABLE).outx : $(SOURCES) $(EXECUTABLE).ls
#	$(CC) $(ALL_FLAGS) -DROMSTART -DDEBUG -T$(EXECUTABLE).ls -Xlinker -Map -Xlinker $(EXECUTABLE).map -o $@ $(SOURCES) -lm -lc -lgcc 
	
	
$(EXECUTABLE).out : $(SOURCES:.c=.o)
	$(CC) $(ALL_FLAGS) -DDEBUG -T../../Library/m16c/m16c62-31k-384k.ld -o $@ ../../Library/m16c/crt.S $(SOURCES:.c=.o) -lm -lc -lgcc

%.o: %.c
	$(CC) $(ALL_FLAGS) -DDEBUG -c -o $@ $^


##$(CC) $(ALL_FLAGS) -DROMSTART -DDEBUG -T$(EXECUTABLE).ls -Xlinker -Map -Xlinker $(EXECUTABLE).map -o $@ $(SOURCES) -lm -lc -lgcc 

all: $(EXECUTABLE).mot

rom: $(EXECUTABLE).mot

clean:
	rm -f *.o *.out *.mot *.map $(filter %.o, $(SOURCES:.c=.o))

# below is only for Mac/Linux
USB_DEVICE = /dev/ttyUSB0
program: 
	sm16cf -b 57600 -d $(USB_DEVICE) -u  -i $(EXECUTABLE).mot --flash-program

debug:
	../mulle_term/mulle_term $(USB_DEVICE)

# Notes:
# $@ means "The file name of the target of the rule"
# $< means "The name of the first prerequisite"
# $+ means "The names of all the prerequisites, with spaces between them, exactly as given"
# For more magic automatic variables, see
# <http://www.gnu.org/software/make/manual/html_chapter/make_10.html#SEC111>
