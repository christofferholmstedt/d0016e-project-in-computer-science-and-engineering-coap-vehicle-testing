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

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Random;

import android.jcoap.Constants;
import android.jcoap.interfaces.CoapChannel;
import android.jcoap.interfaces.CoapChannelManager;
import android.jcoap.interfaces.CoapClient;
import android.jcoap.interfaces.CoapMessage;
import android.jcoap.interfaces.CoapServer;
import android.jcoap.interfaces.CoapServerChannel;
import android.jcoap.interfaces.CoapSocketHandler;

/**
 * @author Christian Lerche <christian.lerche@uni-rostock.de>
 */

public class DefaultCoapChannelManager implements CoapChannelManager {
	// global message id
	private int globalMessageId;
	private static DefaultCoapChannelManager instance;
	private HashMap<Integer, SocketInformation> socketMap = new HashMap<Integer, SocketInformation>();
	CoapServer serverListener = null;

	private DefaultCoapChannelManager() {
		reset();
	}

	public synchronized static CoapChannelManager getInstance() {
		if (instance == null) {
			instance = new DefaultCoapChannelManager();
		}
		return instance;
	}

	/**
	 * Creates a new server channel
	 */
	@Override
	public synchronized CoapChannel createServerChannel(
			CoapSocketHandler socketHandler, CoapMessage request,
			InetAddress addr, int port) {
		SocketInformation socketInfo = socketMap.get(socketHandler
				.getLocalPort());

		if (socketInfo.serverListener == null) {
			/* this is not a server socket */
			return null;
		}

		CoapServer server = socketInfo.serverListener.onAccept(request);
		if (server == null) {
			/* Server rejected channel */
			return null;
		}
		CoapServerChannel newChannel = new DefaultCoapServerChannel(
				socketHandler, server, addr, port);

		return newChannel;
	}

	/**
	 * Creates a new, global message id for a new COAP message
	 */
	@Override
	public synchronized int getNewMessageID() {
		if (globalMessageId < Constants.MESSAGE_ID_MAX) {
			++globalMessageId;
		} else
			globalMessageId = Constants.MESSAGE_ID_MIN;
		return globalMessageId;
	}

	@Override
	public synchronized void reset() {
		// generate random 16 bit messageId
		Random random = new Random();
		globalMessageId = random.nextInt(Constants.MESSAGE_ID_MAX + 1);
	}

	@Override
	public void createServerListener(CoapServer serverListener, int localPort) {
		if (!socketMap.containsKey(localPort)) {
			try {
				SocketInformation socketInfo = new SocketInformation(
						new DefaultCoapSocketHandler(this, localPort),
						serverListener);
				socketMap.put(localPort, socketInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			/* TODO: raise exception: address already in use */
			throw new IllegalStateException();
		}
	}

	@Override
	public CoapChannel connect(CoapClient client, InetAddress addr, int port) {
		CoapSocketHandler socketHandler = null;
		try {
			socketHandler = new DefaultCoapSocketHandler(this);
			SocketInformation sockInfo = new SocketInformation(socketHandler,
					null);
			socketMap.put(socketHandler.getLocalPort(), sockInfo);
			return socketHandler.connect(client, addr, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private class SocketInformation {
		public CoapSocketHandler socketHandler = null;
		public CoapServer serverListener = null;

		public SocketInformation(CoapSocketHandler socketHandler,
				CoapServer serverListener) {
			super();
			this.socketHandler = socketHandler;
			this.serverListener = serverListener;
		}
	}
}
