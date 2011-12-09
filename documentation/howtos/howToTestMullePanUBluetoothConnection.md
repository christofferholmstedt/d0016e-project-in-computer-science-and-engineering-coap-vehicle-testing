How to test Mulle Bluetooth with Bluetooth MAC Access point.
===========================================================
Use the "Mulle_Pan-U_demo" in "Software-PAN-NAP/Applications" folder.

Modify the recieving MAC address in Mulle_PAN-U_demo.c as described in the file. The Mac address should be the one of the Bluetooth access point. The Mac address should be available on the back or under the Access point.

Modify following parts in "lwBT/profiles/bt_ip_panu_dt"

Extras
======
christoffer@ares-1110:~$ hcitool scan
Scanning ...
    00:23:76:9C:21:65   Nexus One
    00:23:4D:FC:76:5C   ThinkPad Bluetooth 2.1 with Enhanced Data Rate
    00:0D:88:A2:6F:4D   Bluetooth-AP
    00:1C:26:D4:AD:2A   NEURON42
