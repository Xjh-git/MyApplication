package com.example.recipes.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by xjh on 2018/7/17.
 */

public class UserPhotoDAO {
    public static final String DB_NAME = "userphoto.db";
    public static final String IMAGE = "image";

    private UserPhotoDatabase helper;

    private SQLiteDatabase db;

    public UserPhotoDAO(Context context) {

        helper = new UserPhotoDatabase(context);

    }

    //添加用户图片
    public void add(UserPhoto userPhoto) {

        db = helper.getWritableDatabase();
        String sql = "INSERT INTO userphoto VALUES (?, ?)";
        db.execSQL(sql, new Object[]{userPhoto.getId(), userPhoto.getImg()});

        db.close();
    }

    //更新用户信息
    public void update(UserPhoto userPhoto) {

        db = helper.getWritableDatabase();
        //更新数据，id值不能修改
        db.execSQL("Update userphoto set photo=? where id=?", new Object[]{
                userPhoto.getImg(), userPhoto.getId()});
        db.close();
    }

    //查找用户信息
    public UserPhoto find(String id) {

        db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM userphoto where id=?", new String[]{id});

        if (cursor.moveToNext()) {
            UserPhoto userPhoto=new UserPhoto(cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getBlob(cursor.getColumnIndex("photo")));

            cursor.close();
            db.close();
            return userPhoto;
        }

        db.close();

        return null;

    }
}
