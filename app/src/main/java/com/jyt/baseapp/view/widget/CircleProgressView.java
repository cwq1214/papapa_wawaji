package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenweiqi on 2017/11/10.
 */

public class CircleProgressView extends View {
    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
