import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt

class FetchTemperatureService (coapy.link.LinkValue):
    def process (self, rx_record):
        msg = coapy.connection.Message(coapy.connection.Message.CON, code=coapy.GET, uri_path="temperature")
        remotes = MULLE_IP
        ep.send(msg, remotes)
        rx_record.ack(msg)
