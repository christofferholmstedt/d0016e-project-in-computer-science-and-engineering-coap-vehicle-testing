import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt
from MysqlConnection import MysqlConnection 

'''
Class CounterService
    Simple service implementation that increases a counter for each
    request to this service. This service shows how to use the MySQL
    connection library aswell to store information to our data-table
    for persistent logging.

    :__counter: Internal counter for this service 
'''
class CounterService (coapy.link.LinkValue):
    __counter = 1

    def process (self, rx_record):
        ctr = self.__counter
        self.__counter += 1

        #####
        # Store information to database
        # All data is stored as strings so if it's an integer that
        # is to be stored use "`" around the integer.
        # 
        # rx_record.remote[0]   IP-address of remote host
        # rx_record.remote[1]   Port used by remote host
        #####
        mysql = MysqlConnection()
        mysql.insertRow("CounterService",`ctr`,rx_record.remote[0])

        ####
        # Send CoAP response to client
        ####
        msg = coapy.connection.Message(coapy.connection.Message.ACK, code=coapy.OK, payload='%d' % (ctr,))
        rx_record.ack(msg)

