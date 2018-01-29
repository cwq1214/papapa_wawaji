package com.jyt.baseapp.model.impl;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.model.OrderListModel;
import com.jyt.baseapp.util.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by chenweiqi on 2017/11/15.
 */

public class OrderListModelImpl implements OrderListModel {
    Context context ;
    Handler handler;
    @Override
    public void onStart(Context context) {
        this.context = context;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(context);
        handler=null;
    }

    @Override
    public void getOrderList(int type, String sequeue, Callback callback) {
        if (sequeue==null){
            sequeue = "";
        }
        OkHttpUtils.get().url(Api.domain+Api.orderList).addParams("type",type+"").addParams("sequeue",sequeue).tag(context).build().execute(callback);
    }

    @Override
    public void getOrderDetail(String orderNo, Callback callback) {
        OkHttpUtils.get().url(Api.domain+Api.orderDetail).addParams("orderNo",orderNo).tag(context).build().execute(callback);
    }

    @Override
    public void receiveOrder(String orderNo, Callback callback) {
        OkHttpUtils.post().url(Api.domain+Api.receiveOrder).addParams("orderNo",orderNo).tag(context).build().execute(callback);
    }

    @Override
    public void submitOrder(final String orderNo, final String addressId, final Callback callback) {

//        OkHttpUtils.post().url(Api.domain+Api.submitOrder).addParams("orderNo",orderNo).addParams("addressId",addressId).tag(context).build().execute(callback);
                String bodyString = "orderNo[]="+orderNo+"&addressId="+addressId;

                OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
                RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),bodyString);
                Request request = new Request.Builder().url(Api.domain+Api.submitOrder).post(body).build();

                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(final Call call, final IOException e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onError(call,e,0);
                                }
                            });
                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String bodyText = response.body().string();
                                        BaseJson baseJson;
                                        baseJson = new Gson().fromJson(bodyText,new TypeToken<BaseJson>(){}.getType());
                                        callback.onResponse(baseJson,0);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        callback.onResponse(new BaseJson(),0);
                                    }
                                }
                            });

                        }
                    });



    }

    @Override
    public void submitOrder(List<String> orderNo, String addressId, final Callback callback) {


        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<orderNo.size();i++){
            stringBuffer.append("orderNo[]="+orderNo.get(i));
            if (i!=orderNo.size()-1){
                stringBuffer.append("&");
            }
        }
        stringBuffer.append("&addressId="+addressId);
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),stringBuffer.toString());
        Request request = new Request.Builder().url(Api.domain+Api.submitOrder).post(body).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(call,e,0);

                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String body = response.body().string();
                            BaseJson baseJson = new Gson().fromJson(body,new TypeToken<BaseJson>(){}.getType());
                            callback.onResponse(baseJson,0);
                        }catch (Exception e){
                            callback.onResponse(new BaseJson(),0);

                        }
                    }
                });


            }
        });
    }

    @Override
    public void exchangeToCoin(List<String> orderNo, final Callback callback) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<orderNo.size();i++){
            stringBuffer.append("orderNo[]="+orderNo.get(i));
            if (i!=orderNo.size()-1){
                stringBuffer.append("&");
            }
        }
        System.out.println(stringBuffer.toString());
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),stringBuffer.toString());
        Request request = new Request.Builder().url(Api.domain+Api.exchangeCoin).post(body).build();


        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(call,e,0);

                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String body = response.body().string();
                            BaseJson baseJson = new Gson().fromJson(body,new TypeToken<BaseJson>(){}.getType());
                            callback.onResponse(baseJson,0);

                        } catch (IOException e) {
                            e.printStackTrace();
                            callback.onResponse(new BaseJson(),0);

                        }
                    }
                });
            }

        });

    }
}
