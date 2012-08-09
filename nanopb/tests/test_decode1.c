/* A very simple decoding test case, using person.proto.
 * Produces output compatible with protoc --decode.
 * Reads the encoded data from stdin and prints the values
 * to stdout as text.
 *
 * Run e.g. ./test_encode1 | ./test_decode1
 */

#include <stdio.h>
#include <pb_decode.h>
#include "person.pb.h"

/* This function is called once from main(), it handles
   the decoding and printing. */
bool print_person(pb_istream_t *stream)
{
    int i;
    Person person;
    
    if (!pb_decode(stream, Person_fields, &person))
        return false;
    
    /* Now the decoding is done, rest is just to print stuff out. */

    printf("name: \"%s\"\n", person.name);
    printf("id: %ld\n", (long)person.id);
    
    if (person.has_email)
        printf("email: \"%s\"\n", person.email);
    
    for (i = 0; i < person.phone_count; i++)
    {
        Person_PhoneNumber *phone = &person.phone[i];
        printf("phone {\n");
        printf("  number: \"%s\"\n", phone->number);
        
        if (phone->has_type)
        {
            switch (phone->type)
            {
                case Person_PhoneType_WORK:
                    printf("  type: WORK\n");
                    break;
                
                case Person_PhoneType_HOME:
                    printf("  type: HOME\n");
                    break;
                
                case Person_PhoneType_MOBILE:
                    printf("  type: MOBILE\n");
                    break;
            }
        }
        printf("}\n");
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
    if (!print_person(&stream))
    {
        printf("Parsing failed.\n");
        return 1;
    } else {
        return 0;
    }
}
