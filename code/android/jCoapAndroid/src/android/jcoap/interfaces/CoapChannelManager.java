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

public interface CoapChannelManager {
	public int getNewMessageID();

	/*
	 * called by the socket Listener to create a new Server Channel the Channel
	 * Manager than asked the Server Listener if he wants to accept a new
	 * connection
	 */
	public CoapChannel createServerChannel(CoapSocketHandler socketHandler,
			CoapMessage request, InetAddress addr, int port);

	/* creates a server socket listener for incoming connections */
	public void createServerListener(CoapServer serverListener, int localPort);

	/*
	 * called by a client to create a connection TODO: allow client to bind to a
	 * special port
	 */
	public CoapChannel connect(CoapClient client, InetAddress addr, int port);

	public void reset();
}
