package com.example.naruto.Table;

import java.io.Serializable;

/**
 * Created by xjh on 2018/8/24.
 */

public class Ninjia implements Serializable{
    private String name, icon, icon_big, skill_one_details, skill_one_icon, skill_two_details, skill_two_icon, skill_big_details, skill_big_icon, end_icon, level;
    private int tall, weight, Max_fragments, fragments, grade;
    private boolean is_have;

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {

        return grade;
    }

    public void setFragments(int fragments) {
        this.fragments = fragments;
    }

    public int getFragments() {
        return fragments;
    }

    public void setIcon_big(String icon_big) {
        this.icon_big = icon_big;
    }

    public String getIcon_big() {

        return icon_big;
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

    public void setSkill_one_details(String skill_one_details) {
        this.skill_one_details = skill_one_details;
    }

    public void setSkill_one_icon(String skill_one_icon) {
        this.skill_one_icon = skill_one_icon;
    }

    public void setSkill_two_details(String skill_two_details) {
        this.skill_two_details = skill_two_details;
    }

    public void setSkill_two_icon(String skill_two_icon) {
        this.skill_two_icon = skill_two_icon;
    }

    public void setSkill_big_details(String skill_big_details) {
        this.skill_big_details = skill_big_details;
    }

    public void setSkill_big_icon(String skill_big_icon) {
        this.skill_big_icon = skill_big_icon;
    }

    public void setEnd_icon(String end_icon) {
        this.end_icon = end_icon;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTall(int tall) {
        this.tall = tall;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getSkill_one_details() {
        return skill_one_details;
    }

    public String getSkill_one_icon() {
        return skill_one_icon;
    }

    public String getSkill_two_details() {
        return skill_two_details;
    }

    public String getSkill_two_icon() {
        return skill_two_icon;
    }

    public String getSkill_big_details() {
        return skill_big_details;
    }

    public String getSkill_big_icon() {
        return skill_big_icon;
    }

    public String getEnd_icon() {
        return end_icon;
    }

    public String getLevel() {
        return level;
    }

    public int getTall() {
        return tall;
    }

    public int getWeight() {
        return weight;
    }

    public void setMax_fragments(int max_fragments) {
        Max_fragments = max_fragments;
    }

    public int getMax_fragments() {
        return Max_fragments;
    }

    public void setIs_have(boolean is_have) {
        this.is_have = is_have;
    }

    public boolean isIs_have() {

        return is_have;
    }

    public Ninjia(String name, String icon, String icon_big, String skill_one_details, String skill_one_icon, String skill_two_details, String skill_two_icon, String skill_big_details, String skill_big_icon, String end_icon, String level, int tall, int weight) {
        this.name = name;
        this.icon = icon;
        this.skill_one_details = skill_one_details;
        this.skill_one_icon = skill_one_icon;
        this.skill_two_details = skill_two_details;
        this.skill_two_icon = skill_two_icon;
        this.skill_big_details = skill_big_details;
        this.skill_big_icon = skill_big_icon;
        this.end_icon = end_icon;
        this.level = level;
        this.tall = tall;
        this.weight = weight;

        this.icon_big = icon_big;
        is_have = false;
        fragments = 0;

        switch (level) {
            case "S":
                Max_fragments = 100;
                grade = 3;
                break;
            case "A":
            case "B":
                grade = 2;
                Max_fragments = 40;
                break;
            case "C":
                grade = 1;
                Max_fragments = 10;
                break;
        }
    }

    public Ninjia(String name, String icon, String icon_big, String skill_one_details, String skill_one_icon, String skill_two_details, String skill_two_icon, String skill_big_details, String skill_big_icon, String end_icon, String level, int tall, int weight, int max_fragments, int fragments, int grade, boolean is_have) {
        this.name = name;
        this.icon = icon;
        this.icon_big = icon_big;
        this.skill_one_details = skill_one_details;
        this.skill_one_icon = skill_one_icon;
        this.skill_two_details = skill_two_details;
        this.skill_two_icon = skill_two_icon;
        this.skill_big_details = skill_big_details;
        this.skill_big_icon = skill_big_icon;
        this.end_icon = end_icon;
        this.level = level;
        this.tall = tall;
        this.weight = weight;
        Max_fragments = max_fragments;
        this.fragments = fragments;
        this.grade = grade;
        this.is_have = is_have;
    }
}
