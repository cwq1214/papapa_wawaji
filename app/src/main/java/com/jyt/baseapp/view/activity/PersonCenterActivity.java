package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.App;
import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.PersonalInfo;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.widget.LabelAndTextItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class PersonCenterActivity extends BaseActivity {
    @BindView(R.id.img_header)
    ImageView imgHeader;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_record)
    TextView textRecord;
    @BindView(R.id.v_toBalance)
    LabelAndTextItem vToBalance;
    @BindView(R.id.v_toAddress)
    LabelAndTextItem vToAddress;
    @BindView(R.id.v_toOrder)
    LabelAndTextItem vToOrder;
    @BindView(R.id.img_BGMControl)
    CheckBox imgBGMControl;
    @BindView(R.id.img_voiceControl)
    CheckBox imgVoiceControl;
    @BindView(R.id.v_toGuidance)
    LabelAndTextItem vToGuidance;
    @BindView(R.id.v_toAboutUs)
    LabelAndTextItem vToAboutUs;
    @BindView(R.id.v_toContractUs)
    LabelAndTextItem vToContractUs;
    @BindView(R.id.v_toFeedback)
    LabelAndTextItem vToFeedback;
    @BindView(R.id.v_viewVersion)
    LabelAndTextItem vViewVersion;
    @BindView(R.id.text_logout)
    TextView textLogout;
    @BindView(R.id.v_toMyWaWa)
    LabelAndTextItem vToMyWaWa;
    PersonalInfoModel personalInfoModel;

    PersonalInfo personalInfo;
    @BindView(R.id.v_toShareCode)
    LabelAndTextItem vToShareCode;
    @BindView(R.id.v_toInputCode)
    LabelAndTextItem vToInputCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        imgBGMControl.setChecked(UserInfo.getRoomBgEnable());
        imgVoiceControl.setChecked(UserInfo.getRoomEffectBgEnable());
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadView();

    }

    private void reloadView() {

//        setFunctionImage(R.mipmap.message);
//        showFunctionImage();

        textLogout.setText(UserInfo.isLogin() ? "退出登录" : "登录");
        if (UserInfo.isLogin()) {
            getUserInfo();
        } else {
            textName.setText("未登录");
            textRecord.setText("");
            imgHeader.setImageDrawable(null);
        }
    }

    private void getUserInfo() {
        personalInfoModel.getUserInfo(new BeanCallback<BaseJson<PersonalInfo>>() {
            @Override
            public void response(boolean success, BaseJson<PersonalInfo> response, int id) {
                if (response.isRet()) {
                    setUserInfo(response.getData());
                }
            }
        });
    }

    //根据个人信息填充界面
    private void setUserInfo(PersonalInfo info) {
        personalInfo = info;
        textName.setText(info.getNickname());
        textRecord.setText(String.format("共抓中  %s 次", info.getGetCount()));
        ImageLoader.getInstance().loadHeader(imgHeader, info.getUserImg());
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(personalInfoModel = new PersonalInfoModelImpl());
        return list;
    }

    @OnClick({R.id.v_toShareCode,R.id.v_toInputCode,R.id.img_header, R.id.v_toMyWaWa, R.id.img_function, R.id.v_toBalance, R.id.v_toAddress, R.id.v_toOrder, R.id.v_toGuidance, R.id.v_toAboutUs, R.id.v_toContractUs, R.id.v_toFeedback, R.id.v_viewVersion, R.id.text_logout})
    public void onViewClicked(View view) {
        int vid = view.getId();
        if (!UserInfo.isLogin() && (vid == R.id.v_toShareCode||vid == R.id.v_toInputCode ||vid == R.id.img_header || vid == R.id.v_toBalance || vid == R.id.v_toAddress || vid == R.id.v_toOrder || vid == R.id.img_function || vid == R.id.v_toMyWaWa)) {
            IntentHelper.openLoginActivity(getContext());
            return;
        }


        switch (vid) {
            case R.id.img_header:
                if (personalInfo != null)
                    IntentHelper.openModifyUserInfoActivity(personalInfo.getNickname(), personalInfo.getUserImg(), getContext());
                break;
            case R.id.v_toMyWaWa:
                IntentHelper.openMyWaWaActivity(getContext());
                break;
            case R.id.v_toBalance:
                IntentHelper.openMyCoinActivity(getContext());
                break;
            case R.id.v_toAddress:
                IntentHelper.openAddressListActivity(getContext());
                break;
            case R.id.v_toOrder:
                IntentHelper.openOrderListActivity(getContext());
                break;
            case R.id.v_toAboutUs:
                IntentHelper.openAboutUsActivity(getContext(), personalInfo);
                break;
            case R.id.v_toContractUs:
                IntentHelper.openContactUsActivity(getContext(), personalInfo);
                break;
            case R.id.v_toGuidance:
                T.showShort(getContext(), "敬请期待");
                break;
            case R.id.v_toFeedback:
                IntentHelper.openFeedbackQuesActivity(getContext());
                break;
            case R.id.v_viewVersion:
                IntentHelper.openVersionInfoActivity(getContext());
                break;
            case R.id.v_toShareCode:
                IntentHelper.openBrowserInviteCodeActivity(getContext(),personalInfo);
                break;
            case R.id.v_toInputCode:
                IntentHelper.openInputInviteCodeActivity(getContext(),personalInfo);
                break;
            case R.id.text_logout:
                if (UserInfo.isLogin()) {
                    UserInfo.clearUserInfo();
                    reloadView();
                    App.unInitJPush();
                } else {
                    IntentHelper.openLoginActivity(getContext());
                }

                break;
        }
    }

    @OnCheckedChanged({R.id.img_BGMControl, R.id.img_voiceControl})
    public void onBgCheckChanged(CompoundButton view, boolean checked) {
        if (view == imgBGMControl) {
            UserInfo.setRoomBgEnable(checked);
        } else if (view == imgVoiceControl) {
            UserInfo.setRoomEffectBgEnable(checked);
        }
    }

}
