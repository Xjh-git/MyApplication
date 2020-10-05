package com.example.naruto.sponsor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class SpMijuanDetailsActivity extends Activity {
    TextView mijuan_name, mijuan_level, mijuan_details, mijuan_fragment, mijuan_upgrade, back, delete, update;
    ImageView mijuan_icon1, mijuan_icon2;
    ProgressBar mijuan_fragment_progress;
    Mijuan mijuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_mijuan_details);
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
                    Toast.makeText(SpMijuanDetailsActivity.this, "恭喜你获得" + mijuan.getName(), Toast.LENGTH_SHORT).show();
                }
                MijuanDAO mijuanDAO = new MijuanDAO(SpMijuanDetailsActivity.this);
                mijuanDAO.update(mijuan);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(SpMijuanDetailsActivity.this)
                        .setTitle("提示")
                        .setMessage("确认删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MijuanDAO mijuanDAO = new MijuanDAO(SpMijuanDetailsActivity.this);
                                mijuanDAO.delete(mijuan.getName());
                                Toast.makeText(SpMijuanDetailsActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SpMijuanDetailsActivity.this, UpdateMijuanActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("mijuan", mijuan);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1, 100);
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
        delete = (TextView) findViewById(R.id.sp_detele_mijuan);
        update = (TextView) findViewById(R.id.sp_update_mijuan);


        mijuan_name.setText(mijuan.getName());
        mijuan_level.setText("Lv" + mijuan.getLevel());
        mijuan_details.setText(mijuan.getDetails());
        Glide.with(SpMijuanDetailsActivity.this)
                .load(Uri.parse(mijuan.getIcon()))
                .into(mijuan_icon1);
        Glide.with(SpMijuanDetailsActivity.this)
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
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            init();
        }
    }
}
