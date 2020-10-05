package com.example.recipes.User.My;

/**
 * Created by xjh on 2018/7/21.
 */

public class Collect {
    int id, recipes_id;
    String user_id;

    public void setRecipes_id(int recipes_id) {
        this.recipes_id = recipes_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public int getRecipes_id() {
        return recipes_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Collect(int recipes_id, String user_id) {
        this.recipes_id = recipes_id;
        this.user_id = user_id;
    }

    public Collect(int id, int recipes_id, String user_id) {
        this.id = id;
        this.recipes_id = recipes_id;
        this.user_id = user_id;
    }
}
