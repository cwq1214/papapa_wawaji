package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.OrderListAdapter;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.WhiteRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class OrderListFragment extends BaseFragment {
    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;
    OrderListAdapter adapter;
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

        vRefreshRecyclerView.setAdapter(adapter = new OrderListAdapter());


        List list = new ArrayList();

        for (int i=0;i<10;i++){
            list.add(new Object());
        }
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();

        vRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

}
