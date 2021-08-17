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

import java.text.DecimalFormat;

/**
 * Description:
 * Created by Czm on 2021/7/19 14:20.
 */
public class HistogramViewV3 extends View {

    private static final String TAG = "HistogramViewV2";
    Paint pillarPaint, textPatint, pp;
    Float datas[] = new Float[]{};
    float spac, histogramW, histogramMaxH, histogramWH;
    Float datasHeight[] = new Float[]{};
    long lastDown = 0;
    float lump;
    float max = 15f;

    public HistogramViewV3(Context context) {
        super(context);
        init();
    }

    public HistogramViewV3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        pillarPaint = new Paint();
        pillarPaint.setColor(Color.GREEN);
        pillarPaint.setStyle(Paint.Style.FILL);

        textPatint = new Paint();
        textPatint.setColor(Color.BLACK);
        textPatint.setStyle(Paint.Style.FILL);
        textPatint.setTextSize(18);
        textPatint.setTextAlign(Paint.Align.CENTER);//Paint设置水平居中

        pp = new Paint();
        pp.setColor(Color.BLACK);
        pp.setStyle(Paint.Style.STROKE);
        //max = Collections.max(Arrays.asList(datas));
    }

    public void setDatas(Float[] datas) {
        this.datas = datas;
        datasHeight = new Float[datas.length];
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (datas == null || datas.length == 0) {
            return;
        }
        spac = (float) ((Double.valueOf(getWidth()) / 10) / (datas.length + 1));
        //宽
        histogramW = (float) ((getWidth() * 0.9) / datas.length);
        //
        histogramMaxH = (float) (getHeight() * 0.95);
        lump = histogramMaxH / (max * 10);
        for (int i = 0; i < datas.length; i++) {
            datasHeight[i] = lump * (datas[i] * 10);
        }
    }


    @Override
    public void postInvalidate() {
        super.postInvalidate();
        if (datas != null) {
            for (int i = 0; i < datas.length; i++) {
                datasHeight[i] = lump * (datas[i] * 10);
            }
        }
    }


    private float getViewData(float height) {
        boolean accurate = false;
        if (accurate) {
            return height / 10 / lump;
        } else {
            DecimalFormat decimalFormat = new DecimalFormat(".0");
            return Float.valueOf(decimalFormat.format(height / 10 / lump));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(255, 255, 255);
        if (datasHeight != null) {
            for (int i = 0; i < datasHeight.length; i++) {
                RectF rectF;
                float left = spac * (i + 1) + histogramW * i;
                float top = getHeight();
                float right = spac * (i + 1) + histogramW * (i + 1);
                float boottom = getHeight() - datasHeight[i];
                rectF = new RectF(left, top, right, boottom);
                canvas.drawRoundRect(rectF, 015, 0, pillarPaint);
                canvas.drawText(getData(datas[i]) + "f", left + (right - left) / 2, boottom, textPatint);
            }
        }
    }

    private double getData(float data) {
        double rt = 0.0;
        try {
            DecimalFormat decimalFormat = new DecimalFormat(".0");
            rt = Double.parseDouble(decimalFormat.format(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rt;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mx, my;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mx = event.getX();
                my = event.getY();
                int index = (int) (mx / (spac + histogramW));
                Log.d(TAG, "onTouchEvent: " + mx + "   my: " + my);
                if (datas != null || datas.length != 0) {
                    if (index >= 0 && index < datas.length) {
                        if (my <= getHeight() && my >= (getHeight() - histogramMaxH)) {
                            float changeData = getViewData(getHeight() - my);
                            Log.d(TAG, "onTouchEvent  index: " + index + "    change: " + changeData);
                            datas[index] = changeData;
                        } else {
                            if (my <= getHeight()) {
                                datas[index] = max;
                            } else {
                                datas[index] = 0f;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if ((System.currentTimeMillis() - lastDown) <= 500) {
                    Log.d(TAG, "onTouchEvent: reset");
                    toStringx();
                    lastDown = 0;
                }
                toStringx();
                lastDown = System.currentTimeMillis();
                break;
        }
        postInvalidate();
        return true;
    }


    private void toStringx() {
        String t = "";
        for (float f : datas) {
            t += f + " ";
        }
        Log.d(TAG, "toStringx: ----------- " + t);
    }
}
