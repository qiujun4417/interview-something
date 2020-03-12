package com.nick.learning.jvm.oom;

import java.util.concurrent.atomic.AtomicInteger;

public class JVMStackOOM {

    private static AtomicInteger threadCount = new AtomicInteger(0);

    private void donStop(){
        while (true) {

        }
    }

    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(this::donStop);
            threadCount.incrementAndGet();
            thread.start();
        }
    }

    public static void main(String[] args) {
        JVMStackOOM jvmStackOOM = new JVMStackOOM();
        try {
            jvmStackOOM.stackLeakByThread();
        } catch (Throwable e) {
            System.out.println("created thread number " + threadCount.get());
            throw e;
        }
    }
}
