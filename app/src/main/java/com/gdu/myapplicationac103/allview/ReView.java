package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.gdu.myapplicationac103.R;

/**
 * Description:
 * Created by ZhDu on 2021/4/7 10:17.
 */
public class ReView extends View {

    private static final String TAG = "ReView";
    //圆的角度、view更新间隔(度)
    protected float timer = 360, interval = 0;
    Context context;
    private Paint paint, textPaint;
    private Paint.FontMetrics fm;
    private Handler mHandler = new Handler();
    //计时器时间
    private int second = 0;
    //刷新时间
    private float rate = 100;
    private String timeStr = "";
    private boolean isStopCount = false, isPause = false;
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            if (!isStopCount) {
                timer -= interval;
                postInvalidate();
            }
            countTimer();
        }
    };
    private float startAngle = 270;
    private int colorid = 0;
    private boolean isMS = true;




    public ReView(Context context) {
        super(context);
        init(context);
    }

    public ReView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttrs(attrs);
    }

    public ReView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReViews);
        this.second = typedArray.getInt(R.styleable.ReViews_second, 30);
        this.isMS = typedArray.getBoolean(R.styleable.ReViews_isMMSS, true);
        Log.d(TAG, "initAttrs: s:" + second + "  -" + isMS);
    }

    private void countTimer() {
        if (!isPause) {
            if (timer > 0) {
                mHandler.postDelayed(timerRunnable, (int) rate);
            } else {
                isStopCount = true;
                isPause = true;
            }
        }
    }

    //启动
    public void start() {
        this.isPause = false;
        if (!isStopCount && !isPause) {
            countTimer();
        }
    }

    //停止 复位参数
    public void stop() {
        this.timer = 360;
        this.isPause = true;
        this.isStopCount = false;
        invalidate();
    }

    //暂停
    public void pause() {
        this.isPause = true;
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(36);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fm = textPaint.getFontMetrics();
    }

    private String timeStr() {
        int s = Math.round((timer / interval) * (rate / 1000));
        String mm = "00";
        String str = "00:00";
        if (isMS) {
            if (s >= 60) {
                if ((s / 60) >= 10) {
                    mm = "" + s / 60;
                } else {
                    mm = "0" + s / 60;
                }
                if ((s % 60) < 10) {
                    str = mm + ":0" + (s % 60);
                } else {
                    str = mm + ":" + (s % 60);
                }
            } else {
                if (s < 10) {
                    str = mm + ":0" + (s % 60);
                } else {
                    str = mm + ":" + (s % 60);
                }
            }
        } else {
            str = "" + (s);
        }
        return str;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
        float w = getWidth(), h = getHeight(), r;
        r = Math.min(w, h);
        RectF rectF = new RectF(3f, 3f, (r) - 3, (r) - 3);
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.GREEN);
        canvas.drawArc(rectF, 0, 360, true, bgPaint);
        Log.d(TAG, "onDraw: " + timer);
        if (false) {
            canvas.drawArc(rectF, startAngle, timer, true, paint);
        } else {
            canvas.drawArc(rectF, startAngle, timer, true, paint);
        }
        canvas.drawText(timeStr(), r / 2, (r / 2) + getTextHeight(fm) / 2, textPaint);
    }

    public String getTime() {
        return timeStr();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
        getDefaultSize(160, heightMeasureSpec);
        getDefaultSize(160, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");
    }

    //获取text的高度值
    private float getTextHeight(Paint.FontMetrics fms) {
        float t = Math.abs(fms.top), b = Math.abs(fms.bottom);
        return Math.abs(t - b);
    }


}
