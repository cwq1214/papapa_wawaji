package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.OrderListAdapter;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
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
    public static final int TYPE_READY = 0;
    public static final int TYPE_SEND = 1;
    public static final int TYPE_FINISH = 2;

    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;

    OrderListAdapter adapter;
    int type;

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
        vRefreshRecyclerView.setAdapter(adapter = new OrderListAdapter());
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                IntentHelper.openDollDetailActivity(getContext(),null);
            }
        });


        type = getArguments().getInt(IntentHelper.KEY_TYPE,-1);

        switch (type){
            case TYPE_READY:
                break;
            case TYPE_SEND:
                break;
            case TYPE_FINISH:
                break;
        }

        List list = new ArrayList();

        for (int i=0;i<10;i++){
            list.add(new Object());
        }
        vRefreshRecyclerView.setDataList(list);
        vRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }



}
