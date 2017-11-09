package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.helper.IntentKey;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.text_name)
    EditText textName;
    @BindView(R.id.text_tel)
    EditText textTel;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.v_selAddress)
    RelativeLayout vSelAddress;
    @BindView(R.id.text_addressDetail)
    EditText textAddressDetail;
    @BindView(R.id.img_setDefault)
    ImageView imgSetDefault;

    Parcelable address;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        address = getIntent().getParcelableExtra(IntentKey.KEY_ADDRESS);
    }
}
