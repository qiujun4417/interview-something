package com.nick.learning.jvm.oom;


public class JVMStackOOM {

    private void donStop(){
        while (true) {

        }
    }

    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(this::donStop);
            thread.start();
        }
    }

    public static void main(String[] args) throws Throwable{
        JVMStackOOM jvmStackOOM = new JVMStackOOM();
        jvmStackOOM.stackLeakByThread();
    }
}
