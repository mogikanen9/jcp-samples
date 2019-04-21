package org.mogikabebsoftware.play.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

public class LifecycleWebServer {

	private final ExecutorService exec = Executors.newFixedThreadPool(2);

	public void start() throws IOException {
		System.out.println("Starting LifecycleWebServer ...");
		final ServerSocket socket = new ServerSocket(8094);
		System.out.println("Socket is opened...");
		try {
			while (!exec.isShutdown()) {
				try {
					final Socket conn = socket.accept();
					System.out.println("Connection established...");
					exec.execute(new Runnable() {
						public void run() {
							try {
								System.out.println(String.format("Handling request in thread -> %s ...", Thread.currentThread().getName()));
								handleRequest(conn);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				} catch (RejectedExecutionException e) {
					if (!exec.isShutdown()) {
						System.err.println("task submission rejected ->" + e);
						e.printStackTrace();
					}
				}
			}
		} finally {
			socket.close();
		}

	}

	public void stop() {
		exec.shutdown();
	}

	protected void handleRequest(Socket connection) throws IOException {
		
		Request req = readRequest(connection);
		if (isShutdownRequest(req)) {
			stop();
		} else {
			dispatchRequest(req);
		}
	}

	protected Request readRequest(Socket connection) throws IOException {
		String result = null;
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			result = buffer.lines().collect(Collectors.joining(""));
		}

		return new Request(result);
	}

	protected boolean isShutdownRequest(Request req) {
		return false;
	}

	protected void dispatchRequest(Request req) {
		System.out.println(String.format("Request->%s",req.getSomeData()));
	}
}