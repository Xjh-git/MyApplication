package com.example.testsqlife;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT id as _id, name,age FROM person", null);
//        要显示的Cursor的列
        String[] from = {"_id", "name", "age"};
//        显示数据的组件id
        int[] to = {R.id.txtID, R.id.txtName, R.id.txtAge};
//      R.layout.listview 自定义的 ListView布局
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.listview, cursor, from, to);
        //cursor.close();

        ListView li = (ListView) findViewById(R.id.listView1);
        li.setAdapter(adapter);
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText("查询到" + cursor.getCount() + "条记录");

//        将上下文菜单注册到ListView上
        registerForContextMenu(li);

        //添加
        Button bt1 = (Button) findViewById(R.id.button1); //注意id值
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivityForResult(intent, 100);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        200表明是来自于InsertActivity的回传
        if (requestCode == 100)
            if (resultCode == RESULT_OK) {
//                重新执行onCreate，完成刷新
                onCreate(null);
            }


        //200表明是来自于UpdateActivity的回传
        if (requestCode == 200)
            if (resultCode == RESULT_OK) {
//                重新执行onCreate，完成刷新
                onCreate(null);
            }

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");
        getMenuInflater().inflate(R.menu.manage, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                delete(item);    // 代码见后
                return true;
            case R.id.update:
                update(item);   // 代码见后
                return true;
            default:
                return false;
        }
    }

    public void delete(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info.id > 0) {
            new AlertDialog.Builder(this).setTitle("删除" + info.id).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    DBHelper helper = new DBHelper(getApplicationContext(), "test.db", null, 1);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("Delete from person where id=?", new Object[]{info.id});
                    db.close();
                    Toast.makeText(getApplicationContext(), " 记录删除成功 ", Toast.LENGTH_SHORT).show();
                    onCreate(null);     //重新加载一次Activity，刷新
                }
            }).setNegativeButton("取消", null).show();
        }
    }

    public void update(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(info.id));
        bundle.putString("username", ((TextView) info.targetView.findViewById(R.id.txtName)).getText().toString());
        bundle.putString("age", ((TextView) info.targetView.findViewById(R.id.txtAge)).getText().toString());
        intent.putExtras(bundle);
        startActivityForResult(intent, 200);
    }

}
