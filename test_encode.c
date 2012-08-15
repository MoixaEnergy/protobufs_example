#include <stdio.h>
#include <pb_encode.h>
#include "moixa.pb.h"

int main()
{
  /*
    ZigbeeMessage msg = { 
      {DeviceType_MCS, 111111}, {DeviceType_MCS, 222222},
      true,
      {BmsMessageType_STATUS, true, {true, 99}, false},
      false
      }; 
    {McsMessageType_MCS_STATUS, {{34, 88.8}, {35, 99.9},}, true, 10, false, 0, true, 50}

      {BmsMessageType_STATUS, true, 
       {true, 99}, false},
  */

    ZigbeeMessage msg = { 
      {DeviceType_MCS, 111111}, {DeviceType_MCS, 222222},
      false,
      {},
      true,
      {McsMessageType_MCS_STATUS, true, 
       {true, 30, false, 0, true, 77, 2,
        {{1, 33.333}, 
         {2, 66.898},
        }
       }}
    };


    uint8_t buffer[512];
    pb_ostream_t stream = pb_ostream_from_buffer(buffer, sizeof(buffer));
    
    /* Now encode it and check if we succeeded. */
    if (pb_encode(&stream, ZigbeeMessage_fields, &msg))
    {
        fwrite(buffer, 1, stream.bytes_written, stdout);
        FILE *file; 
        file = fopen("mcs.c.out","w");
        fwrite(buffer, 1, stream.bytes_written, file);
        return 0; /* Success */
    }
    else
    {
        return 1; /* Failure */
    }

    
}
