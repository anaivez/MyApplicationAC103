package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Collections;

/**
 * Description:
 * Created by ZhDu on 2021/4/16 11:07.
 */
public class ZXView extends View {

    private static final String TAG = "ZXView";
    Paint xyPain, linkPain;
    float height, width;
    Float[] datas = new Float[]{1f, 2f, 3f, 5f, 6f, 3f, 2f, 1f, 10f, 7f, 9f, 7.5f, 9.8f, 9.9f, 8f};
    float xPadding = 0;
    float wPadding;
    float hPadding;
    Context context;

    public ZXView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ZXView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        xyPain = new Paint();
        xyPain.setColor(Color.GRAY);
        xyPain.setStyle(Paint.Style.STROKE);
        xyPain.setAntiAlias(true);
        xyPain.setStrokeWidth(1.5f);
        xyPain.setDither(true);

        linkPain = new Paint();
        linkPain.setColor(Color.BLUE);
        linkPain.setStrokeWidth(1.5f);
        linkPain.setStyle(Paint.Style.FILL);
        linkPain.setAntiAlias(true);
        linkPain.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        height = getHeight();
        width = getWidth();
        wPadding = (width / 10) / 2;
        hPadding = (height / 10) / 2;
        xPadding = (float) (getWidth() * 0.9) / (datas.length - 1);
        //Y轴线
        canvas.drawLine((wPadding), (getHeight() - hPadding), (wPadding), hPadding, xyPain);
        //X轴线
        canvas.drawLine((getWidth() - wPadding), (getHeight() - hPadding), wPadding, (getHeight() - hPadding), xyPain);
        float max = Collections.max(Arrays.asList(datas));
        float min = Collections.min(Arrays.asList(datas));
        for (int i = 0; i < datas.length; i++) {
            if (i < datas.length - 1) {
                canvas.drawLine(getItemStartW(i), getItemHeight(i, max), getItemStartW(i + 1), getItemHeight(i + 1, max), linkPain);
            }
            canvas.drawCircle(getItemStartW(i), getItemHeight(i, max), 5f, linkPain);
            canvas.drawText("" + datas[i], getItemStartW(i) - 5, getItemHeight(i, max) - 11, xyPain);
        }
        //Y轴平均 每格数据
        float avg = Math.round((max - min) / 10);
        //Y轴平均 每格数据(Y轴坐标)
        float avgForY = Math.round(((getHeight() - hPadding) - hPadding) / 9);
        //Y轴数据字体宽度
        float textPadding = xyPain.measureText("" + max);


        //X轴平均 每格数据(X轴坐标)
        float avgForX = Math.round(((getWidth() - wPadding) - wPadding) / datas.length);
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "onDraw: " + (min + avg * i) + " avg " + avg);
            //Y轴数据
            canvas.drawText("" + (min + (avg * (9 - i))), (wPadding) - textPadding, avgForY * i + hPadding, xyPain);
            //X轴数据
        }
        for (int i = 0; i < datas.length; i++) {
            canvas.drawText("" + (i + 1), getItemStartW(i) - 5, (getHeight() - hPadding / 2), xyPain);
        }
    }

    private float getItemHeight(int index, float max) {
        return getHeight() - (getHeight() * (datas[index] / max)) + hPadding;
    }


    private float getItemStartW(int index) {
        return wPadding + (index * xPadding);
    }
}
