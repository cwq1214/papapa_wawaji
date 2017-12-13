package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by chenweiqi on 2017/12/11.
 */

public class RoundLinearLayout extends LinearLayout {
    private float roundLayoutRadius = 28f;
    private Path roundPath;
    private RectF rectF;
    private Paint borderPaint;

    public RoundLinearLayout(Context context) {
        this(context,null);
    }

    public RoundLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }


    private void init(){
        //如果你继承的是ViewGroup,注意此行,否则draw方法是不会回调的;
        setWillNotDraw(false);
        roundPath = new Path();
        rectF = new RectF();
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(12);

    }
    private void setRoundPath() {
        //添加一个圆角矩形到path中, 如果要实现任意形状的View, 只需要手动添加path就行
        roundPath.addRoundRect(rectF, roundLayoutRadius, roundLayoutRadius, Path.Direction.CW);

    }

    public void setRoundLayoutRadius(float roundLayoutRadius) {
        this.roundLayoutRadius = roundLayoutRadius;
        setRoundPath();
        postInvalidate();
//        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(0f, 0f, w, h);
        setRoundPath();
    }

    @Override
    public void draw(Canvas canvas) {

        if (roundLayoutRadius > 0f) {
            canvas.clipPath(roundPath);
        }

        super.draw(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        if (roundLayoutRadius > 0f){
            canvas.clipPath(roundPath);
        }
        super.dispatchDraw(canvas);

    }
}
