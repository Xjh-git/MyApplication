package com.example.recipes.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xjh on 2018/7/17.
 */

public class UserPhotoDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "userphoto.db";

    public static final int DB_VERSION = 1;

    public UserPhotoDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS userphoto ("
                + "id VARCHAR(10) PRIMARY KEY,"
                + "photo BLOB not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
