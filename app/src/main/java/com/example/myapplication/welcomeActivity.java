package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class welcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent(); //获取传递过来的intent
        Bundle bundle = intent.getExtras();   //取出intent中的bundle

        User user=(User) bundle.getSerializable("user");
//        String username = bundle.getString("username");  //取出key对应的value
//        String sex=bundle.getString("sex");
//        String photo_uri=bundle.getString("photo_uri");


        TextView wel_username=(TextView)findViewById(R.id.wel_username);
        wel_username.setText("用户名："+user.getUsername());

        TextView wel_sex=(TextView)findViewById(R.id.wel_sex);
        wel_sex.setText("性别："+user.getSex()
        );

        ImageView wel_photo=(ImageView)findViewById(R.id.wel_photo);
        wel_photo.setImageURI(Uri.parse(user.getPhoto_uri()));


    }
}
