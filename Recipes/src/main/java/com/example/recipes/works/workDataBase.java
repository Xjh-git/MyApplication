package com.example.recipes.works;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xjh on 2018/7/19.
 */

public class workDataBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "work.db";

    public static final int DB_VERSION = 1;


    public workDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS work ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "img VARCHAR(100),"
                + "recipes INTEGER,"
                + "tags VARCHAR(50),"
                + "writer VARCHAR(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
