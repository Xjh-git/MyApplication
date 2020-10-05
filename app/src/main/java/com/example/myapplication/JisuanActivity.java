package com.example.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class JisuanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisuan);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText edt_a = (EditText) findViewById(R.id.inputa);
                EditText edt_b = (EditText) findViewById(R.id.inputb);
//                Intent intent = new Intent(JisuanActivity.this, Jisuan2Activity.class);
                Intent intent=new Intent("wust.zz.myboradcast");
                Bundle bundle = new Bundle();
                bundle.putString("a", edt_a.getText().toString());
                bundle.putString("b", edt_b.getText().toString());
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                bundle.putString("fu",radioButton.getText().toString());

                intent.putExtras(bundle);
//                startActivityForResult(intent, 10);
                intent.setComponent( new ComponentName("com.example.testbroadcast","com.example.testbroadcast.MyReceiver"));
                sendBroadcast(intent);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10)
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Double result = bundle.getDouble("result");
                TextView textView = (TextView) findViewById(R.id.btn_res);
                textView.setText("结果=" + result);
            }
    }
}
