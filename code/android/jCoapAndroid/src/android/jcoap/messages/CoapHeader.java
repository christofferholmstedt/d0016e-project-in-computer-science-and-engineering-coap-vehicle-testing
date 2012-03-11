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
 */

package android.jcoap.messages;

import android.jcoap.messages.CoapMessageCode.MessageCode;

/**
 * Implementation for a CoAP Header (IETF Draft Version 04)
 * 
 * @author Sebastian Unger <sebastian.unger@uni-rostock.de>
 */
public class CoapHeader {
	// static public CoapHeader deserialize(byte[] bytes) {
	// return deserialize(bytes, 0);
	// }
	// static public CoapHeader deserialize(byte[] bytes, int offset) {
	// CoapHeader result = new CoapHeader();
	// /* this is actually pretty straight forward */
	// result.version = (bytes[offset + 0] & 0xC0) >> 6;
	// result.setType((bytes[offset + 0] & 0x30) >> 4);
	// result.optionCount = bytes[offset + 0] & 0x0F;
	// result.setCode(bytes[offset+1] & 0xFF);
	// result.messageID = ((bytes[offset+2] << 8) & 0xFF00) + (bytes[offset+3] &
	// 0xFF);
	//
	// result.length = 4;
	//
	// if (result.getCode() != MessageCode.Empty) { /* Is this supposed to work
	// like that..? */
	// result.options = CoapHeaderOptions.deserialize(bytes, 4,
	// result.optionCount);
	// result.length += result.options.getLength();
	// }
	// return result;
	// }

	private int version;
	private CoapPacketType type;
	private CoapMessageCode code;

	private int messageID;
	private CoapHeaderOptions options;

	private int length; /* numbers of bytes of header */

	public CoapHeader() {
		version = 1; /*
					 * anything different than 1 is strongly discouraged right
					 * now...
					 */
		code = new CoapMessageCode();
		this.options = null;
	}

	public CoapHeader(byte[] bytes) {
		this(bytes, 0);
	}

	public CoapHeader(byte[] bytes, int offset) {
		code = new CoapMessageCode();
		/* this is actually pretty straight forward */
		this.version = (bytes[offset + 0] & 0xC0) >> 6;
		this.setType((bytes[offset + 0] & 0x30) >> 4);
		int optionCount = bytes[offset + 0] & 0x0F;
		this.setCode(bytes[offset + 1] & 0xFF);
		this.messageID = ((bytes[offset + 2] << 8) & 0xFF00)
				+ (bytes[offset + 3] & 0xFF);
		this.length = 4;
		if (this.getCode() != MessageCode.Empty) { /*
													 * Is this supposed to work
													 * like that..?
													 */
			this.options = CoapHeaderOptions.deserialize(bytes, 4, optionCount);
			this.length += this.options.getLength();
		}
	}

	public void addOption(CoapHeaderOption option) {
		if (this.options == null)
			this.options = new CoapHeaderOptions();
		this.options.addOption(option);
	}

	public void addOption(int optionNumber, byte[] value) {
		this.addOption(new CoapHeaderOption(optionNumber, value));
	}

	public MessageCode getCode() {
		return code.getCode();
	}

	public int getCodeAsInt() {
		return code.toInt();
	}

	public CoapHeaderOptions getCoapHeaderOptions() {
		return options;
	}

	public int getLength() {
		return this.length;
	}

	public int getMessageID() {
		return messageID;
	}

	public int getOptionCount() {
		if (options == null) {
			return 0;
		}
		return options.getCount();
	}

	public CoapHeaderOption getOption(int optionNumber) {
		if (options == null) {
			return null;
		}
		return options.getOption(optionNumber);
	}

	public CoapPacketType getType() {
		return type;
	}

	public int getTypeId() {
		return type.typeId();
	}

	public int getVersion() {
		return version;
	}

	public byte[] serialize() {
		int optionsLength = 0;
		byte[] optionsArray = null;
		/* as soon as we serialize options, we know their length */
		if (options != null) {
			optionsArray = this.options.serialize();
			optionsLength = this.options.getLength();
		}
		this.length = 4 + optionsLength;

		/* Now, we can initialize the array for the whole header ... */
		byte[] header_array = new byte[this.length];

		/* ... assign the first four bytes ... */
		header_array[0] = (byte) ((this.version & 0x03) << 6);
		header_array[0] |= (byte) ((this.type.typeId() & 0x03) << 4);
		header_array[0] |= (byte) (getOptionCount() & 0x0F);
		header_array[1] = (byte) (this.code.toInt() & 0xFF);
		header_array[2] = (byte) ((this.messageID >> 8) & 0xFF);
		header_array[3] = (byte) (this.messageID & 0xFF);

		/* ... and copy options into array */
		if (options != null) {
			for (int i = 0; i < this.options.getLength(); i++)
				header_array[i + 4] = optionsArray[i];
		}
		return header_array;
	}

	public void setCode(CoapMessageCode code) {
		this.code = code;
	}

	public void setCode(int code) {
		this.code.setCode(code);
	}

	public void setCode(MessageCode code) {
		this.code.setCode(code);
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public void setType(CoapPacketType type) {
		this.type = type;
	}

	public void setType(int typeId) {
		this.type = CoapPacketType.getPacketType(typeId);
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		String result = "";
		result += "Header:\n";
		result += "\tVersion:      " + this.version + ",\n";
		result += "\tType:         " + this.type.toString() + "("
				+ this.type.typeId() + "),\n";
		result += "\tOption Count: " + getOptionCount() + ",\n";
		result += "\tMessage Code: " + this.code.toString() + "("
				+ this.code.toInt() + "),\n";
		result += "\tMessage ID:   " + this.messageID + ",\n";
		if (this.options != null)
			result += this.options.toString();
		return result;
	}

	public String toStringShort() {
		return "{V" + this.version + " MsgID: " + this.messageID + " "
				+ this.type.toString() + " " + this.code.toString() + "}";
	}
}
