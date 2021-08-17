package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.gdu.myapplicationac103.R;

/**
 * Description:
 * Created by ZhDu on 2021/3/31 15:15.
 */
public class RViewTouch extends View {

    private static final String TAG = "RViewTouch";

    Paint dPaint;
    RectF rectF;
    Canvas canvas;
    float x, y, lastX = 0, lastY = 0, offsetX, offsetY;

    public RViewTouch(Context context) {
        super(context);
        init();
    }

    public RViewTouch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dPaint = new Paint();
        dPaint.setColor(Color.DKGRAY);
        dPaint.setAntiAlias(true);
        dPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        rectF = new RectF(10f, 10f, 80f, 80f);
        dPaint.setColor(+R.color.colorPrimary);
        canvas.drawCircle(offsetX, offsetY, 35f, dPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        Log.d(TAG, "onTouchEvent-- X:" + event.getX() + "  Y:" + event.getY());
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = x ;
                offsetY = y ;
                this.postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }


}
