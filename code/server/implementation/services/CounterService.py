import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt
from MysqlConnection import MysqlConnection 

class CounterService (coapy.link.LinkValue):
    __counter = 0

    def process (self, rx_record):
        ctr = self.__counter
        self.__counter += 1

        #####
        # Store information to database
        # All data are stored as strings so if it's a integer that
        # is to be stored use "`" around the integer.
        #####
        mysql = MysqlConnection()
        mysql.insertRow("counter",`self.__counter`,"hostFromCounterRequest")

        ####
        # Send CoAP response to client
        ####
        msg = coapy.connection.Message(coapy.connection.Message.ACK, code=coapy.OK, payload='%d' % (ctr,))
        rx_record.ack(msg)

