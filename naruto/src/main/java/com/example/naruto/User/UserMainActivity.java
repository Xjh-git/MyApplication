package com.example.naruto.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.naruto.R;

public class UserMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        TextView ninjia, mijuan, back;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.user_main_ninjia:
                        intent = new Intent(UserMainActivity.this, UserNinjiaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.user_main_mijuan:
                        intent = new Intent(UserMainActivity.this, UserMijuanActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.user_mijuan_back:
                        finish();
                        break;
                }
            }
        };

        ninjia = (TextView) findViewById(R.id.user_main_ninjia);
        mijuan = (TextView) findViewById(R.id.user_main_mijuan);
        ninjia.setOnClickListener(listener);
        mijuan.setOnClickListener(listener);
        back = (TextView) findViewById(R.id.user_mijuan_back);
        back.setOnClickListener(listener);
    }
}
