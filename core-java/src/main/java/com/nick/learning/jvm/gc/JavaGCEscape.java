package com.nick.learning.jvm.gc;

public class JavaGCEscape {

    public static JavaGCEscape SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("yes, i'm still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method execute");
        SAVE_HOOK = this;
    }

    /**
     * 针对jvm 对一个对象回收会判断是否有覆盖finalize方法
     * 如果有则会插入f-queue队列里面，并且启动一个低优先级的Finalizer线程执行finalize方法
     * 如果在finalize方法中对象把自己又放入引用链则不会被回收
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new JavaGCEscape();
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no, i'm dead :(");
        }
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if(SAVE_HOOK != null)
            SAVE_HOOK.isAlive();
        else
            System.out.println("no, i'm dead :(");
    }
}
