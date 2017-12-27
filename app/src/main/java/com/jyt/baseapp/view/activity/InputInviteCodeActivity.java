package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.PersonalInfo;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/12/25.
 */

public class InputInviteCodeActivity extends BaseActivity {
    @BindView(R.id.input_inviteCode)
    EditText inputInviteCode;
    @BindView(R.id.text_inputInviteCodeDesc)
    TextView textInputInviteCodeDesc;
    @BindView(R.id.text_submit)
    TextView textSubmit;
    @BindView(R.id.text_inputInviteCodeDesc2)
    TextView textInputInviteCodeDesc2;
    @BindView(R.id.text_domain)
    TextView textDomain;

    PersonalInfo personalInfo;

    PersonalInfoModel personalInfoModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_intite_code;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(personalInfoModel = new PersonalInfoModelImpl());
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        textDomain.setText(Api.domainText);

        personalInfo = getIntent().getParcelableExtra(IntentKey.KEY_DATA);
        if (personalInfo != null) {

            textInputInviteCodeDesc.setText(personalInfo.getInviteContent());

            textInputInviteCodeDesc2.setText(personalInfo.getInviteContentDown());
        }
    }

    @OnClick(R.id.text_submit)
    public void onViewClicked() {
        String inviteCode = inputInviteCode.getText().toString();
        if (TextUtils.isEmpty(inviteCode)){
            T.showShort(getContext(),"请输入邀请码");
            return;
        }


        personalInfoModel.submitInviteCode(inviteCode, new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()){
                    T.showShort(getContext(),response.getForUser());
                }else {
                    T.showShort(getContext(),response.getForUser());
                }
            }
        });

    }
}
