package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.CoinTransactionDetailsAdapter;
import com.jyt.baseapp.adapter.GrabWaWaAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Order;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.OrderListModel;
import com.jyt.baseapp.model.impl.OrderListModelImpl;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.WhiteRefreshView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/17.
 */

public class MyWaWaActivity extends BaseActivity {
    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;

    GrabWaWaAdapter adapter;
    OrderListModel orderListModel;

    BeanCallback refreshCallback = new BeanCallback<BaseJson<List<Order>>>() {
        @Override
        public void response(boolean success, BaseJson<List<Order>> response, int id) {
            if(response.isRet()){
                vRefreshRecyclerView.setDataList(response.getData());
            }
            vRefreshRecyclerView.finishRefreshing();
        }
    };
    BeanCallback loadMoreCallback = new BeanCallback<BaseJson<List<Order>>>() {
        @Override
        public void response(boolean success, BaseJson<List<Order>> response, int id) {
            if(response.isRet()){
                response.getData().addAll(response.getData());
                vRefreshRecyclerView.setDataList(response.getData());
            }
            vRefreshRecyclerView.finishLoadMore();
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

        vRefreshRecyclerView.getRefreshLayout().setHeaderView(headerView );
        vRefreshRecyclerView.getRefreshLayout().setBottomView(bottomView );


        vRefreshRecyclerView.setAdapter(adapter = new GrabWaWaAdapter());
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                IntentHelper.openDollDetailActivity(getContext(), (Parcelable) holder.getData());
            }
        });

        vRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vRefreshRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL, R.drawable.divider_pink, false));

        vRefreshRecyclerView.setRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                orderListModel.getOrderList(OrderListModel.TYPE_UNSEL_ADDRESS, ((Order) adapter.getDataList().get(adapter.getDataList().size() - 1)).getSequeue(),loadMoreCallback);

            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                orderListModel.getOrderList(OrderListModel.TYPE_UNSEL_ADDRESS,null,refreshCallback);

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
}
