package com.example.testthread;

import android.util.Log;

/**
 * Created by xjh on 2018/5/10.
 */

public class MyThread extends Thread {

    @Override
    public void run() {

        int x=0;
        while (x<100){
            Log.d("xiaoxi","x="+x);
            x++;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
