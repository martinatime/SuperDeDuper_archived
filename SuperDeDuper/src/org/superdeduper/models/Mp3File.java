package org.superdeduper.models;
import java.io.File;
import java.net.URI;
import java.util.logging.Logger;

public class Mp3File extends File {
	private static final String CLASSNAME = Mp3File.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);
	
	private boolean valid = true;
	
	public Mp3File(File file, String s) {
		super(file, s);
		init();
	}
	
	public Mp3File(String s, String s1) {
		super(s, s1);
		init();
	}
	
	public Mp3File(String s) {
		super(s);
		init();
	}
	
	public Mp3File(URI uri) {
		super(uri);
		init();
	}
	
	private void init() {
		
	}
	
	public boolean isValid() {
		return valid;
	}
}
