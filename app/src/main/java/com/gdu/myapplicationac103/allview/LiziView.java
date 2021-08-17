package com.gdu.myapplicationac103.allview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:
 * Created by ZhDu on 2021/4/21 17:50.
 */
public class LiziView extends View {
    private static final String TAG = "LiziView";
    Paint paint;
    List<Particle> particles = new ArrayList<>();
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
    Thread handler = new Thread("") {

        @Override
        public void run() {
            super.run();

        }

    };

    public LiziView(Context context) {
        super(context);
        init();
    }

    public LiziView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    /**
     * 初始化 N 个小点
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w:" + w + "  h:" + h);
        for (int i = 0; i < 3000; i++) {
            float radius = (new Random().nextInt(20) + 10);
            particles.add(
                    new Particle(
                            new Random().nextInt(getWidth()),
                            new Random().nextInt(getHeight()),
                            radius / 10,
                            255));
        }
        initF();
        valueAnimator.start();
    }


    //region 属性动画

    /**
     * 定义属性动画
     */
    private void initF() {
        valueAnimator.setDuration(5000);
        //valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                updateParticle((Float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });
    }


    /**
     * 定义属性动画 使y轴在一定范围内随机下落
     *
     * @param value
     */
    private void updateParticle(Float value) {
        float h = getHeight();
        float alphaB = (255 / h);
        for (int i = 0; i < particles.size(); i++) {
            float y = (particles.get(i).y += new Random().nextInt(3) + 1);
            if (y >= getHeight()) {
                y = 0;
                float radius = (new Random().nextInt(20) );
                particles.get(i).radius = radius/ 10;
            }
            particles.get(i).y = y;
            particles.get(i).alpha = (int) (255 - (y * alphaB));
        }
    }

    //endregion

    /**
     * onDraw只需要负责绘制即可
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 0, 0, 0);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        for (Particle p : particles) {
            paint.setAlpha(p.alpha);
            canvas.drawCircle(p.x, p.y, p.radius, paint);
            Log.d(TAG, "onDraw: " + p.toString());
        }
    }


    class Particle {

        float x;
        float y;
        float radius;
        int alpha;

        public Particle(float x, float y, float radius, int alpha) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.alpha = alpha;
        }

        @Override
        public String toString() {
            return "Particle{" +
                    "x=" + x +
                    ", y=" + y +
                    ", radius=" + radius +
                    ", alpha=" + alpha +
                    '}';
        }
    }


}
