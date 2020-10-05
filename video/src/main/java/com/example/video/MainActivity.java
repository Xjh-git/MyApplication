package com.example.video;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    SurfaceView sfv;
    SeekBar seekBar;
    String path;
    SurfaceHolder holder;
    MediaPlayer player;
    EditText text;
    Button play, pause, stop, replay;
    int position = 0;
    boolean isPause;
    private Timer timer;//定时器
    private TimerTask task;//定时器任务


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取权限
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
        }

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            return;
        }

        //判断是否是AndroidN以及更高的版本 N=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        initview();
    }

    private void initview() {
        sfv = (SurfaceView) findViewById(R.id.sfv);
        seekBar = (SeekBar) findViewById(R.id.sb);
        text = (EditText) findViewById(R.id.et);
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        replay = (Button) findViewById(R.id.replay);

        play.setEnabled(false);

        holder = sfv.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //当进度条停止拖动的时候，把媒体播放器的进度跳转到进度条对应的进度
                if (player != null) {
                    player.seekTo(seekBar.getProgress());
                }
            }
        });

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //为了避免图像控件还没有创建成功，用户就开始播放视频，造成程序异常，所以在创建成功后才使播放按钮可点击
                play.setEnabled(true);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //当程序没有退出，但不在前台运行时，因为surfaceview很耗费空间，所以会自动销毁，
                // 这样就会出现当你再次点击进程序的时候点击播放按钮，声音继续播放，却没有图像
                //为了避免这种不友好的问题，简单的解决方式就是只要surfaceview销毁，我就把媒体播放器等
                //都销毁掉，这样每次进来都会重新播放，当然更好的做法是在这里再记录一下当前的播放位置，
                //每次点击进来的时候把位置赋给媒体播放器，很简单加个全局变量就行了。
                if (player != null) {
                    position = player.getCurrentPosition();
                    stop();
                }


            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.play:
                        play();
                        break;
                    case R.id.pause:
                        pause();
                        break;
                    case R.id.stop:
                        stop();
                        break;
                    case R.id.replay:
                        replay();
                        break;
                }
            }
        };
        play.setOnClickListener(listener);
        pause.setOnClickListener(listener);
        stop.setOnClickListener(listener);
        replay.setOnClickListener(listener);

    }

    private void play() {
        play.setEnabled(false);//在播放时不允许再点击播放按钮

        if (isPause) {//如果是暂停状态下播放，直接start
            isPause = false;
            player.start();
            return;
        }

        path = Environment.getExternalStorageDirectory().getPath() + "/";
        path = path + text.getText().toString();//sdcard的路径加上文件名称是文件全路径
        File file = new File(path);
        if (!file.exists()) {//判断需要播放的文件路径是否存在，不存在退出播放流程
            Toast.makeText(this, "文件路径不存在", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            player = new MediaPlayer();
            player.setDataSource(path);
            player.setDisplay(holder);//将影像播放控件与媒体播放控件关联起来

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {//视频播放完成后，释放资源
                    play.setEnabled(true);
                    stop();
                }
            });

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //媒体播放器就绪后，设置进度条总长度，开启计时器不断更新进度条，播放视频
                    seekBar.setMax(player.getDuration());
                    timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            if (player != null) {
                                int time = player.getCurrentPosition();
                                seekBar.setProgress(time);
                            }
                        }
                    };
                    timer.schedule(task, 0, 500);
                    seekBar.setProgress(position);
                    player.seekTo(position);
                    player.start();
                }
            });

            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
            play.setEnabled(true);
            isPause = true;
        }
    }

    private void replay() {
        isPause = false;
        if (player != null) {
            stop();
            play();
        }
    }

    private void stop() {
        isPause = false;
        if (player != null) {
            seekBar.setProgress(0);
            player.stop();
            player.release();
            player = null;
            if (timer != null) {
                timer.cancel();
            }
            play.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }
}
