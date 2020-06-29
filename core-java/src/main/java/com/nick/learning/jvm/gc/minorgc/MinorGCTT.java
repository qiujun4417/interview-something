package com.nick.learning.jvm.gc.minorgc;

public class MinorGCTT {

    /**
     * 1M 的字节数量
     * 1024byte = 1K
     * 1024K = 1M
     */
    private static final int _1MB = 1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
     * -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        // 出现一次Minor GC
        allocation4 = new byte[2 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }

}
