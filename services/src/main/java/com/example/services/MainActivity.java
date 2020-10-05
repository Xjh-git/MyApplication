package com.example.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Intent intent;
    int op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(MainActivity.this, MusicService.class);
        op = 2;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.play:
                        op = 1;
                        break;
                    case R.id.pause:
                        op = 2;
                        break;
                    case R.id.replay:
                        op = 3;
                        break;
                    case R.id.stop:
                        op = 0;
                        break;
                    case R.id.exit:
                        op = -1;
                        break;
                }
                if (op != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("op", op);
                    intent.putExtras(bundle);
                    startService(intent);
                }
                if (op == -1) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                            .setMessage("确认要退出吗？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stopService(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        };
        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(listener);
        Button replay = (Button) findViewById(R.id.replay);
        replay.setOnClickListener(listener);
        Button pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(listener);
        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(listener);
        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(listener);


    }
}
