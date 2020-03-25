package com.nick.learning.jvm.gc.singleton;

/**
 * 有一个问题 如果一个对象是单例，那么会不会被gc回收掉
 * 针对这个问题，我们来做下试验
 * 不过做试验之前，我们可以通过正常逻辑来推到一下
 * 1 单例是全局唯一，不管任何时候创建那么应该都是唯一，则被称为单例
 * 2 假设单例可以被回收，那么就意味着，被回收之后需要重新创建对象，那么这个创建的对象肯定跟之前的对象不是一个对象，也违反了单例的定义
 * 3 所以推断是不会被gc回收
 *
 * 具体原因:
 * 1 单例instance在类中属于静态成员变量的，属于class内容
 * 2 jvm卸载类的判定条件如下：
 *     1.该类所有的实例都已经被回收，也就是java堆中不存在该类的任何实例。
 *     2.加载该类的ClassLoader已经被回收。
 *     3.该类对应的java.lang.Class对象没有任何地方被引用，无法在任何地方通过反射访问该类的方法。
 * 3 只有三个条件都满足，jvm才会在垃圾收集的时候卸载类。显然，单例的类不满足条件一，因此单例类也不会被卸载。
 */
public class SingletonGC {

    public static void main(String[] args) throws InterruptedException {
        SingletonRefer singletonRefer = new SingletonRefer();
        SingletonObj singletonObj = SingletonObj.getInstance();
        singletonRefer.setSingletonObj(singletonObj);
        System.out.println("before hashCode : " + singletonObj.hashCode());
        singletonObj = null;
        singletonRefer.setSingletonObj(null);
        singletonRefer = null;
        System.gc();
        Thread.sleep(1200);
        singletonObj = SingletonObj.getInstance();
        System.out.println("after hashCode : " + singletonObj.hashCode());
    }
}
