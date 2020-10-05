package com.example.recipes.User.My;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xjh on 2018/7/21.
 */

public class CollectDataBase extends SQLiteOpenHelper {

    final static String name = "collect.db";

    final static int version = 1;

    public CollectDataBase(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS collect ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "recipes_id INTEGER,"
                + "user_id VARCHAR(10))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
