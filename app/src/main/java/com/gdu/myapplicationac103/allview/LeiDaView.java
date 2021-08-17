package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gdu.myapplicationac103.R;

/**
 * Description:
 * Created by Czm on 2021/5/28 16:38.
 */
public class LeiDaView extends View {

    private static final String TAG = "LeiDaView";
    Paint linkPaint, paint;
    float centerX, centerY;
    int data[] = new int[]{100, 80, 95, 66, 78, 99};
    float lx = 360 / data.length;
    float dx, dy;
    float currentValue;
    float pos[] = new float[2];
    float tan[] = new float[2];
    Path path;
    float mx, my, ux, uy;
    PathMeasure pathMeasure;
    float w, h;
    boolean isDown = true;
    private float angle = (float) (Math.PI * 2 / data.length);
    private Bitmap mBitmap;
    private Matrix mMatrix;

    public LeiDaView(Context context) {
        super(context);
        init();
    }

    public LeiDaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linkPaint = new Paint();
        linkPaint.setStyle(Paint.Style.STROKE);
        linkPaint.setAntiAlias(true);


        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAlpha(127);
        paint.setAntiAlias(true);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 20;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.to, options);
        mMatrix = new Matrix();
        path = new Path();
        pathMeasure = new PathMeasure(path, false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dx = centerX = getWidth() / 2;
        dy = centerY = getHeight() / 2;
        dy -= 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawRedar(canvas);
        //drawRedarData(canvas);
        //drawBeser(canvas);
        drawCanvas(canvas);
        //drawGoPath(canvas);
        //drawGoProPath(canvas);
        //drawPathPro(canvas);
    }

    private void drawGoProPath(Canvas canvas) {
        w = getWidth() - 100;
        h = getHeight() - 100;
        canvas.translate(50, getHeight() - 50);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(w, 0);
        path.lineTo(w, -h);
        path.lineTo(0, -h);
        path.lineTo(0, 0);
        path.lineTo(w, -h);
        path.lineTo(0, -h / 2);
        path.lineTo(0, 0);
        canvas.drawPath(path, linkPaint);
        PathMeasure pm = new PathMeasure(path, false);
        if (pm.getPosTan(pm.getLength() * currentValue, pos, tan)) {
            currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
            if (currentValue >= 1) {
                currentValue = 0;
            }
            mMatrix.reset();                                                        // 重置Matrix
            float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

            mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
            mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

            canvas.drawPath(path, linkPaint);                                   // 绘制 Path
            canvas.drawBitmap(mBitmap, mMatrix, linkPaint);                     // 绘制箭头

            invalidate();
        }
    }

    private void drawGoPath(Canvas canvas) {
        w = getWidth() - 100;
        h = getHeight() - 100;
        canvas.translate(50, getHeight() - 50);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(w, 0);
        path.lineTo(w, -h);
        path.lineTo(0, -h);
        path.lineTo(0, 0);
        path.lineTo(w, -h);
        path.lineTo(0, -h / 2);
        path.lineTo(0, 0);
        canvas.drawPath(path, linkPaint);
        PathMeasure pm = new PathMeasure(path, false);
        if (pm.getPosTan(pm.getLength() * currentValue, pos, tan)) {
            currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
            if (currentValue >= 1) {
                currentValue = 0;
            }
            mMatrix.reset();                                                        // 重置Matrix
            float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

            mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
            mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

            canvas.drawPath(path, linkPaint);                                   // 绘制 Path
            canvas.drawBitmap(mBitmap, mMatrix, linkPaint);                     // 绘制箭头

            invalidate();
        }
    }

    private void drawCanvas(Canvas canvas) {
        canvas.drawPath(path, linkPaint);
        PathMeasure pm = new PathMeasure(path, false);
        if (!isDown) {
            if (pm.getPosTan(pm.getLength() * currentValue, pos, tan)) {
                currentValue += 0.01;                                  // 计算当前的位置在总长度上的比例[0,1]
                if (currentValue >= 1) {
                    currentValue = 0;
                }
                mMatrix.reset();                                                        // 重置Matrix
                float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

                mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
                mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

                canvas.drawPath(path, linkPaint);                                   // 绘制 Path
                canvas.drawBitmap(mBitmap, mMatrix, linkPaint);                     // 绘制箭头
                invalidate();
            }
        }
    }

    //普通二阶贝塞尔曲线带轨迹动画
    private void drawBeser(Canvas canvas) {
        Path path = new Path();
        path.moveTo(50, centerY);
        path.quadTo(dx, dy, getWidth() - 50, centerY);
        canvas.drawPath(path, linkPaint);
        canvas.drawLine(50, centerY, dx, dy, linkPaint);
        canvas.drawLine(getWidth() - 50, centerY, dx, dy, linkPaint);
        PathMeasure pm = new PathMeasure(path, false);
        Log.d(TAG, "drawBeser: " + pm.getLength());

        if (pm.getPosTan(pm.getLength() * currentValue, pos, tan)) {
            currentValue += 0.01;                                  // 计算当前的位置在总长度上的比例[0,1]
            if (currentValue >= 1) {
                currentValue = 0;
            }
            mMatrix.reset();                                                        // 重置Matrix
            float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

            mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
            mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

            canvas.drawPath(path, linkPaint);                                   // 绘制 Path
            canvas.drawBitmap(mBitmap, mMatrix, linkPaint);                     // 绘制箭头

            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mx = event.getX();
        my = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                dx = event.getX();
                dy = event.getY();
                path.reset();
                path.moveTo(dx, dy);
                break;
            case MotionEvent.ACTION_MOVE:
                float cx = (mx + dx) / 2;
                float cy = (my + dy) / 2;
                path.quadTo(dx, dy, cx, cy);
                dx = mx;
                dy = my;
                //dx = event.getX();
                //dy = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                ux = getX();
                uy = getY();
                break;
        }
        postInvalidate();
        return true;
    }

    //雷达矩阵数据
    private void drawRedarData(Canvas canvas) {
        Path path = new Path();
        for (int c = 0; c < data.length; c++) {
            double percent = data[c] * 0.01;
            Log.d(TAG, "drawRedarData: " + percent + "  dd: " + data[c]);
            float llx = (float) (centerX + Math.max(centerX, centerY) * Math.cos(angle * c) * percent);
            float lly = (float) (centerY + Math.max(centerX, centerY) * Math.sin(angle * c) * percent);
            if (c == 0) {
                path.moveTo(llx, centerY);
                canvas.drawText("" + data[c], llx, centerY, paint);
            } else {
                path.lineTo(llx, lly);
                canvas.drawText("" + data[c], llx, lly, paint);
            }
        }
        path.close();
        canvas.drawPath(path, paint);
    }

    int minX,minY,maxX,maxY;

    private void drawPathPro(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawLine(-getWidth(), 0, getWidth(), 0, linkPaint);
        maxX=getWidth()/2;
        minX=-maxX;
        canvas.drawLine(0, -getHeight(), 0, getHeight(), linkPaint);
        maxY=getHeight()/2;
        minY=-maxY;
/*        Path pp=new Path();
        pp.moveTo(0,minY);
        pp.lineTo(minX,0);
        pp.lineTo(0,maxY);
        pp.lineTo(maxX,0);
        pp.close();
        canvas.drawPath(pp,linkPaint);*/
        for (int i = 0; i < 800; i += 10) {
            canvas.drawLine(3.5f, i, -3.5f, i, linkPaint);
            canvas.drawLine(3.5f, -i, -3.5f, -i, linkPaint);

            canvas.drawLine(i, 3.5f, i, -3.5f, linkPaint);
            canvas.drawLine(-i, 3.5f, -i, -3.5f, linkPaint);
            if (i%100==0) {
                canvas.drawLine(10f, i, -10f, i, linkPaint);
                canvas.drawLine(10f, -i, -10f, -i, linkPaint);

                canvas.drawLine(i, 10f, i, -10f, linkPaint);
                canvas.drawLine(-i, 10f, -i, -10f, linkPaint);
            }
        }


        path = new Path();
        path.moveTo(0, -240);
        path.lineTo(-60, -60);
        //path.lineTo(-240, 0);
        //path.lineTo(-60, 60);
        //path.lineTo(0, 240);
        //path.lineTo(60, 60);
        //path.lineTo(240, 0);
        //path.lineTo(60, -60);
        path.close();

        canvas.drawPath(path, linkPaint);

        linkPaint.setTextSize(42);
        canvas.drawText("-X -Y", -getWidth() / 4, -getHeight() / 4, linkPaint);
        canvas.drawText("X Y", getWidth() / 4, getHeight() / 4, linkPaint);
        canvas.drawText("-X Y", -getWidth() / 4, getHeight() / 4, linkPaint);
        canvas.drawText("X -Y", getWidth() / 4, -getHeight() / 4, linkPaint);
    }

    //雷达矩阵
    private void drawRedar(Canvas canvas) {
        Path path = new Path();
        float r = Math.max(centerX, centerY) / (data.length - 1);
        for (int y = 1; y < data.length; y++) {
            float curR = r * y;
            path.reset();
            for (int z = 0; z < data.length; z++) {
                if (z == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    float llx = (float) (centerX + curR * Math.cos(z * angle));
                    float lly = (float) (centerX + curR * Math.sin(z * angle));
                    path.lineTo(llx, lly);
                }
            }
            path.close();
            canvas.drawPath(path, linkPaint);
        }
    }
}
