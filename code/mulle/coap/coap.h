/* COAP header file


	The COAP - Header
     0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |Ver| T |  OC   |      Code     |        Transaction ID         |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Options (if any) ...					 							    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Payload (if any) ...					  							    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   Version: 2-bit
   Transaction Type: 2-bit
   Option Count: 4-bit
   Code: 8-bit
   Transaction ID: 16-bit   Options: 32-bit
   Payload: 32-bit 

*/

#ifndef __COAP_H__
#define __COAP_H__

#define COAP_DEFAULT_PORT  61616

#include "lwip/err.h"
#include "lwip/debug.h"
#include "lwip/udp.h"
#include "lwip/ip.h"


PACK_STRUCT_BEGIN
struct coap_hdr {
  PACK_STRUCT_FIELD(u8_t ver_t_oc);
  PACK_STRUCT_FIELD(u8_t code);
  PACK_STRUCT_FIELD(u16_t transaction_ID);
  PACK_STRUCT_FIELD(u32_t options);
  PACK_STRUCT_FIELD(u32_t payload);
}PACK_STRUCT_STRUCT;
PACK_STRUCT_END


/* function declarations */
err_t coap_init(u16_t port);
static void coap_input(void *arg, struct udp_pcb *upcb, struct pbuf *p, struct ip_addr *addr, u16_t port);
err_t coap_stop(void);
err_t coap_connect(struct ip_addr *dest, u16_t port);
static void coap_debug_print(struct coap_hdr *hdr);

#endif /* __COAP_H__ */

