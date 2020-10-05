package com.example.naruto.Table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xjh on 2018/8/26.
 */

public class NarutoDatabase extends SQLiteOpenHelper {
    public final static String db_name = "naruto.db";

    public final static int version = 1;


    public NarutoDatabase(Context context) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ninjia (" +
                "name  VARCHAR(30) PRIMARY KEY," +
                "icon VARCHAR(100)," +
                "icon_big VARCHAR(100)," +
                "skill_one_details VARCHAR(400)," +
                "skill_one_icon VARCHAR(100)," +
                "skill_two_details VARCHAR(400)," +
                "skill_two_icon VARCHAR(100)," +
                "skill_big_details VARCHAR(400)," +
                "skill_big_icon VARCHAR(100)," +
                "end_icon VARCHAR(100)," +
                "level VARCHAR(1)," +
                "tall INTEGER," +
                "weight INTEGER," +
                "Max_fragments INTEGER," +
                "fragments INTEGER,"+
                "grade INTEGER,"+
                "is_have INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS mijuan (" +
                "name  VARCHAR(30) PRIMARY KEY," +
                "icon VARCHAR(100)," +
                "details VARCHAR(400)," +
                "effect VARCHAR(400)," +
                "level INTEGER," +
                "max_fragment INTEGER," +
                "fragment INTEGER,"+
                "is_have INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
