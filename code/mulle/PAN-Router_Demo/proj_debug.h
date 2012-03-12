/*
 * Copyright (c) 2007 EISLAB, Lulea University of Technology.
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
 * This file is part of the EISLAB eisplatform.
 *
 */

/*
 * File: proj_debug.h
 *
 * Definitions for debugging the project
 *
 */

#ifndef __PROJ_DEBUG_H__
#define __PROJ_DEBUG_H__

#include <stdio.h>

/* comment out a line for no debugging */
#define SHOW_MEM_ERROR
#define LWIP_DEBUG
#define LWIP_NOASSERT
#define LWBT_DEBUG
#define SENSORD_DEBUG DBG_ON

//#define VJ_DEBUG          DBG_ON  /* Debugging VJ TCP header compression */
//#define HTTPD_LWIP_DEBUGF DBG_ON
#define BT_IP_DEBUG     DBG_ON
#define BT_SPP_DEBUG 	DBG_OFF
#define NTP_DEBUG       DBG_OFF
#define I2C_DEBUG       DBG_OFF
#define TM_DEBUG	DBG_OFF
//D0016E: Coap debugger på
#define COAP_DEBUG		DBG_ON


/* Debug for the service discovery core           */
/* .. and debug for the service discovery daemon  */
/* ... and debug for dynamic dns                  */
/* ... and debug for NAT PMP client               */
#define SD_DEBUG DBG_ON         /* Controls debug messages for e.g. browse, incoming query, and sending response */
#define SDD_DEBUG DBG_ON         /*  */

#endif // __PROJ_DEBUG_H__

