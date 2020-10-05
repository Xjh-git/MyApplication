package com.example.naruto.sponsor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naruto.R;
import com.example.naruto.Table.Mijuan;
import com.example.naruto.Table.MijuanDAO;

public class AddMijuanActivity extends Activity {

    EditText mijuan_name, mijuan_details, mijuan_effect;
    ImageView icon;
    String name, detail, effect, icon_uri = null;
    Button sub;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mijuan);

        init();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add_mijuan_sub:
                        if (check()) {
                            Mijuan mijuan = new Mijuan(name, detail, effect, icon_uri);

                            MijuanDAO mijuanDAO = new MijuanDAO(AddMijuanActivity.this);
                            mijuanDAO.add(mijuan);
                            Toast.makeText(AddMijuanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(AddMijuanActivity.this, "请输入完整内容", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.add_mijuan_back:
                        finish();
                        break;
                    case R.id.add_mijuan_icon:
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x1);
                        break;
                }
            }
        };

        sub.setOnClickListener(listener);
        back.setOnClickListener(listener);
        icon.setOnClickListener(listener);

    }

    private void init() {
        mijuan_name = (EditText) findViewById(R.id.add_mijuan_name);
        mijuan_details = (EditText) findViewById(R.id.add_mijuan_details);
        mijuan_effect = (EditText) findViewById(R.id.add_mijuan_effect);
        icon = (ImageView) findViewById(R.id.add_mijuan_icon);
        sub = (Button) findViewById(R.id.add_mijuan_sub);
        back = (TextView) findViewById(R.id.add_mijuan_back);


    }

    private boolean check() {
        name = mijuan_name.getText().toString();
        detail = mijuan_details.getText().toString();
        effect = mijuan_effect.getText().toString();
        if (name == null || detail == null || effect == null || icon_uri == null) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                icon_uri = data.getData().toString();
                Glide.with(AddMijuanActivity.this)
                        .load(data.getData())
                        .into(icon);
                System.out.println(icon_uri);
            }
        }
    }
}
