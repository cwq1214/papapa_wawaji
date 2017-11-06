package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.CountDownUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/6.
 */

public class ResetPsdActivity extends BaseActivity {
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_psd)
    EditText inputPsd;
    @BindView(R.id.v_psdLayout)
    LinearLayout vPsdLayout;
    @BindView(R.id.input_code)
    EditText inputCode;
    @BindView(R.id.btn_getCode)
    TextView btnGetCode;
    @BindView(R.id.v_codeLayout)
    LinearLayout vCodeLayout;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;


    CountDownUtil countDownUtil;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_rest_psd;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countDownUtil = new CountDownUtil(getContext());
        countDownUtil.setCountDownCallback(new CountDownUtil.CountDownCallback() {
            @Override
            public void countDownCallback(boolean finish, int currentCount) {
                if (finish){
                    btnGetCode.setEnabled(true);
                    btnGetCode.setText("获取验证码");
                }else {
                    btnGetCode.setText("("+currentCount+"s)");

                }
            }
        });

    }

    @OnClick(R.id.btn_confirm)
    public void onConfirmClick(){

    }

    @OnClick(R.id.btn_getCode)
    public void onGetCodeClick(){
        countDownUtil.start();
        btnGetCode.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownUtil.stop();
    }
}
