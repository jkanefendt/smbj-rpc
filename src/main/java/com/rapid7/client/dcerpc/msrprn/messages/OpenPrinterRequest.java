package com.rapid7.client.dcerpc.msrprn.messages;

import java.io.IOException;
import java.util.Objects;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

public class OpenPrinterRequest extends RequestCall<OpenPrinterResponse> {

	private static final short OPNUM = 1;
	private WChar.NullTerminated name; 
	
	static final int JOB_ACCESS_ADMINISTER = 0x00000010;
	static final int JOB_ACCESS_READ = 0x00000020;
	static final int PRINTER_ALL_ACCESS = 0x000F000C;
	
	public OpenPrinterRequest(String name) {
		super(OPNUM);
		this.name = WChar.NullTerminated.of(Objects.requireNonNull(name, "name must not be null"));
	}

	private static final Marshallable NULL_DEVMODE_CONTAINER = new Marshallable() {
		@Override
		public void marshalPreamble(PacketOutput out) throws IOException {
		}

		@Override
		public void marshalEntity(PacketOutput out) throws IOException {
			out.align(Alignment.EIGHT);
			// DWORD cbBuf;
			out.writeInt(0);
			// [size_is(cbBuf), unique] BYTE* pDevMode;
			out.writeNull();
		}

		@Override
		public void marshalDeferrals(PacketOutput out) throws IOException {
		}
	};
	
	public void marshal(PacketOutput packetOut) throws IOException {
		// [in, string, unique] STRING_HANDLE pPrinterName,
		packetOut.writeReferentID();
		packetOut.writeMarshallable(name);
		packetOut.align(Alignment.FOUR);
		//   [in, string, unique] wchar_t* pDatatype,
		packetOut.writeNull();
		//   [in] DEVMODE_CONTAINER* pDevModeContainer,
		packetOut.writeReferentID();
		packetOut.writeMarshallable(NULL_DEVMODE_CONTAINER);
		//   [in] DWORD AccessRequired
		packetOut.writeInt(PRINTER_ALL_ACCESS);
	}

	@Override
	public OpenPrinterResponse getResponseObject() {
		return new OpenPrinterResponse();
	}

}
