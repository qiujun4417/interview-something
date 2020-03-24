#### 关于gc

> gc在jvm里面是非常重要的一环，在各个面试中是必问的一项，了解java的垃圾回收才能对jvm的调优理解更深，
本文主要讲一下CMS和G1垃圾回收器的各个特点

* gc root枚举
    * 全局性引用(常量或者静态属性)与执行上下文作为起点
    * 保证一致性 需要stop the world
    * 类加载和编译过程中特定位置会创建OopMap，通过OopMap数据结构来存储引用指针
    * 并不是所有改动都会生成OopMap
    * 为了保证空间使用最小化，使用安全点(safePoint), 一般是方法调用、循环跳转、异常跳转指令才会产生safePoint
    * 采用主动中断的方式让所有线程到安全点，主要方式为设置一个flag, 线程主动轮询, flag状态变换则线程自行挂起，
    hotspot虚拟机会使用test指令生成轮询指令
    * 安全区域，进入安全区域需要等待根节点枚举结束或者gc结束才能走出安全区域
    
* 垃圾回收(GC)
    * 垃圾回收器
        * Serial、SerialOld
        * ParNew(新生代)
        * Parallel Scavenge、Parallel Old
        * CMS(老年代)
        * G1(横跨新、老年代)
    
* CMS垃圾回收器
    * 初始标记(STW)
    * 并发标记
    * 重新标记(STW)
    * 并发清除
    
* CMS的缺点
    * 浮动垃圾
    * 内存不规整
    * 对cpu资源敏感，因为是并发标记和清理，线程切换成本，
    如果cpu个数比较少是会占用cpu时间影响整体吞吐
    
* G1
    * G1主要目标在大容量的内存和多处理器环境下，关注减少gc时间和实现高吞吐
    
    * G1是一个有整理内存过程的收集器
    
    * G1的停顿可控，提供了暂停预测模型, 用户可以指定期望停顿时间
    
    * 物理内存上面不在有2块连续的内存空间作为新生代和老年代
    
    * G1把整个堆划分为多个大小一样的Region, 
      region大小由```-XX:G1HeapRegionSize=n```来设置，默认为1-32M, jvm自行调整
    
    * 一个region在不同时间可能为新生代也有可能为老年代
    
    * 一个region中每512byte为一个card, 使用card table来记录当前card是否被使用以及card index
    
    * 超过一半region大小的对象直接分配到老年代
    
    * 起始快照算法 Snapshot at the beginning (SATB)
    > 主要针对标记-清除垃圾收集器的并发标记阶段，非常适合G1的分区块的堆结构，同时解决了CMS的主要烦恼：重新标记暂停时间长带来的潜在风险。
      SATB会创建一个对象图，相当于堆的逻辑快照，从而确保并发标记阶段所有的垃圾对象都能通过快照被鉴别出来。当赋值语句发生时，应用将会改变了它的对象图，
      那么JVM需要记录被覆盖的对象。因此写前栅栏会在引用变更前，将值记录在SATB日志或缓冲区中。每个线程都会独占一个SATB缓冲区，初始有256条记录空间。
      当空间用尽时，线程会分配新的SATB缓冲区继续使用，而原有的缓冲去则加入全局列表中。最终在并发标记阶段，
      并发标记线程(Concurrent Marking Threads)在标记的同时，还会定期检查和处理全局缓冲区列表的记录，
      然后根据标记位图分片的标记位，扫描引用字段来更新RSet。此过程又称为并发标记/SATB写前栅栏。
    
    * 每个region都有一个RememberedSet结构来记录当前region被哪些其他region引用，
      结构类似为已给hash table, key为region的内存地址, value为哪些region引用当前region中哪些card的集合
    
    * 使用RSet的做法主要是为了解决GC时，young区引用了old区对象，标注这些对象时避免去整个old区全区扫描
    
    * 使用CSet(Collect Set)来存储哪些region需要被回收

    * G1分为 young gc和mix gc
    
        ### Minor GC/Young GC(`STW`)
    
        > Young GC主要是对Eden区进行GC，它在Eden空间不够时会被触发。Eden空间的数据移动到Survivor空间中，如果Survivor空间不够，Eden空间的部分数据会直接晋升到年老代空间。From Survivor区的数据移动到To Survivor区中，也有部分数据晋升到老年代空间中。
    
        其阶段主要如下：
    
        | 阶段               | 执行动作                         |
        | ------------------ | -------------------------------- |
        | 阶段1 根扫描       | 静态和本地对象被扫描             |
        | 阶段2 更新RS       | 处理dirty card队列更新RS         |
        | 阶段3 处理RS       | 检测从年轻代指向年老代的对象     |
        | 阶段4 对象拷贝     | 拷贝存活的对象到survivor/old区域 |
        | 阶段5 处理引用队列 | 软引用，弱引用，虚引用处理       |
    
        ### Mixed/Old GC(`STW`)
    
        > 主要是对年老代进行并发标记然后进行GC，其中部分阶段涉及到ygc，同时既有ygc及old gc的部分称为mixed gc。
    
        并发标记周期(Concurrent Marking Cycle Phases)阶段如下：
    
        | 阶段                                                         | 执行动作                                                     |
        | ------------------------------------------------------------ | ------------------------------------------------------------ |
        | (1) Initial Mark(Stop the World Event) 初始标记阶段          | 在此阶段G1 GC对根进行标记。该阶段与常规的 (STW) 年轻代垃圾回收密切相关。日志标记为Pause Initial Mark (G1 Evacuation Pause). |
        | (2) Root Region Scanning 根区域扫描阶段                      | G1 GC在初始标记的存活区扫描对老年代的引用，并标记被引用的对象。该阶段与应用程序（非 STW）同时运行，并且只有完成该阶段后，才能开始下一次STW年轻代垃圾回收。 |
        | (3) Concurrent Marking 并发标记阶段                          | G1 GC在整个堆中查找可访问的（存活的）对象。该阶段与应用程序同时运行，可以被 STW 年轻代垃圾回收中断。 |
        | (4) Remark(Stop the World Event) 重新标记阶段                | 该阶段是STW回收，帮助完成标记周期。G1 GC清空SATB缓冲区，跟踪未被访问的存活对象，并执行引用处理。 |
        | (5) Copying(Stop the World Event) / Cleanup(Stop the World Event and Concurrent) 拷贝/清理阶段 | 在这个最后阶段，G1 GC为了更快进行垃圾回收，会选择那些存活率低的region进行拷贝，即evacuate或者拷贝存活对象到新的空闲的regions，然后清理回收该region，此时会STW，如果是在年轻代产生的，则日志标记为Pause Young (G1 Evacuation Pause)，如果年轻代和年老代都进行这个动作，则日志标记为Pause Mixed (G1 Evacuation Pause). |
    
        > 核心的阶段主要是Concurrent Marking Phase、Remark Phase、Copying/Cleanup Phase
        
    * 当老年代分配对象速度超过mix gc时，会退化为Serial Old的全局单线程full gc（stop the world)
    
    * G1在以下场景中会触发Full GC，同时会在日志中记录to-space-exhausted以及Evacuation Failure：
        * 从年轻代分区拷贝存活对象时，无法找到可用的空闲分区
        * 从老年代分区转移存活对象时，无法找到可用的空闲分区
        * 分配巨型对象时在老年代无法找到足够的连续分区
      
    

