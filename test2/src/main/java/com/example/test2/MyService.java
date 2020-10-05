package com.example.test2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {

    private File recordPath;
    private File recordFile;
    private MediaRecorder mr;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(),"已监听",Toast.LENGTH_SHORT).show();

        if (checkSDCard()) {
            recordPath = Environment.getExternalStorageDirectory();
            File path = new File(recordPath.getPath() + File.separator + "audioRecords");
            if (!path.exists()) {
                path.mkdirs();
            }
            recordPath = path;
        } else {
            recordFile = new File(recordPath + "//" +getCurTime());
            Toast.makeText(getApplicationContext(), "SCCard未连接", Toast.LENGTH_SHORT).show();
            return super.onStartCommand(intent, flags, startId);
        }

        recordFile = new File(recordPath + "//" + getCurTime() + ".mp3");
        //        try {
        //            recordFile=File.createTempFile(String.valueOf(System.currentTimeMillis()),".amr",recordPath);
        //            Toast.makeText(getApplicationContext(),String.valueOf(System.currentTimeMillis()),Toast.LENGTH_SHORT).show();
        //        } catch (IOException e) {
        //            Toast.makeText(getApplicationContext(), "文件创建失败", Toast.LENGTH_SHORT).show();
        //        }


        mr = new MediaRecorder();
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
        mr.setOutputFormat(MediaRecorder.AudioEncoder.DEFAULT);//文件格式
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//音频编码
        mr.setOutputFile(recordFile.getAbsolutePath());//文件路径

        try {
            mr.prepare();
            mr.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"停止录音",Toast.LENGTH_SHORT).show();
        stop();
    }

    private boolean checkSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }


    private void stop() {
        try {
            if (mr != null) {
                mr.stop();
                mr.release();
                mr = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    private String getCurTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String string=format.format(date);
        return string;
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
