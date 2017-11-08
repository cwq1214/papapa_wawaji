package com.jyt.baseapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.jyt.baseapp.R;

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
}
