package com.example.testthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class BannerActivity extends Activity {

    private ImageSwitcher imageSwitcher;
    private int index;
    private int[] img;

    Handler handler;
    /*
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 123) {
                    imageSwitcher.setImageResource(img[msg.arg1]);

                }
            }
        };
    */
    float mPosX = 0, mCurPosx = 0;
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitch);
        index = 0;
        img = new int[]{
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3
        };
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(BannerActivity.this);
                imageView.setBackgroundColor(0xFFFFFFFF);  //白色背景
                imageView.setScaleType(ImageView.ScaleType.CENTER);   //居中显示
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));    //定义布局
                return imageView;
            }
        });
        imageSwitcher.setImageResource(img[0]);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(BannerActivity.this, android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(BannerActivity.this, android.R.anim.fade_out));


        mPosX = 0;
        mCurPosx = 0;

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                index++;
                if (index >= img.length) {
                    index = 0;
                }
                imageSwitcher.setImageResource(img[index]);
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(r, 2000);

        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        handler.removeCallbacks(r);
                        break;
                    case MotionEvent.ACTION_UP:
                        mCurPosx = event.getX();
                        if ((mCurPosx - mPosX) > 0) {//左滑
                            index--;
                            if (index < 0) {
                                index = img.length - 1;
                            }
                        }
                        if ((mPosX - mCurPosx) > 0) {//右滑
                            index++;
                            if (index >= img.length) {
                                index = 0;
                            }
                        }
                        imageSwitcher.setImageResource(img[index]);
                        handler.postDelayed(r, 2000);
                        break;
                }
                return true;
            }
        };
        imageSwitcher.setOnTouchListener(listener);



/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (index <= img.length) {
                    index++;
                    if (index >= img.length) {
                        index = 0;
                    }

                    Message msg = new Message();
                    msg.what = 123;
                    msg.arg1 = index;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();*/
    }

}
