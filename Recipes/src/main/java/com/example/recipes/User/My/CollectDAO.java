package com.example.recipes.User.My;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recipes.Recipes.Recipes;
import com.example.recipes.Recipes.RecipesDAO;
import com.example.recipes.User.User;
import com.example.recipes.User.UserDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjh on 2018/7/21.
 */

public class CollectDAO {
    private CollectDataBase helper;

    private SQLiteDatabase db;

    private RecipesDAO recipesDAO;

    public CollectDAO(Context context) {
        recipesDAO = new RecipesDAO(context);
        helper = new CollectDataBase(context);

    }

    //添加用户信息
    public void add(Collect collect) {

        db = helper.getWritableDatabase();

        String sql = "INSERT INTO collect VALUES (null, ?, ?)";
        db.execSQL(sql, new Object[]{collect.getRecipes_id(), collect.getUser_id()});
        db.close();
    }

    //更新用户信息
    public void update(Collect collect) {

        db = helper.getWritableDatabase();
        //更新数据，id值不能修改
        db.execSQL("Update collect set recipes_id=?, user_id=? where id=?", new Object[]{
                collect.getRecipes_id(), collect.getUser_id(), collect.getId()});
        db.close();
    }

    public void delete(Collect collect) {

        db = helper.getWritableDatabase();
        //更新数据，id值不能修改
        db.execSQL("Delete from collect where id=?", new Object[]{collect.getId()});
        db.close();
    }

    public List<Recipes> get(String user_id) {
        List<Recipes> list = new ArrayList<Recipes>();

        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM collect where user_id=?", new String[]{user_id});

        while (cursor.moveToNext()) {
            Recipes recipes=recipesDAO.find(cursor.getInt(cursor.getColumnIndex("recipes_id")));
            list.add(recipes);
        }
        return list;
    }

    public Collect find(String user_id,int recipes_id){
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM collect where user_id=? and recipes_id=?", new String[]{user_id,String.valueOf(recipes_id)});
        if (cursor.moveToNext()){
            return new Collect(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getInt(cursor.getColumnIndex("recipes_id")),
                    cursor.getString(cursor.getColumnIndex("user_id")));
        }
        return null;
    }

}
