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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by ZhDu on 2021/3/31 17:50.
 */
public class LinesView extends View {

    Paint mPaint_Line;

    List<Float[]> floats = new ArrayList<>();
    Map<Long, Float[]> map = new HashMap<>();
    float x = 0, y = 0, mx = 0, my = 0, dx = 0, dy = 0;
    long timeKey = 0;
    int w = getWidth(), h = getHeight();

    public LinesView(Context context) {
        super(context);
        init();
    }

    public LinesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint_Line = new Paint();
        mPaint_Line.setColor(Color.argb(255, 227, 224, 59));
        mPaint_Line.setStrokeWidth(3);
        mPaint_Line.setAntiAlias(true);
        mPaint_Line.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = getWidth();
        h = getHeight();
        //canvas.drawLine(0, 0, w, 0, mPaint_Line);
        //canvas.drawLine(0, h, w, h, mPaint_Line);
        //canvas.drawLine(0, 0, 0, h, mPaint_Line);
        //canvas.drawLine(w, 0, w, h, mPaint_Line);
        RectF rectF = new RectF(0, 0, w, h);
        mPaint_Line.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF, 0, 0, mPaint_Line);
        canvas.drawLine(0, (float) (h * 0.8), w, (float) (h * 0.8), mPaint_Line);
        for (long key : map.keySet()) {
            Float[] f = map.get(key);
            canvas.drawLine(f[0], f[1], f[4], f[5], mPaint_Line);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                timeKey = System.currentTimeMillis();
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mx = event.getX();
                my = event.getY();
                map.put(timeKey, new Float[]{x, y, dx, dy, mx, my});
                this.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                dx = event.getX();
                dy = event.getY();
                floats.add(new Float[]{x, y, dx, dy, mx, my});
                map.put(timeKey, new Float[]{x, y, dx, dy, mx, my});
                if ((h * 0.8) < dy) {
                    map.remove(timeKey);
                }
                this.postInvalidate();
                break;
        }

        return true;
    }
}
