package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description:
 * Created by Czm on 2021/6/15 11:28.
 */
public class LoadingView extends View {

    private static final String TAG = "LoadingView";
    Paint paint;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    float startAngle = 270;
    float sweepAngle = 20;
    boolean isAdd = true;
    int ge = 20;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setGe(int ge) {
        this.ge = ge;
        Log.d(TAG, "setGe: "+ge);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initF();
        valueAnimator.start();
    }

    private void initF() {
        valueAnimator.setDuration(3000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        //循环次数  -1表示无限循环
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                updateParticle((Float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });
    }

    private void updateParticle(Float value) {
        if (isAdd) {
            sweepAngle += ge;
            isAdd = sweepAngle <= 360;
            startAngle = 270;
        } else {
            sweepAngle -= ge;
            if (sweepAngle != (360 - ge)) {
                startAngle += ge;
                if (startAngle >= 360) {
                    startAngle = startAngle - 360;
                }
            }
            isAdd = sweepAngle <= 0;
        }
    }

    /**
     * 0   270
     * 90  0
     * 180 90
     * 270 180
     *
     * @param canvas
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth(), h = getHeight(), r;
        r = Math.min(w, h);
        RectF rectF = new RectF(3f, 3f, (r) - 3, (r) - 3);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);

    }
}
