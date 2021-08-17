package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by Czm on 2021/5/18 17:51.
 */
public class SwitchView extends View {



    private static final String TAG = "SwitchView";
    int radius;
    boolean isDown = false, isDownDot = false, isMove = false;
    private Paint bgPaint, dotPaint;
    private int w, h;
    private int cx = 0, offX, onX, dotY;
    private float dx, dy, moveX;
    private int padding = 10;

    public SwitchView(Context context) {
        super(context);
        init();
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setColor(Color.GRAY);
        bgPaint.setAntiAlias(true);


        dotPaint = new Paint();
        dotPaint.setColor(Color.GREEN);
        dotPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = getWidth();
        h = getHeight();
        canvas.drawRGB(255, 255, 255);
        drawGroove(canvas);
        drawDot(canvas);
    }

    private void drawDot(Canvas canvas) {
        //计算得出半径后减去间距10
        radius = (Math.min(w, h)) / 2 - padding;
        offX = radius + padding;
        if (cx == 0) {
            cx = offX;
            moveX = cx;
        }
        onX = w - (radius + padding);
        dotY = radius + padding;
        if (isDownDot) {
            dotPaint.setColor(moveX == onX ? Color.RED : Color.GREEN);
            canvas.drawCircle(moveX, radius + padding, radius, dotPaint);
        } else {
            moveX = cx;
            dotPaint.setColor(cx == onX ? Color.RED : Color.GREEN);
            canvas.drawCircle(cx, radius + padding, radius, dotPaint);
        }
    }

    private void drawGroove(Canvas canvas) {
        RectF rectFforR = new RectF(w - radius * 2 - padding, padding, w - padding, h - padding);
        RectF rectFforL = new RectF(padding, 10, padding + radius * 2, h - padding);
        RectF rectFforCent = new RectF(padding + radius, padding, w - padding - radius, h - padding);
        canvas.drawArc(rectFforR, 270, 180, true, bgPaint);
        canvas.drawArc(rectFforL, 270, -180, true, bgPaint);
        canvas.drawRect(rectFforCent, bgPaint);
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                dx = event.getX();
                dy = event.getY();
                if (cx == offX) {
                    isDownDot = (dx >= padding && dx <= (padding + radius * 2));
                } else {
                    isDownDot = (dx >= (w - padding - radius * 2));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDownDot) {
                    moveX = cx - (dx - event.getX());
                    if (moveX < offX) {
                        moveX = offX;
                    }
                    if (moveX > onX) {
                        moveX = onX;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isDownDot = false;
                isDown = false;
                isMove = false;
                if (dx == (event.getX())) {
                    if (cx == offX) {
                        cx = onX;
                    } else {
                        cx = offX;
                    }
                } else {
                    if (!isDownDot) {
                        if (moveX > w / 2) {
                            cx = onX;
                        } else {
                            cx = offX;
                        }
                        moveX = cx;
                    }
                }

                break;
        }
        this.postInvalidate();
        return true;
    }
}
