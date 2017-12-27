package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.bean.json.PersonalInfo;
import com.jyt.baseapp.helper.IntentKey;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/12/21.
 */

public class ContactUsActivity extends BaseActivity {
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.text_contactUs)
    TextView textContactUs;
    @BindView(R.id.text_domain)
    TextView textDomain;

    PersonalInfo personalInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personalInfo = getIntent().getParcelableExtra(IntentKey.KEY_DATA);

        textDomain.setText(Api.domainText);

        if (personalInfo!=null){
            textContactUs.setText(personalInfo.getContact());
        }
    }
}
