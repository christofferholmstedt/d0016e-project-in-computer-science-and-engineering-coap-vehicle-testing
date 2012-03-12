/*
 * Copyright (c) 2010 EISLAB, Lulea University of Technology.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of EISLAB nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY EISLAB AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL EISLAB OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * This file is part of the EISLAB eisplatform.
 *
 */


/****************************************************************
   User options
*****************************************************************/
/* this should be the MAC address of your receiver, written backwards
 * i.e The MAC address 00:14:A4:FB:B4:71 is written in the following way
 * 0x71, 0xB4, 0xFB, 0xA4, 0x14, 0x00
 *
 */
#define TARGET_BDADDR 0x71, 0xB4, 0xFB, 0xA4, 0x14, 0x00

/****************************************************************
 Includes
 *****************************************************************/
#include "bits.h"
#include "hardware.h"
#include "power.h"

#include "hal.h"

#include "uart.h"
#include "semaphore.h"
#include "hwtimers.h"

#include "titask/titask.h"
#include "task_manager/task_manager.h"

#include "spi_sw.h"
#include "at45db321d.h"
#include "eis_config.h"

#include "iic.h"
#include "rv8564.h"
#include "ds2782.h"

#include <time.h>
//#include <math.h>
//#include <string.h>
#include "printf.h"

#include "lwip/mem.h"
#include "lwip/memp.h"

#if LWIP_STATS
#include "lwip/stats.h"
#endif

#include "lwip/ip.h"
#include "lwip/udp.h"
#include "lwip/tcp.h"
#include "lwip/dhcp.h"

#include "netif/loopif.h"
#include "sensord/sensord.h"
#include "olpv2/olpv2.h"
//D0016E: Includes our coap header file
#include "coap/coap.h"

#include "dhcpd/dhcpd.h"

/* protothreads */
#include "ProtoThreads/pt.h"

#if USE_LWBT
#include "phybusif.h"
#include "l2cap.h"
#include "linkif.h"
#include "lwbtif_startup.h"

void lwbt_start(void *arg);
void lwbt_abort(void *arg);

/****************************************************************
 External function Prototypes
*****************************************************************/
void bt_ip_tmr(void);

static struct phybusif_cb cb_struct;
static struct phybusif_cb *cb;
u8_t bt_timer = 0;
u8_t bt_ip_timer = 0;

#endif // USE_LWBT


/****************************************************************
 Function Prototypes
*****************************************************************/

volatile char semaphores[NR_SEMAS] = {0};

static struct netif *loopif;
static struct ip_addr lo_ipaddr, lo_netmask, lo_gw;

struct pt int4_pt;

/* INT4 protothread */
PT_THREAD(protothread_int4(struct pt *pt))
{
  PT_BEGIN(pt);
 
  for(;;) {
    PT_WAIT_UNTIL(pt, (sema_status(INT4_SEM) == SEMA_SET) );
    sema_unset(INT4_SEM);
    _puts("INT #4");
    
    /* startup the whole lwIP and lwBT stacks (testing only) */
    lwbt_start(NULL);
  }
  
  PT_END(pt);
}


/**
 * \fn void main(void)
 * \brief Initializes the whole Mulle and runs the system super loop
 *
 */
