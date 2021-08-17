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
 * Created by Czm on 2021/7/19 10:46.
 */
public class AWeiView extends View {

    private static final String TAG = "AWeiView";
    Paint paint, paintC, paintRC;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    float cw, ch, r;
    float padding = 10, tcc, fcc = 1000, vcc, vPadding = 2, radioPa = vPadding + 10;
    private float sweep = 0;

    public AWeiView(Context context) {
        super(context);
        init();
    }

    public AWeiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        paintC = new Paint();
        paintC.setStyle(Paint.Style.STROKE);
        paintC.setAntiAlias(true);
        paintC.setColor(Color.GRAY);

        paintRC = new Paint();
        paintRC.setStyle(Paint.Style.STROKE);
        paintRC.setColor(Color.RED);
        paintRC.setAntiAlias(true);
        initF();
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

    private void updateParticle(Float value) {
        padding -= 0.5;
        if (padding <= 0) {
            padding = 0;
            vcc = tcc;
            sweep += 5;
        }
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cw = getWidth() / 2;
        ch = getHeight() / 2;
        tcc = (Math.min(cw, ch) - radioPa);
        vcc = fcc;
        radioPa = (float) ((Math.min(getWidth(), getHeight()) - vPadding * 2)*0.85);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        r = (Math.min(cw, ch) - radioPa);
        canvas.drawCircle(cw, ch, r, paint);
        canvas.drawCircle(cw, ch, vcc, paintC);
        RectF rectF = new RectF(radioPa, radioPa, getWidth() - radioPa, getHeight() - radioPa);
        canvas.drawArc(rectF, 270, sweep, false, paintRC);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                valueAnimator.start();
                break;
            case MotionEvent.ACTION_UP:
                valueAnimator.cancel();
                vcc = fcc;
                padding = 10;
                sweep = 0;
                break;
        }
        postInvalidate();
        return true;
    }
}
