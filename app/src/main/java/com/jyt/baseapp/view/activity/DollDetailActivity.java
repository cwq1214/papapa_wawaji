package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.helper.IntentHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by v7 on 2017/11/9.
 */
public class DollDetailActivity extends BaseActivity {
    @BindView(R.id.img_doll)
    ImageView imgDoll;
    @BindView(R.id.text_dollName)
    TextView textDollName;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.v_noAddress)
    LinearLayout vNoAddress;
    @BindView(R.id.text_receiver)
    TextView textReceiver;
    @BindView(R.id.text_tel)
    TextView textTel;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.v_addressLayout)
    LinearLayout vAddressLayout;
    @BindView(R.id.v_selAddress)
    FrameLayout vSelAddress;
    @BindView(R.id.text_send)
    TextView textSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doll_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.v_selAddress)
    public void onSelAddressClick(){
        IntentHelper.openAddressListActivityForResult(getContext(),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK){

        }
    }
}
