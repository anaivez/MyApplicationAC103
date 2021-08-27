package com.gdu.myapplicationac103.allview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gdu.myapplicationac103.R;

/**
 * Description:
 * Created by Czm on 2021/8/26 11:18.
 */
public class DugoubissView extends BaseView {

    Paint paint;
    Paint handPaint;
    float dx = 0, dy = 0, ux = 0, uy = 0;
    Path path;
    PathMeasure pathMeasure;
    float pos[] = new float[2];
    float tan[] = new float[2];
    float mx, my;
    public DugoubissView(Context context) {
        super(context);
    }
    public DugoubissView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Bitmap mBitmap;
    private Canvas mCanvas;


    @Override
    public void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(38);


        handPaint = new Paint();
        //handPaint.setAntiAlias(true);
        //handPaint.setDither(true);
        handPaint.setStrokeJoin(Paint.Join.ROUND);//设置圆角
        handPaint.setStrokeCap(Paint.Cap.ROUND);
        handPaint.setStyle(Paint.Style.STROKE);
        handPaint.setStrokeWidth(10);//设置画笔宽度
        handPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        path = new Path();
        pathMeasure = new PathMeasure(path, false);

        mOutterBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.wallpaper6);
    }

    private Bitmap mOutterBitmap;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);


        canvas.drawRect(new Rect(0, 0, (int) width, (int) height), paint);
        //canvas.drawBitmap(mOutterBitmap, 0, 0, null);
        canvas.drawText("FA Q", centreX, centreY, paint);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        mCanvas.drawPath(path
                , handPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mx = event.getX();
        my = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
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
                break;
            case MotionEvent.ACTION_UP:
                ux = getX();
                uy = getY();
                break;
        }
        postInvalidate();
        return true;

    }
}
