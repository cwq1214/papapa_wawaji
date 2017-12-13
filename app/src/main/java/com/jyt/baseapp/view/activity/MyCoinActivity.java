package com.jyt.baseapp.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jyt.baseapp.App;
import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.CoinTransactionDetailsAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.PayResult;
import com.jyt.baseapp.bean.json.AliPayResult;
import com.jyt.baseapp.bean.json.PersonalInfo;
import com.jyt.baseapp.bean.json.RechargePrice;
import com.jyt.baseapp.bean.json.TransactionDetail;
import com.jyt.baseapp.bean.json.WxPayResult;
import com.jyt.baseapp.helper.WeChartHelper;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.PayModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.impl.PayModelImpl;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.dialog.PayDialog;
import com.jyt.baseapp.view.dialog.RechargeCoinDialog;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.WhiteRefreshView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    PayModel payModel;

    RechargeCoinDialog rechargeCoinDialog;

    WeChartHelper weChartHelper;


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
                            public void onPriceClick(final RechargePrice price) {
                                rechargeCoinDialog.dismiss();

                                PayDialog payDialog = new PayDialog(getContext());
                                payDialog.setOnDoneClickListener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which==1){//wx
                                            payModel.chargeCoinByWeChart("1", price.getRuleId(), new BeanCallback<BaseJson<WxPayResult>>() {
                                                @Override
                                                public void response(boolean success, BaseJson<WxPayResult> response, int id) {
                                                    if (response.isRet()){
                                                        weChartHelper.pay(response.getData().getPartnerId(),response.getData().getPrepayId(),response.getData().getTimeStamp(),response.getData().getNonceStr(),response.getData().getPaySign());
                                                    }else {
                                                        T.showShort(getContext(),response.getForUser());
                                                    }

                                                }
                                            });
                                        }else if(which == 2){//ali
                                            payModel.chargeCoinByAli("1", price.getRuleId(), new BeanCallback<BaseJson<AliPayResult>>() {
                                                @Override
                                                public void response(boolean success, final BaseJson<AliPayResult> response, int id) {
                                                    if (response.isRet()){
                                                        Runnable payRunnable = new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                PayTask alipay = new PayTask(getActivity());
                                                                Map<String, String> result = alipay.payV2(response.getData().getSign(),true);
                                                                PayResult payResult = new PayResult(result);
                                                                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                                                                String resultStatus = payResult.getResultStatus();
                                                                if (TextUtils.equals(resultStatus, "9000")) {
                                                                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                                                                    T.showShort(getContext(), "支付成功");
                                                                    vCoinTransactionDetails.getRefreshLayout().startRefresh();
                                                                    getSelfCoin();
                                                                } else {
                                                                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                                                                    T.showShort(getContext(), "支付失败");
                                                                }

                                                            }
                                                        };
                                                        new Thread(payRunnable).start();
                                                    }else {
                                                        T.showShort(getContext(),response.getForUser());
                                                    }
                                                }
                                            });
                                        }

                                    }
                                });
                                payDialog.show();


                            }
                        });
                    }
                }
            });
        }


        weChartHelper = new WeChartHelper();
        weChartHelper.init(getContext(), App.weiXin_AppKey);
        weChartHelper.registerToWx();
        weChartHelper.setReceivePayResultListener(new WeChartHelper.ReceivePayResultListener() {
            @Override
            public void onPayResult(boolean payResult) {
                if (payResult){
                    T.showShort(getContext(),"支付成功");

                    vCoinTransactionDetails.getRefreshLayout().startRefresh();
                    getSelfCoin();

                }else {
                    T.showShort(getContext(),"支付失败");
                }
            }
        });
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
        getSelfCoin();
    }

    private void getSelfCoin(){
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
    protected void onDestroy() {
        super.onDestroy();
        weChartHelper.unInit();
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(personalInfoModel = new PersonalInfoModelImpl());
        list.add(payModel = new PayModelImpl());
        return list;
    }
}
