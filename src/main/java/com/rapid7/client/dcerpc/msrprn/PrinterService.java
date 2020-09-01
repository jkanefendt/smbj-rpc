package com.rapid7.client.dcerpc.msrprn;

import java.io.IOException;
import java.util.List;

import com.rapid7.client.dcerpc.msrprn.messages.EnumJobsRequest;
import com.rapid7.client.dcerpc.msrprn.messages.EnumJobsResponse;
import com.rapid7.client.dcerpc.msrprn.messages.OpenPrinterRequest;
import com.rapid7.client.dcerpc.msrprn.messages.OpenPrinterResponse;
import com.rapid7.client.dcerpc.msrprn.objects.JobInfo1;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class PrinterService extends Service {

	private static final int MAX_RETRY_COUNT = 256;
	
	protected PrinterService(RPCTransport transport) {
		super(transport);
	}
	
	public byte[] open(String name) {
		OpenPrinterResponse r;
		try {
			r = call(new OpenPrinterRequest(name));
		} catch (IOException e) {
			throw new RuntimeException("RpcOpenPrinter failed", e);
		}
		if (r.getReturnValue() != 0)
			throw new RuntimeException("RpcOpenPrinter failed with code " + r.getReturnValue());
		return r.getHandle();
	}
	
	public List<JobInfo1> enumJobs(byte[] handle) {
		long length = 0;
		for (int i = 0; i < MAX_RETRY_COUNT; ++i) {
			EnumJobsResponse r;
			try {
				r = call(new EnumJobsRequest(handle, length));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (r.getReturnValue() == 0) {
				return r.getJobs();
			} else if (r.getReturnValue() != EnumJobsResponse.ERROR_INSUFFICIENT_BUFFER) {
				throw new RuntimeException("RpcEnumJobs failed with code " + r.getReturnValue());
			}
			length = r.getLengthNeeded();
		}
		throw new RuntimeException("Maximum retry count for RpcEnumJobs reached");
	}

}
