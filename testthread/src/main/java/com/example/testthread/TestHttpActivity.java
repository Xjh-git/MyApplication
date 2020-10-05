package com.example.testthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;

public class TestHttpActivity extends Activity {

    Handler handler;
    Button button;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);



        handler = new Handler();
        button = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.tv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String httpUrl = //"http://www.qiushibaike.com/8hr/page/1/";
                           "http://news.ifeng.com/a/20170502/51034749_0.shtml";

                        org.jsoup.nodes.Document doc = null;
                        try {
                            doc = Jsoup.connect("http://www.qiushibaike.com/8hr/page/1/").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Elements els = doc.select("a.contentHerf");


                        final String r = els.toString();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("结果：" + r);
                                //                                tv.setText("结果：" + r);
                            }
                        });


                    }
                }).start();

            }
        });

    }

    public String crawler(String httpUrl) {
        String msg = ""; //服务器返回结果
        try {
            Document document = (Document) Jsoup.connect(httpUrl).get();
            return document.toString();

           /* URL url = new URL(httpUrl);
            //创建HttpURLConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection)
                    url.openConnection();
            //设置连接相关属性
            httpURLConnection.setConnectTimeout(5000); //设置连接超时为5秒
            httpURLConnection.setRequestMethod("GET"); //设置请求方式(默认为get)
            httpURLConnection.setRequestProperty("Charset", "UTF-8"); //设置uft-8字符集
            //建立到连接(可省略)
            httpURLConnection.connect();
            //获得服务器反馈状态信息
            //200：表示成功完成(HTTP_OK)， 404：请求资源不存在(HTTP_NOT_FOUND)
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                //接收服务器返回的信息（输入流）
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    msg += line + "\n";
                }
                //关闭缓冲区和连接
                bufferedReader.close();
                httpURLConnection.disconnect();
            }

            //复制前页get流程代码…*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    } //end scawler

    public String filterHtml(String source) {
        if (null == source) {
            return "";
        }
        //        result=source.replaceAll("</?[^>]+>","").trim();

        //        String regex = "<p.*?>(.*?)</p> ";
        //        Pattern p = Pattern.compile(regex);
        //        Matcher m = p.matcher(source);
        //        while (m.find()) {
        //             m.group(1);
        //        }

        return source.replaceAll("</?[^>]+>", "").trim();
    }

}
