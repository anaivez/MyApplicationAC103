package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by Czm on 2021/5/20 15:29.
 */
public class TwistedView extends View {

    private static final String TAG = "TwistedView";
    Paint cardPaint;
    int w, h;
    float btnH = 35f;
    int i = 0;
    boolean stop = false;
    long begin;
    private Handler mHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            if (i == 0) {
                cardPaint.setColor(Color.BLUE);
                //Log.d(TAG, "run: BLUE");
            } else if (i == 1) {
                cardPaint.setColor(Color.RED);
                //Log.d(TAG, "run: RED");
            } else {
                begin = begin != 0 ? begin : System.currentTimeMillis();
                cardPaint.setColor(Color.YELLOW);
                //Log.d(TAG, "run: GOLD");
            }
            i++;
            if (i == 3) {
                i = 0;
            }
            if (!stop) {
                postInvalidate();
            }
            countTimer();
        }
    };

    public TwistedView(Context context) {
        super(context);
        init();
    }

    public TwistedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        cardPaint = new Paint();
        cardPaint.setColor(Color.BLUE);
        //countTimer();
        CountDownTimer countDownTimer = new CountDownTimer(1000 * 60 * 60, 750) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (i == 0) {
                    cardPaint.setColor(Color.BLUE);
                    //Log.d(TAG, "run: BLUE");
                } else if (i == 1) {
                    cardPaint.setColor(Color.RED);
                    //Log.d(TAG, "run: RED");
                } else {
                    begin = begin != 0 ? begin : System.currentTimeMillis();
                    cardPaint.setColor(Color.YELLOW);
                    //Log.d(TAG, "run: GOLD");
                }
                i++;
                if (i == 3) {
                    i = 0;
                }
                if (!stop) {
                    postInvalidate();
                }
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = getWidth();
        h = getHeight();
        btnH = Math.min(35f, (float) (h * 0.2));
        canvas.drawRGB(255, 255, 255);
        onDrawCard(canvas);
    }


    private void onDrawCard(Canvas canvas) {
        RectF rectF = new RectF(12, 12, w - 12, h - 12);
        canvas.drawRoundRect(rectF, 20, 20, cardPaint);
    }

    private void countTimer() {
        mHandler.postDelayed(timerRunnable, 750);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stop = !stop;
                if (stop) {
                    Log.d("", "onTouchEvent: " + (System.currentTimeMillis() - begin));
                    begin = 0;
                }
                break;
            default:
                break;
        }
        postInvalidate();
        return true;
    }
}
