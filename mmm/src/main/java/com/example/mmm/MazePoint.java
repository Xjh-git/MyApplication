package com.example.mmm;

/**
 * Created by xjh on 2018/9/19.
 */

public class MazePoint {
    private boolean isVisited;
    private boolean upWall, downWall, leftWall, rightWall;

    public MazePoint() {
        this.isVisited = false;
        this.upWall = true;
        this.downWall = true;
        this.leftWall = true;
        this.rightWall = true;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setUpWall(boolean upWall) {
        this.upWall = upWall;
    }

    public void setDownWall(boolean downWall) {
        this.downWall = downWall;
    }

    public void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    public void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    public boolean isUpWall() {
        return upWall;
    }

    public boolean isDownWall() {
        return downWall;
    }

    public boolean isLeftWall() {
        return leftWall;
    }

    public boolean isRightWall() {
        return rightWall;
    }
}
