package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description:
 * Created by ZhDu on 2021/3/31 14:30.
 */
public class RView extends View {

    private static final String TAG = "RView";
    Paint dPaint;
    int width, height;
    int num = 10;

    public RView(Context context) {
        super(context);
        init();
    }

    public RView(Context context, @Nullable AttributeSet attrs) {
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
        width = getWidth();
        height = getHeight();
        Log.d(TAG, "onDraw  h:" + height + "  w:" + width);
        int vWidth=width / num;
        int left=0;
        for (int i = 0; i < num; i++) {
            RectF rectF = new RectF(vWidth*i-vWidth+8, (height/num) * i, vWidth*i, height);
            dPaint.setColor(Color.argb(100, 204, 0, 255));
            canvas.drawRoundRect(rectF, 14f, 14f, dPaint);
        }
    }
}
