package com.example.myapplication;

import java.io.Serializable;

/**
 * Created by xjh on 2018/4/16.
 */

public class User implements Serializable {
    private String username;
    private String sex;
    private String photo_uri;

    public User() {
    }

    public User(String username, String sex, String photo_uri) {
        this.username = username;
        this.sex = sex;
        this.photo_uri = photo_uri;
    }

    public String getUsername() {
        return username;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }
}
