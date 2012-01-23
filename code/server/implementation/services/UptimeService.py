import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt

class UptimeService (coapy.link.LinkValue):
    __started = time.time()

    def process (self, rx_record):
        uptime = time.time() - self.__started
        msg = coapy.connection.Message(coapy.connection.Message.ACK, code=coapy.OK, payload='%g' % (uptime,))
        rx_record.ack(msg)
