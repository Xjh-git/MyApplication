package com.example.recipes.works;

/**
 * Created by xjh on 2018/7/19.
 */

public class work {
    private int id, recipes;
    private String img, tags, writer;

    public work(int id, String img, int recipes, String tags, String writer) {
        this.id = id;
        this.img = img;
        this.recipes = recipes;
        this.tags = tags;
        this.writer = writer;
    }

    public work(String img, int recipes, String tags, String writer) {
        this.img = img;
        this.recipes = recipes;
        this.tags = tags;
        this.writer = writer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setRecipes(int recipes) {
        this.recipes = recipes;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public int getRecipes() {
        return recipes;
    }

    public String getTags() {
        return tags;
    }

    public String getWriter() {
        return writer;
    }


}
