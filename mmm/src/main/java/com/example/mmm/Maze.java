package com.example.mmm;

import java.util.Random;

/**
 * Created by xjh on 2018/9/19.
 */

public class Maze {
    private final int up = 0, down = 1, left = 2, right = 3;
    private final int dirX[] = new int[]{0, 0, -1, 1};
    private final int dirY[] = new int[]{-1, 1, 0, 0};
    private final int wall = 1, path = 0, Visit = 2, Visited = 3;
    private int sizeX, sizeY;
    private MazePoint[][] mazePoints;
    private int[][] maze;   //值为 1 是墙，为 0 是路

    private int[][] visit;
    //    private Stack stackX, stackY;

    public Maze(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        mazePoints = new MazePoint[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                mazePoints[i][j] = new MazePoint();
            }
        }
        this.maze = new int[sizeX * 2 + 1][sizeY * 2 + 1];
        for (int i = 0; i < sizeX * 2 + 1; i++) {
            for (int j = 0; j < sizeY * 2 + 1; j++) {
                if (i % 2 == 1 && j % 2 == 1) {
                    maze[i][j] = path;
                } else {
                    maze[i][j] = wall;
                }
                //                maze[i][j] = wall;
            }
        }
    }

    public int[][] createMaze() {
        createMaze(0, 0);
        return maze;
    }

    private void createMaze(int x, int y) {
        mazePoints[x][y].setVisited(true);
        //        maze[x * 2 + 1][y * 2 + 1] = path;
        Random random = new Random();
        int dir = random.nextInt(3) + 1;
        int nextX;
        int nextY;

        if (isDead(x, y)) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            dir = (dir + i) % 4;
            nextX = x + dirX[dir];
            nextY = y + dirY[dir];
            if (check(nextX, nextY)) {
                switch (dir) {
                    case up:
                        mazePoints[x][y].setUpWall(false);
                        mazePoints[x][y - 1].setDownWall(false);
                        maze[x * 2 + 1][y * 2] = path;
                        break;
                    case down:
                        mazePoints[x][y].setDownWall(false);
                        mazePoints[x][y + 1].setUpWall(false);
                        maze[x * 2 + 1][y * 2 + 2] = path;
                        break;
                    case left:
                        mazePoints[x][y].setLeftWall(false);
                        mazePoints[x - 1][y].setRightWall(false);
                        maze[x * 2][y * 2 + 1] = path;
                        break;
                    case right:
                        mazePoints[x][y].setRightWall(false);
                        mazePoints[x + 1][y].setLeftWall(false);
                        maze[x * 2 + 2][y * 2 + 1] = path;
                        break;
                }
                createMaze(nextX, nextY);
            }
        }
    }

    private boolean check(int x, int y) {
        if (x < 0 || y < 0 || x >= sizeX || y >= sizeY) {
            return false;
        }
        MazePoint mazePoint = mazePoints[x][y];
        if (mazePoint.isVisited()) {
            return false;
        }
        return true;
    }

    private boolean isDead(int x, int y) {
        boolean result = true;
        for (int dir = 0; dir < 4; dir++) {
            int nextX = x + dirX[dir];
            int nextY = y + dirY[dir];
            if (check(nextX, nextY)) {
                result = false;
            }
        }
        return result;
    }

    public int[][] getVisit() {
        visit = new int[sizeX * 2 + 1][sizeY * 2 + 1];
        for (int i = 0; i < sizeX * 2 + 1; i++) {
            for (int j = 0; j < sizeY * 2 + 1; j++) {
                visit[i][j] = maze[i][j];
            }
        }

        findPath(1, 1);

        return visit;
    }

    private boolean findPath(int x, int y) {
        //        stackX.push(x);
        //        stackY.push(y);
        visit[x][y] = Visit;

        if (x == sizeX * 2 - 1 && y == sizeY * 2 - 1) {//到达终点
            return true;
        }

        Random random = new Random();
        int dir = random.nextInt(3) + 1;
        int nextX;
        int nextY;

        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            dir = (dir + i) % 4;
            nextX = x + dirX[dir];
            nextY = y + dirY[dir];
            if (checkNext(nextX, nextY)) {
                flag = flag || findPath(nextX, nextY);
            }
        }
        if (!flag) {
            //            stackX.pop();
            //            stackY.pop();
            visit[x][y] = Visited;
        }
        return flag;
        //        if (!checkNow(x, y)) {
        //            visit[x][y] = Visited;
        //        }
    }



    private boolean checkNext(int x, int y) {
        if (x < 0 || y < 0 || x >= sizeX * 2 + 1 || y >= sizeY * 2 + 1) {
            return false;
        }
        if (visit[x][y] != path) {
            return false;
        }
        return true;
    }

    //    public void printMaze() {
    //        for (int i = 0; i < sizeX * 2 + 1; i++) {
    //            for (int j = 0; j < sizeY * 2 + 1; j++) {
    //                if (maze[i][j] == wall) {
    //                    System.out.printf("[]");
    //                } else {
    //                    System.out.printf("  ");
    //                }
    //            }
    //            System.out.println();
    //        }
    //    }
}
