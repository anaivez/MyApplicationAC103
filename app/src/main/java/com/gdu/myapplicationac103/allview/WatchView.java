package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by ZhDu on 2021/4/29 13:59.
 */
public class WatchView extends View {

    private static final String TAG = "WatchView";


    Paint scalePaint, pointerPaint;
    float w, h, r;

    public WatchView(Context context) {
        super(context);
        init();
    }

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);
        scalePaint.setColor(Color.WHITE);
        scalePaint.setStrokeCap(Paint.Cap.ROUND);
        scalePaint.setDither(true);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setStrokeWidth(2f);

        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = getWidth();
        h = getHeight();
        r = Math.min(w, h);
        Log.d(TAG, "onDraw: " + w + " h" + h);
        RectF rectF = new RectF(2f, 2f, (r) - 2, (r) - 2);
        canvas.drawArc(rectF, 135, 270, false, scalePaint);
        canvas.save();
        canvas.rotate(15f, getWidth() / 2, getHeight() / 2);
        Paint.FontMetrics fm = scalePaint.getFontMetrics();
        for (int i = 0; i < 10; i++) {
            canvas.rotate(30f, getWidth() / 2, getHeight() / 2);
            canvas.drawLine(getWidth() / 2, getHeight() - 3, getWidth() / 2, getHeight() - 20, scalePaint);
            canvas.drawText("" + i * 20, getWidth() / 2 , getHeight() - 20, scalePaint);
        }
        canvas.restore();
        canvas.save();
        scalePaint.setTextSize(16);
        scalePaint.setTypeface(Typeface.DEFAULT);
        canvas.rotate(45f, getWidth() / 2, getHeight() / 2);
        for (int i = 0; i < 18; i++) {
            canvas.rotate(15f, getWidth() / 2, getHeight() / 2);
            canvas.drawLine(getWidth() / 2, getHeight() - 3, getWidth() / 2, getHeight() - 10, scalePaint);

        }
        canvas.restore();
    }


}