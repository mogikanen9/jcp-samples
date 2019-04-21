package org.mogikabebsoftware.play.log;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriter {

	private static final int CAPACITY = 100;

	private final BlockingQueue<String> queue;
	private final LoggerThread logger;

	public LogWriter(PrintWriter writer) {
		this.queue = new LinkedBlockingQueue<String>(CAPACITY);
		this.logger = new LoggerThread(writer);
	}

	public void start() {
		logger.start();
	}

	public void stop() {
		synchronized (this) {
			logger.setShutDownRequested(true);
		}
	}

	public void log(String msg) throws InterruptedException {
		queue.put(msg);
	}

	private class LoggerThread extends Thread {

		private final PrintWriter writer;

		private boolean shutDownRequested;

		public LoggerThread(PrintWriter writer) {
			super();
			this.writer = writer;
		}

		public void setShutDownRequested(boolean shutDownRequested) {
			//Option 1
			this.shutDownRequested = shutDownRequested;
			
			//Option 2
			//logger.interrupt();
		}

		public void run() {
			try {
				while (true) {
					if (!shutDownRequested) {
						String msg = queue.take();
						writer.println(msg);
						writer.flush();
						System.out.println(msg);
					} else {
						throw new InterruptedException("Shuttign down the logger");
					}
				}
			} catch (InterruptedException ignored) {
				ignored.printStackTrace();
			} finally {
				writer.close();
			}
		}
	}
}