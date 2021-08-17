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

import com.gdu.myapplicationac103.R;

import java.util.Arrays;
import java.util.Collections;

/**
 * Description:
 * Created by ZhDu on 2021/4/13 10:29.
 */
public class HistogramView extends View {
    private static final String TAG = "HistogramView";
    Paint paint1, paint2, paint3;
    Float[] floats = null;
    float viewWidth;
    int moveIndex = -1;
    float moveY = 0;
    float spac;
    float max;
    private Context context;

    public HistogramView(Context context) {
        super(context);
        init(context);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void addData(Float[] floats) {
        this.floats = floats;

        postInvalidate();
    }

    private void init(Context context) {
        this.context = context;
        paint1 = new Paint();
        paint1.setColor(getResources().getColor(R.color.bgColor));
        paint1.setStrokeWidth(3);
        paint1.setAntiAlias(true);
        paint1.setDither(true);
        paint1.setStyle(Paint.Style.FILL);

        paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        paint2.setStrokeWidth(3);
        paint2.setAntiAlias(true);
        paint2.setDither(true);
        paint2.setStyle(Paint.Style.FILL);

        paint3 = new Paint();
        paint3.setColor(Color.RED);
        paint3.setStrokeWidth(3);
        paint3.setAntiAlias(true);
        paint3.setDither(true);
        paint3.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (floats != null) {
            int w = getWidth(), h = getHeight();
            int maxItem = floats.length;
            max = Collections.max(Arrays.asList(floats));
            //设置view宽度的10%平均分配后的间隔
            spac = (w / 10) / (maxItem - 1);
            //设置view宽度的90%平均分配后的每个item的宽度
            viewWidth = (getWidth() - (spac * (maxItem - 1))) / maxItem;
            canvas.drawRGB(100, 149, 237);
            float startX = 0, startY = 0;
            for (int i = 0; i < maxItem; i++) {
                //每个item的起始位置
                float startW = getItemStartW(i);
                canvas.drawRect(new RectF(startW, 0, (startW + viewWidth), h), paint1);
                float itemh = getItemHeight(i, max);
                canvas.drawRect(new RectF(startW, itemh, (startW + viewWidth), h), paint2);
                //折线图模式
                //if (startX != 0) {
                //    canvas.drawLine(startX, startY, (startW + (startW + viewWidth)) / 2, itemh, paint3);
                //}
                //startX = (startW + (startW + viewWidth)) / 2;
                //startY = itemh;
            }
        }
    }

    private float getItemStartW(int index) {
        return viewWidth * index + (index == 0 ? 0 : spac * index);
    }

    private float getItemHeight(int index, float max) {
        return getHeight() - (getHeight() * (floats[index] / max));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (floats != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float xx = event.getX();
                    for (int i = 0; i < floats.length; i++) {
                        if (getItemStartW(i) < xx && (getItemStartW(i) + viewWidth) > xx) {
                            moveIndex = i;
                            break;
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveY = event.getY();
                    if (moveY > 0) {

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    moveIndex = -1;
                    break;
            }
            postInvalidate();
        }
        return true;
    }
}
