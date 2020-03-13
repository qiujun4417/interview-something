package com.nick.learning.jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class JavaMethodAreaOOM {

    /**
     * 1.8 metaspace
     * VM args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
     * @param args
     */
    public static void main(String[] args) {
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(InnerOOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, methodProxy)
                    -> methodProxy.invokeSuper(obj, args1));
            enhancer.create();
        }
    }

    static class InnerOOMObject{

    }
}
