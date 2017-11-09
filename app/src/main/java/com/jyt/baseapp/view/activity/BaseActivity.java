package com.jyt.baseapp.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.util.FinishActivityManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/5/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    FinishActivityManager manager = FinishActivityManager.getManager();


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_function)
    TextView textFunction;
    @BindView(R.id.v_actionBar)
    RelativeLayout vActionBar;
    @BindView(R.id.v_main)
    LinearLayout vMain;
    @BindView(R.id.img_function)
    ImageView imgFunction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        //绑定baseActivity内的控件
        try {
            ButterKnife.bind(this);
        } catch (Exception e) {
        }


        View contentView = getContentView();
        if (contentView != null) {
            vMain.addView(contentView);
        }
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            vMain.addView(LayoutInflater.from(getContext()).inflate(layoutId, vMain, false));
        }

        //绑定子类控件
        ButterKnife.bind(this);


        getAnnotation();

        if (!TextUtils.isEmpty(getTitle())) {
            textTitle.setText(getTitle());
        }
        manager.addActivity(this);

    }

    private void getAnnotation() {
        if (textTitle == null || imgBack == null || textFunction == null) {
            return;
        }

        ActivityAnnotation annotation = this.getClass().getAnnotation(ActivityAnnotation.class);
        System.out.println(annotation);
        if (annotation == null) {
            return;
        }
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            Object invoke = null;
            try {
                invoke = method.invoke(annotation);
                if (method.getName().equals("showBack")) {
                    imgBack.setVisibility(((boolean) invoke) ? View.VISIBLE : View.GONE);
                } else if (method.getName().equals("title")) {
                    textTitle.setText((CharSequence) invoke);
                } else if (method.getName().equals("showFunctionText")) {
                    textFunction.setVisibility(((boolean) invoke) ? View.VISIBLE : View.GONE);
                } else if (method.getName().equals("functionText")) {
                    textFunction.setText((CharSequence) invoke);
                } else if (method.getName().equals("showActionBar")) {
                    vActionBar.setVisibility((boolean) invoke ? View.VISIBLE : View.GONE);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTextTitle(String text) {
        textTitle.setText(text);
    }

    public void showBackBtn() {
        imgBack.setVisibility(View.VISIBLE);
    }

    public void hideBackBtn() {
        imgBack.setVisibility(View.INVISIBLE);

    }

    public void setFunctionText(String text) {
        textFunction.setText(text);
    }

    public void showFunctionText() {
        textFunction.setVisibility(View.VISIBLE);
    }

    public void showFunctionImage(){
        imgFunction.setVisibility(View.VISIBLE);
    }

    public void hideFunctionImage(){
        imgFunction.setVisibility(View.GONE);
    }

    public void hideFunctionText() {
        textFunction.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.img_back)
    public void onBackClick() {
        onBackPressed();
    }

    public void setFunctionImage(int resId){
        imgFunction.setImageDrawable(getResources().getDrawable(resId));
    }

    @OnClick({R.id.text_function,R.id.img_function})
    public void onFunctionClick() {

    }

    /**
     * 隐藏ActionBar
     */
    public void HideActionBar() {
        vActionBar.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.finishActivity(this);
    }

    public Context getContext() {
        return this;
    }

    public Activity getActivity(){
        return this;
    }

    abstract protected int getLayoutId();

    abstract protected View getContentView();
}
