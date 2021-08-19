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

    Paint paint;
    float initialAArcStartAngle = 300, initialBArcStartAngle = 120,initialArcSweepAngle=120;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);

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
        canvas.drawArc(
                new RectF(4f, 4f, width - 4, height - 4),
                getAArcStartAngle(),
                getArcSweepAngle(),
                false,
                paint);

        canvas.drawArc(
                new RectF(4f, 4f, width - 4, height - 4),
                getBArcStartAngle(),
                getArcSweepAngle(),
                false,
                paint);

    }

    private float getAArcStartAngle() {
        //initialAArcStartAngle+=(5+((120-initialArcSweepAngle)/20));
        initialAArcStartAngle+=10;
        if (initialAArcStartAngle>360) {
            initialAArcStartAngle=0;
        }
        return initialAArcStartAngle;
    }

    private float getBArcStartAngle() {
        //initialBArcStartAngle+=(5+((120-initialArcSweepAngle)/20));
        initialBArcStartAngle+=10;
        if (initialBArcStartAngle>360) {
            initialBArcStartAngle=0;
        }

        return initialBArcStartAngle;
    }
    boolean isRise=true;
    private float getArcSweepAngle() {
        if (initialArcSweepAngle>=120) {
            isRise=false;
        }
        if (initialArcSweepAngle<=15) {
            isRise=true;
        }
        if (isRise) {
            initialArcSweepAngle++;
        }else{
            initialArcSweepAngle--;
        }
        return initialArcSweepAngle;
    }
}
