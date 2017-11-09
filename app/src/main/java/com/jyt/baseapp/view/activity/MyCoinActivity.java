package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by v7 on 2017/11/8.
 */

public class MyCoinActivity extends BaseActivity {
    @BindView(R.id.text_currentCoin)
    TextView textCurrentCoin;
    @BindView(R.id.v_coinTransactionDetails)
    RecyclerView vCoinTransactionDetails;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coin;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        vCoinTransactionDetails.setAdapter();
    }
}
