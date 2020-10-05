package com.example.naruto.Table;

/**
 * Created by xjh on 2018/8/24.
 */

public class MijuanUpgrade {
    private String name, upgrade_add;
    private int cost;
    private int level;


    public MijuanUpgrade() {
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUpgrade_add(String upgrade_add) {
        this.upgrade_add = upgrade_add;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getUpgrade_add() {
        return upgrade_add;
    }

    public int getCost() {
        return cost;
    }

    public MijuanUpgrade(String name, int level, String upgrade_add, int cost) {
        this.name = name;
        this.level = level;
        this.upgrade_add = upgrade_add;
        this.cost = cost;
    }
}
