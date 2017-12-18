package com.jyt.baseapp.view.widget;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Api;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class WhiteRefreshView extends FrameLayout implements IBottomView, IHeaderView {
    @BindView(R.id.img_loadingImg)
    ImageView imgLoadingImg;
    @BindView(R.id.text_loadingText)
    TextView textLoadingText;
    @BindView(R.id.text_domain)
    TextView textDomain;
    @BindView(R.id.v_mainlayout)
    LinearLayout vMainlayout;

    String domain = "域名";
    String refreshingText = "正在加载。。。";
    String preRefreshText = "松开立即加载";
    int refreshingImgResId = R.mipmap.black_refresh;
    int preRefreshImgResId = R.mipmap.black_down_arrow;
    ObjectAnimator objectAnimator;

    public WhiteRefreshView(@NonNull Context context) {
        this(context, null);
    }

    public WhiteRefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(getContext()).inflate(R.layout.widget_white_refresh_view, this, true);
        ButterKnife.bind(this);
        objectAnimator =  ObjectAnimator.ofFloat(imgLoadingImg,"Rotation",0,180);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        textLoadingText.setText(preRefreshText);

        textDomain.setText(Api.domainText);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

    }


    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {
        setRefreshingStyle();
        objectAnimator.start();

    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        setUnRefreshStyle();
        objectAnimator.cancel();
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {
        setUnRefreshStyle();
    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onFinish() {
        setUnRefreshStyle();
        objectAnimator.cancel();
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

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setRefreshingText(String refreshingText) {
        this.refreshingText = refreshingText;
    }

    public void setPreRefreshText(String preRefreshText) {
        this.preRefreshText = preRefreshText;
    }

    public void setRefreshingImgResId(int refreshingImgResId) {
        this.refreshingImgResId = refreshingImgResId;
    }

    public void setPreRefreshImgResId(int preRefreshImgResId) {
        this.preRefreshImgResId = preRefreshImgResId;
        this.imgLoadingImg.setImageDrawable(getResources().getDrawable(preRefreshImgResId));
    }
}
