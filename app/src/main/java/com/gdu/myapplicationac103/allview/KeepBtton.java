package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description:
 * Created by Czm on 2021/7/16 18:18.
 */
public class KeepBtton extends View {


    private static final String TAG = "Keep";
    Paint paint, paintx;
    boolean isDown = false;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    KeepListener keepListener;
    int speed = 10;
    long dt = 0;
    private float sweep = 0, downTime = 0;
    private int color = Color.GREEN;

    public KeepBtton(Context context) {
        super(context);
        init();
    }

    public KeepBtton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);

        paintx = new Paint();
        paintx.setColor(color);
        paintx.setAntiAlias(true);
        paintx.setDither(true);
        paintx.setFilterBitmap(true);
        paintx.setStyle(Paint.Style.FILL);
        paintx.setStrokeWidth(5f);
        initF();
    }

    private void initF() {
        valueAnimator.setDuration(1000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        //循环次数  -1表示无限循环
        valueAnimator.setRepeatCount(1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                updateParticle((Float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });
    }

    private void updateParticle(Float value) {
        if (downTime < 90) {
            downTime += speed;
        } else {
            if (keepListener != null) {
                if (sweep == 0) {
                    keepListener.onBegin();
                }
                if (sweep == 360) {
                    keepListener.onFinish();
                }
            }
            sweep += 10;
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        float w = getWidth(), h = getHeight(), r;
        r = Math.min(w, h);
        RectF rectF = new RectF(1f, 1f, (r) - 1, (r) - 1);
        canvas.drawArc(rectF, 270, sweep, false, paint);
        canvas.drawCircle(w / 2, h / 2, r / 2 - 4, paintx);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                valueAnimator.start();
                dt = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if (downTime<90) {
                    if (keepListener!=null) {
                        keepListener.onClick();
                    }
                }
                sweep = 0;
                downTime = 0;
                isDown = false;
                valueAnimator.cancel();
                postInvalidate();

                break;
        }
        return true;
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
        paintx.setColor(color);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setKeepListener(KeepListener keepListener) {
        this.keepListener = keepListener;
    }

    public interface KeepListener {
        void onClick();

        void onBegin();

        void onFinish();
    }
}
