package com.nick.learning.jvm.gc.cms;

/**
 * 模拟CMS七个垃圾回收过程
 * 1、初始标记
 * 2、并发标记
 * 3、并发预先清除
 * 4、并发可能失败的预先清除
 * 5、最终重新标记
 * 6、并发清除
 * 7、并发重置
 *
 */
public class CMSGCSimulator {

    private static final int _1M = 1024 * 1024;

    /**
     * vm args: -verbose:gc -Xmx20M -Xms20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC
     * output logs:
     * first allocation
     * [GC (Allocation Failure) [ParNew: 6295K->497K(9216K), 0.0046935 secs] 6295K->4595K(19456K), 0.0047354 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * second allocation
     * third allocation
     * [GC (Allocation Failure) [ParNew (promotion failed): 6964K->7051K(9216K), 0.0028952 secs][CMS: 8196K->8192K(10240K), 0.0028831 secs] 11062K->10634K(19456K), [Metaspace: 3160K->3160K(1056768K)], 0.0058246 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
     * [GC (CMS Initial Mark) [1 CMS-initial-mark: 8192K(10240K)] 14730K(19456K), 0.0006196 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * [CMS-concurrent-mark-start]
     * fourth allocation
     * [CMS-concurrent-mark: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * [CMS-concurrent-preclean-start]
     * [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * [CMS-concurrent-abortable-preclean-start]
     * [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * [GC (CMS Final Remark) [YG occupancy: 6779 K (9216 K)][Rescan (parallel) , 0.0004394 secs][weak refs processing, 0.0000370 secs][class unloading, 0.0001273 secs][scrub symbol table, 0.0002477 secs][scrub string table, 0.0001482 secs][1 CMS-remark: 8192K(10240K)] 14971K(19456K), 0.0010385 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * [CMS-concurrent-sweep-start]
     * [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * [CMS-concurrent-reset-start]
     * [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * Heap
     *  par new generation   total 9216K, used 6861K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
     *   eden space 8192K,  83% used [0x00000007bec00000, 0x00000007bf2b3778, 0x00000007bf400000)
     *   from space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
     *   to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
     *  concurrent mark-sweep generation total 10240K, used 8192K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
     *  Metaspace       used 3167K, capacity 4556K, committed 4864K, reserved 1056768K
     *   class space    used 339K, capacity 392K, committed 512K, reserved 1048576K
     * Disconnected from the target VM, address: '127.0.0.1:56822', transport: 'socket'
     * @param args
     */
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        for(int i=0; i<1000; i++){
            byte[] firstAlloc = new byte[_1M * 4];
            System.out.println("first allocation");

            byte[] secondAlloc = new byte[_1M * 4];
            System.out.println("second allocation");
            firstAlloc = null;
            System.out.println("gc 1 memory");
            byte[] thirdAlloc = new byte[_1M * 3];
            System.out.println("third allocation");
            secondAlloc = null;
            thirdAlloc = null;
            System.out.println("gc 2~3 memory");
            byte[] fourthdAlloc = new byte[_1M * 4];
            System.out.println("fourth allocation");
            fourthdAlloc = null;
            System.out.println("gc 4 memory");
        }
        System.out.println("cost: " + (System.currentTimeMillis() - start));

    }
}
