
# Generating 

## c code

    make

## scala code

    scala lib/scalabuff_2.9.2-0.9-SNAPSHOT.jar  --scala_out=src/main/scala/ bms.proto

# Running

## Scala code

    ./sbt  (or sbt.cmd if you're on windows)
    run
    
This generates bms.scala.out    
    
## C Code

     ./test_encode

This generates bms.c.out  If you run 'hexdump' on it you should see that it is exactly
the same as bms.scala.out.

You can decode from both files with the same result.

     ./test_decode < bms.c.out
     ./test_decode < bms.scala.out

     
    
    
