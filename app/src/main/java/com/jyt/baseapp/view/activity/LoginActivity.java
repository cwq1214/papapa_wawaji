package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.App;
import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.LoginResult;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.WeChartHelper;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.LoginLogoutModel;
import com.jyt.baseapp.model.impl.LoginLoutModelImpl;
import com.jyt.baseapp.util.CountDownUtil;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.v_layout)
    LinearLayout vLayout;
    CountDownUtil countDownUtil;

    LoginLogoutModel loginModel;

    WeChartHelper weChartHelper;
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
                if (finish) {
                    btnGetCode.setEnabled(true);
                    btnGetCode.setText("获取验证码");
                } else {
                    btnGetCode.setText("(" + currentCount + "s)");
                }
            }
        });

        weChartHelper = new WeChartHelper();
        weChartHelper.init(getContext(), App.weiXin_AppKey);
        weChartHelper.registerToWx();

        weChartHelper.setReceiveUserInfoListener(new WeChartHelper.ReceiveUserInfoListener() {
            @Override
            public void onGotUserInfo(WeChartHelper.WxUser user) {
                if (user==null){
                    return;
                }
                String openId = user.getOpenid();
                String nickName = user.getNickname();
                String img = user.getHeadimgurl();
                L.e("getOpenid "+openId);
                L.e("getNickname "+nickName);
                L.e("getHeadimgurl "+img);
                if (TextUtils.isEmpty(openId) || TextUtils.isEmpty(nickName) || TextUtils.isEmpty(img)){
                    return;
                }
                loginModel.loginByWeiXin(openId, nickName, img, new BeanCallback<BaseJson<LoginResult>>() {
                    @Override
                    public void response(boolean success, BaseJson<LoginResult> response, int id) {
                        if (response.isRet()) {
                            //登录成功 保存用户信息
                            UserInfo.setUserInfo(response.getData());
                            IntentHelper.openMainActivity(getContext());
                            finish();
                            App.setJPushAlias();
                        }
                        T.showShort(getContext(), response.getForUser());
                    }
                });
            }
        });
    }

    @Override
    public List<BaseModel> createModels() {

        List base = new ArrayList();
        base.add(loginModel = new LoginLoutModelImpl());
        return base;
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        String phone = inputPhone.getText().toString();
        String psd = inputPsd.getText().toString();
        String code = inputCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            T.showShort(getContext(), "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(psd)) {
            T.showShort(getContext(), "请输入密码");
            return;
        }

        if ("登录".equals(btnLogin.getText())) {
            loginModel.loginByMobile(phone, psd, new BeanCallback<BaseJson<LoginResult>>() {

                @Override
                public void response(boolean success, BaseJson<LoginResult> response, int id) {
                    if (response.isRet()) {
                        //登录成功 保存用户信息
                        UserInfo.setUserInfo(response.getData());
                        IntentHelper.openMainActivity(getContext());
                        finish();
                    }
                    T.showShort(getContext(), response.getForUser());
                }
            });
        } else {
            if (TextUtils.isEmpty(code)) {
                T.showShort(getContext(), "请输入验证码");
                return;
            }
            loginModel.register(phone, code, psd, new BeanCallback<BaseJson<LoginResult>>() {

                @Override
                public void response(boolean success, BaseJson<LoginResult> response, int id) {
                    if (response.isRet()) {
                        //注册成功
                        setShowMode(true);
                        inputCode.setText("");
                    }
                    T.showShort(getContext(), response.getForUser());
                }
            });
        }
    }


    @OnClick(R.id.btn_getCode)
    public void onGetCodeClick() {
        loginModel.getVerifyCode(inputPhone.getText().toString(), new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()) {
                    countDownUtil.start();
                    btnGetCode.setEnabled(false);
                }
                T.showShort(getContext(), response.getForUser());
            }
        });

    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        setShowMode(false);
    }

    @OnClick(R.id.btn_toLogin)
    public void onToLoginClick() {
        setShowMode(true);
    }

    @OnClick(R.id.btn_forgetPsd)
    public void onForgetPsdClick() {
        IntentHelper.openResetPsdActivity(getContext());
    }

    @OnClick(R.id.btn_wechartLogin)
    public void onWeChartLoginClick() {
        weChartHelper.login();
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


    private void setShowMode(boolean isLogin) {
        vRegisterBtnGroup.setVisibility(!isLogin ? View.VISIBLE : View.GONE);
        vLoginBtnGroup.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        vCodeLayout.setVisibility(!isLogin ? View.VISIBLE : View.GONE);
//        vPsdLayout.setVisibility(isLogin?View.VISIBLE:View.GONE);
        btnLogin.setText(isLogin ? "登录" : "注册");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownUtil.stop();
        weChartHelper.unInit();
    }
}
