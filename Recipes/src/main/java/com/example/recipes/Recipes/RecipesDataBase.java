package com.example.recipes.Recipes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xjh on 2018/7/18.
 */

public class RecipesDataBase extends SQLiteOpenHelper {
    public static final String DB_NAME = "recipes.db";

    public static final int DB_VERSION = 1;


    public RecipesDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS recipes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "icon_addr VARCHAR(100),"
                + "name VARCHAR(16),"
                + "ingredient VARCHAR(500),"
                + "progress VARCHAR(500),"
                + "tips VARCHAR(200),"
                + "sort VARCHAR(20),"
                + "releasetime date,"
                + "writer VARCHAR(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
