package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.view.widget.LabelAndTextItem;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.v_tpGuidance)
    LabelAndTextItem vTpGuidance;
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

}
