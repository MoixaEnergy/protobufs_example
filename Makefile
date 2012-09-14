CFLAGS=-ansi -Wall -Werror -I . -g -O0
DEPS=pb_decode.c pb_decode.h pb_encode.c pb_encode.h pb.h

all: test_encode test_decode

clean:
	rm -f moixa.pb moixa.pb.c moixa.pb.h siemens.pb siemens.pb.h siemens.pb.c test_encode test_decode

%: %.c $(DEPS) moixa.pb.h moixa.pb.c siemens.pb.h siemens.pb.c
	$(CC) $(CFLAGS) -o $@ $< pb_decode.c pb_encode.c moixa.pb.c 

siemens.pb.c siemens.pb.h: siemens.proto nanopb/generator/nanopb_generator.py
	protoc -I. -Inanopb/generator -I/usr/include -osiemens.pb $<
	python2 nanopb/generator/nanopb_generator.py siemens.pb
  

moixa.pb.c moixa.pb.h: moixa.proto nanopb/generator/nanopb_generator.py
	protoc -I. -Inanopb/generator -I/usr/include -omoixa.pb $<
	python2 nanopb/generator/nanopb_generator.py moixa.pb

