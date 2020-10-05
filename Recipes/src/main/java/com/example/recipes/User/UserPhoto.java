package com.example.recipes.User;

/**
 * Created by xjh on 2018/7/17.
 */

public class UserPhoto {
    private String id;
    private byte[] img;

    public UserPhoto(String id, byte[] img) {
        this.id = id;
        this.img = img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getId() {

        return id;
    }

    public byte[] getImg() {
        return img;
    }
}
