package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by ZhDu on 2021/4/22 17:32.
 */
public class ResetView extends ReView {

    private static final String TAG = "ResetView";

    public ResetView(Context context) {
        super(context);
    }

    public ResetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.save();
        for (int i = 0; i < 10; i++) {
            canvas.rotate(36, getWidth() / 2, getHeight() / 2);
            canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, 20, paint);
        }
        canvas.restore();
        paint.setStrokeWidth(4);
        for (int i = 0; i < 8; i++) {
            canvas.rotate(45, getWidth() / 2, getHeight() / 2);
            canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, -45 + getHeight() / 2, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float cx = getWidth() / 2, cy = getHeight() / 2;
        float dx = Math.abs(cx - event.getX()), dy = Math.abs(cy - event.getY());
        float la = dx, lb = dy, lc = (la + lb) / 2;
        Log.d(TAG, "onTouchEvent: cx:" + cx + "  cy:" + cy + " dx:" + dx + " dy:" + dy + " event.getX():" + event.getX() + " event.getY()" + event.getY());
        Log.d(TAG, "onTouchEvent: la:" + la + "  lb:" + lb + " lc:" + lc);
        return super.onTouchEvent(event);
    }


}