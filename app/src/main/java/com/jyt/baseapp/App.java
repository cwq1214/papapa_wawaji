package com.jyt.baseapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.json.User;
import com.jyt.baseapp.util.FinishActivityManager;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.OkHttpPostInterceptor;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.zego.ZegoApiManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public class App  extends Application {

    public static String weiXin_AppKey = "wx3b44ee4ad08755b6";
    public static String weiXin_AppSecret = "83dddf25ffe14ca901239b49ea241142";

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    private boolean isDebug = false;

    static App app;

    private static final int MSG_SET_ALIAS = 1;
    static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_SET_ALIAS){
                setJPushAlias();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        PlatformConfig.setWeixin(weiXin_AppKey, weiXin_AppSecret);

        UMConfigure.init(this,"5a40ec83b27b0a47d900007c"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setCatchUncaughtExceptions(true);

        initUtil();
        UserInfo.init(getApplicationContext());

        ZegoApiManager.getInstance().getZegoLiveRoom().setAudioDeviceMode(ZegoConstants.AudioDeviceMode.General);
        ZegoLiveRoom.setTestEnv(false);
        ZegoLiveRoom.setVerbose(BuildConfig.DEBUG);
        ZegoApiManager.getInstance().initSDK();
        initJPush();
        if (UserInfo.isLogin()){
            setJPushAlias();
        }

        OkHttpUtils.get().url("http://119.23.66.37/jyt/index/bmc").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean openApp= jsonObject.getBoolean("data");
                    if (!openApp){
                        FinishActivityManager.getManager().finishAllActivity();
                        System.exit(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    private void initUtil() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        LoggerInterceptor interceptor = new LoggerInterceptor("--HTTP--", true);
        builder.addInterceptor(interceptor);
//        builder.hostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        }).sslSocketFactory(createSSLSocketFactory());

        builder.addInterceptor(new OkHttpPostInterceptor());
        //统一请求头添加header
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (UserInfo.getToken()!=null) {
                    Request request = chain.request().newBuilder().addHeader("tokenSession", UserInfo.getToken()).build();
                    return chain.proceed(request);
                }
                return chain.proceed(chain.request());
           }
        });

        OkHttpUtils.initClient(builder.build());

        ImageLoader.init(getApplicationContext());
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static App getApplication(){
        return app;
    }


    private void initJPush(){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        setJPushAlias();

    }

    public static void unInitJPush(){
        JPushInterface.deleteAlias(App.getApplication(),0);
    }

    public static void setJPushAlias(){
        if (!TextUtils.isEmpty(UserInfo.getToken())) {
            JPushInterface.setAlias(app, UserInfo.getToken(), new TagAliasCallback() {
                @Override
                public void gotResult(int code, String s, Set<String> set) {
                    String TAG = "JPush";
                    String logs ;
                    switch (code) {
                        case 0:
                            logs = "Set tag and alias success";
                            Log.i(TAG, logs);
                            // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                            break;
                        case 6002:
                            logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                            Log.i(TAG, logs);
                            handler.sendMessageDelayed(handler.obtainMessage(MSG_SET_ALIAS), 1000 * 60);

                            break;
                        default:
                            logs = "Failed with errorCode = " + code;
                            Log.e(TAG, logs);
                    }
                }
            });
        }
    }
}
