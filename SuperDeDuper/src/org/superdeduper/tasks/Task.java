package org.superdeduper.tasks;

public interface Task {
	void setParameters(Object...params);
	
	void execute();
}
