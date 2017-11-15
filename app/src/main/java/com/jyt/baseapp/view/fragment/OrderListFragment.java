package com.jyt.baseapp.view.fragment;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.OrderListAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Order;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.model.OrderListModel;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.WhiteRefreshView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class OrderListFragment extends BaseFragment {
    public static final int TYPE_READY = 1;
    public static final int TYPE_SEND = 2;
    public static final int TYPE_FINISH = 3;

    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;

    OrderListAdapter adapter;
    int type;
    String sequeue;
    OrderListModel orderListModel;

    BeanCallback beanCallback_refresh = new BeanCallback<BaseJson<List<Order>>>() {
        @Override
        public void response(boolean success, BaseJson<List<Order>> response, int id) {
            if (response.isRet()){
                for (Order order :
                        response.getData()) {
                    order.setOrderType(type);
                }
                vRefreshRecyclerView.setDataList(response.getData());
            }
            vRefreshRecyclerView.finishRefreshing();
        }
    };
    BeanCallback beanCallback_loadMore = new BeanCallback<BaseJson<List<Order>>>() {
        @Override
        public void response(boolean success, BaseJson<List<Order>> response, int id) {
            if (response.isRet()){
                for (Order order :
                        response.getData()) {
                    order.setOrderType(type);
                }
                adapter.getDataList().addAll(response.getData());
                vRefreshRecyclerView.setDataList(adapter.getDataList());
            }
            vRefreshRecyclerView.finishLoadMore();
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void firstInit() {
        WhiteRefreshView headerView = new WhiteRefreshView(getContext());
        WhiteRefreshView bottomView = new WhiteRefreshView(getContext());
        bottomView.setPreRefreshImgResId(R.mipmap.black_up_arrow);

        vRefreshRecyclerView.getRefreshLayout().setHeaderView(headerView );
        vRefreshRecyclerView.getRefreshLayout().setBottomView(bottomView );
        vRefreshRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayout.VERTICAL,R.drawable.divider_full_pink,false));
        vRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        vRefreshRecyclerView.setAdapter(adapter = new OrderListAdapter());

        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                Order order =  (Order) holder.getData();
                if (order.getOrderType()==TYPE_READY){
                    IntentHelper.openDollDetailActivity(getContext(),order);
                }
            }
        });
        adapter.setOnConfirmReceiveGoodsClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                Order order =  (Order) holder.getData();
                if (order.getOrderType()==TYPE_SEND){
                    orderListModel.receiveOrder(order.getOrderNo(), new BeanCallback<BaseJson>() {
                        @Override
                        public void response(boolean success, BaseJson response, int id) {
                            if (response.isRet()){
                                vRefreshRecyclerView.getRefreshLayout().startRefresh();
                            }else{
                                T.showShort(getContext(),response.getForUser());
                            }
                        }
                    });
                }
            }
        });


        type = getArguments().getInt(IntentHelper.KEY_TYPE,-1);




        vRefreshRecyclerView.setRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                orderListModel.getOrderList(type, null,beanCallback_refresh);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                sequeue = ((Order) adapter.getDataList().get(adapter.getDataList().size() - 1)).getSequeue();
                orderListModel.getOrderList(type, sequeue, beanCallback_loadMore);
            }
        });

        vRefreshRecyclerView.getRefreshLayout().startRefresh();

    }

    public void setOrderListModel(OrderListModel orderListModel) {
        this.orderListModel = orderListModel;
    }
}
