/* Copyright [2011] [University of Rostock]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/

package android.jcoap.connection;

import java.net.InetAddress;

import android.jcoap.interfaces.CoapClient;
import android.jcoap.interfaces.CoapClientChannel;
import android.jcoap.interfaces.CoapMessage;
import android.jcoap.interfaces.CoapSocketHandler;

/**
 * @author Christian Lerche <christian.lerche@uni-rostock.de>
 */

public class DefaultCoapClientChannel extends DefaultCoapChannel implements
		CoapClientChannel {
	CoapClient client = null;

	public DefaultCoapClientChannel(CoapSocketHandler socketHandler,
			CoapClient client, InetAddress remoteAddress, int remotePort) {
		super(socketHandler, remoteAddress, remotePort);
		this.client = client;
	}

	@Override
	public void newIncommingMessage(CoapMessage message) {

		client.onResponse(this, message);
	}

	@Override
	public void lostConnection(boolean notReachable, boolean resetByServer) {
		client.onConnectionFailed(this, notReachable, resetByServer);

	}

	// public DefaultCoapClientChannel(CoapChannelManager channelManager) {
	// super(channelManager);
	// }
	//
	// @Override
	// public void connect(String remoteHost, int remotePort) {
	// socket = null;
	// if (remoteHost!=null && remotePort!=-1) {
	// try {
	// socket = new DatagramSocket();
	// } catch (SocketException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// try {
	// InetAddress address = InetAddress.getByName(remoteHost);
	// socket.connect(address, remotePort);
	// super.establish(socket);
	// } catch (UnknownHostException e) {
	// e.printStackTrace();
	// }
	// }

}
