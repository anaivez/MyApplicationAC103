package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:
 * Created by ZhDu on 2021/4/14 10:20.
 */
public class LongView extends View {
    private static final String TAG = "LongView";
    Paint redPaint, bluePaint, greenPaint;
    float x = 0;
    float downX = 0f;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            postInvalidate();
        }


    };
    Paint dpaint;

    public LongView(Context context) {
        super(context);
        init();

    }

    public LongView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL);

        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.FILL);

        dpaint = new Paint();
        dpaint.setColor(Color.BLACK);
        dpaint.setStyle(Paint.Style.STROKE);
    }
    float viewHeight ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
          viewHeight = (float) (getHeight() * 0.55);
        RectF redRF = new RectF(0, x, getWidth(), x + viewHeight);
        canvas.drawRect(redRF, redPaint);
        RectF blueRF = new RectF(0, x + viewHeight * 1, getWidth(), x + viewHeight * 2);
        canvas.drawRect(blueRF, bluePaint);
        RectF greedRF = new RectF(0, x + viewHeight * 2, getWidth(), x + viewHeight * 3);
        canvas.drawRect(greedRF, greenPaint);
        canvas.drawLine(0, x, getWidth(),x + viewHeight * 3, dpaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = 0;
                downX = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x = (event.getY() - downX);
                Log.d(TAG, "onTouchEvent: " + x);
                break;
            case MotionEvent.ACTION_UP:
                if (x > 0) {
                    x = 0;
                }
                Log.d(TAG, "now index is "+(x>viewHeight*3?"gone":x/viewHeight));
                break;
        }
        postInvalidate();
        return true;
    }
}
