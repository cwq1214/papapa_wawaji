package com.jyt.baseapp;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.OkHttpPostInterceptor;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.zego.ZegoApiManager;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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


        initUtil();
        app = this;
        UserInfo.init(getApplicationContext());
        UserInfo.setToken("3");

        ZegoLiveRoom.setTestEnv(true);
        ZegoLiveRoom.setVerbose(BuildConfig.DEBUG);
        ZegoApiManager.getInstance().initSDK();
        initJPush();
        if (UserInfo.isLogin()){
            setJPushAlias();
        }
    }



    private void initUtil() {

//        WeChartHelper.getInstance().init(getApplicationContext(),weiXin_AppKey);
//        WeChartHelper.getInstance().registerToWx();
//        Hawk.init(getApplicationContext()).setLogInterceptor(new LogInterceptor() {
//            @Override
//            public void onLog(String message) {
//                if (isDebug()) {
//                    L.e(message);
//                }
//            }
//        }).build();


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        LoggerInterceptor interceptor = new LoggerInterceptor("--HTTP--", true);
        builder.addInterceptor(interceptor).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).sslSocketFactory(createSSLSocketFactory());

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
