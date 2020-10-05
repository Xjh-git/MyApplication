package com.example.recipes.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.recipes.R;

public class CheckActivity extends Activity {

    LinearLayout back;
    EditText check;
    Button recheck, yes, cancel;

    int time = 60;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        init();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.check_back:
                    case R.id.check_cancel:
                        finish();
                        break;
                    case R.id.check_recheck:
                        if (time <= 0) {

                        }
                        break;
                    case R.id.check_yes:

                        Intent intent = new Intent(CheckActivity.this, EditPsdActivity.class);
                        startActivity(intent);

                        break;
                }
            }
        };
        back.setOnClickListener(listener);
        recheck.setOnClickListener(listener);
        yes.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                while (time > 0) {
                    time--;
                    recheck.setText("(" + time + ")重新发送");
                    handler.postDelayed(runnable, 1000);
                }
//                recheck.setText("重新发送");
//                handler.removeCallbacks(runnable);
            }
        };
        handler.postDelayed(runnable, 1000);


    }

    private void init() {
        back = (LinearLayout) findViewById(R.id.check_back);
        check = (EditText) findViewById(R.id.check_check);
        recheck = (Button) findViewById(R.id.check_recheck);
        yes = (Button) findViewById(R.id.check_yes);
        cancel = (Button) findViewById(R.id.check_cancel);
    }
}
