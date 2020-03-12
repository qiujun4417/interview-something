package com.nick.learning.jvm.oom;

import java.util.ArrayList;
import java.util.List;


public class HeapOOM {

    static class OOMObject{

    }

    /**
     * 模拟堆溢出 +Xmx20M -XX:HeapDumpOnOutOfMemoryErrir
     * @param args
     */
    public static void main(String[] args) {
        List<OOMObject> oomObjectList = new ArrayList<>();
        while (true){
            oomObjectList.add(new OOMObject());
        }
    }
}
