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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naruto.R;
import com.example.naruto.Table.Mijuan;
import com.example.naruto.Table.MijuanDAO;

public class UpdateMijuanActivity extends Activity {

    Mijuan mijuan;

    EditText mijuan_name, mijuan_details, mijuan_effect;
    ImageView icon;
    Button sub;
    TextView back, cancel;
    String m_name, detail, effect, icon_uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mijuan);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mijuan = (Mijuan) bundle.getSerializable("mijuan");

        init();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.update_mijuan_back:
                    case R.id.update_mijuan_cancel:
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case R.id.update_mijuan_sub:
                        if (check()) {
                            Mijuan new_mijuan = new Mijuan(m_name, detail, effect, icon_uri);

                            MijuanDAO mijuanDAO = new MijuanDAO(UpdateMijuanActivity.this);
                            mijuanDAO.update(new_mijuan);
                            Toast.makeText(UpdateMijuanActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(UpdateMijuanActivity.this, "请输入完整内容", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.update_mijuan_icon:
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent1, 0x1);
                        break;

                }
            }
        };
        back.setOnClickListener(listener);
        cancel.setOnClickListener(listener);
        sub.setOnClickListener(listener);

    }

    private void init() {
        mijuan_name = (EditText) findViewById(R.id.update_mijuan_name);
        mijuan_details = (EditText) findViewById(R.id.update_mijuan_details);
        mijuan_effect = (EditText) findViewById(R.id.update_mijuan_effect);
        icon = (ImageView) findViewById(R.id.update_mijuan_icon);
        sub = (Button) findViewById(R.id.update_mijuan_sub);
        back = (TextView) findViewById(R.id.update_mijuan_back);
        cancel = (TextView) findViewById(R.id.update_mijuan_cancel);

        mijuan_name.setText(mijuan.getName());
        mijuan_details.setText(mijuan.getDetails());
        mijuan_effect.setText(mijuan.getEffect());

        Glide.with(UpdateMijuanActivity.this)
                .load(Uri.parse(mijuan.getIcon()))
                .into(icon);
    }

    private boolean check() {

        m_name = mijuan_name.getText().toString();
        detail = mijuan_details.getText().toString();
        effect = mijuan_effect.getText().toString();
        if (m_name == null || detail == null || effect == null || icon_uri == null) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            icon_uri = data.getData().toString();
            Glide.with(UpdateMijuanActivity.this)
                    .load(data.getData())
                    .into(icon);

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
