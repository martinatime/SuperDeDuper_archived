package org.superdeduper.models;

import java.nio.ByteBuffer;


public class ID3v2ExtendedHeader {
	private int size;
	
	public ID3v2ExtendedHeader(byte[] data) {
		if (data == null || data.length < 6) {
			throw new IllegalArgumentException("Invalid ID3 Extended header");
		}
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(data, 0, 4);
		size = byteBuffer.getInt();

		int numberOfFlagBytes = data[4];
		int runningIndex = 5;
		byte[] flagBytes = new byte[numberOfFlagBytes];
		System.arraycopy(data, runningIndex, flagBytes, 0, numberOfFlagBytes);
		runningIndex += numberOfFlagBytes;
		
		
		// TODO: figure out the rest of the extended header stuff
		
	}

	public int getSize() {
		return size;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		/*
		builder.append("majorVersion=").append(majorVersion).append("\n");
		builder.append("revision=").append(revision).append("\n");
		builder.append("flags=").append(flags).append("\n");
		builder.append("isUnsynchronisation=").append(isUnsynchronisation()).append("\n");
		builder.append("isExtendedHeader=").append(isExtendedHeader()).append("\n");
		builder.append("isExperimentalIndicator=").append(isExperimentalIndicator()).append("\n");
		builder.append("isFooterPresent=").append(isFooterPresent()).append("\n");
		*/
		builder.append("size=").append(size).append("\n");
		
		return builder.toString();
	}
}
