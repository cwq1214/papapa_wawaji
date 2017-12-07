package com.jyt.baseapp.view.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.L;

/**
 * 倒计时圆形进度
 * Created by chenweiqi on 2017/11/10.
 */

public class CircleProgressView extends View {

    Paint f_paint;
    Paint b_paint;

    //最小直径
    int minDiameter = 50;//px

    int progressRadius=0;

    int duration = 30000;//ms

    int paintWidth;

    ValueAnimator animator;

    RectF rectF;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        animator = ValueAnimator.ofInt(0,-360);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                progressRadius = animatedValue;
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                progressRadius = from;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public void initPaint(){

        paintWidth = DensityUtil.dpToPx(getContext(),8);

        f_paint = new Paint();
        f_paint.setStyle(Paint.Style.STROKE);
        f_paint.setColor(Color.WHITE);
        f_paint.setStrokeWidth(paintWidth);

        b_paint = new Paint();
        b_paint.setStyle(Paint.Style.STROKE);
        b_paint.setARGB(90,255,255,255);
        b_paint.setStrokeWidth(paintWidth);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int computedWidth = resolveMeasured(widthMeasureSpec, minDiameter);
        int computedHeight = resolveMeasured(heightMeasureSpec, minDiameter);

        setMeasuredDimension(computedWidth, computedHeight);
    }
    private int resolveMeasured(int measureSpec, int desired)
    {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }

    public void setTimes(int ms){
        duration = ms;
    }

    public void start(){
        animator.setDuration(duration);
        animator.start();
    }

    public void stop(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animator.pause();
        }else {
            animator.cancel();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rectF == null) {
            rectF = new RectF(getLeft() + paintWidth / 2, getTop() + paintWidth / 2, getRight() - paintWidth / 2, getBottom() - paintWidth / 2);
        }
        canvas.drawArc(rectF, 0, 360, false, b_paint);

        canvas.drawArc(rectF,-90,progressRadius,false,f_paint);
    }
}
