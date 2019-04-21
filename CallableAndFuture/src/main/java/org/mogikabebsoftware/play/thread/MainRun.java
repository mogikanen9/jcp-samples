package org.mogikabebsoftware.play.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainRun {

	public static void main(String[] args) {

		long initTime = System.currentTimeMillis();
		
		ExecutorService exec = Executors.newFixedThreadPool(5);
		List<Future<Integer>> tasks = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			int from = i*10+1;
			int to = from+9;
			System.out.println(String.format("range: %d - %d", from,to));
			tasks.add(exec.submit(new MyRangeSum(from, to)));
		}

		System.out.println("============All tasks submitted=============");				
		
		int theSum = 0;
		for(Future<Integer> task: tasks) {
			try {
				theSum+=task.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("theSum->"+theSum);
		System.out.println(String.format("Took %d ms",(System.currentTimeMillis()-initTime)));
		exec.shutdown();
	}

}

class MyRangeSum implements Callable<Integer> {

	private int from;
	private int to;

	public MyRangeSum(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}

	@Override
	public Integer call() throws Exception {
		int rs = 0;
		for (int i = from; i <= to; i++) {
			rs += i;
		}
		Thread.sleep(100); // emulate heavy calc
		return rs;
	}

}