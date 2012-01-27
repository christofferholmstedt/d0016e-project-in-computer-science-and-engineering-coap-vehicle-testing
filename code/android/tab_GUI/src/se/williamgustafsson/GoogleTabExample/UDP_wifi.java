/*package se.williamgustafsson.GoogleTabExample;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class UDP_wifi extends Activity {

	WifiManager w;
	TextView status;
	InetAddress server_ip;
	int server_port = 9876;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		status = (TextView) findViewById(R.id.status);

		w = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

		if (!w.isWifiEnabled()) {
			status.setText("switching ON wifi ");
			w.setWifiEnabled(true);
		} else {
			status.setText("Its already ON ");

		}

		int x;
		WifiInfo info = w.getConnectionInfo();
		status.append("\n\nWiFi Status: " + info.toString());

		x = info.getIpAddress();
		String str = info.getMacAddress();

		status.append("\n\nmac address===" + str + "  ,ip===" + x);

		try {
			server_ip = InetAddress.getByName("192.168.181.1"); // ip of my server.How to dynamically update it
		} catch (UnknownHostException e) {
			status.append("Error at fetching inetAddress");
		}

		DatagramSocket s = new DatagramSocket(server_port, server_ip);
		// **ERROR AT PREVIOUS LINE, I HAD TO FORCE STOP MY APP EVERTIME I RUN
		// MY CODE**
		String str = "TEST MESSAGE !!!";
		byte b1[];
		b1 = new byte[100];
		b1 = str.getBytes();
		DatagramPacket p1 = new DatagramPacket(b1, b1.length, server_ip,
				server_port);

	}
}
*/