package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class DetailsActivity extends Activity {

    //    float preX, curX;
    TextView tv;
    String content = "";
    //    String httpUrl =
    //            "http://www.qiushibaike.com";
    String httpUrl = "";
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tv = (TextView) findViewById(R.id.textView);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button button = (Button) findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
/*
        preX = curX = 0;
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        preX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = event.getX();
                        if (curX - preX > 50) {
                            finish();
                        }
                        break;
                }
                return true;
            }
        };*/
        //        tv.setOnTouchListener(listener);
        //        button.setOnTouchListener(listener);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        httpUrl += bundle.getString("url");
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(bundle.getString("title"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc_detail = Jsoup.connect(httpUrl).get();
                    //                    Elements els_detail = doc_detail.select(".content");
                    Elements els_details = doc_detail.select("article p");
                    for (int i = 0; i < els_details.size(); i++) {
                        Element els_detail = els_details.get(i);
                        content += "  "+els_detail.text() + "\n";
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(content);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
