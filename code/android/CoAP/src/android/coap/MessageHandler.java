package android.coap;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.jcoap.connection.DefaultCoapChannelManager;
import android.jcoap.interfaces.CoapChannel;
import android.jcoap.interfaces.CoapChannelManager;
import android.jcoap.interfaces.CoapClient;
import android.jcoap.interfaces.CoapMessage;
import android.jcoap.messages.CoapMessageCode.MessageCode;

public class MessageHandler implements CoapClient {
	//private static final String SERVER_ADDRESS_default = "mulle.csproject.org";
	// private static final int PORT = 61616;
	// static int counter = 0;
	CoapChannelManager channelManager = null;
	CoapChannel clientChannel = null;
	//temp tillsvidare
	public String temp;
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
			clientChannel = channelManager.connect(this,
					InetAddress.getByName(SERVER_ADDRESS), PORT);
			CoapMessage coapRequest = clientChannel.createRequest(true,	MessageCode.GET);
			String option = "temperature";
			coapRequest.getHeader().addOption(9, option.getBytes());
			coapRequest.setPayload(message);
			clientChannel.sendMessage(coapRequest);
			System.out.println("Sent Request");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public void runTestClient() {
//		try {
//			clientChannel = channelManager.connect(this,
//					InetAddress.getByName(SERVER_ADDRESS), PORT);
//			CoapMessage coapRequest = clientChannel.createRequest(true,
//					MessageCode.GET);
//			String option = "temperature";
//			coapRequest.getHeader().addOption(9, option.getBytes());
//			coapRequest.setPayload("Polly vill ha kex");
//			clientChannel.sendMessage(coapRequest);
//
//			clientChannel.sendMessage(coapRequest);
//			System.out.println("Sent Request");
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			for (int i = 0; i < 10; i++) {
//
//				System.out.println("UnknownHostException");
//
//			}
//
//			e.printStackTrace();
//		}
//	}

	@Override
	public void onResponse(CoapChannel channel, CoapMessage response) {
		System.out.println("Received response");
		System.out.println(response.getPayload());
		temp= response.getPayload().toString();
		
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
