package org.mogikabebsoftware.play.thread;

public class CounterThread implements Runnable {

	private Counter counter;
	private int inc;

	
	public CounterThread(Counter counter, int inc) {
		super();
		this.counter = counter;
		this.inc = inc;
	}

	@Override
	public void run() {

		int i = 0;
		while (i++ < 20) {

			try {
				Thread.sleep(100);
				counter.incA(inc);
				counter.incB(inc);
				Thread.yield();
				//counter.printA();
				//counter.printB();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("==========================================================================");
		System.out.println(String.format("CounterThread->%s is done", Thread.currentThread().getName()));
		counter.printA();
		counter.printB();
		System.out.println("==========================================================================");
	}

}
