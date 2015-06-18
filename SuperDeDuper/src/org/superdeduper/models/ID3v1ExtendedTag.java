package org.superdeduper.models;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ID3v1ExtendedTag {
	private String title;
	private String artist;
	private String album;
	
	// 0=unset, 1=slow, 2= medium, 3=fast, 4=hardcore
	private byte speed;
	private String genre;
	
	// in the form of mmm:ss
	private String startTime;
	private String endTime;
	
	private static final int bufferLength = 227;


	public ID3v1ExtendedTag(Mp3File mp3) {
		if (mp3 == null || !mp3.isValid()) {
			throw new IllegalArgumentException("The MP3 file is null or invalid");
		}

		byte[] previous = new byte[bufferLength];
		byte[] current = null;
		int bytesRead = -1;
		try {
			FileInputStream input = new FileInputStream(mp3);
			BufferedInputStream buffered = new BufferedInputStream(input);
			do {
				if (current != null) {
					System.arraycopy(current, 0, previous, 0, current.length);
				}
				current = new byte[bufferLength];

				bytesRead = buffered.read(current);
				// System.out.println(bytesRead);
			} while (bytesRead >= bufferLength);
			// System.out.println("previous=" + new String(previous));
			// System.out.println("current=" + new String(current));

			byte[] doubleEndOfFile = new byte[bufferLength * 2];
			System.arraycopy(previous, 0, doubleEndOfFile, 0, previous.length);
			System.arraycopy(current, 0, doubleEndOfFile, previous.length, current.length);
			System.out.println("doubleEndOfFile=" + new String(doubleEndOfFile));
			parseID3v1ExtendedTag(doubleEndOfFile);
		} catch (FileNotFoundException e) {
			System.err.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private void parseID3v1ExtendedTag(byte[] tagInput) {
		int runningIndex = -1;

		// find header "TAG"
		for (int i = 0; i < tagInput.length; i++) {
			boolean foundT = tagInput[i] == (byte) 'T';
			boolean foundA = (tagInput.length > i + 1 && tagInput[i + 1] == (byte) 'A');
			boolean foundG = (tagInput.length > i + 2 && tagInput[i + 2] == (byte) 'G');
			boolean foundPlus = (tagInput.length > i + 3 && tagInput[i + 3] == (byte) '+');
			if (foundT && foundA && foundG && foundPlus) {
				runningIndex = i;
				break;
			}
		}
		if (runningIndex > 0) {
			byte[] tagData = new byte[bufferLength];
			System.arraycopy(tagInput, runningIndex, tagData, 0, bufferLength);
			// System.out.println("tag data= " + new String(tagData));

			runningIndex = 4;
			title = new String(tagData, runningIndex, 60);
			runningIndex += 60;

			artist = new String(tagData, runningIndex, 60);
			runningIndex += 60;

			album = new String(tagData, runningIndex, 60);
			runningIndex += 60;

			speed = tagData[runningIndex++];
			
			genre = new String(tagData, runningIndex, 30);
			runningIndex += 30;
			
			startTime = new String(tagData, runningIndex, 6);
			runningIndex += 6;
			
			endTime = new String(tagData, runningIndex, 6);

			cleanUpData();
		}
	}

	private void cleanUpData() {
		if (title != null) {
			title = title.trim();
		}

		if (artist != null) {
			artist = artist.trim();
		}

		if (album != null) {
			album = album.trim();
		}

		if (genre != null) {
			genre = genre.trim();
		}
		
		if (startTime != null) {
			startTime = startTime.trim();
		}
		
		if (endTime != null) {
			endTime = endTime.trim();
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("title=").append(title).append("\n");
		builder.append("artist=").append(artist).append("\n");
		builder.append("album=").append(album).append("\n");
		builder.append("speed=").append(speed).append("\n");
		builder.append("genre=").append(genre).append("\n");
		builder.append("startTime=").append(startTime).append("\n");
		builder.append("endTime=").append(endTime).append("\n");

		return builder.toString();
	}

	public static void main(String[] args) {
		String filename = "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3";
		Mp3File mp3 = new Mp3File(filename);

		System.out.println(new ID3v1ExtendedTag(mp3));

		filename = "C:\\Users\\Public\\Music\\Sample Music\\Maid with the Flaxen Hair.mp3";
		mp3 = new Mp3File(filename);

		System.out.println(new ID3v1ExtendedTag(mp3));

		filename = "C:\\Users\\Public\\Music\\Sample Music\\Sleep Away.mp3";
		mp3 = new Mp3File(filename);

		System.out.println(new ID3v1ExtendedTag(mp3));
	}
}
