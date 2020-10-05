package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MusicActivity extends Activity {
    MediaPlayer mp = new MediaPlayer();
    String song_path = "";
    private int currIndex = 0;  //当前播放的音乐
    private Thread thread;  //线程，自动更新进度条
    private ArrayList<String> list; //音乐列表
    private SeekBar seekBar;    //进度条
    private TextView maxtime;   //显示音乐的最长时间
    private TextView curtime;   //显示当前播放的时间
    private TextView musicname;     //显示播放的音乐


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        //获取权限
        if (ActivityCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MusicActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
        }

        //判断是否是AndroidN以及更高的版本 N=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }



        musicname = (TextView) findViewById(R.id.music_name);
        curtime = (TextView) findViewById(R.id.cur_time);
        maxtime = (TextView) findViewById(R.id.max_time);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        list = new ArrayList<String>();   //音乐列表
        File sdpath = Environment.getExternalStorageDirectory(); //获得手机SD卡路径
        File path = new File(sdpath + "//xiami//audios//");    //获得SD卡的音乐所在文件夹
        //返回以.mp3结尾的文件 (自定义文件过滤)
        File[] songFiles = path.listFiles(new MyFilter(".mp3"));
        for (File file : songFiles) {
            list.add(file.getAbsolutePath());   //获取文件的绝对路径
        }

        //seekbar监听器
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //进度改变
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //是否由用户改变
                if (fromUser) {
                    curtime.setText(toTime(progress));
                    mp.seekTo(progress);
                }
                curtime.setText(toTime(seekBar.getProgress()));
            }

            //按住seekbar
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //松开seekbar
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MusicActivity.this,
                android.R.layout.simple_list_item_1,
                list);
        ListView li = (ListView) findViewById(R.id.music_list);
        li.setAdapter(adapter); //添加适配器
        li.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  //单选

        //list点击事件
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currIndex = position;
                start();
                //                song_path = ((TextView) view).getText().toString();
                //                try {
                //                    mp.reset();    //重置
                //                    mp.setDataSource(song_path);
                //                    mp.prepare();     //准备
                //                    mp.start(); //播放
                //                } catch (Exception e) {
                //                }
            }
        });

        final ImageButton btnpause = (ImageButton) findViewById(R.id.btn_pause);
        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song_path.isEmpty())
                    Toast.makeText(getApplicationContext(), "先选首歌曲先听听", Toast.LENGTH_SHORT).show();
                if (mp.isPlaying()) {
                    mp.pause();  //暂停
                    btnpause.setImageResource(R.drawable.bofang);
                } else if (!song_path.isEmpty()) {
                    mp.start();   //继续播放
                    btnpause.setImageResource(R.drawable.zanting);
                }
            }
        });

        //监听器，播放完这首时触发，自动播放下一首
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (list.size() > 0) {
                    next();
                } else {
                    Toast.makeText(getApplicationContext(), "播放列表为空", Toast.LENGTH_SHORT).show();
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

        ImageButton btnnext = (ImageButton) findViewById(R.id.next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        ImageButton btnlast = (ImageButton) findViewById(R.id.last);
        btnlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous();
            }
        });

        ImageButton stop=(ImageButton)findViewById(R.id.btn_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp!=null){
                    mp.stop();
                    mp.reset();
                }
                //更换按钮图片
                final ImageButton btnpause = (ImageButton) findViewById(R.id.btn_pause);
                if (mp.isPlaying()) {
                    //                mp.pause();  //暂停
                    btnpause.setImageResource(R.drawable.zanting);
                } else if (!song_path.isEmpty()) {
                    //                mp.start();   //继续播放
                    btnpause.setImageResource(R.drawable.bofang);
                }
            }
        });

    }

    //退出程序，结束播放
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();  //停止播放
            mp.release();   //释放资源
        }
        Toast.makeText(getApplicationContext(), "退出啦", Toast.LENGTH_SHORT).show();
    }

    //开始播放
    protected void start() {
        if (list.size() > 0 && currIndex < list.size()) {
            song_path = list.get(currIndex);
            try {
                mp.reset();    //重置
                mp.setDataSource(song_path);
                mp.prepare();     //准备
                mp.start(); //播放
                initSeekBar();  //初始进度条
                musicname.setText(list.get(currIndex));     //音乐名
            } catch (Exception e) {
            }
            //更换按钮图片
            final ImageButton btnpause = (ImageButton) findViewById(R.id.btn_pause);
            if (mp.isPlaying()) {
                //                mp.pause();  //暂停
                btnpause.setImageResource(R.drawable.zanting);
            } else if (!song_path.isEmpty()) {
                //                mp.start();   //继续播放
                btnpause.setImageResource(R.drawable.bofang);
            }
            thread=new Thread(new SeekBarThread());
            thread.start();

        }
    }

    //下一首
    protected void next() {
        if (currIndex + 1 < list.size()) {
            currIndex++;
            start();
        } else {
            //当前是最后一首就跳到第一首播放
            currIndex = 0;
            start();
        }
    }

    //上一首
    protected void previous() {
        if (currIndex - 1 > 0) {
            currIndex--;
            start();
        } else {
            //是第一首就跳到最后一首播放
            currIndex = list.size() - 1;
            start();
            //            Toast.makeText(getApplicationContext(),"已是第一首",Toast.LENGTH_SHORT).show();
        }
    }


    private void initSeekBar() {
        curtime.setText("00:00");
        seekBar.setMax(mp.getDuration());
        seekBar.setProgress(0);
        maxtime.setText(toTime(mp.getDuration()));
    }

    private String toTime(int time) {
        int minute = time / 1000 / 60;
        int s = time / 1000 % 60;
        String mm = null;
        String ss = null;
        if (minute < 10)
            mm = "0" + minute;
        else
            mm = minute + "";

        if (s < 10)
            ss = "0" + s;
        else
            ss = "" + s;

        return mm + ":" + ss;
    }

    // 自定义的线程
    class SeekBarThread implements Runnable {
        @Override
        public void run() {
            while (mp.isPlaying()) {
                // 将SeekBar位置设置到当前播放位置
                seekBar.setProgress(mp.getCurrentPosition());
                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(100);
                    //播放进度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}