package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by Czm on 2021/5/20 17:34.
 */
public class RormuView extends View {

    int tc = 1000 * 5;
    int fps=30;

    private static final String TAG = "RormuView";
    float w = 0;
    float c = 0;
    Paint p;
    int i = 0;


    private Handler mHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            if (tc > 0) {
                mHandler.postDelayed(timerRunnable, fps);
                tc -= fps;
                w += c;
                Log.d(TAG, "run    tc:" + tc + " w:" + w);
                postInvalidate();
            }
        }
    };

    public RormuView(Context context) {
        super(context);
        init();
    }

    public RormuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        p = new Paint();
        p.setColor(Color.GRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        c = ((float) w) / (tc / fps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, 25f, 25f, new Paint());
        RectF rectF2 = new RectF(0, 0, w, getHeight());
        canvas.drawRoundRect(rectF2, 25f, 25f, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mHandler.postDelayed(timerRunnable, fps);
        }
        return true;
    }
}