int
main(void)
{
  static u8_t bt_timer = 0;
  static u8_t dhcp_ccntr = 0;
  struct tm now;

  power_init();
  hal_init();   /* initialize HAL, including TB2 */
  tA0_init();   /* initialize system timer */
  tB1_init();   /* initialize delay timer */
  asm("fset i");

  /* Initialise the debug UART */
  uart1_init();

  tA0_start(250); /* Start system timer, period 250 ms */


  /* initialize dynamic software timer library */
  titask_init();
  tm_init(); /* Initialize task manager */
  tm_freq_request(TM_RTC,FREQ_10M); /* for I2C */

  P7.BITS.P7_5 = 1;
  PD7.BITS.PD7_5 = 1;
  iic2_init();
  rv8564_init();
  ds2782_init();

  spiS_init();
  at45db321d_init();
  
  /* read system configuration to be used by all subsystems */
  read_config();
 
  /* initialize RTC interrupt for controlling sample rate */
  int2_start();

  /* initialize protothread for Bluetooth com */
  int4_start();
  PT_INIT(&int4_pt);

  _puts("\nEistec platform Mulle v3/v4 started.\n");

  /* print the current date and time */
  rv8564_read_datetime(&now);
  _printf("Date: %u-%u-%u %u:%u:%u\n", now.tm_year + 1900, now.tm_mon, now.tm_mday, now.tm_hour, now.tm_min, now.tm_sec );

  // TODO: move rv8564 timer and clkout stuff into rv8564 driver...
  /* turn on the timer output */
  iic2_write_register(0x51, 0x0F, eis_config.sample_interval_s / 60); //every x min
  iic2_write_register(0x51, 0x01, 0x01);
  iic2_write_register(0x51, 0x0E, 0x83);

  /* set CLKOE, low = disabled, high = enabled (see RV8564-C2 data sheet) */
  PD4.BITS.PD4_7 = 1;
  P4.BITS.P4_7 = 0;

  /* enable CLKOUT */
  iic2_write_register(0x51, 0x00, 0x00);
  iic2_write_register(0x51, 0x0D, 0x83);

  /* Set AVcc and Vref to off as default */
  AVcc_off();
  Vref_off();

  /* inform Task manager that all other sub systems are idle */
  tm_set_state(TM_SYSTEM, TM_IDLE);
  tm_set_state(TM_SENSOR, TM_IDLE);
  tm_set_state(TM_RTC, TM_ACTIVE);

  //_printf("using %d s sample interval with %d samples per file\n", eis_config.sample_interval_s, eis_config.samples_per_file);

  /* start Bluetooth subsystem */
  sema_set(INT4_SEM);

  /* main super loop */
  _puts("Starting main loop...\n");
  for (;;) {

    if(sema_status(INT1_SEM) == SEMA_SET ){
      INT1IC.byte = 0x00; // disable INT1!
      sema_unset(INT1_SEM);

      tm_set_state(TM_BT, TM_ACTIVE); // inform Task manager that Bluetooth is active
    }

    if ( lwbt_active ) {
      linkif_check_input(cb); /* Check for input */
    }

    if(sema_status(SYSTMR_SEM) == SEMA_SET ){
      sema_unset(SYSTMR_SEM);
      /* System timer expires every 250 ms */
      tcp_tmr();
      dhcp_fine_tmr();    

#if USE_BCSP
      bcsp_tmr();
#endif
      titask_tmr();

      if (++bt_timer == 4) { /* i.e: every second */
	l2cap_tmr();
	dhcpd_tmr();

	if( dhcp_ccntr++ == 60) {
	  dhcp_ccntr = 0;
	  dhcp_coarse_tmr();
	}	
	bt_timer = 0;
	bt_ip_tmr();

	/* toggle green LED to indicate alive */
	green_led_switch();

      }
    } /* SYSTMR_SEM */

    /* RTC INT interrupt -> sensord_sample() */
    if ( sema_status(INT2_SEM) == SEMA_SET) {
      sema_unset(INT2_SEM);
      //if (sensord_sample() == 1) {
	//sema_set(INT4_SEM);//NOT FOR DEBUGGING SINCE IT RESETS THE CONNECTION!!
      //}
    }

    protothread_int4(&int4_pt);

    tm_action();

  } //mainloop

  return 0;
}

/******* OLPv2 callback function *********/
int olpupdatecallback(int event)
{
#if 0
  LWIP_DEBUGF(BT_IP_DEBUG, ("olpupdatecallback\n"));
  switch ( event ) {
    case OLPv2GenericErrorEvent:
      LWIP_DEBUGF(BT_IP_DEBUG, ("\tLPv2GenericErrorEvent\n"));
    break;
    case OLPv2ConnectedEvent:
      LWIP_DEBUGF(BT_IP_DEBUG, ("\tLPv2ConnectedEvent\n"));
    break;
    case OLPv2DisconnectedEvent:
      LWIP_DEBUGF(BT_IP_DEBUG, ("\tLPv2DisconnectedEvent\n"));
    break;
    case OLPv2DataSentEvent:
      LWIP_DEBUGF(BT_IP_DEBUG, ("\tLPv2DataSentEvent\n"));
      titask_reset(lwbt_abort);
    break;
    case OLPv2DataRecvEvent:
      LWIP_DEBUGF(BT_IP_DEBUG, ("\tLPv2DataRecvEvent\n"));
    break;
    default:
      LWIP_DEBUGF(BT_IP_DEBUG, ("\tUnknown event!\n"));
      return -1;
  }
#else
  return 0;
#endif
}

