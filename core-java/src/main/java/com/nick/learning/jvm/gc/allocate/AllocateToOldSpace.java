package com.nick.learning.jvm.gc.allocate;

public class AllocateToOldSpace {

    private static final int _1M = 1024 * 1024;

    /**
     * vm args -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        byte[] allocation = new byte[4 * _1M];
        Thread.sleep(4000l);
    }
}
