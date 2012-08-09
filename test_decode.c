#include <stdio.h>
#include <pb_decode.h>
#include "bms.pb.h"

bool print_bms(pb_istream_t *stream)
{
  BmsMessage msg;

  if (!pb_decode(stream, BmsMessage_fields, &msg))
    return false;

  switch (msg.messageType)
    {
    case MessageType_STATUS:
      printf("type STATUS");
      printf(" voltage %i ", msg.status.voltage);
      break;
    case MessageType_SET_STATE:
      printf("type SET STATE");
      break;
    }
  return true;
}

int main()
{
    /* Read the data into buffer */
    uint8_t buffer[512];
    size_t count = fread(buffer, 1, sizeof(buffer), stdin);
    
    /* Construct a pb_istream_t for reading from the buffer */
    pb_istream_t stream = pb_istream_from_buffer(buffer, count);
    
    /* Decode and print out the stuff */
    if (!print_bms(&stream))
    {
        printf("Parsing failed.\n");
        return 1;
    } else {
        return 0;
    }
}

