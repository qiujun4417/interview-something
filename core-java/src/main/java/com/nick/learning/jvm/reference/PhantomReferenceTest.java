package com.nick.learning.jvm.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhantomReferenceTest {

    /**
     * 虚引用，jvm gc的时候会回收，回收之后会往ReferenceQueue添加一个回收的通知,
     * 可以监听这个回收通知，使用场景DirectBuffer
     * @param args
     */
    public static void main(String[] args) {
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<Student> reference = new PhantomReference<Student>(new Student(), queue);
        List<byte[]> bytes = new ArrayList<>();
        new Thread(()->{
            for(int i=0; i<100; i++){
                bytes.add(new byte[1024 * 1024]);
            }
        }).start();
        new Thread(()->{
            while (true){
                Reference poll = queue.poll();
                if (poll != null) {
                    System.out.println("虚引用被回收了：" + poll);
                }
            }
        }).start();
        Scanner scanner = new Scanner(System.in);
        scanner.hasNext();
    }
}
