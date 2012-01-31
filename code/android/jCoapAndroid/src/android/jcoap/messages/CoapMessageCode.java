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

public class CoapMessageCode {
	public enum MessageCode {
		Empty, /* empty messages have code == 0 */
		GET, POST, PUT, DELETE, OK_200, Created_201, Deleted_202, Valid_203, Changed_204, Content_205, Bad_Request_400, Unauthorized_401, Bad_Option_402, Forbidden_403, Not_Found_404, Method_Not_Allowed_405, Precondition_Failed_412, Request_Entity_To_Large_413, Unsupported_Media_Type_415, Internal_Server_Error_500, Not_Implemented_501, Bad_Gateway_502, Service_Unavailable_503, Gateway_Timeout_504, Proxying_Not_Supported_505, UNKNOWN;

		@Override
		public String toString() {
			switch (this) {
			case Empty:
				return "Empty";
			case GET:
				return "GET";
			case POST:
				return "POST";
			case PUT:
				return "PUT";
			case DELETE:
				return "DELETE";
			case OK_200:
				return "OK_200";
			case Created_201:
				return "Created_201";
			case Deleted_202:
				return "Deleted_202";
			case Valid_203:
				return "Valid_203";
			case Changed_204:
				return "Changed_204";
			case Content_205:
				return "Content_205";
			case Bad_Request_400:
				return "Bad_Request_400";
			case Unauthorized_401:
				return "Unauthorized_401";
			case Bad_Option_402:
				return "Bad_Option_402";
			case Forbidden_403:
				return "Forbidden_403";
			case Not_Found_404:
				return "Not_Found_404";
			case Method_Not_Allowed_405:
				return "Method_Not_Allowed_405";
			case Precondition_Failed_412:
				return "Precondition_Failed_412";
			case Request_Entity_To_Large_413:
				return "Request_Entity_To_Large_413";
			case Unsupported_Media_Type_415:
				return "Unsupported_Media_Type_415";
			case Internal_Server_Error_500:
				return "Internal_Server_Error_500";
			case Not_Implemented_501:
				return "Not_Implemented_501";
			case Bad_Gateway_502:
				return "Bad_Gateway_502";
			case Service_Unavailable_503:
				return "Service_Unavailable_503";
			case Gateway_Timeout_504:
				return "Gateway_Timeout_504";
			case Proxying_Not_Supported_505:
				return "Proxying_Not_Supported_505";
			default:
				return "Unknown MessageCode";
			}
		}
	}

	private MessageCode Code;

	public CoapMessageCode() {
		Code = MessageCode.UNKNOWN;
	}

	public CoapMessageCode(MessageCode code) {
		Code = code;
	}

	public CoapMessageCode(int num_code) {
		setCode(num_code);
	}

	public MessageCode getCode() {
		return Code;
	}

	public void setCode(MessageCode code) {
		Code = code;
	}

	public void setCode(int num_code) {
		switch (num_code) {
		case 0:
			this.Code = MessageCode.Empty;
			break;
		case 1:
			this.Code = MessageCode.GET;
			break;
		case 2:
			this.Code = MessageCode.POST;
			break;
		case 3:
			this.Code = MessageCode.PUT;
			break;
		case 4:
			this.Code = MessageCode.DELETE;
			break;
		/* ..31: more to come */
		/* 32..63: reserved */
		case 64:
			this.Code = MessageCode.OK_200;
			break;
		case 65:
			this.Code = MessageCode.Created_201;
			break;
		case 66:
			this.Code = MessageCode.Deleted_202;
			break;
		case 67:
			this.Code = MessageCode.Valid_203;
			break;
		case 68:
			this.Code = MessageCode.Changed_204;
			break;
		case 69:
			this.Code = MessageCode.Content_205;
			break;
		case 128:
			this.Code = MessageCode.Bad_Request_400;
			break;
		case 129:
			this.Code = MessageCode.Unauthorized_401;
			break;
		case 130:
			this.Code = MessageCode.Bad_Option_402;
			break;
		case 131:
			this.Code = MessageCode.Forbidden_403;
			break;
		case 132:
			this.Code = MessageCode.Not_Found_404;
			break;
		case 133:
			this.Code = MessageCode.Method_Not_Allowed_405;
			break;
		case 140:
			this.Code = MessageCode.Precondition_Failed_412;
			break;
		case 141:
			this.Code = MessageCode.Request_Entity_To_Large_413;
			break;
		case 143:
			this.Code = MessageCode.Unsupported_Media_Type_415;
			break;
		case 160:
			this.Code = MessageCode.Internal_Server_Error_500;
			break;
		case 161:
			this.Code = MessageCode.Not_Implemented_501;
			break;
		case 162:
			this.Code = MessageCode.Bad_Gateway_502;
			break;
		case 163:
			this.Code = MessageCode.Service_Unavailable_503;
			break;
		case 164:
			this.Code = MessageCode.Gateway_Timeout_504;
			break;
		case 165:
			this.Code = MessageCode.Proxying_Not_Supported_505;
			break;
		/* ..191: more to come */
		/* 192..255: reserved */
		default:
			this.Code = MessageCode.UNKNOWN;
			break;
		}
	}

	public int toInt() {
		int result;
		switch (this.Code) {
		case Empty:
			result = 0;
			break;
		case GET:
			result = 1;
			break;
		case POST:
			result = 2;
			break;
		case PUT:
			result = 3;
			break;
		case DELETE:
			result = 4;
			break;
		case OK_200:
			result = 64;
			break;
		case Created_201:
			result = 65;
			break;
		case Deleted_202:
			result = 66;
			break;
		case Valid_203:
			result = 67;
			break;
		case Changed_204:
			result = 68;
			break;
		case Content_205:
			result = 69;
			break;
		case Bad_Request_400:
			result = 128;
			break;
		case Unauthorized_401:
			result = 129;
			break;
		case Bad_Option_402:
			result = 130;
			break;
		case Forbidden_403:
			result = 131;
			break;
		case Not_Found_404:
			result = 132;
			break;
		case Method_Not_Allowed_405:
			result = 133;
			break;
		case Precondition_Failed_412:
			result = 140;
			break;
		case Request_Entity_To_Large_413:
			result = 141;
			break;
		case Unsupported_Media_Type_415:
			result = 143;
			break;
		case Internal_Server_Error_500:
			result = 160;
			break;
		case Not_Implemented_501:
			result = 161;
			break;
		case Bad_Gateway_502:
			result = 162;
			break;
		case Service_Unavailable_503:
			result = 163;
			break;
		case Gateway_Timeout_504:
			result = 164;
			break;
		case Proxying_Not_Supported_505:
			result = 165;
			break;
		default:
			result = -1;
			break;
		}
		return result;
	}

	public String toString() {
		String result;
		result = this.Code.toString();
		if ((this.toInt() >= 64) && (this.toInt() < 192)) { /*
															 * response Codes
															 * only
															 */
			/* response code to the beginning and set the '.' ... */
			int responseCodeIndex = result.lastIndexOf("_");
			String responseCode = result.substring(responseCodeIndex + 1,
					result.length());
			responseCode = responseCode.charAt(0) + "."
					+ responseCode.charAt(1) + responseCode.charAt(2);
			/* eventually, replace underscores by spaces */
			result = responseCode + ": "
					+ result.substring(0, responseCodeIndex).replace("_", " ");
		}
		return result;
	}
}
