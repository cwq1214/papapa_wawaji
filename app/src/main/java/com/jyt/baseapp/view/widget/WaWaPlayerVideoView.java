package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.jyt.baseapp.util.T;
import com.netease.neliveplayer.sdk.NELivePlayer;

import java.io.IOException;

/**
 * Created by chenweiqi on 2017/11/23.
 */

public class WaWaPlayerVideoView {
    private NELivePlayer mLivePlayer = null;
    Context mContext;
    public WaWaPlayerVideoView(Context context){
        mContext = context;
        mLivePlayer = NELivePlayer.create(mContext); // mContext为VideoView的上下文信息
        setUpVideoPlay();
    }

    private void setUpVideoPlay(){
        mLivePlayer = NELivePlayer.create(mContext); // mContext为VideoView的上下文信息
        mLivePlayer.setBufferStrategy(2); //0为直播极速模式，1为直播低延时模式，2为直播流畅模式, 3为点播抗抖动模式
        mLivePlayer.setScreenOnWhilePlaying(true); //true:播放过程中屏幕长亮

    }

    public void setHolder(SurfaceHolder mSurfaceHolder){
        mLivePlayer.setDisplay(mSurfaceHolder); //设置显示surface
    }

    public void setHolder(Surface mSurface){
        mLivePlayer.setSurface(mSurface); //设置显示surface
    }

    public boolean setDataSource(String url){
        try {
            return mLivePlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
            T.showShort(mContext,e.getMessage());
        }
        return false;
    }

    public void changeUrl(String url){
        mLivePlayer.switchContentUrl(url);//切换播放地址功能

    }
}
