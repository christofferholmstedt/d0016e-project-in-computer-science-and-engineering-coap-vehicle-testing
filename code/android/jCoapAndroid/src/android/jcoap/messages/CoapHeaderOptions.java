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

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class that holds a list of CoapHeaderOption
 * 
 * @author Sebastian Unger <sebastian.unger@uni-rostock.de>
 * @author Nico Laum <nico.laum@uni-rostock.de>
 */
public class CoapHeaderOptions implements Iterable<CoapHeaderOption> {

	public static final class HeaderOptionNumber {
		public static final int UNKNOWN = -1;
		public static final int Content_Type = 1;
		public static final int Max_Age = 2;
		public static final int Proxy_Uri = 3;
		public static final int Etag = 4;
		public static final int Uri_Host = 5;
		public static final int Location_Path = 6;
		public static final int Uri_Port = 7;
		public static final int Location_Query = 8;
		public static final int Uri_Path = 9;
		public static final int Token = 11;
		public static final int Accept = 12;
		public static final int If_Match = 13;
		public static final int Uri_Query = 15;
		public static final int If_None_Match = 21;
	}

	static public CoapHeaderOptions deserialize(byte[] bytes, int option_count) {
		return deserialize(bytes, 0, option_count);
	}

	public CoapHeaderOption getOption(int optionNumber) {
		for (CoapHeaderOption headerOption : headerOptions) {
			if (headerOption.getOptionNumber() == optionNumber) {
				return headerOption;
			}
		}
		return null;
	}

	static public CoapHeaderOptions deserialize(byte[] bytes, int offset,
			int option_count) {
		CoapHeaderOptions result = new CoapHeaderOptions();
		/*
		 * again, we need to keep track of this since we only receive deltas and
		 * never concrete numbers
		 */
		int lastOptionNumber = 0;
		int arrayIndex = offset;
		for (int i = 0; i < option_count; i++) {
			CoapHeaderOption tmp = new CoapHeaderOption();
			/* Calculate Option Number from Delta */
			tmp.setOptionNumber(((bytes[arrayIndex] & 0xF0) >> 4)
					+ lastOptionNumber);
			lastOptionNumber = tmp.getOptionNumber();
			result.length += 1; /* keep track of length */

			/* Calculate length fields and real length */
			int tmpLength = 0;
			if ((bytes[arrayIndex] & 0x0F) < 15) {
				tmp.setShortLength(bytes[arrayIndex++] & 0x0F);
				tmp.setLongLength(0);
				tmpLength = tmp.getShortLength();
			} else {
				tmp.setShortLength(bytes[arrayIndex++] & 0x0F);
				tmp.setLongLength(bytes[arrayIndex++]);
				/* FIXED: CL */
				// tmpLength = tmp.getLongLength();
				tmpLength = tmp.getLongLength() + 15;
				result.length += 1; /* additional length byte */
			}
			result.length += tmpLength;
			byte[] optionValue = new byte[tmpLength];
			for (int j = 0; j < tmpLength; j++)
				optionValue[j] = bytes[arrayIndex + j];
			tmp.setOptionValue(optionValue);
			arrayIndex += tmpLength;
			result.addOption(tmp);
		}
		return result;
	}

	private Vector<CoapHeaderOption> headerOptions = new Vector<CoapHeaderOption>();
	private int length = 0; // [b]

	public void addOption(CoapHeaderOption option) {
		headerOptions.add(option);
		Collections.sort(headerOptions);
	}

	public void addOption(int option_number, byte[] value) throws Exception {
		headerOptions.add(new CoapHeaderOption(option_number, value));
	}

	public void removeOption(int optNumber) {
		CoapHeaderOption headerOption;
		// get elements of Vector

		/* note: iterating and changing a vector at the same time is not allowed */
		int i = 0;
		while (i < headerOptions.size()) {
			headerOption = headerOptions.get(i);
			if (headerOption.getOptionNumber() == optNumber) {
				headerOptions.remove(i);
			} else {
				/* only increase when no element was removed */
				i++;
			}
		}
		Collections.sort(headerOptions);
	}

	public int getLength() {
		return this.length;
	}

	public int getNumberOfHeaderoptions() {
		return headerOptions.size();
	}

	public byte[] serialize() {
		this.length = 0;

		/* find length of options_string. Each option contains ... */
		for (CoapHeaderOption option : headerOptions) {
			++this.length;
			// ... sometimes an additional length-byte
			if (option.getLongLength() > 0) {
				++this.length;
			}
			this.length += option.getOptionValue().length;
		}

		// TODO: don't allocate new memory every time serialize() is called
		byte[] data = new byte[this.length];
		int arrayIndex = 0;

		int lastOptionNumber = 0; /* let's keep track of this */
		for (CoapHeaderOption headerOption : headerOptions) {
			int optionDelta = headerOption.getOptionNumber() - lastOptionNumber;
			lastOptionNumber = headerOption.getOptionNumber();
			// set length(s)
			data[arrayIndex++] = (byte) (((optionDelta & 0x0F) << 4) | (headerOption
					.getShortLength() & 0x0F));
			if (headerOption.getLongLength() > 0) {
				data[arrayIndex++] = (byte) (headerOption.getLongLength() & 0xFF);
			}
			// copy option value
			byte[] value = headerOption.getOptionValue();
			for (int i = 0; i < value.length; i++) {
				data[arrayIndex++] = value[i];
			}
		}

		return data;
	}

	@Override
	public String toString() {
		String result = "\tOptions:\n";
		for (CoapHeaderOption option : headerOptions) {
			result += "\t\t" + option.toString() + "\n";
		}
		return result;
	}

	@Override
	public Iterator<CoapHeaderOption> iterator() {
		return headerOptions.iterator();
	}

	public int getCount() {
		return headerOptions.size();
	}
}
