package com.example.testsqlife;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id = bundle.getString("id");
        String name = bundle.getString("username");
        String age = bundle.getString("age");
        final EditText et1 = (EditText) findViewById(R.id.edt_id);
        final EditText et2 = (EditText) findViewById(R.id.edt_name);
        final EditText et3 = (EditText) findViewById(R.id.edt_age);
        et1.setText(id);
        et2.setText(name);
        et3.setText(age);

        //保存
        Button bt1 = (Button) findViewById(R.id.btn_save);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null, 1);
                SQLiteDatabase db = helper.getWritableDatabase();
                //更新数据，id值不能修改
                db.execSQL("Update person set name=? , age=? where id=?", new Object[]{
                        et2.getText().toString(), et3.getText().toString(), et1.getText().toString()});
                db.close();
                Toast.makeText(getApplicationContext(), "记录修改成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, null);
                finish(); //不能少
            }
        });

        //取消
        Button bt2 = (Button) findViewById(R.id.btn_cancel);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }


}
