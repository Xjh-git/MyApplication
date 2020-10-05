package com.example.services;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mp == null) {
            mp = MediaPlayer.create(getApplicationContext(), R.raw.bbbb);
            mp.setLooping(true);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            int op = bundle.getInt("op");
            switch (op) {
                case 1:
                    if (!mp.isPlaying()) {
                        mp.start();
                    }
                    break;
                case 2:
                    if (mp.isPlaying()) {
                        mp.pause();
                    }
                    break;
                case 3:
                    if (mp != null) {
                        mp.stop();
                        try {
                            mp.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mp.seekTo(0);
                        mp.start();
                    }
                    break;
                case 0:
                    stopSelf();
                    break;
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
