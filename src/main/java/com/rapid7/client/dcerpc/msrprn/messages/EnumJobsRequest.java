package com.rapid7.client.dcerpc.msrprn.messages;

import java.io.IOException;
import java.util.Objects;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

public class EnumJobsRequest extends RequestCall<EnumJobsResponse> {

	private static final short OPNUM = 4;
	
	private static final int JOB_INFO_LEVEL_1 = 0x00000001;
	
	private byte[] handle;

	private long length;
	
	public EnumJobsRequest(byte[] handle, long length) {
		super(OPNUM);
		this.handle = handle;
		this.length = length;
	}
	
	public void marshal(PacketOutput packetOut) throws IOException {
		// PRINTER_HANDLE hPrinter
		packetOut.write(handle);
		// DWORD FirstJob,
		packetOut.writeInt(0);
		// DWORD NoJobs
		packetOut.writeInt(0xffffffff);
		// DWORD Level,
		packetOut.writeInt(JOB_INFO_LEVEL_1);

		// [in, out, unique, size_is(cbBuf), disable_consistency_check] BYTE* pJob
		if (length > 0) {
	        packetOut.writeReferentID();
	        packetOut.writeInt(length);
	        packetOut.pad(length);
	        packetOut.align(Alignment.FOUR);
		} else {
			packetOut.writeNull();
		}
		
        // [in] DWORD cbBuf,
        packetOut.writeInt(length);
	}
	
	@Override
	public EnumJobsResponse getResponseObject() {
		return new EnumJobsResponse();
	}

}
