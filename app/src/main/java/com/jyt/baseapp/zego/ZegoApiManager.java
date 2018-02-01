package com.jyt.baseapp.zego;


import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import com.jyt.baseapp.App;
import com.jyt.baseapp.util.UserInfo;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.constants.ZegoAvConfig;


/**
 * des: zego api管理器.
 */
public class ZegoApiManager {

    private static ZegoApiManager sInstance = null;

    private ZegoLiveRoom mZegoLiveRoom = null;

    private ZegoAvConfig mZegoAvConfig = null;

    private long mAppID = 0;
    private byte[] mSignKey = null;

    private String mUserID = null;

    private ZegoApiManager() {
        mZegoLiveRoom = new ZegoLiveRoom();
    }

    public static ZegoApiManager getInstance() {
        if (sInstance == null) {
            synchronized (ZegoApiManager.class) {
                if (sInstance == null) {
                    sInstance = new ZegoApiManager();
                }
            }
        }
        return sInstance;
    }

    private void initUserInfo(){
        // 初始化用户信息
        mUserID = UserInfo.getToken();
        String userName = UserInfo.getUserName();
//        = PreferenceUtil.getInstance().getUserName()";

        if (TextUtils.isEmpty(mUserID) || TextUtils.isEmpty(userName)) {
            long ms = System.currentTimeMillis();
            mUserID = "wawaji_android_" + ms + "_" + (int)(Math.random() * 100);
            userName = mUserID;

            // 保存用户信息
//            PreferenceUtil.getInstance().setUserID(mUserID);
//            PreferenceUtil.getInstance().setUserName(userName);
        }
        // 必须设置用户信息
        ZegoLiveRoom.setUser(mUserID, userName);
    }


    private void init(long appID, byte[] signKey){

        initUserInfo();

        mAppID = appID;
        mSignKey = signKey;


        // 初始化sdk
        boolean ret = mZegoLiveRoom.initSDK(appID, signKey, App.getApplication());
        if(!ret){
            // sdk初始化失败
            Toast.makeText(App.getApplication(), "Zego SDK初始化失败!", Toast.LENGTH_LONG).show();
        } else {
            // 初始化设置级别为"High"
            mZegoAvConfig = new ZegoAvConfig(ZegoAvConfig.Level.High);
            mZegoLiveRoom.setAVConfig(mZegoAvConfig);
        }
    }

    /**
     * 初始化sdk.
     */
    public void initSDK(){
        long appID = 1407134098;
        byte[] signKey = {
                (byte) 0xbb,(byte)0x43,(byte)0x40,(byte)0xf4,(byte)0x80,(byte)0x1d,(byte)0x70,(byte)(byte)0xa2,(byte)0x5e,(byte)0x04,(byte)0xe7,(byte)0x3a,(byte)0x34,(byte)0x7e,(byte)0x37,(byte)0x09,(byte)0xad,(byte)0x14,(byte)0xd3,(byte)0xa7,(byte)0x1a,(byte)0xe4,(byte)0x9b,(byte)0x97,(byte)0x52,(byte)0x7f,(byte)0x89,(byte)0x65,(byte)0xc2,(byte)0x59,(byte)0x04,(byte)0x79
        };

        init(appID, signKey);
    }

    public void releaseSDK() {
        mZegoLiveRoom.unInitSDK();
    }

    public ZegoLiveRoom getZegoLiveRoom() {
        return mZegoLiveRoom;
    }

    public long getAppID(){
        return mAppID;
    }
}