/******* Bluetooth callback functions *********/
/**
 * \fn void lwbt_start(void *arg)
 *
 */
void lwbt_start(void *arg) {
  //__disable_interrupt();
  asm("fclr i");
  linkif_start_input();
  asm("fset i");
  //__enable_interrupt();
  
  tm_freq_request (TM_BT, FREQ_10M);
  tm_set_state(TM_BT, TM_ACTIVE);
    
  mem_init();
  memp_init();
  pbuf_init();
  
  netif_init();
  ip_init();
#if LWIP_UDP
  udp_init();
  _puts("UDP initialized.");
#endif // LWIP_UDP
  tcp_init();
  _puts("TCP/IP initialized.");

  if ( eis_config.ap_bdaddr.addr[0] == 0x00 && eis_config.ap_bdaddr.addr[1] == 0x00 && eis_config.ap_bdaddr.addr[2] == 0x00 && eis_config.ap_bdaddr.addr[3] == 0x00 && \
    eis_config.ap_bdaddr.addr[4] == 0x00 && eis_config.ap_bdaddr.addr[5] == 0x00 ) {
    
      _puts("No prefered AP available, scanning ...");
  }
  
  power_on_bt_module();
  cb = &cb_struct;
  lwbtif_startup(cb);
  
  //titask_add(TI_RUN_ONCE, 125, lwbt_abort, NULL);
  lwbt_active = 1;

  /* start applications*/
  //telnetd_init();
}


/**
 * \fn void lwbt_shutdown(void *arg)
 *
 */
void lwbt_shutdown(void *arg) {

  struct bd_addr *addr = arg;
  
  LWIP_DEBUGF(BT_IP_DEBUG, ("lwbt_shutdown\n"));
  
  /* check if we get an BD addr as argument */
  if ( addr ) {
    hci_disconnect(addr, HCI_OTHER_END_TERMINATED_CONN_USER_ENDED);
    _msleep(200);
  }
  
  power_off_bt_module();
  
  tm_set_state(TM_BT, TM_IDLE);
  tm_freq_request(TM_BT, FREQ_DONT_CARE);

  lwbt_active = 0;
  titask_remove(lwbt_abort); // cancel since we now are shutting down

  /* this is for extreme reliability, reboot after each connection (attempt)
     out comment the line below for normal operation
  */
  reset_system();
}


/**
 * \fn void lwbt_abort(void *arg)
 *
 */
void lwbt_abort(void *arg) {
  _puts("Bluetooth connection took to long, aborting...");
  
  eis_config.nr_failed_connects += (eis_config.nr_failed_connects==255?0:1);
  write_config();
  
  lwbt_shutdown(NULL);
}


/**
 * \fn void bt_init_done(void)
 * \brief Called by Bluetooth control file (bt_spp.c) when init is finished
 * \bug Should check if BCSP is really idle!!
 *
 */
void bt_init_done(void)
{

  tm_freq_request(TM_BT, FREQ_DONT_CARE);
  tm_set_state(TM_BT, TM_ACTIVE);

  PD8.BITS.PD8_3 = INPUT;
  INT1IC.byte = 0x03; //enable interrupt 1
}

/**
 * \fn void bt_disconnect_done(void)
 * \brief Called by Bluetooth control file (bt_spp.c) when disconnect finished
 * \bug Should check if BCSP is really idle!!
 *
 */
void bt_disconnect_done(void)
{
  tm_freq_request(TM_BT, FREQ_DONT_CARE);
  tm_set_state(TM_BT, TM_LISTENING);

  PD8.BITS.PD8_3 = INPUT;
  INT1IC.byte = 0x03; //enable interrupt 1
}

struct bd_addr target_bdaddr = {{TARGET_BDADDR}};


void pan_connected(void)
{
  _printf("Add user specific code here, such as sending sensor data etc\n");
#if LWIP_OLPv2
  OLPv2_init();
  OLPv2_NotifyUpdate(&olpupdatecallback);
  OLPv2_connect();
#elif LWIP_COAP
  coap_init(COAP_DEFAULT_PORT);
#endif
}
