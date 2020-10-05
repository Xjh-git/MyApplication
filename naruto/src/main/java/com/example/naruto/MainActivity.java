package com.example.naruto;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.example.naruto.User.UserMainActivity;
import com.example.naruto.sponsor.SponsorMainActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //申请权限
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
        }
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            return;
        }


        TextView sponer, player;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.main_sponsor:
                        intent = new Intent(MainActivity.this, SponsorMainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.main_player:
                        intent = new Intent(MainActivity.this, UserMainActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        sponer = (TextView) findViewById(R.id.main_sponsor);
        player = (TextView) findViewById(R.id.main_player);
        sponer.setOnClickListener(listener);
        player.setOnClickListener(listener);

    }
}
