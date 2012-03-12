/*
 * Copyright (c) 2008 EISLAB, Lulea University of Technology.
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
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgment:
        This product includes software developed by EISLAB, Lulea University
        of Technology and its contributors.
 * 4. Neither the name of EISLAB nor the names of its contributors may be
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
 * This file is part of the EISLAB 'eisplatform'.
 *
 */

/*
 * File: proj_arch.h
 *
 * Definitions for the architecture of the project
 *
 */

#ifndef __PROJ_ARCH_H__
#define __PROJ_ARCH_H__

/* Mulle version used in this project: 3.1  */

#define MULLE_MAJ_VERSION	3
#define MULLE_MIN_VERSION   1
#define MULLE_BOARD 		3

/* for backwards compatibility with power.h and hardware.h */
#define MULLEv3 			3
#define MULLEv2				2
#define MULLEv1				1

/* byte order for current platform  */
#define BYTE_ORDER			LITTLE_ENDIAN

/*
 * Interface stuff
 */
#define USE_PPP_NO_LWBT     0   /* DO NOT CHANGE THIS Macro ! */
#define USE_LWBT            1
#define USE_LAP             1
#define USE_BNEP            1

/*--------------------------------------------------------------------------------*/
/* Define hardware resources for RV8564 real time clock driver.                   */

#define RV8564_USE_IIC2 1

/*--------------------------------------------------------------------------------*/
/* Define hardware resources for DS2782 battery monitor driver.                   */

#define DS2782_USE_IIC2 1

/*--------------------------------------------------------------------------------*/
/* Define hardware resources for software spi driver.                             */

#define SPIS_PIN_DIR_SCLK PD4.BITS.PD4_2
#define SPIS_PIN_SCLK     P4.BITS.P4_2
#define SPIS_PIN_DIR_MOSI PD4.BITS.PD4_1
#define SPIS_PIN_MOSI     P4.BITS.P4_1
#define SPIS_PIN_DIR_MISO PD4.BITS.PD4_0
#define SPIS_PIN_MISO     P4.BITS.P4_0

/*--------------------------------------------------------------------------------*/
/* Define hardware resources for AT45DB321D flash memory driver.                  */

#define AT45DB321D_USE_SPIS 1
#define AT45DB321D_PIN_DIR_SSEL PD4.BITS.PD4_5
#define AT45DB321D_PIN_SSEL     P4.BITS.P4_5
#define AT45DB321D_PIN_DIR_RST  PD4.BITS.PD4_6
#define AT45DB321D_PIN_RST      P4.BITS.P4_6
#define AT45DB321D_PIN_DIR_WPR  PD4.BITS.PD4_4
#define AT45DB321D_PIN_WPR      P4.BITS.P4_4
#define AT45DB321D_POWER_ON()   PD3.BITS.PD3_2 = 1; P3.BITS.P3_2 = 0
#define AT45DB321D_POWER_OFF()  PD3.BITS.PD3_2 = 1; P3.BITS.P3_2 = 1

/*
 * Appz stuff
 */
#define LWIP_NTPD	1
#define LWIP_OLPv2	0
//D0016E: Byter OLPv2 mot COAP
#define LWIP_COAP 1

/*
 * Sensor stuff
 */
#define DS600                1


/*
 * Defines checking
 */
#ifndef USE_LWBT
  #define USE_LWBT            0
#endif // USE_LWBT

#if USE_LWBT
  #define USE_BCSP            1
  #define USE_BCSP_LE         0
  #define USE_H4              0
  #define USE_NAT             1
  #define USE_NATPMP          0
#else // USE_LWBT == 0
  #define USE_BCSP            0
  #define USE_H4              0
  #define USE_NAT             0
#endif // USE_LWBT


#ifndef NO_RTOS
#define NO_RTOS       0
#endif // not NO_RTOS

#ifndef MULLE_BOARD
#define MULLE_BOARD   0
#endif // not MULLE_BOARD


#define MCU_M16C            1

#define SHOW_MEM_ERROR        /* #define or #undef */
#define LWIP_DEBUG            /* #define or #undef */
#define LWBT_DEBUG            /* #define or #undef */


/*
 * Uart and DMA stuff
 */
#if USE_LWBT
  #define SIO_U0
  #define USE_DMA_0         1
#else
  #define USE_DMA_0         0
#endif // USE_LWBT


#if (defined(SIO_U0) || defined(SIO_U1) || defined(SIO_U2))
  #define SIO
#endif

#if (USE_DMA_0 || USE_DMA_1)
  #define DMA
#endif

/*
 * Debug stuff
 */
#include "proj_debug.h"


#endif // __PROJ_ARCH_H__

