# Demonstration local server.
# In one window:
#  python server.py -D localhost
# In another window:
#  python coapget.py -h localhost -v
#  python coapget.py -h localhost -u uptime
#  python coapget.py -h localhost -u counter
#  python coapget.py -h localhost -u unknown

import os
import sys
import coapy.connection
import coapy.options
import coapy.link
import time
import socket
import getopt

'''
Set this to True to show debug messages.
:DEBUG: = True|False
'''
DEBUG = True 

# Import services from the services folder:
import services
# They are accessed through 'services.filename'

from coapy.options import UriPath

# --verbose (-v): Print all message metadata
verbose = False
port = coapy.COAP_PORT
address_family = socket.AF_INET
# --discovery-addresses csv (-D): Provide a comma-separated list of
#   host names for local interfaces on which CoAP service discovery
#   should be supported.
discovery_addresses = None

MULLE_IP = ("130.240.98.93", 61616);

try:
    opts, args = getopt.getopt(sys.argv[1:], 'vp:46D:', [ 'verbose', '--port=', '--ipv4', '--ipv6', '--discovery-addresses=' ])
    for (o, a) in opts:
        if o in ('-v', '--verbose'):
            verbose = True
        elif o in ('-p', '--port'):
            port = int(a)
        elif o in ('-4', '--ipv4'):
            address_family = socket.AF_INET
        elif o in ('-6', '--ipv6'):
            address_family = socket.AF_INET6
        elif o in ('-D', '--discovery_addresses'):
            discovery_addresses = a
except getopt.GetoptError, e:
    print 'Option error: %s' % (e,)
    sys.exit(1)

if socket.AF_INET == address_family:
    bind_addr = ('', port)
elif socket.AF_INET6 == address_family:
    bind_addr = ('::', port, 0, 0)
ep = coapy.connection.EndPoint(address_family=address_family)
ep.bind(bind_addr)
if discovery_addresses is not None:
    for da_fqdn in discovery_addresses.split(','):
        ep.bindDiscovery(da_fqdn)

class ResourceService (coapy.link.LinkValue):

    __services = None

    def __init__ (self, *args, **kw):
        super(ResourceService, self).__init__('.well-known', ct=[coapy.media_types_rev.get('application/link-format')])
        self.__services = { self.uri : self }

    def add_service (self, service):
        self.__services[service.uri] = service
        
    def lookup (self, uri):
        return self.__services.get(uri)

    def process (self, rx_record):
        msg = coapy.connection.Message(coapy.connection.Message.ACK, code=coapy.OK, content_type='application/link-format')
        msg.payload = ",".join([ _s.encode() for _s in self.__services.itervalues() ])
        rx_record.ack(msg)

ResServices = ResourceService()

for service in os.listdir(os.path.join('services')):
    if service == '__init__.py' or service[-3:] != '.py':
		continue
    serv = service[:-3]
    if DEBUG:
        print serv
	# the chained getattr corresponds to writing services.serv.serv
    ResServices.add_service(getattr(getattr(services, serv), serv)(serv.lower()))		

print "\n\nCoAP server started.\n\n"
while True:
    rxr = ep.process(10000)
    if rxr is None:
        if DEBUG == True:
            print 'No activity'
        continue
    if DEBUG == True:
        print 'Remote: %s, Message: %s' % (rxr.remote, rxr.message)
    msg = rxr.message
    if coapy.OK == msg.code:
        temperatureVar = msg.payload
        continue
    uri = msg.findOption(coapy.options.UriPath)
    if uri is None:
        continue
    service = ResServices.lookup(uri.value)
    if DEBUG == True:
        print 'Lookup %s got %s' % (uri, service)
    if service is None:
        rxr.reset()
        continue
    service.process(rxr)
