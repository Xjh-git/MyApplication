package com.example.naruto;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by xjh on 2018/9/3.
 */

public class MyProgressBar extends ProgressBar {
    String text;
    Paint mPaint;

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        setText(progress);
        super.setProgress(progress);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //this.setText();
        Rect rect = new Rect();
        this.mPaint.setTextSize(50);
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();// 让显示的字体处于中心位置;;
        int y = (getHeight() / 2) - rect.centerY();// 让显示的字体处于中心位置;;
        canvas.drawText(this.text, x, y, this.mPaint);

    }

    //初始化，画笔
    private void initText() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);// 设置抗锯齿;;;;
        this.mPaint.setColor(Color.WHITE);

    }

    private void setText() {
        setText(this.getProgress());
    }

    //设置文字内容
    private void setText(int progress) {
        this.text = progress + "/" + this.getMax();
    }
}
