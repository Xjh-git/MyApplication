package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {


    //    String httpUrl =
    //            "http://www.qiushibaike.com/8hr/page/";
    String httpUrl =
            "https://www.csdn.net/";
    List<Map<String, Object>> list;
    Map<String, Object> map;
    String[] from;
    int[] to;
    SimpleAdapter adapter;
    ListView li;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 123) {

                Bundle bundle = msg.getData();
                String title = bundle.getString("title");
                String href = bundle.getString("href");
                map = new HashMap<String, Object>();
                map.put("title", title);
                map.put("href", href);
                list.add(map);
                //                Toast.makeText(MainActivity.this, title + href, Toast.LENGTH_SHORT).show();
                //                System.out.print(title + href);

                adapter = new SimpleAdapter(MainActivity.this,
                        list, R.layout.listview,
                        from, to);
                li.setAdapter(adapter);
                li.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Map<String, Object> m = list.get(position);
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        String url = (String) m.get("href");
                        String tit = (String) m.get("title");
                        Bundle bundle = new Bundle();
                        bundle.putString("url", url);
                        bundle.putString("title", tit);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = new ArrayList<Map<String, Object>>();

        from = new String[]{"title", "href"};
        to = new int[]{R.id.tv_title, R.id.tv_url};

        li = (ListView) findViewById(R.id.listView);

        new Thread() {
            @Override
            public void run() {

                try {
                    //                    for (int k = 0; k < 5; k++) {
                    //                        Document document = Jsoup.connect(httpUrl + k + "/").get();
                    //                        Elements ele = document.select("a.contentHerf");
                    Document document = Jsoup.connect(httpUrl).get();
                    Elements ele = document.select(".title h2 a");


                    for (int i = 0; i < ele.size(); i++) {
                        Element el = ele.get(i);
                        String title = el.text();
                        String href = el.attr("href");

                           /* map = new HashMap<String, Object>();
                            map.put("title", title);
                            map.put("href", href);*/
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title);
                        bundle.putString("href", href);
                        Message msg = new Message();
                        msg.what = 123;
                        msg.setData(bundle);
                        handler.sendMessage(msg);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
