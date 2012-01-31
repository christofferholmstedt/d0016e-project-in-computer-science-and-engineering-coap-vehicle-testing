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

		socket.send(packet);
		System.out.println("skickat");

	}

}
