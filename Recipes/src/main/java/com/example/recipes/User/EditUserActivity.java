package com.example.recipes.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipes.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUserActivity extends Activity {
    EditText edit_id, edit_name, edit_phone;
    Button btn_cancal, btn_to_edit_psd, btn_edit;
    String id, name, phone;
    RadioGroup sexGroup;
    TextView span_phone;
    User user;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_user);

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
                    case R.id.edit:
                        //得到性别
                        RadioButton sex = (RadioButton) findViewById(sexGroup.getCheckedRadioButtonId());

                        if (check()) {
                            user.setName(edit_name.getText().toString());
                            user.setPhonenum(edit_phone.getText().toString());
                            user.setSex(sex.getText().toString());

                            userDAO.update(user);
                            Toast.makeText(EditUserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                    case R.id.edit_cancel:
                        finish();
                        break;
                    case R.id.to_edit_psd:
                        Intent intent1 = new Intent(getApplicationContext(), InputPsdActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        };

        btn_cancal.setOnClickListener(listener);
        btn_edit.setOnClickListener(listener);
        btn_to_edit_psd.setOnClickListener(listener);

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()) {
                    case R.id.edit_name:
                        if (!hasFocus) {
                            checkName();
                        } else {
                        }
                        break;
                    case R.id.edit_phone:
                        if (!hasFocus) {
                            checkPhone();
                        } else {
                            span_phone.setText("");
                        }
                        break;
                }
            }
        };
        edit_name.setOnFocusChangeListener(focusChangeListener);
        edit_phone.setOnFocusChangeListener(focusChangeListener);

    }

    private void init() {
        edit_id = (EditText) findViewById(R.id.edit_username);
        edit_id.setText(user.getId());
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_name.setText(user.getName());
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_phone.setText(user.getPhonenum());

        sexGroup = (RadioGroup) findViewById(R.id.edit_sex);
        if (user.getSex().equals("男")) {
            sexGroup.check(R.id.edit_sex_man);
        } else {
            sexGroup.check(R.id.edit_sex_woman);
        }


        btn_cancal = (Button) findViewById(R.id.edit_cancel);
        btn_edit = (Button) findViewById(R.id.edit);
        btn_to_edit_psd = (Button) findViewById(R.id.to_edit_psd);

        span_phone = (TextView) findViewById(R.id.edit_span_phone);
        span_phone.setText("");

    }

    private boolean check() {
        if (checkName() && checkPhone()) {
            return true;
        }
        return false;
    }

    private boolean checkName() {
        //检查昵称
        name = edit_name.getText().toString();
        if (edit_name.getText() == null) {
            name = "zhangsan";
        }
        return true;
    }

    private boolean checkPhone() {
        //检查电话号码
        phone = edit_phone.getText().toString();
        if (phone.length() != 11) {
            span_phone.setText("请输入11位数字");
            span_phone.setTextColor(Color.RED);
            return false;
        } else {
            String regex = "^1[3|4|5|6|7|8][0-9]\\d{8}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                span_phone.setText("请输入正确的11手机号");
                span_phone.setTextColor(Color.RED);
                return false;
            }
            span_phone.setText("");
            return true;
        }
    }

}
