package com.example.recipes.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by xjh on 2018/7/12.
 */

public class UserDAO {
    private UserDataBase helper;

    private SQLiteDatabase db;

    public UserDAO(Context context) {

        helper = new UserDataBase(context);

    }

    //添加用户信息
    public void add(User user) {

        db = helper.getWritableDatabase();

        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?,?)";
        db.execSQL(sql, new Object[]{user.getId(), user.getPassword(), user.getName(), user.getSex(), user.getPhonenum(),user.getPhoto_uri()});
        db.close();
    }

    //更新用户信息
    public void update(User user) {

        db = helper.getWritableDatabase();
        //更新数据，id值不能修改
        db.execSQL("Update user set password=?, name=? , sex=?,phonenum=?,photo_uri=? where id=?", new Object[]{
                user.getPassword(), user.getName(), user.getSex(), user.getPhonenum(), user.getId(),user.getPhoto_uri()});
        db.close();
    }

    //查找用户信息
    public User find(String id) {

        db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user where id=?", new String[]{id});

        if (cursor.moveToNext()) {

            return new User(cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("sex")),
                    cursor.getString(cursor.getColumnIndex("phonenum")),
                    cursor.getString(cursor.getColumnIndex("photo_uri")));
        }

        return null;

    }
}
