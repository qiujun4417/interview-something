package com.nick.learning.jvm.reference;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {

    /**
     * 弱引用，不管内存够不够只要发生GC则都会被回收
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        WeakReference<Byte[]> weakReference = new WeakReference<Byte[]>(new Byte[1024 * 1024 * 10]);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
        Thread.sleep(1000L);

    }
}
