package com.example.naruto.Table;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2018/8/26.
 */

public class NinjiaDAO {
    private Context context;
    private SQLiteDatabase db;
    private NarutoDatabase helper;

    public NinjiaDAO(Context context) {
        this.context = context;
        helper = new NarutoDatabase(context);
    }

    public void add(Ninjia ninjia) {
        db = helper.getWritableDatabase();

        int is_have;
        if (ninjia.isIs_have()) {
            is_have = 1;
        } else {
            is_have = 0;
        }

        String sql = "INSERT INTO ninjia VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        db.execSQL(sql, new Object[]{
                ninjia.getName(),
                ninjia.getIcon(),
                ninjia.getIcon_big(),
                ninjia.getSkill_one_details(),
                ninjia.getSkill_one_icon(),
                ninjia.getSkill_two_details(),
                ninjia.getSkill_two_icon(),
                ninjia.getSkill_big_details(),
                ninjia.getSkill_big_icon(),
                ninjia.getEnd_icon(),
                ninjia.getLevel(),
                ninjia.getTall(),
                ninjia.getWeight(),
                ninjia.getMax_fragments(),
                ninjia.getFragments(),
                ninjia.getGrade(),
                is_have
        });

        db.close();
    }

    public void update(Ninjia ninjia) {
        db = helper.getWritableDatabase();

        int is_have;
        if (ninjia.isIs_have()) {
            is_have = 1;
        } else {
            is_have = 0;
        }

        db.execSQL("Update ninjia set icon=?," +
                "icon_big=?," +
                "skill_one_details=?," +
                "skill_one_icon=?," +
                "skill_two_details=?," +
                "skill_two_icon=?," +
                "skill_big_details=?," +
                "skill_big_icon=?," +
                "end_icon=?," +
                "level=?," +
                "tall=?," +
                "weight=?," +
                "Max_fragments=?," +
                "fragments=?," +
                "grade=?," +
                "is_have=? where name=?", new Object[]{
                ninjia.getIcon(),
                ninjia.getIcon_big(),
                ninjia.getSkill_one_details(),
                ninjia.getSkill_one_icon(),
                ninjia.getSkill_two_details(),
                ninjia.getSkill_two_icon(),
                ninjia.getSkill_big_details(),
                ninjia.getSkill_big_icon(),
                ninjia.getEnd_icon(),
                ninjia.getLevel(),
                ninjia.getTall(),
                ninjia.getWeight(),
                ninjia.getMax_fragments(),
                ninjia.getFragments(),
                ninjia.getGrade(),
                is_have,
                ninjia.getName()
        });

        db.close();
    }

    public void delete(String name) {
        db = helper.getWritableDatabase();

        db.execSQL("Delete from ninjia where name=?", new Object[]{name});

        db.close();

    }

    public List<Ninjia> query() {
        List<Ninjia> list = new ArrayList<Ninjia>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ninjia", null);
        Ninjia ninjia;
        boolean is_have;
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("is_have")) == 1) {
                is_have = true;
            } else {
                is_have = false;
            }
            ninjia = new Ninjia(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("icon")),
                    cursor.getString(cursor.getColumnIndex("icon_big")),
                    cursor.getString(cursor.getColumnIndex("skill_one_details")),
                    cursor.getString(cursor.getColumnIndex("skill_one_icon")),
                    cursor.getString(cursor.getColumnIndex("skill_two_details")),
                    cursor.getString(cursor.getColumnIndex("skill_two_icon")),
                    cursor.getString(cursor.getColumnIndex("skill_big_details")),
                    cursor.getString(cursor.getColumnIndex("skill_big_icon")),
                    cursor.getString(cursor.getColumnIndex("end_icon")),
                    cursor.getString(cursor.getColumnIndex("level")),
                    cursor.getInt(cursor.getColumnIndex("tall")),
                    cursor.getInt(cursor.getColumnIndex("weight")),
                    cursor.getInt(cursor.getColumnIndex("Max_fragments")),
                    cursor.getInt(cursor.getColumnIndex("fragments")),
                    cursor.getInt(cursor.getColumnIndex("grade")),
                    is_have);
            list.add(ninjia);
        }


        db.close();

        return list;
    }

    public Ninjia find(String name) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ninjia where name=?", new String[]{name});
        Ninjia ninjia = null;
        boolean is_have;
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex("is_have")) == 1) {
                is_have = true;
            } else {
                is_have = false;
            }
            ninjia = new Ninjia(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("icon")),
                    cursor.getString(cursor.getColumnIndex("icon_big")),
                    cursor.getString(cursor.getColumnIndex("skill_one_details")),
                    cursor.getString(cursor.getColumnIndex("skill_one_icon")),
                    cursor.getString(cursor.getColumnIndex("skill_two_details")),
                    cursor.getString(cursor.getColumnIndex("skill_two_icon")),
                    cursor.getString(cursor.getColumnIndex("skill_big_details")),
                    cursor.getString(cursor.getColumnIndex("skill_big_icon")),
                    cursor.getString(cursor.getColumnIndex("end_icon")),
                    cursor.getString(cursor.getColumnIndex("level")),
                    cursor.getInt(cursor.getColumnIndex("tall")),
                    cursor.getInt(cursor.getColumnIndex("weight")),
                    cursor.getInt(cursor.getColumnIndex("Max_fragments")),
                    cursor.getInt(cursor.getColumnIndex("fragments")),
                    cursor.getInt(cursor.getColumnIndex("grade")),
                    is_have);

        }


        db.close();

        return ninjia;
    }


}
