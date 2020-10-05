package com.example.naruto.sponsor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.naruto.R;

public class NinjiaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninjia);


        TextView query_ninjia, add_ninjia,back;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.sponsor_query_ninjia:
                        intent = new Intent(NinjiaActivity.this, QueryNinjiaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sponsor_add_ninjia:
                        intent = new Intent(NinjiaActivity.this, AddNinjiaActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sponsor_ninjia_back:
                        finish();
                        break;
                }
            }
        };

        query_ninjia = (TextView) findViewById(R.id.sponsor_query_ninjia);
        add_ninjia = (TextView) findViewById(R.id.sponsor_add_ninjia);
        query_ninjia.setOnClickListener(listener);
        add_ninjia.setOnClickListener(listener);
        back=(TextView)findViewById(R.id.sponsor_ninjia_back);
        back.setOnClickListener(listener);


    }
}
