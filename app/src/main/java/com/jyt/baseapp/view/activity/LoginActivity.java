package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.CountDownUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * Created by chenweiqi on 2017/11/6.
 */
@ActivityAnnotation(title = "")
public class LoginActivity extends BaseActivity {
    @BindView(R.id.input_phone)
    EditText inputPhone;
    @BindView(R.id.input_psd)
    EditText inputPsd;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.btn_forgetPsd)
    TextView btnForgetPsd;
    @BindView(R.id.btn_wechartLogin)
    LinearLayout btnWechartLogin;
    @BindView(R.id.v_agree)
    ImageView vAgree;
    @BindView(R.id.text_protocol)
    TextView textProtocol;
    @BindView(R.id.v_psdLayout)
    LinearLayout vPsdLayout;
    @BindView(R.id.input_code)
    EditText inputCode;
    @BindView(R.id.btn_getCode)
    TextView btnGetCode;
    @BindView(R.id.v_codeLayout)
    LinearLayout vCodeLayout;
    @BindView(R.id.v_loginBtnGroup)
    RelativeLayout vLoginBtnGroup;
    @BindView(R.id.btn_toLogin)
    TextView btnToLogin;
    @BindView(R.id.v_registerBtnGroup)
    RelativeLayout vRegisterBtnGroup;

    CountDownUtil countDownUtil;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

    @OnClick(R.id.btn_getCode)
    public void onGetCodeClick(){
        countDownUtil.start();
        btnGetCode.setEnabled(false);
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        setShowMode(false);
    }

    @OnClick(R.id.btn_toLogin)
    public void onToLoginClick(){
        setShowMode(true);
    }

    @OnClick(R.id.btn_forgetPsd)
    public void onForgetPsdClick() {
        IntentHelper.openResetPsdActivity(getContext());
    }

    @OnClick(R.id.btn_wechartLogin)
    public void onWeChartLoginClick() {

    }

    @OnClick(R.id.v_agree)
    public void onAgreeClick() {
        if ("1".equals(vAgree.getTag())) {
            vAgree.setImageDrawable(getResources().getDrawable(R.mipmap.uncheck));
            vAgree.setTag("0");
        } else {
            vAgree.setImageDrawable(getResources().getDrawable(R.mipmap.checked));
            vAgree.setTag("1");
        }
    }



    private void setShowMode(boolean isLogin){
        vRegisterBtnGroup.setVisibility(!isLogin?View.VISIBLE:View.GONE);
        vLoginBtnGroup.setVisibility(isLogin?View.VISIBLE:View.GONE);
        vCodeLayout.setVisibility(!isLogin?View.VISIBLE:View.GONE);
        vPsdLayout.setVisibility(isLogin?View.VISIBLE:View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownUtil.stop();
    }
}
