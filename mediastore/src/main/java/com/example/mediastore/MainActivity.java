package com.example.mediastore;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    MediaPlayer myPlayer=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 123);
            return;
        }

        String[] from = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA };
        int[] to = { R.id.music_id,
                R.id.music_title,
                R.id.music_artist,
                R.id.music_duration,
                R.id.music_url };
        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.TITLE); //按标题排序
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.list_row, cursor, from, to);


        ListView li=(ListView)findViewById(R.id.listView1);
        li.setAdapter(adapter);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView music_url=(TextView)view.findViewById(R.id.music_url);
                try{
                    myPlayer.reset();
                    myPlayer.setDataSource(music_url.getText().toString());
                    myPlayer.prepare();
                    myPlayer.start();
                }catch (Exception e){ }
            }
        });


    }


    protected void onDestroy() {
        super.onDestroy();
        if(myPlayer!=null ){
            myPlayer.stop();
            myPlayer.release();
        }
    }


}
