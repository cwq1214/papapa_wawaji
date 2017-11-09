package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.view.widget.LabelAndTextItem;

import butterknife.BindView;
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
    ImageView imgBGMControl;
    @BindView(R.id.img_voiceControl)
    ImageView imgVoiceControl;
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


        setFunctionImage(R.mipmap.message);
        showFunctionImage();

    }

    @OnClick({R.id.v_toBalance, R.id.v_toAddress, R.id.v_toOrder, R.id.img_BGMControl, R.id.img_voiceControl, R.id.v_toGuidance, R.id.v_toAboutUs, R.id.v_toContractUs, R.id.v_toFeedback, R.id.v_viewVersion, R.id.text_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_toBalance:
                IntentHelper.openMyCoinActivity(getContext());
                break;
            case R.id.v_toAddress:
                IntentHelper.openAddressListActivity(getContext());
                break;
            case R.id.v_toOrder:
                IntentHelper.openOrderListActivity(getContext());
                break;
            case R.id.img_BGMControl:
                break;
            case R.id.img_voiceControl:
                break;
            case R.id.v_toGuidance:
                break;
            case R.id.v_toAboutUs:
                break;
            case R.id.v_toContractUs:
                break;
            case R.id.v_toFeedback:
                break;
            case R.id.v_viewVersion:
                break;
            case R.id.text_logout:
                break;
        }
    }
}
