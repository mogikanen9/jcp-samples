package org.mogikabebsoftware.play.executor;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientRun {

	public static void main(String[] args) throws UnknownHostException, IOException {

		
		List<String> messages = Arrays.asList(
				"I like milk", 
				"Where is banana?", 
				"Santa is cool", 
				"Levyi Parohod",
				"iPad",
				"iPod",
				"iPaid",
				"iPhone",
				"iMac",
				"iHome");
		ExecutorService exec = Executors.newFixedThreadPool(messages.size());
		messages.forEach((msg) -> exec.submit(new SimpleClient(msg)));

	}

}

class SimpleClient implements Runnable {

	private String message;

	public SimpleClient(String message) {
		super();
		this.message = message;
	}

	@Override
	public void run() {
		Socket client = null;
		try {
			
			System.out.println(String.format("Submitting request from thread -> %s ...", Thread.currentThread().getName()));
			
			client = new Socket("127.0.01", 8094);
						
			//Thread.sleep(100);
			
			client.getOutputStream().write(new String(message).getBytes());
			client.close();
		} catch (IOException /*| InterruptedException*/ e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
