package com.rapid7.client.dcerpc.msrprn.messages;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.msrprn.objects.JobInfo1;

public class EnumJobsResponse extends RequestResponse {

	private long lengthNeeded;
	private long jobCount;
	private List<JobInfo1> jobs;
	
	public static final int ERROR_INSUFFICIENT_BUFFER = 0x7a;
	
	public EnumJobsResponse() {
		super();
	}

	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		byte[] data = null;

		// [in, out, unique, size_is(cbBuf), disable_consistency_check] 
		if (packetIn.readReferentID() != 0) {
			int length = packetIn.readInt();
			data = new byte[length];
			packetIn.readFully(data);
		}
		// [out] DWORD* pcbNeeded,
		lengthNeeded = packetIn.readUnsignedInt();
		// [out] DWORD* pcReturned
		jobCount = packetIn.readUnsignedInt();
		
		if (data != null && jobCount > 0) {
			jobs = new LinkedList<>();
			for (int i = 0; i < jobCount; ++i) {
				JobInfo1 info = new JobInfo1();
				info.decode(data, i * 64);
				jobs.add(info);
			}
		}
	}

	public long getLengthNeeded() {
		return lengthNeeded;
	}

	long getJobCount() {
		return jobCount;
	}

	public List<JobInfo1> getJobs() {
		return jobs;
	}
	
}
