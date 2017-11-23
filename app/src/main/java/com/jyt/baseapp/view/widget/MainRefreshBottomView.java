package com.jyt.baseapp.view.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.util.ImageLoader;
import com.lcodecore.tkrefreshlayout.IBottomView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class MainRefreshBottomView extends FrameLayout implements IBottomView {
    @BindView(R.id.img_loadingImg)
    ImageView imgLoadingImg;
    @BindView(R.id.text_loadingText)
    TextView textLoadingText;
    @BindView(R.id.text_domain)
    TextView textDomain;

    String refreshingText = "正在加载。。。";
    String preRefreshText = "松开立即加载下一批";
    int refreshingImgResId = R.mipmap.refresh2;
    int preRefreshImgResId = R.mipmap.loading_down_arrow;

    ObjectAnimator objectAnimator;
    public MainRefreshBottomView(@NonNull Context context) {
        this(context, null);
    }

    public MainRefreshBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_refresh_bottom_view, this, true);
        ButterKnife.bind(this);
        objectAnimator =  ObjectAnimator.ofFloat(imgLoadingImg,"Rotation",0,180);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {
        setRefreshingStyle();
        objectAnimator.start();
    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onFinish() {
        objectAnimator.cancel();
    }

    @Override
    public void reset() {
        setUnRefreshStyle();
    }

    public void setRefreshingStyle(){
        imgLoadingImg.setImageDrawable(getResources().getDrawable(refreshingImgResId));
        textLoadingText.setText(refreshingText);
    }

    public void setUnRefreshStyle(){
        imgLoadingImg.setRotation(0);
        imgLoadingImg.setImageDrawable(getResources().getDrawable(preRefreshImgResId));
        textLoadingText.setText(preRefreshText);
    }

}
