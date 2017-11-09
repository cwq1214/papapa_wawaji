package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.AddressListAdapter;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class AddressListActivity extends BaseActivity {
    @BindView(R.id.v_recyclerView)
    RecyclerView vRecyclerView;
    @BindView(R.id.text_addAddress)
    TextView textAddAddress;

    AddressListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vRecyclerView.setAdapter(adapter = new AddressListAdapter());
        vRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        vRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),LinearLayoutManager.VERTICAL,R.drawable.divider_10dp,false));
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                IntentHelper.openEditAddressActivity(getContext(), (Parcelable) holder.getData());
            }
        });

        List list = new ArrayList();
        for (int i=0;i<10;i++){
            list.add(new Object());
        }
        adapter.setDataList(list);
        adapter.notifyDataSetChanged();


    }

    @OnClick(R.id.text_addAddress)
    public void onAddAddressClick(){
        IntentHelper.openEditAddressActivity(getContext(),null);
    }
}
