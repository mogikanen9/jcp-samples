package org.mogikabebsoftware.play.thread;

import java.util.Objects;

public class Counter {
	
	private int counterA;
	
	private ThreadLocal<Integer> counterB = new ThreadLocal<>();
	
	
	public int incA(int inc) {
		counterA = counterA+inc;
		return counterA;
	}
			
	public int incB(int inc) {
		
		if(Objects.isNull(counterB.get())) {
			counterB.set(Integer.valueOf(0));
		}
		
		counterB.set(counterB.get()+inc);
		return counterB.get();
	}
	
	public void printA() {
		System.out.println(String.format("counterA->%d from thread->%s", counterA, Thread.currentThread().getName()));
	}
	
	public void printB() {
		System.out.println(String.format("counterB->%d from thread->%s", counterB.get(), Thread.currentThread().getName()));
	}
}
