package com.example.naruto.sponsor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.naruto.R;

public class MijuanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mijuan);

        TextView query_mijuan, add_mijuan,back;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.query_mijuan:
                        intent = new Intent(MijuanActivity.this, QueryMijuanActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.add_mijuan:
                        intent = new Intent(MijuanActivity.this, AddMijuanActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sponsor_mijuan_back:
                        finish();
                        break;
                }
            }
        };

        query_mijuan = (TextView) findViewById(R.id.query_mijuan);
        add_mijuan = (TextView) findViewById(R.id.add_mijuan);
        query_mijuan.setOnClickListener(listener);
        add_mijuan.setOnClickListener(listener);
        back=(TextView)findViewById(R.id.sponsor_mijuan_back);
        back.setOnClickListener(listener);

    }
}
