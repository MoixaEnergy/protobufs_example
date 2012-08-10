CFLAGS=-ansi -Wall -Werror -I nanopb -g -O0
DEPS=nanopb/pb_decode.c nanopb/pb_decode.h nanopb/pb_encode.c nanopb/pb_encode.h nanopb/pb.h

all: test_encode test_decode

clean:
	rm -f client bms.pb bms.pb.c bms.pb.h test_encode test_decode

%: %.c $(DEPS) bms.pb.h bms.pb.c
	$(CC) $(CFLAGS) -o $@ $< nanopb/pb_decode.c nanopb/pb_encode.c bms.pb.c 

bms.pb.c bms.pb.h: bms.proto nanopb/generator/nanopb_generator.py
	protoc -I. -Inanopb/generator -I/usr/include -obms.pb $<
	python2 nanopb/generator/nanopb_generator.py bms.pb

