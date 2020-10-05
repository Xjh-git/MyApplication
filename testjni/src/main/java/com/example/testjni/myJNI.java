package com.example.testjni;

/**
 * Created by xjh on 2018/8/29.
 */

public class myJNI {
    //加载so库
    static {
        System.loadLibrary("JniTest");
    }

    //native方法
    public static native String sayHello(String name);
}
