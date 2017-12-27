package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/12/21.
 */

public class FeedbackQuesActivity extends BaseActivity {

    @BindView(R.id.input_ques)
    EditText inputQues;
    @BindView(R.id.input_contactWay)
    EditText inputContactWay;
    @BindView(R.id.text_submit)
    TextView textSubmit;

    PersonalInfoModel personalInfoModel ;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_ques;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(personalInfoModel = new PersonalInfoModelImpl());
        return list;
    }

    @OnClick(R.id.text_submit)
    public void onViewClicked() {
        String inputQuesText = inputQues.getText().toString();
        String inputContactWayText = inputContactWay.getText().toString();

        if (TextUtils.isEmpty(inputQuesText)){
            T.showShort(getContext(),"请输入问题");
            return;
        }
        if (TextUtils.isEmpty(inputContactWayText)){
            T.showShort(getContext(),"请输入联系方式");
            return;
        }


        personalInfoModel.submitFeedBack(inputQuesText, inputContactWayText, new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()){
                    T.showShort(getContext(),"提交成功");
                    onBackPressed();
                }else {
                    T.showShort(getContext(),response.getForUser());

                }
            }
        });
    }
}
