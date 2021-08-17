package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description:
 * Created by Czm on 2021/5/24 17:14.
 */
public class TrochalDiskView extends View {

    private static final String TAG = "TrochalDiskView";
    float dx, dy, ux, uy;
    float ax = ((getWidth()) / 2);
    float ay = ((getHeight()) / 2);
    private Paint paint;

    public TrochalDiskView(Context context) {
        super(context);
        init();
    }

    public TrochalDiskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4f);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ax = ((getWidth()) / 2);
        ay = ((getHeight()) / 2);
        float r = Math.min(ax, ay) - 10;
        Log.d(TAG, "2πR:  2*" + Math.PI + "*" + r + "=" + 2 * Math.PI * r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(10, 10, getWidth() - 10, getHeight() - 10);
        for (int i = 0; i < 360; i += 36) {
            canvas.drawArc(rectF, i, 36, false, paint);
        }

        paint.setStrokeWidth(1f);
        Path path = new Path();
        //canvas.drawLine(ax, ay, ax, 10, paint);
        //canvas.drawLine(ax, ay, getWidth() - 10, ay, paint);
        //canvas.drawLine(ax, 10, getWidth() - 10, ay, paint);
        canvas.drawLine(ax, ay, getWidth() - 10, 10, paint);
        canvas.translate(ax, ay);
        path.lineTo(ax-10, 0);
        path.lineTo(0 , -ay+10);
        path.close();
        canvas.drawPath(path, paint );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                ux = event.getX();
                uy = event.getY();
                break;
            case MotionEvent.ACTION_DOWN:
                dx = event.getX();
                dy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        return true;
    }


    /**
     * ax   ay
     * 50   50
     * bx   by
     * 50   0
     * cx   cy
     * 50   100
     *
     * @param dp
     * @return
     */

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
