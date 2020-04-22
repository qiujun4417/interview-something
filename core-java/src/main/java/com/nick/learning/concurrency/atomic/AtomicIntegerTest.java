package com.nick.learning.concurrency.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private static AtomicInteger count = new AtomicInteger();

    static class Calculator implements Runnable{

        /**
         *
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            for(int i=0; i<1000; i++){
                int result = count.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + " result = " + result);
            }
        }
    }

    /**
     *
     * atomicInteger的测试
     * @param args
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(new Calculator(), "t1");
        Thread t2 = new Thread(new Calculator(), "t2");
        Thread t3 = new Thread(new Calculator(), "t3");
        t1.start();
        t2.start();
        t3.start();
    }
}
