package android.jcoap.messages;

public final class CoapMediaType {

	public static final int UNKNOWN = -1;
	public static final int text_plain = 0; // text/plain; charset=utf-8
	public static final int link_format = 40; // application/link-format
	public static final int xml = 41; // application/xml
	public static final int octet_stream = 42; // application/octet-stream
	public static final int exi = 47; // application/exi
	public static final int json = 50; // application/json

	// text_plain(0x00), //charset utf8
	// linkformat(0x28),
	// xml(0x29),
	// octetstream(0x2A),
	// exi(0x2F),
	// json(0x32),
	// UNKNOWN(-1);
	//
	// private final int typeId;
	//
	// CoapMediaType(int typeId) {
	// this.typeId = typeId;
	// }
	//
	// public static CoapMediaType getMediaType(int typeId) {
	// if (typeId == 0x00)
	// return text_plain;
	// else if (typeId == 0x28)
	// return linkformat;
	// else if (typeId == 0x29)
	// return xml;
	// else if (typeId == 0x2A)
	// return octetstream;
	// else if (typeId == 0x2F)
	// return exi;
	// else if (typeId == 0x32)
	// return json;
	// else
	// return UNKNOWN;
	// }
	//
	// public int typeId() {
	// return typeId;
	// }

}