package com.example.recipes.User;

/**
 * Created by xjh on 2018/7/11.
 */

public class User {
    private String password, sex, name, phonenum, id, photo_uri;

    public User() {
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public User(String id, String password, String name, String sex, String phonenum, String photo_uri) {
        this.id = id;
        this.password = password;
        this.sex = sex;
        this.name = name;
        this.phonenum = phonenum;
        this.photo_uri = photo_uri;

    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }


    public String getName() {
        return name;
    }


}
