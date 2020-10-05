package com.example.recipes.Shouye;

/**
 * Created by xjh on 2018/7/16.
 */

public class myRecipes {
    private int img;
    private String name,writer,score;

    public myRecipes(int img, String name, String writer, String score) {
        this.img = img;
        this.name = name;
        this.writer = writer;
        this.score = score;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getWriter() {
        return writer;
    }

    public String getScore() {
        return score;
    }
}
