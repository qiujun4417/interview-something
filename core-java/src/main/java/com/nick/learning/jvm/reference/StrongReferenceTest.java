package com.nick.learning.jvm.reference;

public class StrongReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        Student student = new Student();
        student = null;
        System.gc();
        Thread.sleep(3000L);
    }
}
