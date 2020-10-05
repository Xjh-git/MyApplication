package com.example.naruto.sponsor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naruto.R;
import com.example.naruto.Table.Ninjia;
import com.example.naruto.Table.NinjiaDAO;

public class UpdateNinjiaActivity extends Activity {

    Ninjia ninjia;

    EditText n_name, details1, details2, details3, tall, weight;
    ImageView icon, icon1, icon2, icon3, icon_end, icon_big;
    Button sub, cancel;
    String icon_uri, icon1_uri, icon2_uri, icon3_uri, icon_end_uri, icon_big_uri;
    Spinner level_sp;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ninjia);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ninjia = (Ninjia) bundle.getSerializable("ninjia");

        init();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.add_ninjia_sub:
                        if (check()) {
                            String level = level_sp.getSelectedItem().toString();
                            int max_fragments = 0;
                            switch (level) {
                                case "S":
                                    max_fragments = 100;
                                    break;
                                case "A":
                                case "B":
                                    max_fragments = 40;
                                    break;
                                case "C":
                                    max_fragments = 10;
                                    break;
                            }

                            Ninjia ninjia = new Ninjia(n_name.getText().toString(),
                                    icon_uri,
                                    icon_big_uri,
                                    details1.getText().toString(),
                                    icon1_uri,
                                    details2.getText().toString(),
                                    icon2_uri,
                                    details3.getText().toString(),
                                    icon3_uri,
                                    icon_end_uri,
                                    level,
                                    Integer.valueOf(tall.getText().toString()),
                                    Integer.valueOf(weight.getText().toString()),
                                    max_fragments
                                    , 10, 3, false);

                            NinjiaDAO ninjiaDAO = new NinjiaDAO(UpdateNinjiaActivity.this);
                            ninjiaDAO.update(ninjia);
                            Toast.makeText(UpdateNinjiaActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UpdateNinjiaActivity.this, "请输入完整的内容", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.add_ninjia_icon1:
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x1);

                    case R.id.add_ninjia_icon2:
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x2);
                    case R.id.add_ninjia_icon3:
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x3);
                    case R.id.add_ninjia_icon:
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x4);
                    case R.id.add_ninjia_icon_big:
                        intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x5);

                    case R.id.add_ninjia_back:
                    case R.id.update_ninjia_cancel:
                        setResult(RESULT_OK);
                        finish();
                        break;

                }
            }
        };
        icon.setOnClickListener(listener);
        icon1.setOnClickListener(listener);
        icon2.setOnClickListener(listener);
        icon3.setOnClickListener(listener);
        sub.setOnClickListener(listener);
        back.setOnClickListener(listener);
        cancel.setOnClickListener(listener);


    }

    private void init() {
        n_name = (EditText) findViewById(R.id.update_ninjia_name);
        icon1 = (ImageView) findViewById(R.id.update_ninjia_icon1);
        icon2 = (ImageView) findViewById(R.id.update_ninjia_icon2);
        icon3 = (ImageView) findViewById(R.id.update_ninjia_icon3);
        icon_end = (ImageView) findViewById(R.id.update_ninjia_icon_end);
        sub = (Button) findViewById(R.id.update_ninjia_sub);
        details1 = (EditText) findViewById(R.id.update_ninjia_details1);
        details2 = (EditText) findViewById(R.id.update_ninjia_details2);
        details3 = (EditText) findViewById(R.id.update_ninjia_details3);
        tall = (EditText) findViewById(R.id.update_ninjia_tall);
        weight = (EditText) findViewById(R.id.update_ninjia_weight);
        icon = (ImageView) findViewById(R.id.update_ninjia_icon);
        level_sp = (Spinner) findViewById(R.id.update_ninjia_level);
        icon_big = (ImageView) findViewById(R.id.update_ninjia_icon_big);
        back = (TextView) findViewById(R.id.update_ninjia_back);
        cancel = (Button) findViewById(R.id.update_ninjia_cancel);

        n_name.setText(ninjia.getName());
        Glide.with(UpdateNinjiaActivity.this)
                .load(Uri.parse(ninjia.getSkill_one_icon()))
                .into(icon1);
        Glide.with(UpdateNinjiaActivity.this)
                .load(Uri.parse(ninjia.getSkill_two_icon()))
                .into(icon2);
        Glide.with(UpdateNinjiaActivity.this)
                .load(Uri.parse(ninjia.getSkill_big_icon()))
                .into(icon3);
        Glide.with(UpdateNinjiaActivity.this)
                .load(Uri.parse(ninjia.getEnd_icon()))
                .into(icon_end);
        details1.setText(ninjia.getSkill_one_details());
        details2.setText(ninjia.getSkill_two_details());
        details3.setText(ninjia.getSkill_big_details());
        tall.setText(ninjia.getTall());
        weight.setText(ninjia.getWeight());
        Glide.with(UpdateNinjiaActivity.this)
                .load(Uri.parse(ninjia.getIcon()))
                .into(icon);
        Glide.with(UpdateNinjiaActivity.this)
                .load(Uri.parse(ninjia.getIcon_big()))
                .into(icon_big);

    }

    private boolean check() {
        if (n_name.getText() == null || details1.getText() == null || details2.getText() == null || details3.getText() == null || tall.getText() == null || weight.getText() == null) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            Glide.with(UpdateNinjiaActivity.this)
                    .load(data.getData())
                    .into(icon1);
            icon1_uri = data.getData().toString();
        }
        if (requestCode == 0x2 && resultCode == RESULT_OK) {
            Glide.with(UpdateNinjiaActivity.this)
                    .load(data.getData())
                    .into(icon2);
            icon2_uri = data.getData().toString();
        }
        if (requestCode == 0x3 && resultCode == RESULT_OK) {
            Glide.with(UpdateNinjiaActivity.this)
                    .load(data.getData())
                    .into(icon3);
            icon3_uri = data.getData().toString();
        }
        if (requestCode == 0x4 && resultCode == RESULT_OK) {
            Glide.with(UpdateNinjiaActivity.this)
                    .load(data.getData())
                    .into(icon);
            icon_uri = data.getData().toString();
        }
        if (requestCode == 0x5 && resultCode == RESULT_OK) {
            Glide.with(UpdateNinjiaActivity.this)
                    .load(data.getData())
                    .into(icon_big);
            icon_big_uri = data.getData().toString();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}