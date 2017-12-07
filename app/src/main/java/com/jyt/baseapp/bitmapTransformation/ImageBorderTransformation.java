package com.jyt.baseapp.bitmapTransformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.jyt.baseapp.R;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.L;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public class ImageBorderTransformation extends BitmapTransformation {
    Context mContext;
    int radius;
    Paint borderPaint;
    int color;
    float borderWidth;

    Paint bitmapPaint;
    public ImageBorderTransformation(Context context, int radius, float borderWidth, int color) {
        super(context);
        this.mContext = context;
        this.radius = radius;
        this.color = color;
        this.borderWidth = borderWidth;

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(color);

        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        return drawBorder(pool,toTransform);
    }

    private Bitmap drawBorder(BitmapPool pool, Bitmap source){
        if (source==null){
            return null;
        }
        //radius 要再乘以density 才能得到正确值
        float radius =(this.radius);

        Bitmap result = pool.get(source.getWidth(),source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);

        //圆角矩形
        RectF rectF = new RectF(0+(borderWidth/2), 0+(borderWidth/2), result.getWidth()-(borderWidth/2), result.getHeight()-(borderWidth/2));
        borderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRoundRect(rectF,radius,radius,borderPaint);

        //图片
        Rect bitmapSrcRect = new Rect(0, 0, source.getWidth(), source.getHeight());
        Rect bitmapDesRect = new Rect((int) (0+(borderWidth/2)), (int)(0+(borderWidth/2)), (int)(result.getWidth()-(borderWidth/2)),(int)(result.getHeight()-(borderWidth/2)));
        canvas.drawBitmap(source,bitmapSrcRect,bitmapDesRect,bitmapPaint);

        //边框
        float border = borderWidth/2;
        RectF rectF1 = new RectF(0+(border), 0+(border), result.getWidth()-(border), result.getHeight()-(border));
        borderPaint.setStyle(Paint.Style.STROKE);
        //setStrokeWidth 要再乘以density 才能得到正确值
        borderPaint.setStrokeWidth(borderWidth);
        canvas.drawRoundRect(rectF1,radius,radius,borderPaint);

        if (!source.isRecycled())
            source.recycle();

        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
