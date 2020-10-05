package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class registerActivity extends Activity {

    private String msg = "";
    ImageView iv;
    Uri photo_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        Button button = (Button) findViewById(R.id.reg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = "";
                EditText username = (EditText) findViewById(R.id.username);
                msg += "用户名：" + username.getText() + "\n";

                EditText phone = (EditText) findViewById(R.id.phone);
                msg += "手机号：" + phone.getText() + "\n";

                EditText password = (EditText) findViewById(R.id.password);
                msg += "密码：" + password.getText() + "\n";

                RadioGroup sexGroup = (RadioGroup) findViewById(R.id.sex);
                RadioButton sex = (RadioButton) findViewById(sexGroup.getCheckedRadioButtonId());
                if (sex != null)
                    msg += "性别：" + sex.getText() + "\n";

                msg += "爱好：";
                CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
                CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
                CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
                if (checkBox.isChecked())
                    msg += checkBox.getText() + " ";
                if (checkBox2.isChecked())
                    msg += checkBox2.getText() + " ";
                if (checkBox3.isChecked())
                    msg += checkBox3.getText() + "";

                Spinner spinner = (Spinner) findViewById(R.id.major);
                msg += "\n专业：" + spinner.getSelectedItem();

                Intent intent=new Intent(registerActivity.this,welcomeActivity.class);
                Bundle bundle=new Bundle();

                User user=new User(username.getText().toString(),sex.getText().toString(),photo_uri.toString());

//                bundle.putString("username",username.getText().toString());
//                bundle.putString("sex",sex.getText().toString());
//                bundle.putString("photo_uri",photo_uri.toString());

                bundle.putSerializable("user",user);

                intent.putExtras(bundle);
                startActivity(intent);


                Log.d("xiaoxi", msg);
            }
        });

        iv = (ImageView) findViewById(R.id.photo);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 0x1);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                photo_uri=data.getData();
                iv.setImageURI(photo_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
