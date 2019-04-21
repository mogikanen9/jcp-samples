package org.mogikabebsoftware.play.executor;

import java.io.IOException;

public class MainRun {

	public static void main(String[] args) throws IOException {
		new LifecycleWebServer().start();
	}

}
