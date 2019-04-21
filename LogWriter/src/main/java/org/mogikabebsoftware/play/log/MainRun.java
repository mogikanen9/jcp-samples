package org.mogikabebsoftware.play.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainRun {

	private static final int NUMBER_OF_THREADS = 10; 
	
	public static void main(String[] args) throws IOException {
		
			
		final CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
		
		ExecutorService exec = Executors.newFixedThreadPool(2);

		final LogWriter logger = new LogWriter(new PrintWriter(new FileOutputStream(new File("c:/temp/mylog.log"))));

		try {
			logger.log("Prep...");
			logger.start();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			exec.execute(() -> {
				try {
					logger.log(String.format("Am doing work in thread ->%s", Thread.currentThread().getName()));
					Thread.sleep(100);
					latch.countDown();					
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			});
			
		}
	
		
		try {
			latch.await();
			logger.log("Done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			logger.stop();
			exec.shutdown();
		}

	}

}
