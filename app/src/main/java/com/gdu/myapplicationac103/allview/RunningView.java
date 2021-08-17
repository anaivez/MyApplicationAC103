package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RunningView extends View {
    public static double MAXSPEED = 16.8;//最大速度
    public static double MINSPEED = 0.6;//最小速度
    public static int SLOPES = 15;//最大扬升
    static boolean isShan;
    int index;
    float lineHeight_Event;
    /**
     * 声明画笔
     */
    private Paint mPaint_X;//X坐标轴画笔
    private Paint mPaint_Y;//Y坐标轴画笔
    private Paint mPaint_InsideLine;//内部虚线P
    private Paint mPaint_Text;//字体画笔
    private Paint mPaint_Rec;//矩形画笔
    //矩形数据
    private double[] dataRec;
    //视图的宽高
    private float width;
    private float height;

    public RunningView(Context context) {
        super(context);
        init();
    }

    public RunningView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 更新数据
     */
    public void upDataRec(double[] data, int ind) {
        dataRec = data;
        index = ind;
        postInvalidate();  //更新视图
    }

    /**
     * 初始化画笔
     */
    private void init() {
        mPaint_X = new Paint();
        mPaint_InsideLine = new Paint();
        mPaint_Text = new Paint();
        mPaint_Rec = new Paint();
        mPaint_Y = new Paint();

        mPaint_X.setColor(Color.DKGRAY);
        mPaint_X.setStrokeWidth(3);
        mPaint_X.setAntiAlias(true);
        mPaint_X.setDither(true);

//        mPaint_Y.setColor(Color.argb(110, 12, 77, 218));
        mPaint_Y.setColor(Color.argb(55, 255, 255, 255));
        mPaint_Y.setAntiAlias(true);
        mPaint_Y.setDither(true);

        mPaint_InsideLine.setColor(Color.BLACK);
        mPaint_InsideLine.setAntiAlias(true);
        mPaint_InsideLine.setDither(true);

        mPaint_Text.setTextSize(14);
        mPaint_Text.setTextAlign(Paint.Align.CENTER);
        mPaint_Text.setColor(Color.WHITE);
        mPaint_Text.setAntiAlias(true);
        mPaint_Text.setDither(true);

//        mPaint_Rec.setColor(Color.YELLOW);
        mPaint_Rec.setColor(Color.argb(255, 4, 222, 202));
        mPaint_Rec.setAntiAlias(true);
        mPaint_Rec.setDither(true);

    }

    public void setIsShan(boolean b) {
        isShan = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        height = (int) (height * 0.95);
        double leftHeight_Every = height / MAXSPEED; //Y轴每个数据的间距
        if (SLOPES != 0) {
            lineHeight_Event = height / SLOPES;
        }
        if (dataRec != null && dataRec.length > 0) {
            float downWeight_Every = width / (dataRec.length + 1);//X轴每个数据的间距
            for (int i = 0; i < dataRec.length; i++) { //画矩形
                //绘制背景
                RectF rectGary;
                if (width < 600) {
                    rectGary = new RectF(
                            downWeight_Every * (i + 0.5f),
                            0,
                            (downWeight_Every * (i + 1.15f)),
                            height);
                } else {
                    rectGary = new RectF(
                            downWeight_Every * (i + 0.5f),
                            0,
                            (downWeight_Every * (i + 1.15f)) - 8,
                            height);
                }
                //程序模式下超过16列
                if (dataRec.length > 17) {
                    rectGary.right = rectGary.right + 10;
                }
                canvas.drawRoundRect(rectGary, 15f, 15f, mPaint_Y);
                //当前列闪动  已跑过列变灰
                if (i == index) {
                    if (isShan) {
                        mPaint_Rec.setColor(Color.argb(255, 4, 222, 202));
                    } else {
                        mPaint_Rec.setColor(Color.argb(0, 0, 0, 0));
                    }
                } else if (i < index) {
                    mPaint_Rec.setColor(Color.argb(255, 196, 196, 196));
                } else {
                    mPaint_Rec.setColor(Color.argb(255, 4, 222, 202));
                }
                //绘制矩形
                RectF rect;
                if (width < 600) {
                    rect = new RectF(
                            downWeight_Every * (i + 0.5f),
                            (float) (height - (dataRec[i]) * leftHeight_Every),
                            (downWeight_Every * (i + 1.15f)),
                            height);
                } else {
                    rect = new RectF(
                            downWeight_Every * (i + 0.5f),
                            (float) (height - (dataRec[i]) * leftHeight_Every),
                            (downWeight_Every * (i + 1.15f)) - 8,
                            height);
                    Log.d("TT", "onDraw: " + (downWeight_Every * (i + 0.5f)) + "---" +
                            ((float) (height - (dataRec[i]) * leftHeight_Every)) + "---" +
                            ((downWeight_Every * (i + 1.15f)) - 8) + "---" +
                            height);
                }
                if (dataRec.length > 17) {//程序模式下超过16列
                    rect.right = rect.right + 10;
                }
                canvas.drawRoundRect(rect, 15f, 15f, mPaint_Rec);
            }
        }

    }
}