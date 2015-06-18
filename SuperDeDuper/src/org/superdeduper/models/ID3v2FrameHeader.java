package org.superdeduper.models;

import java.nio.ByteBuffer;

public class ID3v2FrameHeader {
	private String frameId;
	private int size;
	private byte[] flags = new byte[2];
	
	public ID3v2FrameHeader(byte[] data) {
		if (data == null || data.length != 10) {
			throw new IllegalArgumentException("Invalid ID3 frame header");
		}
		
		//System.out.println(new String(data));
		
		// skip first 3 bytes as they are ID3
		int runningIndex = 0;
		frameId = new String(data, 0, 4);
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(data, 4, 4);
		size = byteBuffer.getInt();
		
		System.arraycopy(data, 7, flags, 0, 2);
	}

	public byte[] getFlags() {
		return flags;
	}

	public int getSize() {
		return size;
	}
	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("frameId=").append(frameId).append("\n");
		builder.append("size=").append(size).append("\n");
		builder.append("flags=").append(flags).append("\n");
		return builder.toString();
	}
}
