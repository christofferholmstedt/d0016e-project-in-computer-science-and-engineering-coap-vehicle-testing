package android.coap;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.jcoap.connection.DefaultCoapChannelManager;
import android.jcoap.interfaces.CoapChannel;
import android.jcoap.interfaces.CoapChannelManager;
import android.jcoap.interfaces.CoapClient;
import android.jcoap.interfaces.CoapMessage;
import android.jcoap.messages.CoapMessageCode.MessageCode;

public class MessageHandler implements CoapClient{
	private static final String SERVER_ADDRESS = "130.240.233.15";
    private static final int PORT = 61616;
    static int counter = 0;
    CoapChannelManager channelManager = null;
    CoapChannel clientChannel = null;

    public MessageHandler() {
    	System.out.println("Start CoAP Client");
        
        channelManager = DefaultCoapChannelManager.getInstance();
        runTestClient();
    }

    public void runTestClient(){
    	try {
			clientChannel = channelManager.connect(this, InetAddress.getByName(SERVER_ADDRESS), PORT);
			CoapMessage coapRequest = clientChannel.createRequest(true, MessageCode.GET);
			String option = "counter";
			coapRequest.getHeader().addOption(9, option.getBytes());
			coapRequest.setPayload("Polly vill ha kex");
			clientChannel.sendMessage(coapRequest);
			System.out.println("Sent Request");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("UnknownHostException");
			//e.printStackTrace();
			
		}
    }

    @Override
	public void onResponse(CoapChannel channel, CoapMessage response) {
    	System.out.println("Received response");
    	System.out.println(response.getPayload());
	}

	@Override
	public void onConnectionFailed(CoapChannel channel, boolean notReachable, boolean resetByServer) {
		System.out.println("Connection Failed");
	}
}
