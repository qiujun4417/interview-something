package com.nick.learning.jvm.gc.minorgc;

public class MinroGCTest {

    private static final int _1MB = 1024 * 1024;

    public static void allocateMemory(){
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; //minor gc
    }

    /**
     * vm args: -verbose:gc -Xms20 -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * [GC (Allocation Failure) [PSYoungGen: 6295K->567K(9216K)] 6295K->4671K(19456K), 0.0088960 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     * Heap
     *  PSYoungGen      total 9216K, used 7116K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
     *   eden space 8192K, 79% used [0x00000007bf600000,0x00000007bfc65660,0x00000007bfe00000)
     *   from space 1024K, 55% used [0x00000007bfe00000,0x00000007bfe8dcb0,0x00000007bff00000)
     *   to   space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
     *  ParOldGen       total 10240K, used 4104K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
     *   object space 10240K, 40% used [0x00000007bec00000,0x00000007bf002020,0x00000007bf600000)
     *  Metaspace       used 3167K, capacity 4556K, committed 4864K, reserved 1056768K
     *   class space    used 339K, capacity 392K, committed 512K, reserved 1048576K
     * Disconnected from the target VM, address: '127.0.0.1:55574', transport: 'socket'
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        allocateMemory();
        Thread.sleep(3000L);
    }
}
