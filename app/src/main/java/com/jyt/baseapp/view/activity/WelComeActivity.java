package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.CountDownUtil;
import com.jyt.baseapp.util.FinishActivityManager;
import com.jyt.baseapp.util.UserInfo;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by chenweiqi on 2017/11/13.
 */
@ActivityAnnotation(showActionBar = false)
public class WelComeActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (FinishActivityManager.getManager().isInStack(MainActivity.class)){
//            finish();
//            return;
//        }

//        if (true){
//            throw new RuntimeException("boom");
//        }

        CountDownUtil countDownUtil = new CountDownUtil(getContext(),2,1000);
        countDownUtil.setCountDownCallback(new CountDownUtil.CountDownCallback() {
            @Override
            public void countDownCallback(boolean finish, int currentCount) {
                if (finish){
                    if (UserInfo.isLogin()){
                        IntentHelper.openMainActivity(getContext());
                    }else {
                        IntentHelper.openLoginActivity(getContext());
                    }
                    finish();
                }
            }
        });
        countDownUtil.start();


    }
}
