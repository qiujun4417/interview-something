package com.nick.learning.jvm.reference;

import java.lang.ref.SoftReference;

public class SoftReferenceTest {

    /**
     * 软引用，只有在内存不足的时候才会回收，在此例中, gc之后软引用未被回收
     * 设置堆内存 -Xmx20M
     * @param args
     */
    public static void main(String[] args) {
        SoftReference<byte[]> softReference = new SoftReference<byte[]>(new byte[1024*1024*10]);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());

        byte[] bytes = new byte[1024 * 1024 * 10];
        System.out.println(softReference.get());
    }
}
