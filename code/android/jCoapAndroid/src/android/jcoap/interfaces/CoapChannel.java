package android.jcoap.interfaces;

import java.net.InetAddress;
import java.nio.channels.DatagramChannel;

import android.jcoap.messages.CoapMessageCode;

public interface CoapChannel {

	public void sendMessage(CoapMessage msg);

	public void sendMessage(CoapMessage msg, CoapMessage request);

	public void close();

	public InetAddress getRemoteAddress();

	public int getRemotePort();

	public CoapMessage createRequest(boolean reliable,
			CoapMessageCode.MessageCode messageCode);

	public CoapMessage createResponse(CoapMessage request,
			CoapMessageCode.MessageCode messageCode);

	public void newIncommingMessage(CoapMessage message);

	/* TODO: implement Error Type */
	public void lostConnection(boolean notReachable, boolean resetByServer);

}
