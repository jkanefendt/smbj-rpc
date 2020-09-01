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

		if (length > 0) {
			// [in, out, unique, size_is(cbBuf), disable_consistency_check] BYTE* pJob
	        packetOut.writeReferentID();
	        packetOut.writeInt(length);
	        packetOut.pad(length);
	        // Alignment: 4 - Already aligned
	        
	        
	        //packetOut.writeEmptyCVArray((int) length);
	        
	       
			//packetOut.writeInt(44);
		} else {
			packetOut.writeNull();
		}
		
        // [in] DWORD cbBuf,
        packetOut.writeInt(length);
        
        //packetOut.pad(length);
        /*
        // [out] DWORD* pcbNeeded,
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
        
        // [out] DWORD* pcReturned
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
        */
	}
	
	@Override
	public EnumJobsResponse getResponseObject() {
		// TODO Auto-generated method stub
		return new EnumJobsResponse();
	}

}
