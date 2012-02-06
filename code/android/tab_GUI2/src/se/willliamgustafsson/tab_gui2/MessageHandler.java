package se.willliamgustafsson.tab_gui2;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.jcoap.connection.DefaultCoapChannelManager;
import android.jcoap.interfaces.CoapChannel;
import android.jcoap.interfaces.CoapChannelManager;
import android.jcoap.interfaces.CoapClient;
import android.jcoap.interfaces.CoapMessage;
import android.jcoap.messages.CoapMessageCode.MessageCode;
import android.os.Handler;

public class MessageHandler implements CoapClient {
	//private static final String SERVER_ADDRESS_default = "mulle.csproject.org";
	// private static final int PORT = 61616;
	// static int counter = 0;
	CoapChannelManager channelManager = null;
	CoapChannel clientChannel = null;
	//temp tillsvidare
	public byte[] temp;
/**
 * Builds our custom CoAP message handler, no idea what it actually does :)
 */
	public MessageHandler() {
		System.out.println("Start CoAP Client");

		channelManager = DefaultCoapChannelManager.getInstance();
		// runTestClient();
	}

	/**
	 * Calls CoAPGET with port number 61616
	 * 
	 * @param SERVER_ADDRESS
	 *            to be used by InetAddress.getbyname()
	 * @param message
	 *            String, the payload to send
	 * @param Options
	 *            Section, for example temperature or Counter, might be case
	 *            sensitive
	 */
	public void CoAPGET(String SERVER_ADDRESS, String message, String Options) {
		CoAPGET(SERVER_ADDRESS, 61616, message, Options);
	}

	
	/**
	 * 
	 * @param SERVER_ADDRESS
	 *            to be used by InetAddress.getbyname()
	 * @param PORT
	 *            port number, if port = 61616, call with CoAPGET(String,
	 *            String, Options) instead
	 * @param message
	 *            String, the payload to send
	 * @param Options
	 *            Section, for example temperature or Counter, might be case
	 *            sensitive
	 */
	public void CoAPGET(String SERVER_ADDRESS, int PORT, String message, String Options) {
		// * Moved to constructor to be able to receive message?
		// channelManager = DefaultCoapChannelManager.getInstance();

		try {
			clientChannel = channelManager.connect(this, InetAddress.getByName(SERVER_ADDRESS), PORT);
			CoapMessage coapRequest = clientChannel.createRequest(true,	MessageCode.GET);
			coapRequest.getHeader().addOption(9, Options.getBytes());
			coapRequest.setPayload(message);
			clientChannel.sendMessage(coapRequest);
			System.out.println("Sent Request");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onResponse(CoapChannel channel, CoapMessage response) {
		System.out.println("Received response");
		System.out.println("HEJ DETTA ÄR INDATA: "+response.getPayload().toString());
		temp= response.getPayload();
		//Handler srvActivity = new Handler();
		
		
		//TODO: Figure out where this is supposed to return
		//return response.getPayload().toString();
	}

	@Override
	public void onConnectionFailed(CoapChannel channel, boolean notReachable,
			boolean resetByServer) {
		System.out.println("Connection Failed");
		//TODO: figure out where this is supposed to return to...

	}
}
