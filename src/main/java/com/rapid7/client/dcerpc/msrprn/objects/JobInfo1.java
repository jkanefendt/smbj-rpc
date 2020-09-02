package com.rapid7.client.dcerpc.msrprn.objects;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public class JobInfo1 {

	private int priority;
	private int position;
	private int status;
	private int id;
	private int totalPages;
	private int pagesPrinted;
	private Date submitted;
	private String machineName;
	private String printerName;
	private String userName;
	private String document;
	private String datatype;
	private String statusString;

	private String decodeNullTerminatedUnicodeString(byte[] data, int offset) {
		int pos = offset;
		while (data[pos] != 0 || data[pos + 1] != 0)
			pos += 2;
		return new String(data, offset, pos - offset, StandardCharsets.UTF_16LE);
	}
	
	private String getNullTerminatedUnicodeString(ByteBuffer buffer, int bufferOffset) {
		int offset = buffer.getInt();
		return offset != 0 ? decodeNullTerminatedUnicodeString(buffer.array(), bufferOffset + offset) : null;
	}
	
	private static Date decodeSystemTime(ByteBuffer buffer) {
		  short year = buffer.getShort();
		  short month = buffer.getShort();
		  // ignore wDayOfWeek
		  buffer.getShort();
		  short day = buffer.getShort();
		  short hour = buffer.getShort();
		  short minute = buffer.getShort();
		  short second = buffer.getShort();
		  short milliseconds = buffer.getShort();
		 
		  Calendar cal = Calendar.getInstance();
		  cal.set(year, month - 1, day, hour, minute, second);
		  return new Date(cal.getTimeInMillis() + milliseconds);
	}
	
	public void decode(byte[] data, int offset) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
		buffer.position(offset);
		
		id = buffer.getInt();
		
		printerName = getNullTerminatedUnicodeString(buffer, offset);
		machineName = getNullTerminatedUnicodeString(buffer, offset);
		userName = getNullTerminatedUnicodeString(buffer, offset);
		document = getNullTerminatedUnicodeString(buffer, offset);
		datatype = getNullTerminatedUnicodeString(buffer, offset);
		statusString = getNullTerminatedUnicodeString(buffer, offset);

		status = buffer.getInt();
		priority = buffer.getInt();
		position = buffer.getInt();
		totalPages = buffer.getInt();
		pagesPrinted = buffer.getInt();
		submitted = decodeSystemTime(buffer);
	}

	int getPriority() {
		return priority;
	}

	int getPosition() {
		return position;
	}

	int getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	int getTotalPages() {
		return totalPages;
	}

	int getPagesPrinted() {
		return pagesPrinted;
	}

	Date getSubmitted() {
		return submitted;
	}

	String getMachineName() {
		return machineName;
	}

	String getPrinterName() {
		return printerName;
	}

	String getUserName() {
		return userName;
	}

	public String getDocument() {
		return document;
	}

	String getDatatype() {
		return datatype;
	}

	String getStatusString() {
		return statusString;
	}
	
}
