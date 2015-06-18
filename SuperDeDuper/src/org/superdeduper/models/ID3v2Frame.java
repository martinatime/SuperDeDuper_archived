package org.superdeduper.models;

import java.nio.ByteBuffer;

public class ID3v2Frame {
	private byte majorVersion;
	private byte revision;
	private byte flags;
	private int size;
	
	public ID3v2Frame(byte[] data) {
		if (data == null || data.length != 10) {
			throw new IllegalArgumentException("Invalid ID3 header");
		}
		
		//System.out.println(new String(data));
		
		// skip first 3 bytes as they are ID3
		int runningIndex = 3;
		majorVersion = data[runningIndex++];
		revision = data[runningIndex++];
		flags = data[runningIndex++];
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(data, runningIndex,4);
		size = byteBuffer.getInt();
		
		// if any of the lower 4 bits of the flags are set then the flags need to be zeroed
		int flagValidate = flags & 0x0F;
		if (flagValidate > 0) {
			flags = 0;
		}
	}

	public byte getMajorVersion() {
		return majorVersion;
	}

	public byte getRevision() {
		return revision;
	}

	public byte getFlags() {
		return flags;
	}

	public int getSize() {
		return size;
	}
	
	public boolean isUnsynchronisation() {
		int result = flags & 0x80;
		return result == 0x80;
	}
	
	public boolean isExtendedHeader() {
		int result = flags & 0x40;
		return result == 0x40;
	}
	
	public boolean isExperimentalIndicator() {
		int result = flags & 0x20;
		return result == 0x20;
	}
	
	public boolean isFooterPresent() {
		int result = flags & 0x10;
		return result == 0x10;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("majorVersion=").append(majorVersion).append("\n");
		builder.append("revision=").append(revision).append("\n");
		builder.append("flags=").append(flags).append("\n");
		builder.append("isUnsynchronisation=").append(isUnsynchronisation()).append("\n");
		builder.append("isExtendedHeader=").append(isExtendedHeader()).append("\n");
		builder.append("isExperimentalIndicator=").append(isExperimentalIndicator()).append("\n");
		builder.append("isFooterPresent=").append(isFooterPresent()).append("\n");
		builder.append("size=").append(size).append("\n");
		return builder.toString();
	}
}
