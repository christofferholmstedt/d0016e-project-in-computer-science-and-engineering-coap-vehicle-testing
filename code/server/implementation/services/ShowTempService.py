import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt

class ShowTempService (coapy.link.LinkValue):
    def process (self, rx_record):
        msg = coapy.connection.Message(coapy.connection.Message.ACK, code=coapy.OK, uri_path="temp", payload='%f' % (temperatureVar,))
        rx_record.ack(msg)
