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

/* WS4D Java CoAP Implementation
 * (c) 2011 WS4D.org
 * 
 * written by Sebastian Unger 
 */

package android.jcoap.messages;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Random;

import android.jcoap.interfaces.CoapChannel;
import android.jcoap.interfaces.CoapMessage;
import android.jcoap.messages.CoapHeaderOptions.HeaderOptionNumber;
import android.jcoap.messages.CoapMessageCode.MessageCode;

public class DefaultCoapMessage implements CoapMessage {
	// public static DefaultCoapMessage deserialize(byte[] bytes, int length) {
	// /* length ought to be provided by UDP header */
	// return deserialize(bytes, length, 0);
	// }
	//
	// public static DefaultCoapMessage deserialize(byte[] bytes, int length,
	// int offset) {
	// DefaultCoapMessage result = new DefaultCoapMessage();
	// result.length = length;
	// result.header = CoapHeader.deserialize(bytes, offset);
	// result.payloadLength = length-result.header.getLength();
	// result.payload = new byte[result.payloadLength];
	// for (int i = 0; i < result.payloadLength;i++)
	// result.payload[i] = bytes[result.header.getLength()+i];
	// return result;
	// }

	private byte[] payload = null;
	private int length = 0; /* number of bytes */
	CoapChannel channel = null;

	private int payloadLength = 0;

	private CoapHeader header;

	public DefaultCoapMessage(byte[] bytes, int length) {
		this(bytes, length, 0);
	}

	public DefaultCoapMessage(byte[] bytes, int length, int offset) {
		this.length = length;
		this.header = new CoapHeader(bytes, offset);
		this.payloadLength = length - this.header.getLength();
		this.payload = new byte[this.payloadLength];
		for (int i = 0; i < this.payloadLength; i++)
			this.payload[i] = bytes[this.header.getLength() + i];
	}

	public DefaultCoapMessage(CoapPacketType packetType,
			CoapMessageCode.MessageCode messageCode, int messageID) {
		header = new CoapHeader();
		setPacketType(packetType);
		setMessageCode(messageCode);
		header.setMessageID(messageID);

	}

	public void setHeader(CoapHeader header) {
		this.header = header;
	}

	public int getLength() {
		return length;
	}

	@Override
	public MessageCode getMessageCode() {
		return header.getCode();
	}

	@Override
	public CoapPacketType getPacketType() {
		return header.getType();
	}

	public byte[] getPayload() {
		return payload;
	}

	public int getPayloadLength() {
		return payloadLength;
	}

	@Override
	public int getProtocolVersion() {
		return header.getVersion();
	}

	@Override
	public int getMessageID() {
		return header.getMessageID();
	}

	@Override
	public void setMessageID(int msgID) {
		header.setMessageID(msgID);
	}

	public byte[] serialize() {
		byte[] header = this.header.serialize();
		byte[] bytes = new byte[this.header.getLength() + this.payloadLength];
		for (int i = 0; i < this.header.getLength(); i++)
			bytes[i] = header[i];
		for (int i = 0; i < this.payloadLength; i++) {
			bytes[this.header.getLength() + i] = payload[i];
		}
		return bytes;
	}

	@Override
	public void setMessageCode(MessageCode messageCode) {
		header.setCode(messageCode);
	}

	@Override
	public void setPacketType(CoapPacketType packetType) {
		header.setType(packetType);
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
		if (payload != null)
			this.payloadLength = payload.length;
		else
			this.payloadLength = 0;
	}

	public void setPayload(char[] payload) {
		this.payload = new byte[payload.length];
		for (int i = 0; i < payload.length; i++) {
			this.payload[i] = (byte) payload[i];
		}
		this.payloadLength = payload.length;
	}

	public void setPayload(String payload) {
		setPayload(payload.toCharArray());
	}

	@Override
	public String toString() {
		String result = "";
		result += this.header.toString();
		result += "\nPayload (char):";
		for (int i = 0; i < this.payloadLength; i++)
			result += " " + (char) this.payload[i];
		result += "\nPayload (hex):";
		for (int i = 0; i < this.payloadLength; i++)
			result += " " + Integer.toHexString(this.payload[i]);
		result += "\n";
		return result;
	}

	@Override
	public CoapHeader getHeader() {
		return header;
	}

	@Override
	public void setOptions(URI uri) {

	}

	/* This methods are used by the sending thread for retransmissions */
	private long sendTimestamp = 0;

	@Override
	public void setSendTimestamp(long timestamp) {
		sendTimestamp = timestamp;
	}

	@Override
	public long getSendTimestamp() {
		return sendTimestamp;
	}

	boolean confirmed = false;

	@Override
	public boolean isConfirmed() {
		return confirmed;
	}

	@Override
	public void confirmMessage() {
		confirmed = true;
	}

	int timeout = 0;
	int retransCounter = 0;

	@Override
	public int getTimeout() {
		if (timeout == 0) {
			Random random = new Random();
			timeout = RESPONSE_TIMEOUT_MS
					+ random
							.nextInt((int) (RESPONSE_TIMEOUT_MS * RESPONSE_RANDOM_FACTOR)
									- RESPONSE_TIMEOUT_MS);
		}
		return timeout;
	}

	@Override
	public boolean maxRetransReached() {
		if (retransCounter < MAX_RETRANSMIT) {
			return false;
		}
		return true;
	}

	@Override
	public void incRetransCounterAndTimeout() {
		retransCounter += 1;
		timeout *= 2;
	}

	@Override
	public CoapChannel getCoapChannel() {
		return channel;
	}

	@Override
	public void setChannel(CoapChannel channel) {
		this.channel = channel;
	}

	@Override
	public String getUriPath() {
		if (header != null) {
			StringBuilder uriPathBuilder = new StringBuilder();
			CoapHeaderOptions headerOptions = header.getCoapHeaderOptions();
			for (CoapHeaderOption coapHeaderOption : headerOptions) {
				if (coapHeaderOption.getOptionNumber() == HeaderOptionNumber.Uri_Path) {
					String uriPathElement;
					try {
						uriPathElement = new String(coapHeaderOption
								.getOptionValue(), "UTF-8");
						uriPathBuilder.append("/");
						uriPathBuilder.append(uriPathElement);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			return uriPathBuilder.toString();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + header.getMessageID();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultCoapMessage other = (DefaultCoapMessage) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (header.getMessageID() != other.header.getMessageID())
			return false;
		return true;
	}

	@Override
	public boolean isReliable() {
		if (header.getType() == CoapPacketType.NON) {
			return false;
		}
		return true;
	}

	@Override
	public void copyHeaderOptions(CoapMessage other) {
		for (CoapHeaderOption option : other.getHeader().getCoapHeaderOptions()) {
			getHeader().addOption(option);
		}
	}
}
