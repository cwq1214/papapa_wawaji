package com.jyt.baseapp.view.activity;

import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;

/**
 * Created by chenweiqi on 2017/11/6.
 */
@ActivityAnnotation(showActionBar = false)
public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected View getContentView() {
        return null;
    }
}
