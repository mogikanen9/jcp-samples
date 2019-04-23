package org.mogikanensoftware.play.rwlock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainRun {

    public static void main(String[] args) {

        int num = 10000;

        useMutex(num);

        useReadWriteLock(num);

    }

    protected static void useReadWriteLock(int num) {
        long initTime = System.currentTimeMillis();

        final ReadWriteMap<String, String> map = new ReadWriteMap<String, String>(new HashMap<>());
        // final Map<String, String> map = new ConcurrentHashMap<>();

        ExecutorService exec = Executors.newFixedThreadPool(num / 10);

        for (int i = 0; i < num; i++) {

            String key = Integer.toString(i);
            String value = "val" + i;

            exec.execute(() -> {
                map.put(key, value);
            });

            exec.execute(() -> {
                map.get(key);
            });
        }

        exec.shutdown();

        System.out.println("useReadWriteLock took " + (System.currentTimeMillis() - initTime) + " ms.");
    }

    protected static void useMutex(int num) {
        long initTime = System.currentTimeMillis();

        final Map<String, String> map = Collections.synchronizedMap(new HashMap<>());

        ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int i = 0; i < num; i++) {

            String key = Integer.toString(i);
            String value = "val" + i;

            exec.execute(() -> {
                map.put(key, value);
            });

            exec.execute(() -> {
                map.get(key);
            });
        }

        exec.shutdown();

        System.out.println("useMutex took " + (System.currentTimeMillis() - initTime) + " ms.");
    }
}
