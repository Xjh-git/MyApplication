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
import com.example.naruto.Table.Ninjia;
import com.example.naruto.Table.NinjiaDAO;

public class SpNinjiaDetailsActivity extends Activity {

    TextView name, skill1, skill2, skill3, fragment, get, back, delete, update;
    ImageView icon, icon1, icon2, icon3, icon_end;
    ProgressBar fragment_progress;
    Ninjia ninjia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_ninjia_details);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ninjia = (Ninjia) bundle.getSerializable("ninjia");

        init();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ninjia.setFragments(ninjia.getFragments() + 1);
                fragment_progress.setProgress(ninjia.getFragments());
                if (ninjia.getFragments() == ninjia.getMax_fragments()) {
                    ninjia.setIs_have(true);
                    ninjia.setMax_fragments(ninjia.getMax_fragments() * 2);
                    ninjia.setFragments(0);
                    Toast.makeText(SpNinjiaDetailsActivity.this, "恭喜你获得" + ninjia.getName(), Toast.LENGTH_SHORT).show();
                }
                NinjiaDAO ninjiaDAO = new NinjiaDAO(SpNinjiaDetailsActivity.this);
                ninjiaDAO.update(ninjia);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SpNinjiaDetailsActivity.this, UpdateNinjiaActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("ninjia", ninjia);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1, 100);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(SpNinjiaDetailsActivity.this)
                        .setTitle("提示")
                        .setMessage("确认删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NinjiaDAO ninjiaDAO = new NinjiaDAO(SpNinjiaDetailsActivity.this);
                                ninjiaDAO.delete(ninjia.getName());
                                Toast.makeText(SpNinjiaDetailsActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

            }
        });

    }

    private void init() {
        name = (TextView) findViewById(R.id.ninjia_details_name);
        skill1 = (TextView) findViewById(R.id.ninjia_details_details1);
        skill2 = (TextView) findViewById(R.id.ninjia_details_details2);
        skill3 = (TextView) findViewById(R.id.ninjia_details_details3);
        fragment = (TextView) findViewById(R.id.ninjia_details_fragment);
        get = (TextView) findViewById(R.id.ninjia_details_get);
        icon = (ImageView) findViewById(R.id.ninjia_details_icon);
        icon1 = (ImageView) findViewById(R.id.ninjia_details_icon1);
        icon2 = (ImageView) findViewById(R.id.ninjia_details_icon2);
        icon3 = (ImageView) findViewById(R.id.ninjia_details_icon3);
        icon_end = (ImageView) findViewById(R.id.ninjia_details_icon_end);
        fragment_progress = (ProgressBar) findViewById(R.id.ninjia_details_fragment_progressBar);
        back = (TextView) findViewById(R.id.ninjia_details_back);
        delete = (TextView) findViewById(R.id.sp_detele_ninjia);
        update = (TextView) findViewById(R.id.sp_update_ninjia);

        name.setText(ninjia.getName() + ninjia.getLevel());
        skill1.setText(ninjia.getSkill_one_details());
        skill2.setText(ninjia.getSkill_two_details());
        skill3.setText(ninjia.getSkill_big_details());
        fragment.setText(ninjia.getName() + "碎片");

        Glide.with(this)
                .load(Uri.parse(ninjia.getEnd_icon()))
                .into(icon_end);
        Glide.with(this)
                .load(Uri.parse(ninjia.getSkill_one_icon()))
                .into(icon1);
        Glide.with(this)
                .load(Uri.parse(ninjia.getSkill_two_icon()))
                .into(icon2);
        Glide.with(this)
                .load(Uri.parse(ninjia.getIcon_big()))
                .into(icon3);
        Glide.with(this)
                .load(Uri.parse(ninjia.getIcon()))
                .into(icon);

        if (ninjia.isIs_have()) {
            if (ninjia.getGrade() == 5) {
                get.setText("~~~");
            } else {
                get.setText("升星");
            }
        } else {
            get.setText("招募");
        }

        fragment_progress.setMax(ninjia.getMax_fragments());
        fragment_progress.setProgress(ninjia.getFragments());


    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            setResult(RESULT_OK);
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
