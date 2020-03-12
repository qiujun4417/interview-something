package com.nick.learning.jvm.oom;

public class StackOverFlow {

    private static int stackLength = 1;

    public void applyStack(){
        stackLength ++;
        applyStack();
    }

    /**
     * -Xss160k jdk 1.8最小栈大小160k
     * @param args
     */
    public static void main(String[] args) {
        StackOverFlow stackOverFlow = new StackOverFlow();
        try{
            stackOverFlow.applyStack();
        }catch (Throwable throwable){
            System.out.println("stack length : " + stackLength);
            throw throwable;
        }
    }
}
