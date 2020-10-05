package com.example.recipes.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.recipes.R;
import com.example.recipes.User.EditUserActivity;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.btn_nologin:
                        //退出登录
                        SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("IsLogin", false);
                        editor.commit();
                        Toast.makeText(SettingActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case R.id.setting_back:
                        //返回
                        finish();
                        break;
                    case R.id.set_edit_user:
                        //管理账号
                        intent=new Intent(SettingActivity.this, EditUserActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.set_msg:
                        //消息通知
                        Toast.makeText(SettingActivity.this, "暂无消息！",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.set_clear:
                        //清楚缓存
                        Toast.makeText(SettingActivity.this, "已清除！",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.set_opinion:
                        //意见反馈

                        break;
                    case R.id.set_check:
                        //检查更新
                        Toast.makeText(SettingActivity.this, "已是最新版本！",
                                Toast.LENGTH_LONG).show();
                        break;
                    case R.id.set_about:
                        //关于app

                        break;

                }
            }
        };
        Button btn_nologin = (Button) findViewById(R.id.btn_nologin);
        btn_nologin.setOnClickListener(listener);
        ImageView img_back = (ImageView) findViewById(R.id.setting_back);
        img_back.setOnClickListener(listener);
        LinearLayout set_edit_user = (LinearLayout) findViewById(R.id.set_edit_user);
        set_edit_user.setOnClickListener(listener);
        LinearLayout set_msg = (LinearLayout) findViewById(R.id.set_msg);
        set_msg.setOnClickListener(listener);
        LinearLayout set_clear = (LinearLayout) findViewById(R.id.set_clear);
        set_clear.setOnClickListener(listener);
        LinearLayout set_opinion = (LinearLayout) findViewById(R.id.set_opinion);
        set_opinion.setOnClickListener(listener);
        LinearLayout set_check = (LinearLayout) findViewById(R.id.set_check);
        set_check.setOnClickListener(listener);
        LinearLayout set_about = (LinearLayout) findViewById(R.id.set_about);
        set_about.setOnClickListener(listener);


    }
}
