package org.superdeduper.tasks;

import java.io.File;
import java.io.IOError;
import java.util.List;
import java.util.logging.Logger;

public class CatalogingTask implements Task {
	private static final String CLASSNAME = CatalogingTask.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

	private File startingDirectory;

	private List<File> filesToProcess;

	private List<File> directoriesToProcess;
	
	private List<File> rejectedFiles;

	@Override
	public void execute() {
		final String methodName = "execute";
		LOGGER.entering(CLASSNAME, methodName);

		if (startingDirectory != null && filesToProcess != null && directoriesToProcess != null && rejectedFiles != null) {
			try {
				if (startingDirectory.isDirectory()) {
					File[] files = startingDirectory.listFiles();
					
					for (File file : files) {
						if (file.canRead()) {
							if (file.isDirectory()) {
								directoriesToProcess.add(file);
							} else if (file.isFile()) {
								filesToProcess.add(file);
							} else {
								rejectedFiles.add(file);
							}
						} else {
							rejectedFiles.add(file);
						}
					}
					
				}
			} catch (IOError ioe) {

			}
		}

		LOGGER.exiting(CLASSNAME, methodName);
	}

	@Override
	public void setParameters(Object... params) {
		final String methodName = "setParameters";
		LOGGER.entering(CLASSNAME, methodName);

		if (params != null) {
			if (params[0] != null) {
				if (params[0] instanceof File) {
					startingDirectory = (File) params[0];
				} else if (params[0] instanceof String) {
					String startingDirectoryPath = (String) params[0];
					startingDirectory = new File(startingDirectoryPath);
				}
			}
			if (params[1] != null) {
				filesToProcess = (List<File>)params[1];
			}
			
			if (params[2] != null) {
				directoriesToProcess = (List<File>)params[2];
			}
			
			if (params[3] != null) {
				rejectedFiles = (List<File>)params[3];
			}
		}

		LOGGER.exiting(CLASSNAME, methodName);
	}
}
