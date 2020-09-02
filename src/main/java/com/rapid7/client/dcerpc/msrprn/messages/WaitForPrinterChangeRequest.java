package com.rapid7.client.dcerpc.msrprn.messages;

import java.io.IOException;
import java.util.Objects;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

public class WaitForPrinterChangeRequest extends RequestCall<WaitForPrinterChangeResponse> {

	private static final short OPNUM = 28;
	private byte[] handle;
	private int flags;
		
	public WaitForPrinterChangeRequest(byte[] handle, int flags) {
		super(OPNUM);
		this.handle = Objects.requireNonNull(handle, "handle must not be null");
		this.flags = flags;
	}
	
	public void marshal(PacketOutput packetOut) throws IOException {
		packetOut.write(handle);
		packetOut.writeInt(flags);
	}

	@Override
	public WaitForPrinterChangeResponse getResponseObject() {
		return new WaitForPrinterChangeResponse();
	}

}
