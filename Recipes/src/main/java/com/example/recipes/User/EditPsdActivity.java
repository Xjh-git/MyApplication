package com.example.recipes.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipes.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditPsdActivity extends Activity {
    TextView back, span_psd, span_repsd;
    Button edit, cancel;
    EditText edit_psd, edit_repsd;
    String psd, repsd, id;
    UserDAO userDAO;
    User user;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_psd);

        //得到账号
        SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        id = sp.getString("id", "123456");
        userDAO = new UserDAO(getApplicationContext());
        user = userDAO.find(id);
        init();//初始化

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.edit_psd_back:
                    case R.id.edit_psd_cancel:
                        finish();
                        break;
                    case R.id.edit_psd_edit:
                        if (user != null) {
                            if (check()) {
                                psd = edit_psd.getText().toString();
                                user.setPassword(psd);
                                userDAO.update(user);
                                Toast.makeText(EditPsdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        break;
                }
            }
        };
        back.setOnClickListener(listener);
        edit.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()) {
                    case R.id.edit_psd_new:
                        if (!hasFocus) {
                            checkPassword();
                        } else {
                            span_psd.setText("");
                        }
                        break;
                    case R.id.edit_psd_renew:
                        if (!hasFocus) {
                            checkRepassword();
                        } else {
                            span_repsd.setText("");
                        }
                        break;
                }
            }
        };
        edit_psd.setOnFocusChangeListener(focusChangeListener);
        edit_repsd.setOnFocusChangeListener(focusChangeListener);


    }

    private void init() {
        back = (TextView) findViewById(R.id.edit_psd_back);
        span_psd = (TextView) findViewById(R.id.edit_psd_new_span);
        span_repsd = (TextView) findViewById(R.id.edit_psd_renew_span);

        edit_psd = (EditText) findViewById(R.id.edit_psd_new);
        edit_repsd = (EditText) findViewById(R.id.edit_psd_renew);

        edit = (Button) findViewById(R.id.edit_psd_edit);
        cancel = (Button) findViewById(R.id.edit_psd_cancel);
    }

    private boolean check() {
        if (checkPassword() && checkRepassword()) {
            return true;
        }
        return false;
    }

    private boolean checkPassword() {
        //检查密码
        psd = edit_psd.getText().toString();
        String regex = "^[a-zA-Z0-9]{8,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(psd);
        boolean isMatch = m.matches();
        if (!isMatch) {
            span_psd.setText("请输入8-16位字母或数字");
            span_psd.setTextColor(Color.RED);
            return false;
        }
        return true;
    }

    private boolean checkRepassword() {
        //检查重复输入的密码
        repsd = edit_repsd.getText().toString();
        psd = edit_psd.getText().toString();
        if (!psd.equals(repsd)) {
            span_repsd.setText("两次输入的密码不一致");
            span_repsd.setTextColor(Color.RED);
            return false;
        }
        return true;
    }
}
