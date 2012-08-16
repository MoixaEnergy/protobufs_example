
# Generating 

## c code

For pic32:

    make

For pc:
    
    make -f Makefile.pc

## scala code

    sed 's/\[(nanopb).*/;/' moixa.proto > moixa.proto.scala
    scala lib/scalabuff_2.9.2-0.9-SNAPSHOT.jar  --scala_out=src/main/scala/ moixa.proto.scala
    rm moixa.proto.scala

# Running

## Scala code

    ./sbt  (or sbt.cmd if you're on windows)
    run
    
This generates bms.scala.out    
    
## C Code (on PC)

     ./test_encode

This generates bms.c.out  If you run 'hexdump' on it you should see that it is exactly
the same as bms.scala.out.

You can decode from both files with the same result.

     ./test_decode < mcs.c.out
     ./test_decode < moixa.mcs.scala.out

     
    
    
