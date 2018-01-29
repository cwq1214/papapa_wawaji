package com.jyt.baseapp.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.GrabWaWaAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Address;
import com.jyt.baseapp.bean.json.Order;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.OrderListModel;
import com.jyt.baseapp.model.impl.OrderListModelImpl;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.dialog.CenterImageDialog;
import com.jyt.baseapp.view.dialog.ExchangeCoinDialog;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.WhiteRefreshView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/17.
 */

public class MyWaWaActivity extends BaseActivity {
    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;
    @BindView(R.id.img_check)
    ImageView imgCheck;
    @BindView(R.id.btn_checkAll)
    LinearLayout btnCheckAll;
    @BindView(R.id.btn_exchangeCoin)
    TextView btnExchangeCoin;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;

    boolean isCheckAll = false;
    GrabWaWaAdapter adapter;
    OrderListModel orderListModel;

    BeanCallback refreshCallback = new BeanCallback<BaseJson<List<Order>>>() {
        @Override
        public void response(boolean success, BaseJson<List<Order>> response, int id) {
            if (response.isRet()) {
                vRefreshRecyclerView.setDataList(response.getData());
            }
            vRefreshRecyclerView.finishRefreshing();

            isCheckAll = false;
            if (isCheckAll){
                imgCheck.setImageDrawable(getResources().getDrawable(R.mipmap.checked));
            }else {
                imgCheck.setImageDrawable(getResources().getDrawable(R.mipmap.uncheck));
            }
        }
    };
    BeanCallback loadMoreCallback = new BeanCallback<BaseJson<List<Order>>>() {
        @Override
        public void response(boolean success, BaseJson<List<Order>> response, int id) {
            if (response.isRet()) {
                response.getData().addAll(response.getData());
                vRefreshRecyclerView.setDataList(response.getData());
            }
            vRefreshRecyclerView.finishLoadMore();

            isCheckAll = false;
            if (isCheckAll){
                imgCheck.setImageDrawable(getResources().getDrawable(R.mipmap.checked));
            }else {
                imgCheck.setImageDrawable(getResources().getDrawable(R.mipmap.uncheck));
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mywawa;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WhiteRefreshView headerView = new WhiteRefreshView(getContext());
        WhiteRefreshView bottomView = new WhiteRefreshView(getContext());
        bottomView.setPreRefreshImgResId(R.mipmap.black_up_arrow);

        vRefreshRecyclerView.getRefreshLayout().setHeaderView(headerView);
        vRefreshRecyclerView.getRefreshLayout().setBottomView(bottomView);


        vRefreshRecyclerView.setAdapter(adapter = new GrabWaWaAdapter());
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                IntentHelper.openDollDetailActivity(getContext(), (Parcelable) holder.getData());
            }
        });
        adapter.setOnCheckClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {

            }
        });

        vRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vRefreshRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL, R.drawable.divider_pink, false));

        vRefreshRecyclerView.setRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                orderListModel.getOrderList(OrderListModel.TYPE_UNSEL_ADDRESS, ((Order) adapter.getDataList().get(adapter.getDataList().size() - 1)).getSequeue(), loadMoreCallback);

            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                orderListModel.getOrderList(OrderListModel.TYPE_UNSEL_ADDRESS, null, refreshCallback);

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        vRefreshRecyclerView.getRefreshLayout().startRefresh();

    }

    @Override
    public List<BaseModel> createModels() {
        List modes = new ArrayList();
        modes.add(orderListModel = new OrderListModelImpl());
        return modes;
    }

    @OnClick({R.id.btn_checkAll, R.id.btn_exchangeCoin, R.id.btn_submit})
    public void onViewClicked(View view) {
        List<Order> orders = adapter.getDataList();

        switch (view.getId()) {
            case R.id.btn_checkAll:
                isCheckAll = !isCheckAll;
                for (Order order:
                orders) {
                        order.setCheck(isCheckAll);
                }
                adapter.notifyDataSetChanged();
                if (isCheckAll){
                    imgCheck.setImageDrawable(getResources().getDrawable(R.mipmap.checked));
                }else {
                    imgCheck.setImageDrawable(getResources().getDrawable(R.mipmap.uncheck));
                }
                break;
            case R.id.btn_exchangeCoin://兑换
                final List<String> orderNo = new ArrayList<>();
                int coin = 0;
                for (Order order :
                        orders) {
                    if (order.isCheck()){
                        orderNo.add(order.getOrderNo());
                        try{
                            coin += Float.valueOf(order.getBackFee());
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
                if (orderNo==null || orderNo.size()==0){
                    T.showShort(getContext(),"请先选择娃娃");
                    return;
                }
                final ExchangeCoinDialog exchange = new ExchangeCoinDialog(getContext());

                exchange.setToyImage(orders.get(0).getToyImg());
                exchange.setTextToyCount(orders.size());
                exchange.setAllCoin(coin);
                exchange.setOnButtonClickListener(new CenterImageDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog,int index) {
                        if (index==0){
                            orderListModel.exchangeToCoin(orderNo, new BeanCallback<BaseJson>() {
                                @Override
                                public void response(boolean success, BaseJson response, int id) {
                                    if (response.isRet()){
//                                        exchange.dismiss();
                                    }else {
                                    }
                                    T.showShort(getContext(),response.getForUser());
                                    exchange.dismiss();
                                    vRefreshRecyclerView.getRefreshLayout().startRefresh();

                                }
                            });
                        }else if (index == 1){
                            exchange.dismiss();
                        }
                    }
                });
                exchange.show();
                break;
            case R.id.btn_submit://提交发货
                List<String> orderNo2 = getCheckedList();
                if (orderNo2==null || orderNo2.size()==0){
                    T.showShort(getContext(),"请先选择娃娃");
                    return;
                }
                IntentHelper.openAddressListActivityForResult(getContext(),11);



                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11 && resultCode == Activity.RESULT_OK){
            final Address address = data.getParcelableExtra(IntentKey.KEY_ADDRESS);

            List<Order> orders = adapter.getDataList();
            final List<String> orderNos = new ArrayList<>();
            int coin = 0;
            for (Order order:
                 orders) {
                if (order.isCheck()){
                    orderNos.add(order.getOrderNo());
                    try {
                        coin += Float.parseFloat(order.getFreight());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
            if (orderNos.size()>=2){
                orderListModel.submitOrder(orderNos, address.getAddressId(), new BeanCallback<BaseJson>() {
                    @Override
                    public void response(boolean success, BaseJson response, int id) {
                        if (response.isRet()){

                        }else {

                        }
                        vRefreshRecyclerView.getRefreshLayout().startRefresh();
                    }
                });
                return;
            }

            final CenterImageDialog payDialog = new CenterImageDialog(getContext());
            payDialog.setDialogTitle("确认支付运费");
            payDialog.setDialogCenterImgResId(R.mipmap.coin_tow);
            payDialog.setDialogDescText(coin+"娃娃币");
            payDialog.setOnButtonClickListener(new CenterImageDialog.OnButtonClickListener() {
                @Override
                public void onClick(Dialog dialog,int index) {
                    if (index==0){
                        orderListModel.submitOrder(orderNos, address.getAddressId(), new BeanCallback<BaseJson>() {
                            @Override
                            public void response(boolean success, BaseJson response, int id) {
                                if (response.isRet()){

                                }else {
                                     CenterImageDialog noEnought = new CenterImageDialog(getContext());
                                    noEnought.setDialogTitle("确认支付运费");
                                    noEnought.setDialogCenterImgResId(R.mipmap.fail);
                                    noEnought.setLeftBtnText("去充值");
                                    noEnought.setOnButtonClickListener(new CenterImageDialog.OnButtonClickListener() {
                                        @Override
                                        public void onClick(Dialog dialog,int index) {
                                            if ( index == 0){
                                                IntentHelper.openMyCoinActivity(getContext());
                                            }else if (index == 1){

                                            }
                                            payDialog.dismiss();
                                            dialog.dismiss();
                                        }
                                    });
                                    noEnought.show();
                                }
                                payDialog.dismiss();
                                vRefreshRecyclerView.getRefreshLayout().startRefresh();
                            }
                        });
                    }else if (index == 1){
                        dialog.dismiss();
                    }
                }
            });
            payDialog.show();

//            orderListModel.submitOrder(orderNo2, address.getAddressId(), new BeanCallback<BaseJson>() {
//                @Override
//                public void response(boolean success, BaseJson response, int id) {
//                    if (response.isRet()){
//
//                    }else {
//                        IntentHelper.openMyCoinActivity(getContext());
//                    }
//                }
//            });
        }
    }

    private void submitOrders(List<String> orderNo){
//        orderListModel.submitOrder(orderNo,);
    }

    public List<String> getCheckedList(){
        List<Order> orders = adapter.getDataList();

        List<String> orderIds = new ArrayList();
        for (Order order:
                orders) {
            if (order.isCheck()){
                orderIds.add(order.getOrderNo());
            }
        }
        return orderIds;
    }
}
