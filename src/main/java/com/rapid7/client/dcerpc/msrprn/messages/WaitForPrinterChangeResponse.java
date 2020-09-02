package com.rapid7.client.dcerpc.msrprn.messages;

import java.io.IOException;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

public class WaitForPrinterChangeResponse extends RequestResponse {

	public static final int PRINTER_CHANGE_TIMEOUT = 0x80000000; 
	
	private int flags;
	
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		flags = packetIn.readInt();
	}

	public int getFlags() {
		return flags;
	}
	
}
