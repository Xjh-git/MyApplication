package com.example.recipes.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipes.R;

import java.util.zip.InflaterInputStream;

public class InputPsdActivity extends Activity {
    TextView back;
    Button btn_cancel, btn_next;
    EditText input_old_psd;
    String id,old_psd;
    UserDAO userDAO;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_input_psd);

        init();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.in_next:
                        //得到账号
                        SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                        id = sp.getString("id", "123456");
                        userDAO = new UserDAO(getApplicationContext());
                        user = userDAO.find(id);
                        old_psd=input_old_psd.getText().toString();
                        if(user!=null){
                            if(old_psd.equals(user.getPassword())){
                                Intent intent=new Intent(InputPsdActivity.this,EditPsdActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(InputPsdActivity.this, "输入的密码不正确", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case R.id.in_cancel:
                    case R.id.in_back:
                        finish();
                        break;
                }
            }
        };

        back.setOnClickListener(listener);
        btn_cancel.setOnClickListener(listener);
        btn_next.setOnClickListener(listener);

    }

    private void init() {
        back = (TextView) findViewById(R.id.in_back);
        btn_cancel = (Button) findViewById(R.id.in_cancel);
        btn_next = (Button) findViewById(R.id.in_next);
        input_old_psd=(EditText)findViewById(R.id.input_old_psd);
    }
}
