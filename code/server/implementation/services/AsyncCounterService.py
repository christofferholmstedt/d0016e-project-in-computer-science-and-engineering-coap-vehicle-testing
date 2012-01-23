import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt

class AsyncCounterService (coapy.link.LinkValue):
    __counter = 0

    def process (self, rx_record):
        rx_record.ack()
        ctr = self.__counter
        self.__counter += 1
        msg = coapy.connection.Message(coapy.connection.Message.CON, code=coapy.OK, payload='%d delayed' % (ctr,))
        for opt in rx_record.message.options:
            msg.addOption(opt)
        rx_record.end_point.send(msg, rx_record.remote)
