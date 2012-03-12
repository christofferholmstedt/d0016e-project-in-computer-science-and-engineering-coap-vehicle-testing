/*
 * Copyright (c) 2003 EISLAB, Lulea University of Technology.
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED 
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING 
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 *
 * This file is part of the lwBT Bluetooth stack.
 * 
 * Author: Conny Ohult <conny@sm.luth.se>
 *
 */

#ifndef __NATOPTS_H__
#define __NATOPTS_H__

/* ---------- NAT options ---------- */
#include "lwipopts.h"

#ifdef LWBT_DEBUG
  #define NAT_DEBUG DBG_OFF
  #define NATPMP_DEBUG DBG_OFF
#else
  #define NAT_DEBUG DBG_OFF
  #define NATPMP_DEBUG DBG_OFF
#endif // LWBT_DEBUG

#define NEW_NATOPTS 1

/* NAT_LOCAL_NETMASK: Netmask for the local network. */
#define NAT_LOCAL_NETMASK (((u32_t)(255) << 24) | ((u32_t)(255) << 16) | \
                           ((u32_t)(255) << 8) | (u32_t)(0))

/* NAT_LOCAL_SUBNETMASK: Netmask for the local sub networks. */
#define NAT_LOCAL_SUBNETMASK (((u32_t)(255) << 24) | ((u32_t)(255) << 16) | \
                              ((u32_t)(255) << 8) | (u32_t)(0))

/* NAT_LOCAL_NETWORK: The default local network. */
#define NAT_LOCAL_NETWORK (((u32_t)(172) << 24) | ((u32_t)(16) << 16) | \
                           ((u32_t)(0) << 8) | (u32_t)(1))


/* NAT_MIN_PORT and NAT_MAX_PORT specify the range used to assign port numbers that can be used
   to form globally unique address */
#define NAT_MIN_PORT 49152U
#define NAT_MAX_PORT 65535U
/* NAT_RETIRE_LONGEST_IDLE: Control if the longest idle session should be retired when it
   becomes necessary */
#define NAT_RETIRE_LONGEST_IDLE 1 /* Default 1 */
/* NAT_IDLE_TIMEOUT: Control how long a session can remain idle before it is removed */
#define NAT_IDLE_TIMEOUT 0 /* Default 0 (No timeout). RFC2663 informs that TCP sessions
                              that have not been used for say, 24 hours, and non-TCP
                              sessions that have not been used for a couple of minutes, are
                              terminated, but that this is application specific */
/* NAT_TCP_END_DETECT: Control if a TCP session should be closed after 2 * MSL (Maximum
   Segment Lifetime) or 4 minutes when a RST or second FIN is sent or received */
#define NAT_TCP_END_DETECT 0 /* Default 1 */

#endif /* __NATOPTS_H__ */

