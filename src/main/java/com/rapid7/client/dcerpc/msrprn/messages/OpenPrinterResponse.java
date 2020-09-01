package com.rapid7.client.dcerpc.msrprn.messages;

import java.io.IOException;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

public class OpenPrinterResponse extends RequestResponse {

	private byte[] handle = new byte[20];
	
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		packetIn.readFully(handle);
	}

	public byte[] getHandle() {
		return handle;
	}

}
