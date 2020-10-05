package com.example.test2;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    MediaPlayer mp = new MediaPlayer();
    String song_path = "";
    private ArrayList<String> list; //音乐列表
    private int currIndex = 0;  //当前播放的音乐

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取权限
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.PROCESS_OUTGOING_CALLS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, 123);
            return;
        }

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
            return;
        }

        //判断是否是AndroidN以及更高的版本 N=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        final Intent intent = new Intent(MainActivity.this, MyService.class);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });

        final Button button1 = (Button) findViewById(R.id.btn_pause);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    mp.pause();  //暂停
                    button1.setText("暂停播放");
                } else if (!song_path.isEmpty()) {
                    mp.start();   //继续播放
                    button1.setText("开始播放");
                }
            }
        });


        list = new ArrayList<String>();   //音乐列表
        File sdpath = Environment.getExternalStorageDirectory(); //获得手机SD卡路径
        File path = new File(sdpath + "//audioRecords//");    //获得SD卡的音乐所在文件夹
        //返回以.mp3结尾的文件 (自定义文件过滤)
        File[] songFiles = path.listFiles(new MyFilter(".mp3"));
        for (File file : songFiles) {
            list.add(file.getAbsolutePath());   //获取文件的绝对路径
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                list);
        ListView li = (ListView) findViewById(R.id.music_list);
        li.setAdapter(adapter); //添加适配器·
        li.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  //单选

        //list点击事件
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currIndex = position;
                start();
            }
        });

        //监听器，播放完这首时触发
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //更换按钮
                final Button btnpause = (Button) findViewById(R.id.btn_pause);
                if (mp.isPlaying()) {
                    //                mp.pause();  //暂停
                    btnpause.setText("暂停播放");
                } else if (!song_path.isEmpty()) {
                    //                mp.start();   //继续播放
                    btnpause.setText("开始播放");
                }
            }
        });
        //出现错误
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.reset();
                return false;
            }
        });

    }

    protected void start() {
        if (list.size() > 0 && currIndex < list.size()) {
            song_path = list.get(currIndex);
            try {
                mp.reset();    //重置
                mp.setDataSource(song_path);
                mp.prepare();     //准备
                mp.start(); //播放
            } catch (Exception e) {
            }
            //更换按钮
            final Button btnpause = (Button) findViewById(R.id.btn_pause);
            if (mp.isPlaying()) {
                //                mp.pause();  //暂停
                btnpause.setText("暂停播放");
            } else if (!song_path.isEmpty()) {
                //                mp.start();   //继续播放
                btnpause.setText("开始播放");
            }

        }
    }


}
