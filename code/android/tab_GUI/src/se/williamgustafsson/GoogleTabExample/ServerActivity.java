package se.williamgustafsson.GoogleTabExample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
//import android.content.Context;
//import android.net.wifi.WifiInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class ServerActivity extends Activity{

	 	WifiManager w;
	    InetAddress server_ip;
	    int server_port = 9876;
	    String ipaddress = "192.168.181.1";
	    String message = "test message of doom";
	    DatagramSocket DSocket;
	    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		TextView textview = new TextView(this);
		textview.setText("This is the Server tab");
		setContentView(textview);
	
		w = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		
		if (!w.isWifiEnabled()) {
			textview.append("switching ON wifi ");
			w.setWifiEnabled(true);
		} else textview.setText("Its already ON ");
		
		WifiInfo info = w.getConnectionInfo();
		textview.append("\n\nWiFi Status: " + info.toString());
		
		int x = info.getIpAddress();
		
		
		String macA = info.getMacAddress();
		textview.append("\n\nmac address===" + macA + "  ,ip===" + x);
		
		
		try {
			server_ip = InetAddress.getByName(ipaddress); //translates string to inetaddress
		} catch (UnknownHostException e) {
			textview.append("Error at fetching inetAddress");
			e.printStackTrace();
		}
		
		
		try {
			DSocket = new DatagramSocket(server_port, server_ip);
		} catch (SocketException e) {
			textview.append("Error at creating Datagramsocket");
			e.printStackTrace();
		}
		
		byte b1[] = message.getBytes();
		DatagramPacket p1 = new DatagramPacket(b1, b1.length, server_ip, server_port);
		
		try {
			DSocket.send(p1);
			textview.append("Sent message");
		} catch (IOException e) {
			textview.append("Error at sending packet");
			e.printStackTrace();
		}
		
		DatagramPacket p2 = new DatagramPacket(null, 100);
		try {
			DSocket.receive(p2);
			textview.append("message recieved: " + p2.toString());
		} catch (IOException e) {
			textview.append("Error at recieve message");
			e.printStackTrace();
		}
	   
	}
}

