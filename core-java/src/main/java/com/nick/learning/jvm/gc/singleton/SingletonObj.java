package com.nick.learning.jvm.gc.singleton;

/**
 * 采用一个dcl的单例模式
 */
public class SingletonObj {

    private static volatile SingletonObj INSTANCE = null;

    private SingletonObj(){
        System.out.println("create singleton obj success");
    }

    public static SingletonObj getInstance(){
        if(INSTANCE == null){
            synchronized (SingletonObj.class){
                if(INSTANCE == null){
                    INSTANCE = new SingletonObj();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void finalize(){
        System.out.println("singleton obj was removed");
    }
}
