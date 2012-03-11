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

import java.net.URI;

import android.jcoap.messages.CoapHeader;
import android.jcoap.messages.CoapMessageCode.MessageCode;
import android.jcoap.messages.CoapPacketType;

public interface CoapMessage {
	public static final int TYPE_CON = 0;
	public static final int TYPE_NON = 1;
	public static final int TYPE_ACK = 2;
	public static final int TYPE_RST = 3;

	public static final int RESPONSE_TIMEOUT_MS = 2000;
	public static final double RESPONSE_RANDOM_FACTOR = 1.5;
	public static final int MAX_RETRANSMIT = 4;
	/* TODO: what is the right value? */
	public static final int ACK_RST_RETRANS_TIMEOUT_MS = 120000;

	public byte[] serialize();

	public int getProtocolVersion();

	public CoapHeader getHeader();

	public int getMessageID();

	public void setMessageID(int msgID);

	public int getLength();

	public MessageCode getMessageCode();

	public void setMessageCode(MessageCode messageCode);

	public void setOptions(URI uri);

	public CoapPacketType getPacketType();

	public void setPacketType(CoapPacketType packetType);

	public byte[] getPayload();

	public void setPayload(byte[] payload);

	public void setPayload(char[] payload);

	public void setPayload(String payload);

	public int getPayloadLength();

	public String toString();

	public CoapChannel getCoapChannel();

	public void setChannel(CoapChannel channel);

	public void setSendTimestamp(long timestamp);

	public long getSendTimestamp();

	public boolean isConfirmed();

	public void confirmMessage();

	public int getTimeout();

	public boolean maxRetransReached();

	public void incRetransCounterAndTimeout();

	public String getUriPath();

	public boolean isReliable();

	public void copyHeaderOptions(CoapMessage other);

	/* unique by remote address, remote port, local port and message id */
	public int hashCode();

	public boolean equals(Object obj);

}
