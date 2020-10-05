package com.example.recipes;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.recipes.User.NoLoginFragment;
import com.example.recipes.User.PersonCenterFragment;
import com.example.recipes.share.PublishFragment;
import com.example.recipes.Shouye.ShouyeFragment;

public class MainActivity extends Activity {
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private FragmentTransaction transaction;
    private TextView index, personal;
    private TextView more;

    //    初始化成员
    public void initComponent() {
        index = (TextView) findViewById(R.id.main_shouye);
        more = (TextView) findViewById(R.id.main_add);
        personal = (TextView) findViewById(R.id.main_personal);
    }

    //    加载Fragment
    public void loadFragment(int containerViewId, Fragment fragment) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment);
        transaction.commit();

    }

    //    清除掉所有的选中状态。
    private void clearSelection() {
        index.setTextColor(Color.parseColor("#82858b"));
        more.setTextColor(Color.parseColor("#82858b"));
        personal.setTextColor(Color.parseColor("#82858b"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //隐藏窗口title
        setContentView(R.layout.activity_main);


        //申请权限
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            return;
        }
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            return;
        }


        initComponent(); //初始化成员
        fragment = new ShouyeFragment();
        loadFragment(R.id.content, fragment);
        //设置选中状态
        index.setTextColor(Color.GREEN);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.main_shouye:
                        if (!(fragment instanceof ShouyeFragment)) {
                            clearSelection();
                            fragment = new ShouyeFragment();//加载Fragment
                            loadFragment(R.id.content, fragment);
                            //设置选中状态
                            index.setTextColor(Color.GREEN);
                        }
                        break;
                    case R.id.main_add:
                        if (!(fragment instanceof PublishFragment)) {
                            clearSelection();
                            fragment = new PublishFragment();//加载Fragment
                            loadFragment(R.id.content, fragment);
                            //设置选中状态
                            more.setTextColor(Color.GREEN);
                        }
                        break;
                    case R.id.main_personal:
                        if (!(fragment instanceof PersonCenterFragment)) {
                            toPersonCenter();
                            //                            clearSelection();
                            //                            SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
                            //                            boolean isLogin = sp.getBoolean("IsLogin", false);
                            //                            if (isLogin) {
                            //                                fragment = new PersonCenterFragment();//加载Fragment
                            //                            } else {
                            //                                fragment = new NoLoginFragment();//加载Fragment
                            //                            }
                            //                            loadFragment(R.id.content, fragment);
                            //                            //设置选中状态
                            //                            personal.setTextColor(Color.GREEN);
                        }
                        break;

                }//end case
            }//end onclick
        };//end new

        personal.setOnClickListener(listener);
        index.setOnClickListener(listener);
        more.setOnClickListener(listener);

    }

    public void toPersonCenter() {
        clearSelection();
        SharedPreferences sp = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("IsLogin", false);
        if (isLogin) {
            fragment = new PersonCenterFragment();//加载Fragment
        } else {
            fragment = new NoLoginFragment();//加载Fragment
        }
        loadFragment(R.id.content, fragment);
        //设置选中状态
        personal.setTextColor(Color.GREEN);
    }
}
