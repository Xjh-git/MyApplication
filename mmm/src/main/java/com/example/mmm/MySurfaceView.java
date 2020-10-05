package com.example.mmm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by xjh on 2018/9/19.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback  {
    private SurfaceHolder mHolder;    // 用于控制SurfaceView
    private Canvas mCanvas;    // 声明一张画布
    private Paint wallPaint;    //画墙的画笔
    private Paint pathPaint;    //画路的画笔
    private Paint visitPaint;    //画路径的画笔
    private Paint visitedPaint;    //画已访问但是是死路的画笔

    private Thread thread;    //线程

    Maze Maze;    //地图
    //    int[][] maze;
    int[][] visit;  //
    int rectWidth = 50, rectHight = 50;    //矩形的宽和高
    int sleepTime = 500;    //线程休息时间
    float top = 100, left = 50;    //地图左上角坐标

    private final int sizeX = 5, sizeY = 5;    //地图大小为sizeX*2+1,sizeY*2+1

    private void init() {
        mHolder = getHolder();    // 获得SurfaceHolder对象
        mHolder.addCallback(this);    // 为SurfaceView添加状态监听

        wallPaint = new Paint();
        wallPaint.setColor(0xFF71727b);    //设置强的颜色为71727b
        pathPaint = new Paint();
        pathPaint.setColor(Color.WHITE);
        visitPaint = new Paint();
        visitPaint.setColor(Color.GREEN);
        visitedPaint = new Paint();
        visitedPaint.setColor(Color.GRAY);

        setFocusable(true);    // 设置焦点

        Maze = new Maze(sizeX, sizeY);
        visit = Maze.createMaze();
        //        maze = Maze.createMaze();

        //        visit = new int[sizeX * 2 + 1][sizeY * 2 + 1];
        //        for (int i = 0; i < sizeX * 2 + 1; i++) {
        //            for (int j = 0; j < sizeY * 2 + 1; j++) {
        //                visit[i][j] = maze[i][j];
        //            }
        //        }
    }


    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    /**
     * 当SurfaceView创建的时候，调用此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(new MazeThread());    // 创建一个线程对象
        //        isRun = true;    // 把线程运行的标识设置成true
        thread.start();    // 启动线程
    }

    /**
     * 当SurfaceView的视图发生改变的时候，调用此函数
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * 当SurfaceView销毁的时候，调用此函数
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //        isRun = false;
    }

    private void Draw() {
        mCanvas = mHolder.lockCanvas();    // 获得画布对象，开始对画布画画
        mCanvas.drawRGB(255, 255, 255);    // 把画布填充为白色

        //        for (int i = 0; i < maze[0].length; i++) {
        //            for (int j = 0; j < maze.length; j++) {
        //                if (maze[i][j] == 1) {
        //                    mCanvas.drawRect(i * rectWidth, j * rectHight, (i + 1) * rectWidth, (j + 1) * rectHight, wallPaint);
        //                }
        //                if (maze[i][j] == 0) {
        //                    mCanvas.drawRect(i * rectWidth, j * rectHight, (i + 1) * rectWidth, (j + 1) * rectHight, pathPaint);
        //                }
        //
        //            }
        //        }

        //        visit = Maze.getVisit();

        for (int i = 0; i < visit[0].length; i++) {
            for (int j = 0; j < visit.length; j++) {
                if (visit[i][j] == 0) {
                    mCanvas.drawRect(left + i * rectWidth, top + j * rectHight, left + (i + 1) * rectWidth, top + (j + 1) * rectHight, pathPaint);
                }
                if (visit[i][j] == 1) {
                    mCanvas.drawRect(left + i * rectWidth, top + j * rectHight, left + (i + 1) * rectWidth, top + (j + 1) * rectHight, wallPaint);
                }
                if (visit[i][j] == 2) {
                    mCanvas.drawCircle(left + i * rectWidth + rectWidth / 2, top + j * rectHight + rectHight / 2, rectHight / 2, visitPaint);
                }
                if (visit[i][j] == 3) {
                    mCanvas.drawCircle(left + i * rectWidth + rectWidth / 2, top + j * rectHight + rectHight / 2, rectHight / 2, visitedPaint);
                }
            }
        }

        mHolder.unlockCanvasAndPost(mCanvas);    // 完成画画，把画布显示在屏幕上
    }

    private void findPath() {
        findPath(1, 1);
    }

    private final int dirX[] = new int[]{0, 0, -1, 1};
    private final int dirY[] = new int[]{-1, 1, 0, 0};
    private final int path = 0, Visit = 2, Visited = 3;

    private boolean findPath(int x, int y) {

        visit[x][y] = Visit;

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Draw();


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
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            visit[x][y] = Visited;
            Draw();
        }

        return flag;


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

    class MazeThread implements Runnable {

        @Override
        public void run() {
            Draw();    // 调用自定义画画方法
            findPath();//开始寻路
        }
    }
}
