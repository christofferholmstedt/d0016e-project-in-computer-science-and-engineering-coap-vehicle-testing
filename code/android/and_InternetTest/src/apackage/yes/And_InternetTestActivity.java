package apackage.yes;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class And_InternetTestActivity extends Activity {

	String ipaddress = "130.240.5.199";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.main);
		TextView textview = new TextView(this);
		textview.setText("This is UDPTEST!! \n(not sparta)");
		setContentView(textview);

		try {
			Connect(ipaddress);
		} catch (Exception e) {
			System.out.println("connect() failed");
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void Connect(String ipAdress) throws Exception {
		InetAddress dest;
		dest = InetAddress.getByName(ipAdress);

		DatagramSocket socket = new DatagramSocket();
		System.out.println("Dsocket startad");

		socket.connect(dest, 15900);
		System.out.println("socket connect ok");

		byte[] message = "Oh Hai!".getBytes();
		System.out.println("byte skapad");

		DatagramPacket packet = new DatagramPacket(message, message.length,
				dest, 15900);
		System.out.println("packet förberedd");

		int x = 0;
		while (x < 100) {
			x++;
			socket.send(packet);
			System.out.println("skickat");
		}
	}

}

// WIFI MANAGER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// w = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
//
// if (!w.isWifiEnabled()) {
// textview.append("switching ON wifi ");
// w.setWifiEnabled(true);
// } else textview.setText("Its already ON ");
//
// WifiInfo info = w.getConnectionInfo();
// textview.append("\n\nWiFi Status: " + info.toString());

// int x = info.getIpAddress();

// String macA = info.getMacAddress();
// textview.append("\n\nmac address===" + macA + "  ,ip===" + x);

// try {
// server_ip = InetAddress.getByName(ipaddress); //translates string to
// inetaddress
// } catch (UnknownHostException e) {
// System.out.println("Error: fetching inetAddress");
// textview.append("Error at fetching inetAddress");
// e.printStackTrace();
// }
//
//
// try {
// // DSocket = new DatagramSocket(server_port, server_ip);
// DSocket = new DatagramSocket();
// } catch (SocketException e) {
// System.out.println("Error: Creating Dsocket");
// textview.append("Error at creating Datagramsocket");
// e.printStackTrace();
// }
//
// byte b1[] = message.getBytes();
// DatagramPacket p1 = new DatagramPacket(b1, b1.length, server_ip,
// server_port);
//
// try {
// DSocket.send(p1);
// textview.append("Sent message");
// System.out.println("Sent message");
// } catch (IOException e) {
// System.out.println("Error: sending packet");
// textview.append("Error at sending packet");
// e.printStackTrace();
// }
//
// DatagramPacket p2 = new DatagramPacket(null, 100);
// try {
// DSocket.receive(p2);
// textview.append("message recieved: " + p2.toString());
// System.out.println("message recieved: "+ p2.toString());
// } catch (IOException e) {
// System.out.println("Error: recieve message");
// textview.append("Error at recieve message");
// e.printStackTrace();
// }

