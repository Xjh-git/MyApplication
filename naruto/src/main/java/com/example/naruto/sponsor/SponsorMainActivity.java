package com.example.naruto.sponsor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.naruto.R;

public class SponsorMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_main);


        TextView ninjia, mijuan, back;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.sponsor_ninjia:
                        intent = new Intent(SponsorMainActivity.this, NinjiaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sponsor_mijuan:
                        intent = new Intent(SponsorMainActivity.this, MijuanActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sponsor_back:
                        finish();
                        break;
                }
            }
        };

        ninjia = (TextView) findViewById(R.id.sponsor_ninjia);
        mijuan = (TextView) findViewById(R.id.sponsor_mijuan);
        ninjia.setOnClickListener(listener);
        mijuan.setOnClickListener(listener);
        back = (TextView) findViewById(R.id.sponsor_back);
        back.setOnClickListener(listener);


    }
}
