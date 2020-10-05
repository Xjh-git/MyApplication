package com.example.naruto.User;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naruto.R;
import com.example.naruto.Table.Mijuan;
import com.example.naruto.Table.MijuanDAO;

public class MijuanDetailsActivity extends Activity {

    TextView mijuan_name, mijuan_level, mijuan_details, mijuan_fragment, mijuan_upgrade, back;
    ImageView mijuan_icon1, mijuan_icon2;
    ProgressBar mijuan_fragment_progress;
    Mijuan mijuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mijuan_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mijuan = (Mijuan) bundle.getSerializable("mijuan");

        init();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        mijuan_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mijuan.setFragment(mijuan.getFragment() + 1);
                mijuan_fragment_progress.setProgress(mijuan.getFragment());
                if (mijuan.getFragment() == mijuan.getMax_fragment()) {
                    mijuan.setIs_have(true);
                    mijuan.setMax_fragment(mijuan.getMax_fragment() * 2);
                    mijuan.setFragment(0);
                    Toast.makeText(MijuanDetailsActivity.this, "恭喜你获得" + mijuan.getName(), Toast.LENGTH_SHORT).show();
                }
                MijuanDAO mijuanDAO = new MijuanDAO(MijuanDetailsActivity.this);
                mijuanDAO.update(mijuan);
            }
        });

    }

    private void init() {
        mijuan_name = (TextView) findViewById(R.id.user_mijuan_name);
        mijuan_level = (TextView) findViewById(R.id.user_mijuan_level);
        mijuan_details = (TextView) findViewById(R.id.user_mijuan_details);
        mijuan_icon1 = (ImageView) findViewById(R.id.user_mijuan_icon1);
        mijuan_icon2 = (ImageView) findViewById(R.id.user_mijuan_icon2);
        mijuan_fragment = (TextView) findViewById(R.id.user_mijuan_fragment);
        mijuan_fragment_progress = (ProgressBar) findViewById(R.id.user_mijuan_fragment_progress);
        mijuan_upgrade = (TextView) findViewById(R.id.user_mijuan_upgrade);
        back = (TextView) findViewById(R.id.mijuan_details_back);


        mijuan_name.setText(mijuan.getName());
        mijuan_level.setText("Lv" + mijuan.getLevel());
        mijuan_details.setText(mijuan.getDetails());
        Glide.with(MijuanDetailsActivity.this)
                .load(Uri.parse(mijuan.getIcon()))
                .into(mijuan_icon1);
        Glide.with(MijuanDetailsActivity.this)
                .load(Uri.parse(mijuan.getIcon()))
                .into(mijuan_icon2);
        mijuan_fragment.setText(mijuan.getName() + "碎片");

        mijuan_fragment_progress.setMax(mijuan.getMax_fragment());
        mijuan_fragment_progress.setProgress(mijuan.getFragment());


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }
}
