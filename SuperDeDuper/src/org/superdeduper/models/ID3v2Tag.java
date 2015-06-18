package org.superdeduper.models;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ID3v2Tag {

	private static final int bufferLength = 10;

	public ID3v2Tag(Mp3File mp3) {
		if (mp3 == null || !mp3.isValid()) {
			throw new IllegalArgumentException("The MP3 file is null or invalid");
		}

		try {
			FileInputStream input = new FileInputStream(mp3);
			BufferedInputStream buffered = new BufferedInputStream(input);
			byte[] headerData = new byte[10];
			buffered.read(headerData);
			// System.out.println(new String(buffer));
			boolean foundI = headerData[0] == 'I';
			boolean foundD = headerData[1] == 'D';
			boolean found3 = headerData[2] == '3';
			if (foundI && foundD && found3) {
				// System.out.println("Tag found");
				ID3v2Header header = new ID3v2Header(headerData);
				// System.out.println(header);

				byte[] buffer = new byte[header.getSize()];
				buffered.read(buffer);
				// System.out.println(new String(buffer));

				ID3v2Frame[] frames = processFrames(buffer);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private ID3v2Frame[] processFrames(byte[] framesData) {
		ID3v2Frame[] frames = null;

		int runningIndex = 0;

		while (framesData.length > runningIndex) {
			byte[] frameHeaderData = new byte[10];
			System.arraycopy(framesData, runningIndex, frameHeaderData, 0, 10);
			ID3v2FrameHeader frameHeader = new ID3v2FrameHeader(frameHeaderData);
			runningIndex += 10;
			System.out.println("runningIndex =" + runningIndex + " frameHeader=" + frameHeader);
			runningIndex += frameHeader.getSize();
		}

		return frames;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		return builder.toString();
	}

	public static void main(String[] args) {
		String filename = "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3";
		Mp3File mp3 = new Mp3File(filename);

		System.out.println(new ID3v2Tag(mp3));

		/*
		 * filename = "C:\\Users\\Public\\Music\\Sample Music\\Maid with the Flaxen Hair.mp3"; mp3 = new
		 * Mp3File(filename);
		 * 
		 * System.out.println(new ID3v2Tag(mp3));
		 * 
		 * filename = "C:\\Users\\Public\\Music\\Sample Music\\Sleep Away.mp3"; mp3 = new Mp3File(filename);
		 * 
		 * System.out.println(new ID3v2Tag(mp3));
		 */
	}
}
