package com.example.testsqlife;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //“保存”按钮
        Button bt1 = (Button) findViewById(R.id.btn_save);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText et1 = (EditText) findViewById(R.id.edt_name);
                EditText et2 = (EditText) findViewById(R.id.edt_age);
                Person person = new Person(et1.getText().toString(),
                        Integer.parseInt(et2.getText().toString()));
                insert(person); //代码后页
                setResult(RESULT_OK, null);
                finish();
            }
        });

        //取消
        Button bt2 = (Button) findViewById(R.id.btn_cancel);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void insert(Person person) {
        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "INSERT INTO person VALUES (NULL, ?, ?)";
        db.execSQL(sql, new Object[]{person.getName(), person.getAge()});
        db.close();
        Toast.makeText(getApplicationContext(), "记录添加成功",
                Toast.LENGTH_SHORT).show();
    }


}
