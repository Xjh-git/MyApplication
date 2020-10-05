package com.example.recipes.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recipes.R;

public class LossPsdActivity extends Activity {
    LinearLayout back;
    EditText id, phone;
    Button cancel, loss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss_psd);
        init();
    }

    private void init() {
        back = (LinearLayout) findViewById(R.id.loss_back);
        id = (EditText) findViewById(R.id.loss_id);
        phone = (EditText) findViewById(R.id.loss_phone);
        cancel = (Button) findViewById(R.id.loss_cancel);
        loss = (Button) findViewById(R.id.loss);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.loss_back:
                    case R.id.cancel:
                        finish();
                        break;
                    case R.id.loss:
                        String user_id = id.getText().toString();
                        String user_phone = phone.getText().toString();

                        UserDAO userDAO = new UserDAO(LossPsdActivity.this);
                        User user = userDAO.find(user_id);
                        if (user != null) {
                            if (user.getPhonenum().equals(user_phone)) {
                                Intent intent = new Intent(LossPsdActivity.this, CheckActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LossPsdActivity.this, "您输入的手机号不对", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(LossPsdActivity.this, "此用户不存在", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };

        back.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
        loss.setOnClickListener(listener);
    }
}
