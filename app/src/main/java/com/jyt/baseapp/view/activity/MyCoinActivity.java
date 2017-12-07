package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.CoinTransactionDetailsAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.PersonalInfo;
import com.jyt.baseapp.bean.json.RechargePrice;
import com.jyt.baseapp.bean.json.TransactionDetail;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.dialog.RechargeCoinDialog;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.WhiteRefreshView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

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
    RefreshRecyclerView vCoinTransactionDetails;

    CoinTransactionDetailsAdapter adapter;
    PersonalInfoModel personalInfoModel;

    RechargeCoinDialog rechargeCoinDialog;

    BeanCallback loadMoreCallback = new BeanCallback<BaseJson<List<TransactionDetail>>>() {
        @Override
        public void response(boolean success, BaseJson<List<TransactionDetail>> response, int id) {
            if (response.isRet()){
                adapter.getDataList().addAll(response.getData());
                vCoinTransactionDetails.notifyDataSetChanged();
            }
            vCoinTransactionDetails.finishLoadMore();
        }
    };
    BeanCallback refreshCallback = new BeanCallback<BaseJson<List<TransactionDetail>>>() {
        @Override
        public void response(boolean success, BaseJson<List<TransactionDetail>> response, int id) {
            if (response.isRet()){
                vCoinTransactionDetails.setDataList(response.getData());
            }
            vCoinTransactionDetails.finishRefreshing();
        }
    };

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

        WhiteRefreshView headerView = new WhiteRefreshView(getContext());
        WhiteRefreshView bottomView = new WhiteRefreshView(getContext());
        bottomView.setPreRefreshImgResId(R.mipmap.black_up_arrow);

        vCoinTransactionDetails.getRefreshLayout().setHeaderView(headerView );
        vCoinTransactionDetails.getRefreshLayout().setBottomView(bottomView );


        vCoinTransactionDetails.setAdapter(adapter = new CoinTransactionDetailsAdapter());
        vCoinTransactionDetails.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vCoinTransactionDetails.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL, R.drawable.divider_pink, false));


        vCoinTransactionDetails.setRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                personalInfoModel.getUserCoinTransactionDetails(refreshCallback);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                personalInfoModel.getUserCoinTransactionDetails(loadMoreCallback);
            }
        });
        vCoinTransactionDetails.getRefreshLayout().startRefresh();

        if (UserInfo.isLogin()){
            personalInfoModel.getChargeRole(new BeanCallback<BaseJson<List<RechargePrice>>>() {
                @Override
                public void response(boolean success, BaseJson<List<RechargePrice>> response, int id) {
                    if (response.isRet()){
                        rechargeCoinDialog = new RechargeCoinDialog(getContext());
                        rechargeCoinDialog.setPriceList(response.getData());
                        rechargeCoinDialog.setOnPriceClick(new RechargeCoinDialog.OnPriceClick() {
                            @Override
                            public void onPriceClick(RechargePrice price) {

                            }
                        });
                    }
                }
            });
        }

    }

    @Override
    public void onFunctionClick() {
        super.onFunctionClick();

        if (rechargeCoinDialog!=null){
            rechargeCoinDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        personalInfoModel.getUserInfo(new BeanCallback<BaseJson<PersonalInfo>>() {
            @Override
            public void response(boolean success, BaseJson<PersonalInfo> response, int id) {
                if (response.isRet()){
                    textCurrentCoin.setText(response.getData().getBalance());
                }
            }
        });
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(personalInfoModel = new PersonalInfoModelImpl());
        return list;
    }
}
