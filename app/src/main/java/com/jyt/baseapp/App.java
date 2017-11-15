package com.jyt.baseapp;

import android.app.Application;
import android.widget.LinearLayout;

import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public class App  extends Application {
    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    private boolean isDebug = false;

    static App app;

    @Override
    public void onCreate() {
        super.onCreate();


        initUtil();
        app = this;
        UserInfo.init(getApplicationContext());
        UserInfo.setToken("2");
    }


    private void initUtil() {
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

}
