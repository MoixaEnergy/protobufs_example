#include <stdio.h>
#include <pb_decode.h>
#include "moixa.pb.h"

void print_bms(BmsMessage msg) 
{
  switch (msg.messageType)
    {
    case BmsMessageType_STATUS:
      printf("type STATUS");
      printf(" voltage %i ", msg.status.voltage);
      break;
    case BmsMessageType_SET_STATE:
      printf("type SET STATE");
      break;
    }  
}

/*
void print_bms(BmsMessage *msg) 
{
  switch (msg->messageType)
    {
    case BmsMessageType_STATUS:
      printf("type STATUS");
      printf(" voltage %i ", msg->status->voltage);
      break;
    case BmsMessageType_SET_STATE:
      printf("type SET STATE");
      break;
    }  
    }
*/

void print_mcs(McsStatus msg)
{
  int i;
  for (i=0; i < msg.clamps_count; i++)
    {
      McsClamp *clamp = &msg.clamps[i];
      printf("clamp num %i ", clamp->clampNum);
      printf("  reading %f \n", clamp->reading);
    }
}

bool print_zigbee_message(pb_istream_t *stream)
{
  ZigbeeMessage msg;

  if (!pb_decode(stream, ZigbeeMessage_fields, &msg))
    return false;

  switch (msg.source.dtype) {
  case DeviceType_MOIXA_HUB:
    printf("from moixa hub");
    break;   
  case DeviceType_MCS:
    printf("from mcs");
    print_mcs(msg.mcsMessage.status);
    break;
  case DeviceType_BMS:
    printf("from bms");
    print_bms(msg.bmsMessage);
    break;
  }

  switch (msg.destination.dtype) {
  case DeviceType_MOIXA_HUB:
    printf("to moixa hub");
    break;
  case DeviceType_MCS:
    printf("to mcs");
    break;
  case DeviceType_BMS:
    printf("to bms");
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
    if (!print_zigbee_message(&stream))
    {
        printf("Parsing failed.\n");
        return 1;
    } else {
        return 0;
    }
}

