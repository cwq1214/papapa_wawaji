package com.jyt.baseapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.jyt.baseapp.R;
import com.jyt.baseapp.bitmapTransformation.PinkBorderTransformation;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class ImageLoader {
    static Context appContext;
    static ImageLoader imageLoader;
    public static void init(Context context){
        appContext = context;
        imageLoader = new ImageLoader();
    }

    public static ImageLoader getInstance(){
        return imageLoader;
    }

    public void load(ImageView imageView, String url){
        Glide.with(appContext).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }
    public void loadRectangle(ImageView imageView, String url){
        Glide.with(appContext).load(url).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }
    public void loadSquare(ImageView imageView, String url){
        Glide.with(appContext).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }
    public void loadHeader(ImageView imageView, String url){
        Glide.with(appContext).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }

    public void loadWhiteRadiusBorder(ImageView imageView, String url){
        Glide.with(appContext).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).diskCacheStrategy(DiskCacheStrategy.NONE).transform(new PinkBorderTransformation(imageView.getContext(),DensityUtil.dpToPx(imageView.getContext(),4),DensityUtil.dpToPx(imageView.getContext(),5), Color.RED)).into(imageView);
    }
}
