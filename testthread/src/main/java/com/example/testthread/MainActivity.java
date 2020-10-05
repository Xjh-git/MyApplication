package com.example.testthread;

import android.app.Activity;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private int x = 0;
    private int y = 0;
    private TextView tv;
    int xx = 0, yy = 0, sum = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 123) {
                tv.setText("x=" + x);
            }
        }
    };


    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            x++;
            Message msg = new Message();
            msg.what = 123;
            msg.arg1 = x;
            handler.sendMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        tv = (TextView) findViewById(R.id.textView);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (y < 100) {
                            y++;

                            //                            Message message = new Message();
                            //                            message.what = 111;
                            //                            message.arg1 = y;
                            //                            handler.sendMessage(message);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setText("y=" + y);
                                }
                            });

                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (x < 100) {
                            x++;

                            Message msg = new Message();
                            msg.what = 123;
                            msg.arg1 = x;
                            handler.sendMessage(msg);

/*
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }*/
                        }
                    }
                }).start();
            }
        });

//        tv.setText("x=" + x);

    }
}
