package com.nick.learning.jvm.gc.singleton;

public class SingletonRefer {

    public SingletonRefer(){
        System.out.println("singleton obj refer obj created success");
    }

    private SingletonObj singletonObj;

    public void setSingletonObj(SingletonObj singletonObj){
        this.singletonObj = singletonObj;
    }

    public SingletonObj getSingletonObj() {
        return singletonObj;
    }
}
