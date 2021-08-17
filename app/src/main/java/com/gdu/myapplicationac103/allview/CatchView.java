package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 * Created by Czm on 2021/6/19 16:02.
 */
public class CatchView extends View {

    private static final String TAG = "CatchView";
    Paint paint;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    float f = 0;
    boolean isAdd;
    float startAngle = 270;
    float sweepAngle = 10;
    private int g = 6;

    public CatchView(Context context) {
        super(context);
        init();
    }

    public CatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initF();
        //valueAnimator.start();
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
        f += g;
        if (f > 360) {
            f = g;
        }
        sweepAngle += 10;
        if (isAdd) {
            sweepAngle += 10;
            isAdd = sweepAngle <= 360;
        } else {
            sweepAngle -= 10;
            isAdd = sweepAngle <= 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(f, 0, 0);
        //drawLine(canvas);
        RectF rectF = new RectF(-50, -50, 50, 50);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(12);
        paint.setColor(Color.GREEN);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(0, -200, 0, 200, paint);
    }

}
