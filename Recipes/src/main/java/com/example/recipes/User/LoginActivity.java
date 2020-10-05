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

public class LoginActivity extends Activity {
    EditText id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏窗口title
        setContentView(R.layout.activity_login);

        id = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_login:
                        //检查账号密码后登录
                        if (CheckLogin()) {
                            SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("IsLogin", true);
                            editor.putString("id", id.getText().toString());
                            editor.commit();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        break;
                    case R.id.btn_goRegister:
                        //前往注册
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.login_cancel:
                        setResult(0);
                        finish();
                        break;
                    case R.id.loss_psd:
                        Intent intent1 = new Intent(LoginActivity.this, LossPsdActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        };

        Button btn_login, btn_goRegister, btn_cancel;
        btn_login = findViewById(R.id.btn_login);
        btn_goRegister = findViewById(R.id.btn_goRegister);
        btn_login.setOnClickListener(listener);
        btn_goRegister.setOnClickListener(listener);
        btn_cancel = (Button) findViewById(R.id.login_cancel);
        btn_cancel.setOnClickListener(listener);
        TextView loss_psd = (TextView) findViewById(R.id.loss_psd);
        loss_psd.setOnClickListener(listener);

    }

    private boolean CheckLogin() {
        UserDAO userDAO = new UserDAO(getApplicationContext());
        User user = userDAO.find(id.getText().toString());
        if (user == null) {
            Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.getText().toString().equals(user.getPassword())) {
            Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//end check
}//end class
