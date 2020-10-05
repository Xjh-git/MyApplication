package com.example.recipes.Recipes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recipes.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2018/7/18.
 */

public class RecipesDAO {
    private RecipesDataBase helper;

    private SQLiteDatabase db;


    public RecipesDAO(Context context) {
        helper = new RecipesDataBase(context);
    }

    public void add(Recipes recipes) {
        db = helper.getWritableDatabase();

        String sql = "INSERT INTO recipes VALUES (null, ?, ?, ?, ?,?,?,?,?)";
        db.execSQL(sql, new Object[]{
                recipes.getIcon_addr(),
                recipes.getName(),
                recipes.getIngredient(),
                recipes.getProgress(),
                recipes.getTips(),
                recipes.getSort(),
                recipes.getReleasetime(),
                recipes.getWriter()});
        db.close();
    }

    public void update(Recipes recipes) {

        db = helper.getWritableDatabase();
        //更新数据，id值不能修改
        db.execSQL("Update recipes set icon_addr=?, name=? , ingredient=?,progress=?,tips=?,sort=?,releasetime=?,writer=? where id=?", new Object[]{
                recipes.getIcon_addr(),
                recipes.getName(),
                recipes.getIngredient(),
                recipes.getProgress(),
                recipes.getTips(),
                recipes.getSort(),
                recipes.getReleasetime(),
                recipes.getWriter(),
                recipes.getRid()});
        db.close();
    }

    public List<Recipes> get() {
        List<Recipes> list = new ArrayList<Recipes>();
        Recipes recipes;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes", new String[]{});
        while (cursor.moveToNext()) {
            recipes = new Recipes(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("icon_addr")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("ingredient")),
                    cursor.getString(cursor.getColumnIndex("progress")),
                    cursor.getString(cursor.getColumnIndex("tips")),
                    cursor.getString(cursor.getColumnIndex("sort")),
                    cursor.getString(cursor.getColumnIndex("releasetime")),
                    cursor.getString(cursor.getColumnIndex("writer")));
            list.add(recipes);
        }
        return list;
    }

    public Recipes find(int id) {

        Recipes recipes;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes where id= ?", new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            recipes = new Recipes(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("icon_addr")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("ingredient")),
                    cursor.getString(cursor.getColumnIndex("progress")),
                    cursor.getString(cursor.getColumnIndex("tips")),
                    cursor.getString(cursor.getColumnIndex("sort")),
                    cursor.getString(cursor.getColumnIndex("releasetime")),
                    cursor.getString(cursor.getColumnIndex("writer")));
            return recipes;
        }
        return null;
    }

    public List<Recipes> getIdByName(String name) {
        List<Recipes> list = new ArrayList<Recipes>();
        Recipes recipes;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes where name like ?", new String[]{"%" + name + "%"});
        while (cursor.moveToNext()) {
            recipes = new Recipes(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("icon_addr")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("ingredient")),
                    cursor.getString(cursor.getColumnIndex("progress")),
                    cursor.getString(cursor.getColumnIndex("tips")),
                    cursor.getString(cursor.getColumnIndex("sort")),
                    cursor.getString(cursor.getColumnIndex("releasetime")),
                    cursor.getString(cursor.getColumnIndex("writer")));
            list.add(recipes);
        }
        return list;
    }


    public List<Recipes> getBySort(String sort) {
        List<Recipes> list = new ArrayList<Recipes>();
        Recipes recipes;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes where sort like ?", new String[]{"%" + sort + "%"});
        while (cursor.moveToNext()) {
            recipes = new Recipes(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("icon_addr")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("ingredient")),
                    cursor.getString(cursor.getColumnIndex("progress")),
                    cursor.getString(cursor.getColumnIndex("tips")),
                    cursor.getString(cursor.getColumnIndex("sort")),
                    cursor.getString(cursor.getColumnIndex("releasetime")),
                    cursor.getString(cursor.getColumnIndex("writer")));
            list.add(recipes);
        }
        return list;
    }

    public List<Recipes> getByWriterId(String id) {
        List<Recipes> list = new ArrayList<Recipes>();
        Recipes recipes;
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes where writer=?", new String[]{id});
        while (cursor.moveToNext()) {
            recipes = new Recipes(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("icon_addr")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("ingredient")),
                    cursor.getString(cursor.getColumnIndex("progress")),
                    cursor.getString(cursor.getColumnIndex("tips")),
                    cursor.getString(cursor.getColumnIndex("sort")),
                    cursor.getString(cursor.getColumnIndex("releasetime")),
                    cursor.getString(cursor.getColumnIndex("writer")));
            list.add(recipes);
        }
        return list;
    }


}
