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

package android.jcoap.messages;

import android.jcoap.messages.CoapHeaderOptions.HeaderOptionNumber;

public class CoapHeaderOption implements Comparable<CoapHeaderOption> {

	int optionNumber = HeaderOptionNumber.UNKNOWN;
	byte[] optionValue = null;
	int shortLength = 0;
	int longLength = 0;

	public CoapHeaderOption() {
	}

	// Andy: repaired header-long-length serializing: if length > 15 then make
	// short-length = 15 and set long-length to length-15
	public CoapHeaderOption(int optionNumber, byte[] value) {
		this.optionNumber = optionNumber;
		this.optionValue = value;
		if (value.length < 15) {
			shortLength = value.length;
			longLength = 0;
		} else {
			shortLength = 15;
			longLength = value.length - shortLength; // Andy: added
														// "- shortLength"
		}
	}

	@Override
	public int compareTo(CoapHeaderOption option) {
		if (this.optionNumber != option.optionNumber)
			return this.optionNumber < option.optionNumber ? -1 : 1;
		else
			return 0;
	}

	public int getLongLength() {
		return longLength;
	}

	public int getOptionNumber() {
		return optionNumber;
	}

	public byte[] getOptionValue() {
		return optionValue;
	}

	public int getShortLength() {
		return shortLength;
	}

	public void setLongLength(int l) {
		this.longLength = l;
	}

	public void setOptionNumber(int optionNumber) {
		this.optionNumber = optionNumber;
	}

	public void setOptionValue(byte[] v) {
		this.optionValue = v;
	}

	public void setOptionValue(char[] v) {
		this.optionValue = new byte[v.length];
		for (int i = 0; i < v.length; i++) {
			this.optionValue[i] = (byte) v[i];
		}
	}

	public void setOptionValue(String v) {
		this.optionValue = v.getBytes();
	}

	public void setShortLength(int l) {
		this.shortLength = l;
	}

	@Override
	public String toString() {
		char[] printableOptionValue = new char[optionValue.length];
		for (int i = 0; i < optionValue.length; i++)
			printableOptionValue[i] = (char) optionValue[i];
		return "Option Number: " + " (" + optionNumber + ")"
				+ ", Option Value: " + String.copyValueOf(printableOptionValue);
	}

}
