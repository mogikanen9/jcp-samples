package org.mogikabebsoftware.play.thread;

public class MainRun {

	public static void main(String[] args) {
		
		Counter myCounter = new Counter();
		
		CounterThread t1 = new CounterThread(myCounter, 1);
		
		CounterThread t2 = new CounterThread(myCounter, 1);

		new Thread(t1).start();
		new Thread(t2).start();
	}

}
