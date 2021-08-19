package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 * Created by Czm on 2021/8/18 17:00.
 */
public class LoadingView2 extends View {


    private static final String TAG = "LoadingView2";
    Paint paint;
    float vw, vh, radius;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    float degress = 00;
    int index = 1;

    public LoadingView2(Context context) {
        super(context);
        init();
    }

    public LoadingView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void initF() {
        valueAnimator.setDuration(1000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        //循环次数  -1表示无限循环
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                index++;
                if (index % 5 == 0) {
                    degress += 30;
                    if (degress > 360) {
                        degress = 0;
                    }
                    postInvalidate();
                }
            }
        });
        valueAnimator.start();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        vw = getWidth();
        vh = getHeight();
        radius = Math.min(vw, vh) / 12;
        initF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(degress, vw / 2, vh / 2);
        for (int i = 1; i <= 12; i++) {
            paint.setAlpha(80 + ((int) 14.5 * i));
            canvas.drawCircle(vw / 2, radius, radius, paint);
            canvas.rotate(30, vw / 2, vh / 2);
            canvas.save();
        }
    }
}
