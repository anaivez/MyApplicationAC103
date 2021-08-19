package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gdu.myapplicationac103.R;

/**
 * Description:
 * Created by Czm on 2021/7/30 14:13.
 */
public class SpinView extends View {

    Paint backPaint, whitePaint;
    float ro = 0;
    float dx, dy;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    float ang = 0;
    private Bitmap buttonImage = BitmapFactory.decodeResource(getResources(),
            R.mipmap.ic_launcher);

    public SpinView(Context context) {
        super(context);
        init();
    }

    public SpinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=height=Math.min(getWidth(),getHeight());
        initF();
    }

    private void initF() {
        valueAnimator.setDuration(1000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ang += 5;
                if (ang > 360) {
                    ang = 0;
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float h2 = getHeight() / 2;
        float w2 = getWidth() / 2;
        float r = (float) (Math.min(h2, w2) * 0.9);

        canvas.drawCircle(w2, h2, r, backPaint);
        canvas.drawCircle(w2, h2, r, whitePaint);
        canvas.rotate(ang , h2, w2);
        canvas.drawLine(w2, (Math.min(h2, w2)) - r, w2, Math.min(h2, w2) - r + 20, whitePaint);
    }

float  currentAngle  , angleIncreased;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = getX();
                dy = getY();
                currentAngle = calcAngle( getX(), getY());
                break;
            case MotionEvent.ACTION_MOVE:
                ro = (Math.abs((getX() - dx)));
                float angle = calcAngle(getX(), getY());
                // 滑过的角度增量
                float angleIncreased = angle - currentAngle;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        Log.d("TT", "onTouchEvent: "+currentAngle+"-----"+angleIncreased);
        //Log.d("TT", "onTouchEvent   mx:" + getX() + " my:" + getY());
        //Log.d("TT ", "onTouchEvent: " + ro + "  dx:" + dx + "  nx:" + getX());
        postInvalidate();
        return true;
    }
float width,height;
    private float calcAngle(float targetX, float targetY) {
        float x = targetX - width / 2;
        float y = targetY - height / 2;
        double radian;

        if (x != 0) {
            float tan = Math.abs(y / x);
            if (x > 0) {
                if (y >= 0) {
                    radian = Math.atan(tan);
                } else {
                    radian = 2 * Math.PI - Math.atan(tan);
                }
            } else {
                if (y >= 0) {
                    radian = Math.PI - Math.atan(tan);
                } else {
                    radian = Math.PI + Math.atan(tan);
                }
            }
        } else {
            if (y > 0) {
                radian = Math.PI / 2;
            } else {
                radian = -Math.PI / 2;
            }
        }
        return (float) ((radian * 180) / Math.PI);
    }

    private void init() {
        backPaint = new Paint();
        backPaint.setColor(Color.BLACK);
        backPaint.setStyle(Paint.Style.FILL);

        whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(8);
        whitePaint.setStrokeCap(Paint.Cap.ROUND);
    }

}
