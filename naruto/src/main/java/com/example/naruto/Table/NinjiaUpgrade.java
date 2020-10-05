package com.example.naruto.Table;

/**
 * Created by xjh on 2018/8/24.
 */

public class NinjiaUpgrade {
    private String name;
    private int level, cost;

    public NinjiaUpgrade(String name, int level, int cost) {
        this.name = name;
        this.level = level;
        this.cost = cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
