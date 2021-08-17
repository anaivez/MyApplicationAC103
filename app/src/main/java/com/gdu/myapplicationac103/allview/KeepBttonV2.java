package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description:
 * Created by Czm on 2021/7/19 11:24.
 */
public class KeepBttonV2 extends View {

    Paint cr, keepCr, keepCrRun;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    ValueAnimator valueAnimatorx = ValueAnimator.ofFloat(0, 1f);
    float cw, ch, r, radioPa;
    double pp = 0.2;
    float keepCrcle, mKeepCrcle, sweep, dRadioPa;

    public KeepBttonV2(Context context) {
        super(context);
        init();
    }

    public KeepBttonV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        cr = new Paint();
        cr.setAntiAlias(true);

        keepCr = new Paint();
        keepCr.setStyle(Paint.Style.STROKE);
        keepCr.setAntiAlias(true);
        keepCr.setColor(Color.GRAY);

        keepCrRun = new Paint();
        keepCrRun.setStyle(Paint.Style.STROKE);
        keepCrRun.setColor(Color.RED);
        keepCrRun.setAntiAlias(true);
        initF();
        initFx();
    }

    private void initF() {
        valueAnimator.setDuration(2000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        //循环次数  -1表示无限循环
        valueAnimator.setRepeatCount(1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                updateParticle((Float) valueAnimator.getAnimatedValue());
            }
        });
    }

    private void initFx() {
        valueAnimatorx.setDuration(500);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        //循环次数  -1表示无限循环
        valueAnimatorx.setRepeatCount(1);
        valueAnimatorx.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                updateParticlex((Float) valueAnimator.getAnimatedValue());
            }
        });
    }

    private void updateParticle(Float value) {
        radioPa -= pp * 2;
        if (radioPa <= 0) {
            radioPa = 0;
            mKeepCrcle = keepCrcle;
            sweep += 10;
        }
        postInvalidate();
    }
    private void updateParticlex(Float value) {
        radioPa += pp * 2;
        if (radioPa >= (float) (Math.min(cw, ch) * pp)) {
            radioPa = (float) (Math.min(cw, ch) * pp);
            valueAnimatorx.cancel();
        }
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cw = getWidth() / 2;
        ch = getHeight() / 2;
        pp=(Double.valueOf(Math.min(cw, ch))/0.95)/10;
        radioPa = (float) (Math.min(cw, ch) * pp);
        dRadioPa = (float) (Math.min(cw, ch) * pp);
        keepCrcle = (Math.min(cw, ch) - radioPa);
        mKeepCrcle = Math.max(cw, ch) * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        r = (Math.min(cw, ch) - radioPa);
        canvas.drawCircle(cw, ch, r, cr);
        canvas.drawCircle(cw, ch, mKeepCrcle, keepCr);
        RectF rectF = new RectF(dRadioPa, dRadioPa, getWidth() - dRadioPa, getHeight() - dRadioPa);
        canvas.drawArc(rectF, 270, sweep, false, keepCrRun);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                valueAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
                valueAnimator.cancel();
                //radioPa = (float) (Math.min(cw, ch) * pp);
                valueAnimatorx.start();

                mKeepCrcle = Math.max(cw, ch) * 2;
                sweep = 0;
                break;
        }
        postInvalidate();
        return true;
    }
}
