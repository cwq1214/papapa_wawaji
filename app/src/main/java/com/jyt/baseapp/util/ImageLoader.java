package com.jyt.baseapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jyt.baseapp.R;
import com.jyt.baseapp.bitmapTransformation.ImageBorderTransformation;

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
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }
    public void loadRectangle(ImageView imageView, String url){
        Glide.with(imageView.getContext()).load(url).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }
    public void loadSquare(ImageView imageView, String url){
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }
    public void loadHeader(ImageView imageView, String url){
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).centerCrop().into(imageView);
    }

    public void loadWithRadiusBorder(ImageView imageView, String url,int borderRadius_px,int borderWidth_px ,int borderColor){
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.loadinggif).error(R.mipmap.loadingfailed).diskCacheStrategy(DiskCacheStrategy.NONE).transform(new ImageBorderTransformation(imageView.getContext(),borderRadius_px,borderWidth_px,borderColor)).into(imageView);
    }
}
