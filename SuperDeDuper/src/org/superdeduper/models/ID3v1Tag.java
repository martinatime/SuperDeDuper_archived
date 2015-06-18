package org.superdeduper.models;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ID3v1Tag {
	private String title;
	private String artist;
	private String album;
	private String year;
	private String comment;
	private byte track;
	private byte genre;

	public ID3v1Tag(Mp3File mp3) {
		if (mp3 == null || !mp3.isValid()) {
			throw new IllegalArgumentException("The MP3 file is null or invalid");
		}

		byte[] previous = new byte[128];
		byte[] current = null;
		int bytesRead = -1;
		try {
			FileInputStream input = new FileInputStream(mp3);
			BufferedInputStream buffered = new BufferedInputStream(input);
			do {
				if (current != null) {
					System.arraycopy(current, 0, previous, 0, current.length);
				}
				current = new byte[128];

				bytesRead = buffered.read(current);
				// System.out.println(bytesRead);
			} while (bytesRead >= 128);
			// System.out.println("previous=" + new String(previous));
			// System.out.println("current=" + new String(current));

			byte[] doubleEndOfFile = new byte[256];
			System.arraycopy(previous, 0, doubleEndOfFile, 0, previous.length);
			System.arraycopy(current, 0, doubleEndOfFile, previous.length, current.length);
			// System.out.println("doubleEndOfFile=" + new String(doubleEndOfFile));
			parseID3v1Tag(doubleEndOfFile);
		} catch (FileNotFoundException e) {
			System.err.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private void parseID3v1Tag(byte[] tagInput) {
		int runningIndex = -1;

		// find header "TAG"
		for (int i = 0; i < tagInput.length; i++) {
			boolean foundT = tagInput[i] == (byte) 'T';
			boolean foundA = (tagInput.length > i + 1 && tagInput[i + 1] == (byte) 'A');
			boolean foundG = (tagInput.length > i + 2 && tagInput[i + 2] == (byte) 'G');
			if (foundT && foundA && foundG) {
				runningIndex = i;
				break;
			}
		}
		if (runningIndex > 0) {
			byte[] tagData = new byte[128];
			System.arraycopy(tagInput, runningIndex, tagData, 0, 128);
			// System.out.println("tag data= " + new String(tagData));

			runningIndex = 3;
			title = new String(tagData, runningIndex, 30);
			runningIndex += 30;

			artist = new String(tagData, runningIndex, 30);
			runningIndex += 30;

			album = new String(tagData, runningIndex, 30);
			runningIndex += 30;

			year = new String(tagData, runningIndex, 4);
			runningIndex += 4;

			byte[] commentBytes = new byte[30];
			System.arraycopy(tagData, runningIndex, commentBytes, 0, 30);
			// check for track number flag
			if (commentBytes[28] == 0) {
				track = commentBytes[29];
				comment = new String(commentBytes, 0, 28);
			} else {
				comment = new String(commentBytes, 0, 30);
			}
			runningIndex += 30;

			genre = tagData[runningIndex];

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

		if (year != null) {
			year = year.trim();
		}

		if (comment != null) {
			comment = comment.trim();
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("title=").append(title).append("\n");
		builder.append("artist=").append(artist).append("\n");
		builder.append("album=").append(album).append("\n");
		builder.append("year=").append(year).append("\n");
		builder.append("comment=").append(comment).append("\n");
		builder.append("track=").append(track).append("\n");
		builder.append("genre=").append(genre).append("\n");

		return builder.toString();
	}

	public static void main(String[] args) {
		String filename = "C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3";
		Mp3File mp3 = new Mp3File(filename);

		System.out.println(new ID3v1Tag(mp3));

		filename = "C:\\Users\\Public\\Music\\Sample Music\\Maid with the Flaxen Hair.mp3";
		mp3 = new Mp3File(filename);

		System.out.println(new ID3v1Tag(mp3));

		filename = "C:\\Users\\Public\\Music\\Sample Music\\Sleep Away.mp3";
		mp3 = new Mp3File(filename);

		System.out.println(new ID3v1Tag(mp3));
	}
}
