package com.example.recipes.Recipes;

import java.io.Serializable;

/**
 * Created by Anleeno on 2018/7/9.
 */

public class Recipes implements Serializable {
    public int rid;
    public String icon_addr;
    public String name;
    public String ingredient;
    public String progress;
    public String tips;
    public String sort;
    public String releasetime;
    public String writer;
    public int score;

    public Recipes() {
    }

    public Recipes(int rid, String icon_addr, String name, String ingredient, String progress, String tips, String sort, String releasetime, String writer) {
        this.rid = rid;
        this.icon_addr = icon_addr;
        this.name = name;
        this.ingredient = ingredient;
        this.progress = progress;
        this.tips = tips;
        this.sort = sort;
        this.releasetime = releasetime;
        this.writer = writer;
        this.score = 0;
    }

    public Recipes(String icon_addr, String name, String ingredient, String progress, String tips, String sort, String releasetime, String writer) {
        this.icon_addr = icon_addr;
        this.name = name;
        this.ingredient = ingredient;
        this.progress = progress;
        this.tips = tips;
        this.sort = sort;
        this.releasetime = releasetime;
        this.writer = writer;
        this.score = 0;
    }

    public String getIcon_addr() {
        return icon_addr;
    }

    public void setIcon_addr(String icon_addr) {
        this.icon_addr = icon_addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }
}
