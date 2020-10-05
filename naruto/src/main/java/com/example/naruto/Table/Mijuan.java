package com.example.naruto.Table;

import java.io.Serializable;

/**
 * Created by xjh on 2018/8/24.
 */

public class Mijuan implements Serializable {
    private String name, details, effect, icon;
    private int level, max_fragment, fragment;
    private boolean is_have;

    public Mijuan(String name, String details, String effect, String icon, int level, int max_fragment, int fragment, boolean is_have) {
        this.name = name;
        this.details = details;
        this.effect = effect;
        this.icon = icon;
        this.level = level;
        this.max_fragment = max_fragment;
        this.fragment = fragment;
        this.is_have = is_have;
    }

    public Mijuan(String name, String details, String effect, String icon) {
        this.name = name;
        this.details = details;
        this.effect = effect;
        this.icon = icon;
        this.level = 0;
        this.max_fragment = 10;
        is_have = false;
        this.fragment = 0;
    }

    public void setFragment(int fragment) {
        this.fragment = fragment;
    }

    public int getFragment() {

        return fragment;
    }

    public void setIs_have(boolean is_have) {
        this.is_have = is_have;
    }

    public boolean isIs_have() {

        return is_have;
    }

    public void setMax_fragment(int max_fragment) {
        this.max_fragment = max_fragment;
    }

    public int getMax_fragment() {

        return max_fragment;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getLevel() {
        return level;
    }

    public String getEffect() {
        return effect;
    }
}
