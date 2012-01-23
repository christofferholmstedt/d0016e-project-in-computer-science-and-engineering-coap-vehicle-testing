import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt

class CounterService (coapy.link.LinkValue):
    __counter = 0

    def process (self, rx_record):
        ctr = self.__counter
        self.__counter += 1
        msg = coapy.connection.Message(coapy.connection.Message.ACK, code=coapy.OK, payload='%d' % (ctr,))
        rx_record.ack(msg)

