package com.example.recipes.User;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.recipes.R;

/**
 * Created by xjh on 2018/7/10.
 */

public class NoLoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.no_login,container,false);
        Button btn_tologin;
        btn_tologin=(Button)view.findViewById(R.id.btn_tologin);
        btn_tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,123);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123){
            if(resultCode==-1){
                FragmentManager fm=getActivity().getFragmentManager();
                Fragment fragment=new PersonCenterFragment();
                fm.beginTransaction().replace(R.id.content,fragment).commit();

            }
        }
    }
}
