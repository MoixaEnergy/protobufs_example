CFLAGS=-ansi -Wall -Werror -I nanopb -g -O0
DEPS=nanopb/pb_decode.c nanopb/pb_decode.h nanopb/pb_encode.c nanopb/pb_encode.h nanopb/pb.h

all: test_encode test_decode

clean:
	rm -f client moixa.pb moixa.pb.c moixa.pb.h test_encode test_decode

%: %.c $(DEPS) moixa.pb.h moixa.pb.c
	$(CC) $(CFLAGS) -o $@ $< nanopb/pb_decode.c nanopb/pb_encode.c moixa.pb.c 

moixa.pb.c moixa.pb.h: moixa.proto nanopb/generator/nanopb_generator.py
	protoc -I. -Inanopb/generator -I/usr/include -omoixa.pb $<
	python2 nanopb/generator/nanopb_generator.py moixa.pb

