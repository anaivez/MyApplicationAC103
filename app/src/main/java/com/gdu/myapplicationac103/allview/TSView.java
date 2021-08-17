package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by ZhDu on 2021/4/1 9:08.
 */
public class TSView extends View {
    List<Float[]> floats = new ArrayList<>();
    float x = 0, y = 0, mx = 0, my = 0, dx = 0, dy = 0;
    Paint godPaint;

    public TSView(Context context) {
        super(context);
        init();
    }

    public TSView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        godPaint = new Paint();
        godPaint.setColor(Color.argb(255, 227, 224, 59));
        godPaint.setStrokeWidth(3);
        godPaint.setAntiAlias(true);
        godPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 139, 197, 186);
        for (Float[] f : floats) {
            canvas.drawPoint(f[2], f[3], godPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
            case MotionEvent.ACTION_MOVE:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                dx = event.getX();
                dy = event.getY();
                floats.add(new Float[]{x, y, dx, dy,mx,my});
                break;
        }
        this.postInvalidate();
        return true;
    }
}
