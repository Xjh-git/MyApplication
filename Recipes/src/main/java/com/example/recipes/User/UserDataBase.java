package com.example.recipes.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xjh on 2018/7/11.
 */

public class UserDataBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "user.db";

    public static final int DB_VERSION = 1;

    public UserDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user ("
                + "id VARCHAR(10) PRIMARY KEY,"
                + "password VARCHAR(16),"
                + "name VARCHAR(10),"
                + "sex VARCHAR(5),"
                + "phonenum VARCHAR(11),"
                + "photo_uri VARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
