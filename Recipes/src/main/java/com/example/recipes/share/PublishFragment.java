package com.example.recipes.share;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipes.MainActivity;
import com.example.recipes.R;
import com.example.recipes.User.User;
import com.example.recipes.User.UserDAO;

/**
 * Created by xjh on 2018/7/10.
 */

public class PublishFragment extends Fragment {
    private ImageView imv1, imv2, userpotrait_civ;
    private TextView tv1, tv2;
    boolean isLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_fragment, container, false);

        imv1 = (ImageView) view.findViewById(R.id.publish_img1);
        imv2 = (ImageView) view.findViewById(R.id.publish_img2);
        tv1 = (TextView) view.findViewById(R.id.publish_tv);
        tv2 = (TextView) view.findViewById(R.id.publish_tv2);
        userpotrait_civ = (ImageView) view.findViewById(R.id.userpotrait_civ);

        SharedPreferences sp = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("IsLogin", false);
        String id = sp.getString("id", "123456");

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.publish_img1:
                    case R.id.publish_tv:
                        if (isLogin) {
                            intent = new Intent(getActivity(), CreateRecipesActivity.class);
                            startActivityForResult(intent, 111);
                        } else {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.toPersonCenter();
                        }
                        break;
                    case R.id.publish_img2:
                    case R.id.publish_tv2:
                        if (isLogin) {
                            intent = new Intent(getActivity(), PublishWorkActivity.class);
                            startActivity(intent);
                        } else {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.toPersonCenter();
                        }
                        break;
                }
            }
        };

        imv1.setOnClickListener(onClickListener);
        imv2.setOnClickListener(onClickListener);
        tv1.setOnClickListener(onClickListener);
        tv2.setOnClickListener(onClickListener);


        //设置头像
        if (isLogin) {
            UserDAO userDAO = new UserDAO(getActivity());
            User user = userDAO.find(id);
            Glide.with(getActivity())
                    .load(Uri.parse(user.getPhoto_uri()))
                    .into(userpotrait_civ);
        } else {
            userpotrait_civ.setImageResource(R.drawable.xiaorentu);
        }

        return view;
    }
}
