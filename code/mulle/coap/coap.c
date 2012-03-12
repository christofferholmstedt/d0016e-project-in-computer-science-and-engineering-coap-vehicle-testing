
#include "proj_arch.h"
#ifdef LWIP_COAP
#include "coap.h" 

static struct udp_pcb *pcb = NULL;
static struct udp_pcb *client_pcb = NULL;


/**
 * @fn err_t coap_init(u16_t port)
 * @brief Init function, must be first
 *
 * @param Port to be used, CoAP standard port is 61616
 * 
 * @return ERR_OK on success, ERR_MEM if UDP pcb couldn't be allocated
 */

err_t coap_init(u16_t port){
	LWIP_DEBUGF(COAP_DEBUG, ("coap_init\n"));	
	if(pcb)
		udp_remove(pcb);
  
	if( (pcb = udp_new()) == NULL){
		LWIP_DEBUGF(COAP_DEBUG, ("coap_init: could not allocate memory for UDP pcb\n"));
		return ERR_MEM; 
  	}

	udp_bind(pcb, IP_ADDR_ANY, port);
	udp_recv(pcb, coap_input, NULL);
	return ERR_OK; 
}

/**
 * @fn err_t coap_stop(void)
 * @brief Stop fuction, frees memory, not currently used for anything
 *
 * @return ERR_OK
 */

err_t coap_stop(void){
	if(pcb) {
		udp_remove(pcb);
		pcb = NULL;
   }
	if( client_pcb ) {
		udp_remove(client_pcb);
		client_pcb = NULL;
	}

	return ERR_OK;
}

/**
 * @fn err_t coap_connect(struct ip_addr *dest, u16_t port)
 * @brief IN PROGRESS: Function for connecting, finish this however you want, or completely discard it and make it from scratch
 *
 * @param Destination IP-address
 * @param Destination port (CoAP servers usually support UDP ports on 61616-61631)
 */

err_t coap_connect(struct ip_addr *dest, u16_t port){
	struct coap_hdr *hdr;
	struct pbuf *p;
	err_t ret = ERR_OK;

	LWIP_DEBUGF(COAP_DEBUG, ("coap_connect:\n"));

	if(client_pcb == NULL){
   	client_pcb = udp_new();
   	if( client_pcb == NULL){
      	LWIP_DEBUGF(DBG_ON, ("coap_connect: couldn't allocate UDP pbc\n"));
      	return ERR_MEM;
			}
		udp_recv(client_pcb, coap_input, NULL);
  		udp_connect(client_pcb, dest, port);
	}
  
	p = pbuf_alloc(PBUF_RAW, sizeof(struct coap_hdr), PBUF_RAM);
	if( p == NULL){
		LWIP_DEBUGF(DBG_ON, ("coap_connect: couldn't allocate pbuf\n"));
		udp_remove(client_pcb);
    	client_pcb = NULL;
    	return ERR_MEM;
  	}

	hdr = p->payload;
 	hdr->ver_t_oc = 0x00;
	hdr->code = 0x00;
	hdr->transaction_ID = 0x00;
	hdr->options = 0x00;
	hdr->payload = 0x00;
  
	ret = udp_send(client_pcb, p);
 	pbuf_free(p);

	if( ret != 0){
		udp_remove(client_pcb);
	   client_pcb = NULL;
	}
  
 	LWIP_DEBUGF(COAP_DEBUG, ("coap_connect: ret=%d\n", ret));
  	
	return ret;
}


/**
 * @fn static void coap_input(void *arg, struct udp_pcb *upcb, struct pbuf *p, struct ip_addr *addr, u16_t port)
 * @brief IN PROGRESS: coap_init binds this function to be run whenever receiving a UPD packet
 * @brief At this time it only runs a print to verify that it was run when a packet arrived
 *
 * @param arg UNUSED, should be NULL
 * @param upcb a pointer to the listening UDP pcb
 * @param p the payload
 * @param addr the src addr
 * @param port the src port
 */

static void coap_input(void *arg, struct udp_pcb *upcb, struct pbuf *p, struct ip_addr *addr, u16_t port){

  	struct coap_hdr *hdr;
	LWIP_DEBUGF(COAP_DEBUG, ("coap_input:\n"));

	/*TODO: Support for defragmation */
  
	hdr = p->payload;

	if (upcb == client_pcb){

		return;
	}

	pbuf_free(p);
}

/**
 * @fn static void coap_debug_print(struct coap_hdr *hdr)
 * @brief IN PROGRESS: A debug function that will print CoAP headers for easier debugging
 *
 * @param CoAP header
 */

static void coap_debug_print(struct coap_hdr *hdr){

	LWIP_DEBUGF(COAP_DEBUG, ("coap_debug_print: version=%u, transaction type=%d, option count=%d\n"));
   LWIP_DEBUGF(COAP_DEBUG, ("\t 0                   1                   2                   3  \n"));
   LWIP_DEBUGF(COAP_DEBUG, ("\t 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1\n"));
   LWIP_DEBUGF(COAP_DEBUG, ("\t+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n"));
   LWIP_DEBUGF(COAP_DEBUG, ("\t|Ver| T |   OC   |      Code      |        Transaction ID       |\n"));
   LWIP_DEBUGF(COAP_DEBUG, ("\t+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n"));
   LWIP_DEBUGF(COAP_DEBUG, ("\t|                       Options %lu                             |\n", hdr->options));
   LWIP_DEBUGF(COAP_DEBUG, ("\t+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n"));
   LWIP_DEBUGF(COAP_DEBUG, ("\t|                       Payload %s                              |\n", hdr->payload));
   LWIP_DEBUGF(COAP_DEBUG, ("\t+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n"));
}

#endif
