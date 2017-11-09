package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.CoinTransactionDetailsAdapter;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by v7 on 2017/11/8.
 */

public class MyCoinActivity extends BaseActivity {
    @BindView(R.id.text_currentCoin)
    TextView textCurrentCoin;
    @BindView(R.id.v_coinTransactionDetails)
    RecyclerView vCoinTransactionDetails;

    CoinTransactionDetailsAdapter adapter;
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

        showFunctionText();
        setFunctionText("充值");

        vCoinTransactionDetails.setAdapter(adapter = new CoinTransactionDetailsAdapter());
        vCoinTransactionDetails.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        vCoinTransactionDetails.addItemDecoration(new RecyclerViewDivider(getContext(),LinearLayoutManager.VERTICAL,R.drawable.divider_pink,false));

        List list = new ArrayList();
        for (int i=0;i<10;i++){
            list.add(new Object());
        }

        adapter.setDataList(list);
        adapter.notifyDataSetChanged();
    }
}
