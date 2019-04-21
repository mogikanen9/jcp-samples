package org.mogikanensoftware.play.tpsat;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainRun {

    private static final int CAPACITY = 2;
    private static final int N_THREADS = 2;

    public static void main(String[] args) {
        
        //ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        ThreadPoolExecutor exec
                = new ThreadPoolExecutor(N_THREADS, N_THREADS,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(CAPACITY));
    
        exec.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
       
        for (int i = 0; i < 5; i++) {
            final int count = i;
            exec.execute(() -> {
                String tName = Thread.currentThread().getName();
                System.out.println("Doing some work in thread->"+tName+", count->"+count);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                System.out.println("Work is done in thread->"+tName+", count->"+count);
            }); 
        } 
       
        exec.shutdown();
    }
}