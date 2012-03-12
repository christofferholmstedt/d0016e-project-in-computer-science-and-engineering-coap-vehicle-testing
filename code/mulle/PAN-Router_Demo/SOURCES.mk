#
#  Copyright (c) 2010 Eistec AB, Sweden.
#  All rights reserved.
# 
#  Redistribution and use in source and binary forms, with or without
#  modification, are permitted provided that the following conditions
#  are met:
#  1. Redistributions of source code must retain the above copyright
#     notice, this list of conditions and the following disclaimer.
#  2. Redistributions in binary form must reproduce the above copyright
#     notice, this list of conditions and the following disclaimer in the
#     documentation and/or other materials provided with the distribution.
#  3. Neither the name of Eistec AB nor the names of its contributors may be
#     used to endorse or promote products derived from this software
#     without specific prior written permission.
# 
#  THIS SOFTWARE IS PROVIDED BY Eistec AB AND CONTRIBUTORS ``AS IS'' AND
#  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
#  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
#  ARE DISCLAIMED.  IN NO EVENT SHALL Eistec AB OR CONTRIBUTORS BE LIABLE
#  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
#  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
#  OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
#  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
#  LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
#  OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
#  SUCH DAMAGE.
# 
# Project specific options for the Mulle Demo Software
#
# This makefile specifies settings, sources and include directories 
# to be used in the above mentioned software for the Mulle Platform.



################ Settings ################################################
# Application name
EXECUTABLE = Mulle_PAN-Router_demo



################ Directories ################################################

MULLEROOT=../..

#Set this to where you have the different modules checked out from SVN.
#Default assumes the module name in the Mulle root directory

LIBDIR=$(MULLEROOT)/Library
LWBTDIR=$(MULLEROOT)/lwBT/stack
LWIPDIR=$(MULLEROOT)/lwIP/src

#Set these to the corresponding HW architecture
LWBTARCH=$(LWBTDIR)/../ports/m16c
LWIPARCH=$(LWIPDIR)/../ports/m16c

#Set this to where the bluetooth control applications are, i.e. spp, pan etc.
LWBT_APPS=$(LWBTDIR)/profiles

#Hardware control
#change HALDIR if using other HW
DRIVERDIR = $(LIBDIR)/Drivers
HALDIR = $(LIBDIR)/m16c




################ Include Directories ##########################################

INCLUDES = \
	-I. \
	-I$(LWIPDIR)/include \
	-I$(LWIPARCH)/include \
	-I$(LWIPDIR)/include/ipv4 \
	-I$(LWBTDIR) \
	-I$(LWBTARCH) \
	-I$(LIBDIR)/misc \
	-I$(LIBDIR)/misc/apps \
	-I$(HALDIR) \
	-I$(DRIVERDIR)/temperature \
	-I$(DRIVERDIR)/rtc \
	-I$(HALDIR)/spi \
	-I$(HALDIR)/i2c \
	-I$(DRIVERDIR)/flash \
	-I$(DRIVERDIR)/battery \


################ Source files ################################################



# COREFILES, CORE4FILES: The minimum set of files needed for lwIP.
COREFILES=$(LWIPDIR)/core/mem.c $(LWIPDIR)/core/memp.c $(LWIPDIR)/core/netif.c \
	$(LWIPDIR)/core/pbuf.c $(LWIPDIR)/core/stats.c $(LWIPDIR)/core/sys.c \
        $(LWIPDIR)/core/tcp.c $(LWIPDIR)/core/tcp_in.c \
        $(LWIPDIR)/core/tcp_out.c $(LWIPDIR)/core/udp.c
CORE4FILES=$(LWIPDIR)/core/ipv4/icmp.c $(LWIPDIR)/core/ipv4/ip.c \
	$(LWIPDIR)/core/inet.c $(LWIPDIR)/core/ipv4/ip_addr.c \
	$(LWIPDIR)/core/dhcp.c $(LWIPDIR)/netif/loopif.c

# UART files for lwBT
UARTFILES=$(LWBTARCH)/uartif_bcsp.c $(LWBTARCH)/uartif_h4.c

# NETIFFILES: Files implementing various generic network interface functions.
NETIFFILES=$(LWBTARCH)/lwbtif_startup.c $(LWBTARCH)/linkif.c \
	$(LWBTARCH)/dma0start.c $(UARTFILES) \
	$(LWBTDIR)/lwbt_memp.c $(LWBTDIR)/hci.c \
	$(LWBTDIR)/l2cap.c $(LWBTDIR)/sdp.c \
	$(LWBTDIR)/arp.c $(LWBTDIR)/bnep.c $(LWBTDIR)/bneparp.c $(LIBDIR)/misc/nat/nat.c

#The Bluetooth profile to use for this project
LWBTPROFILE=$(LWBTDIR)/../profiles/bt_ip_pan_u-nap_router.c

LWIP_APPS=$(LIBDIR)/misc/apps/ntp/ntp.c $(LIBDIR)/misc/apps/sensord/sensord.c \
	 $(LIBDIR)/misc/apps/dhcpd/dhcpd.c $(LIBDIR)/misc/apps/olpv2/olpv2.c $(LIBDIR)/misc/apps/coap/coap.c

LWIPFILES = $(COREFILES) $(CORE4FILES) $(NETIFFILES) $(LWBTPROFILE) $(LWIP_APPS)

HWSRC = $(HALDIR)/hal.c $(HALDIR)/printf.c $(HALDIR)/hw_function_lib.c \
    $(HALDIR)/hwtimers.c $(HALDIR)/uart.c $(HALDIR)/spi/spi_sw.c \
    $(DRIVERDIR)/temperature/temperature.c \
    $(DRIVERDIR)/rtc/rv8564.c \
    $(HALDIR)/i2c/iic.c \
	$(DRIVERDIR)/flash/at45db321d.c \
	$(DRIVERDIR)/flash/eis_config.c \
	$(DRIVERDIR)/battery/ds2782.c \


SOURCES =  $(EXECUTABLE).c \
    $(LWIPFILES) \
	$(HWSRC) \
    $(LIBDIR)/misc/task_manager/task_manager.c \
    $(LIBDIR)/misc/apps/httpc/simple_httpc.c \
    $(LIBDIR)/misc/titask/titask.c


