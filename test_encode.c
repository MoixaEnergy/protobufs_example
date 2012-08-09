#include <stdio.h>
#include <pb_encode.h>
#include "bms.pb.h"

int main()
{
    BmsMessage msg = {MessageType_STATUS, true, {true, 99}, false};

    uint8_t buffer[512];
    pb_ostream_t stream = pb_ostream_from_buffer(buffer, sizeof(buffer));
    
    /* Now encode it and check if we succeeded. */
    if (pb_encode(&stream, BmsMessage_fields, &msg))
    {
        fwrite(buffer, 1, stream.bytes_written, stdout);
        FILE *file; 
        file = fopen("bms.c.out","w");
        fwrite(buffer, 1, stream.bytes_written, file);
        return 0; /* Success */
    }
    else
    {
        return 1; /* Failure */
    }

    
}
