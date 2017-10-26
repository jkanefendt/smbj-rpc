/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr;

import java.io.IOException;
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Request;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Response;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class SecurityAccountManagerService {
    private final RPCTransport transport;

    public SecurityAccountManagerService(RPCTransport transport) {
	this.transport = transport;
    }

    public ServerHandle getServerHandle(String serverName) throws IOException {
	final SamrConnect2Request request = new SamrConnect2Request(serverName, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
	final SamrConnect2Response response = transport.call(request);
	return response.getHandle();
    }

    public DomainHandle openDomain(String serverName, SID sid) throws IOException {
	final ServerHandle handle = getServerHandle(serverName);
	final SamrOpenDomainRequest request = new SamrOpenDomainRequest(handle, sid,
	        EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
	final SamrOpenDomainResponse response = transport.call(request);
	return response.getHandle();
    }

    public DomainHandle openDomain(ServerHandle serverHandle, SID sid) throws IOException {
	final SamrOpenDomainRequest request = new SamrOpenDomainRequest(serverHandle, sid,
	        EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
	final SamrOpenDomainResponse response = transport.call(request);
	return response.getHandle();
    }
}
