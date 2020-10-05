package com.example.testthread;

import android.util.Log;

/**
 * Created by xjh on 2018/5/10.
 */

public class MyThread2 implements Runnable {
    @Override
    public void run() {
        int x=0;
        while (x<100){
            Log.d("msg",Thread.currentThread().getName()+",x="+x);
            x++;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
