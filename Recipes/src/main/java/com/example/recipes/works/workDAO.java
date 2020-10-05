package com.example.recipes.works;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2018/7/19.
 */

public class workDAO {
    private workDataBase helper;

    private SQLiteDatabase db;

    public workDAO(Context context){
        helper=new workDataBase(context);
    }

    public void add(work work) {
        db = helper.getWritableDatabase();

        String sql = "INSERT INTO work VALUES (null, ?, ?, ?, ?)";
        db.execSQL(sql, new Object[]{
                work.getImg(),
                work.getRecipes(),
                work.getTags(),
                work.getWriter()
        });
        db.close();
    }

    public void update(work work) {

        db = helper.getWritableDatabase();
        //更新数据，id值不能修改
        db.execSQL("Update work set img=?, recipes=? , tags=?,writer=? where id=?", new Object[]{
                work.getImg(),
                work.getRecipes(),
                work.getTags(),
                work.getWriter(),
                work.getId()
        });
        db.close();
    }

    public List<work> get() {
        List<work> list=new ArrayList<work>();
        work work;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM work", new String[]{});
        while (cursor.moveToNext()) {
            work = new work(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("img")),
                    cursor.getInt(cursor.getColumnIndex("recipes")),
                    cursor.getString(cursor.getColumnIndex("tags")),
                    cursor.getString(cursor.getColumnIndex("writer")));
            list.add(work);
        }
        return list;
    }

}
