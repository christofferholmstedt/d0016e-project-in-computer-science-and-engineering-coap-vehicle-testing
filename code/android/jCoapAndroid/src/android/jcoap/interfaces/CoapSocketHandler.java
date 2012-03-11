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

package android.jcoap.interfaces;

import java.net.InetAddress;

public interface CoapSocketHandler {
	// public void registerResponseListener(CoapResponseListener
	// responseListener);
	// public void unregisterResponseListener(CoapResponseListener
	// responseListener);
	// public int sendRequest(CoapMessage request);
	// public void sendResponse(CoapResponse response);
	// public void establish(DatagramSocket socket);
	// public void testConfirmation(int msgID);
	//
	// public boolean isOpen();
	/* TODO */
	public CoapChannel connect(CoapClient client, InetAddress remoteAddress,
			int remotePort);

	public void close();

	public void removeChannel(CoapChannel channel);

	public void sendMessage(CoapMessage msg);

	public CoapChannelManager getChannelManager();

	int getLocalPort();
}
