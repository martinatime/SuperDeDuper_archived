package org.superdeduper.driver;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.superdeduper.tasks.CatalogingTask;

public class SuperDeDuper {
	private static final String CLASSNAME = SuperDeDuper.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	private List<File> masterFileList = new ArrayList<File>();
	private List<File> masterDirectoryList = new ArrayList<File>();
	private List<File> masterRejectedFileList = new ArrayList<File>();
	
	private SuperDeDuper() {
		// empty constructor
	}
	
	private void execute(File startingDir) throws Exception {
		catalogDir(startingDir);
		
		for (File file : masterFileList) {
			System.out.println("[File] " + file.getCanonicalPath());
		}
		
		for (File dir : masterDirectoryList) {
			System.out.println("[Dir] " + dir.getCanonicalPath());
		}
		
		for (File reject : masterRejectedFileList) {
			System.out.println("[Reject] " + reject.getCanonicalPath());
		}
		
		while(!masterDirectoryList.isEmpty()) {
			File poppedDir = masterDirectoryList.remove(0);
			catalogDir(poppedDir);
			
			for (File file : masterFileList) {
				System.out.println("[File] " + file.getCanonicalPath());
			}
			
			for (File dir : masterDirectoryList) {
				System.out.println("[Dir] " + dir.getCanonicalPath());
			}
			
			for (File reject : masterRejectedFileList) {
				System.out.println("[Reject] " + reject.getCanonicalPath());
			}
		}
		
		
	}
	
	private void catalogDir(File dir) throws Exception {
		if (dir.canRead() && dir.isDirectory()) {
			List<File> files = new ArrayList<File>();
			List<File> directories = new ArrayList<File>();
			List<File> rejects = new ArrayList<File>();
			
			CatalogingTask task = new CatalogingTask();
			task.setParameters(dir, files, directories, rejects);
			task.execute();
			
			masterFileList.addAll(files);
			masterDirectoryList.addAll(directories);
			masterRejectedFileList.addAll(rejects);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final String methodName = "main";
		LOGGER.entering(CLASSNAME, methodName);

		if (args != null) {
			String startingDirectory = args[0];
			if (startingDirectory != null && startingDirectory.length() > 0) {
				File startDir = new File(startingDirectory);
				SuperDeDuper driver = new SuperDeDuper();
				driver.execute(startDir);
			}
		}
		
		LOGGER.exiting(CLASSNAME, methodName);
	}
}
