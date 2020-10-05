package com.example.recipes.User;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipes.R;
import com.example.recipes.User.My.MycollectActivity;
import com.example.recipes.User.My.MyrecipesActivity;
import com.example.recipes.setting.SettingActivity;
import com.example.recipes.works.MyWorksActivity;


/**
 * Created by xjh on 2018/7/11.
 */

public class PersonCenterFragment extends Fragment {
    ImageView img_touxiang, img_setting, iv_tomywork, iv_tomycollect, iv_tomyrecipes;
    TextView btn_edit, name, tv_tomywork, tv_tomycollect, tv_tomyrecipes;
    UserDAO userDAO;
    User user;
    View view;
    Uri photo_uri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.person_center, container, false);
        //初始化控件
        initview();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.img_setting:
                        intent = new Intent(getActivity(), SettingActivity.class);
                        startActivityForResult(intent, 111);
                        break;
                    case R.id.img_touxiang:
                        //前往图库获得图片
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x1);
                        break;
                    case R.id.Pc_toedit:
                        //编辑个人资料
                        intent = new Intent(getActivity(), EditUserActivity.class);
                        startActivityForResult(intent, 999);
                        break;
                    case R.id.iv_tomywork:
                    case R.id.tv_tomywork:
                        //我的作品
                        intent = new Intent(getActivity(), MyWorksActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.iv_tomycollect:
                    case R.id.tv_tomycollect:
                        //我的收藏
                        intent = new Intent(getActivity(), MycollectActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.iv_tomyrecipes:
                    case R.id.tv_tomyrecipes:
                        //我的菜谱
                        intent = new Intent(getActivity(), MyrecipesActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };


        img_setting.setOnClickListener(listener);
        img_touxiang.setOnClickListener(listener);
        btn_edit.setOnClickListener(listener);
        iv_tomywork.setOnClickListener(listener);
        tv_tomywork.setOnClickListener(listener);
        iv_tomycollect.setOnClickListener(listener);
        tv_tomycollect.setOnClickListener(listener);
        iv_tomyrecipes.setOnClickListener(listener);
        tv_tomyrecipes.setOnClickListener(listener);


        //初始化数据，如昵称，头像等
        initdata();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            SharedPreferences sp = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
            boolean isLogin = sp.getBoolean("IsLogin", false);
            if (!isLogin) {
                FragmentManager fm = getActivity().getFragmentManager();
                Fragment fragment = new NoLoginFragment();
                fm.beginTransaction().replace(R.id.content, fragment).commit();
            }
        }
        if (requestCode == 0x1 && resultCode == -1) {
            if (data != null) {
                //得到图片的uri
                photo_uri = data.getData();
                //更新图片
                img_touxiang.setImageURI(photo_uri);
                //更新数据库
                user.setPhoto_uri(photo_uri.toString());
                userDAO.update(user);
                System.out.println(user.getPhoto_uri());
            }
        }
        if (requestCode == 999) {
            initdata();
        }

    }

    private void initview() {
        img_setting = (ImageView) view.findViewById(R.id.img_setting);
        img_touxiang = (ImageView) view.findViewById(R.id.img_touxiang);
//        img_publish = (ImageView) view.findViewById(R.id.img_publish);
        name = (TextView) view.findViewById(R.id.PC_name);
        btn_edit = (TextView) view.findViewById(R.id.Pc_toedit);
        iv_tomycollect = (ImageView) view.findViewById(R.id.iv_tomycollect);
        iv_tomywork = (ImageView) view.findViewById(R.id.iv_tomywork);
        iv_tomyrecipes = (ImageView) view.findViewById(R.id.iv_tomyrecipes);
        tv_tomycollect = (TextView) view.findViewById(R.id.tv_tomycollect);
        tv_tomywork = (TextView) view.findViewById(R.id.tv_tomywork);
        tv_tomyrecipes = (TextView) view.findViewById(R.id.tv_tomyrecipes);
    }

    private void initdata() {

        SharedPreferences sp = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("IsLogin", false);
        String id = sp.getString("id", "123456");

        //判断是否登录
        if (isLogin) {
            userDAO = new UserDAO(getActivity());
            user = userDAO.find(id);

        } else {
            //没有登录，前往登录页面
            FragmentManager fm = getActivity().getFragmentManager();
            Fragment fragment = new NoLoginFragment();
            fm.beginTransaction().replace(R.id.content, fragment).commit();
        }

        //设置用户名
        name.setText(user.getName());

        if (user.getPhoto_uri().equals("")) {
            img_touxiang.setImageResource(R.drawable.xiaorentu);
        } else {
            img_touxiang.setImageURI(Uri.parse(user.getPhoto_uri()));
        }

    }
}
