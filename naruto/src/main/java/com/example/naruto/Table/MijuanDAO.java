package com.example.naruto.Table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2018/8/26.
 */

public class MijuanDAO {

    private Context context;
    private SQLiteDatabase db;
    private NarutoDatabase helper;

    public MijuanDAO(Context context) {
        this.context = context;
        helper = new NarutoDatabase(context);
    }

    public void add(Mijuan mijuan) {
        db = helper.getWritableDatabase();

        int is_have;
        if (mijuan.isIs_have()) {
            is_have = 1;
        } else {
            is_have = 0;
        }

        String sql = "INSERT INTO mijuan VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        db.execSQL(sql, new Object[]{
                mijuan.getName(),
                mijuan.getIcon(),
                mijuan.getDetails(),
                mijuan.getEffect(),
                mijuan.getLevel(),
                mijuan.getMax_fragment(),
                mijuan.getFragment(),
                is_have
        });

        db.close();
    }

    public void update(Mijuan mijuan) {
        db = helper.getWritableDatabase();

        int is_have;
        if (mijuan.isIs_have()) {
            is_have = 1;
        } else {
            is_have = 0;
        }

        db.execSQL("Update mijuan set icon=?," +
                "details=?," +
                "effect=?," +
                "level=?," +
                "max_fragment=?," +
                "fragment=?," +
                "is_have=? where name=?", new Object[]{
                mijuan.getIcon(),
                mijuan.getDetails(),
                mijuan.getEffect(),
                mijuan.getLevel(),
                mijuan.getMax_fragment(),
                mijuan.getFragment(),
                is_have,
                mijuan.getName()
        });

        db.close();
    }

    public void delete(String name) {
        db = helper.getWritableDatabase();

        db.execSQL("Delete from mijuan where name=?", new Object[]{name});

        db.close();

    }

    public List<Mijuan> query() {
        List<Mijuan> list = new ArrayList<Mijuan>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mijuan", new String[]{});
        boolean is_have;
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("is_have")) == 1) {
                is_have = true;
            } else {
                is_have = false;
            }
            Mijuan  mijuan = new Mijuan(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getString(cursor.getColumnIndex("effect")),
                    cursor.getString(cursor.getColumnIndex("icon")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getInt(cursor.getColumnIndex("max_fragment")),
                    cursor.getInt(cursor.getColumnIndex("fragment")),
                    is_have);
            list.add(mijuan);
        }


        db.close();

        return list;
    }

    public Mijuan find(String name) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mijuan where name=? ", new String[]{name});
        Mijuan mijuan = null;
        boolean is_have;
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("is_have")) == 1) {
                is_have = true;
            } else {
                is_have = false;
            }
            mijuan = new Mijuan(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("details")),
                    cursor.getString(cursor.getColumnIndex("effect")),
                    cursor.getString(cursor.getColumnIndex("icon")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getInt(cursor.getColumnIndex("max_fragment")),
                    cursor.getInt(cursor.getColumnIndex("fragment")),
                    is_have);

        }


        db.close();

        return mijuan;
    }


}
