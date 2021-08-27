package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Description:
 * Created by Czm on 2021/8/19 16:53.
 */
public class LoadingView3 extends BaseView {

    Paint paint, tPaint;
    float initialAArcStartAngle = 300, initialBArcStartAngle = 120, initialArcSweepAngle = 120;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    boolean isRise = true;
    int rate = 0;

    public LoadingView3(Context context) {
        super(context);
    }

    public LoadingView3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(6f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        tPaint = new Paint();
        tPaint.setColor(Color.WHITE);
        tPaint.setStrokeWidth(2f);
        tPaint.setTextSize(18);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initF();
    }

    private void initF() {
        valueAnimator.setDuration(1000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        //循环次数  -1表示无限循环
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sweepAngle = getArcSweepAngle();
        canvas.drawArc(
                new RectF(4f, 4f, width - 4, height - 4),
                getAArcStartAngle(),
                sweepAngle,
                false,
                paint);

        canvas.drawArc(
                new RectF(4f, 4f, width - 4, height - 4),
                getBArcStartAngle(),
                sweepAngle,
                false,
                paint);
        canvas.drawText("" + getRate(), centreX, centreY, tPaint);
    }

    private float getAArcStartAngle() {
        initialAArcStartAngle += getRate();
        if (initialAArcStartAngle >= 360) {
            initialAArcStartAngle = 0;
        }
        return initialAArcStartAngle;
    }

    private float getBArcStartAngle() {
        initialBArcStartAngle = initialAArcStartAngle + 180;
        if (initialAArcStartAngle >= 360) {
            initialAArcStartAngle = initialAArcStartAngle % 360;
        }

        return initialBArcStartAngle;
    }

    private int getRate() {
        return 7 + rate;
    }

    private float getArcSweepAngle() {
        if (initialArcSweepAngle >= 120) {
            isRise = false;
        }
        if (initialArcSweepAngle <= 15) {
            isRise = true;
        }
        if (isRise) {
            initialArcSweepAngle++;
        } else {
            initialArcSweepAngle--;
        }
        rate = (int) ((120-initialArcSweepAngle) / 20);
        return initialArcSweepAngle;
    }
}
