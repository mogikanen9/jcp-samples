package org.mogikanensoftware.play.wn.example2;

public class RunExample {
    
    public static void main(String[] args) {
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}