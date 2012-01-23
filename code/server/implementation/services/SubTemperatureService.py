import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt

class SubTemperatureService (coapy.link.LinkValue):
    def process (self, rx_record):
        msg = coapy.connection.Message(coapy.connection.Message.CON, code=coapy.GET, uri_path="subtemperature")
        remotes = MULLE_IP
        ep.send(msg, remotes)
