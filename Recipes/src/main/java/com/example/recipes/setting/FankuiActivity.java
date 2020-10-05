package com.example.recipes.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipes.R;

public class FankuiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);

        Button button = (Button) findViewById(R.id.tijiao);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FankuiActivity.this, "提交成功!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
