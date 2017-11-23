package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TableLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.OrderListModel;
import com.jyt.baseapp.model.impl.OrderListModelImpl;
import com.jyt.baseapp.view.fragment.BaseFragment;
import com.jyt.baseapp.view.fragment.OrderListFragment;
import com.jyt.baseapp.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class OrderListActivity extends BaseActivity {
    @BindView(R.id.v_tabLayout)
    TabLayout vTabLayout;
    @BindView(R.id.v_viewPager)
    NoScrollViewPager vViewPager;

    FragmentViewPagerAdapter adapter ;

    OrderListModel orderListModel;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vViewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()));

        OrderListFragment ready = new OrderListFragment();
        Bundle readyBundle = new Bundle();
        readyBundle.putInt(IntentKey.KEY_TYPE,OrderListFragment.TYPE_READY);
        ready.setArguments(readyBundle);
        ready.setOrderListModel(orderListModel);
        adapter.addFragment(ready,"待配送");

        OrderListFragment send = new OrderListFragment();
        Bundle sendBundle = new Bundle();
        sendBundle.putInt(IntentKey.KEY_TYPE,OrderListFragment.TYPE_SEND);
        send.setArguments(sendBundle);
        send.setOrderListModel(orderListModel);
        adapter.addFragment(send,"配送中");

        OrderListFragment finish = new OrderListFragment();
        Bundle finishBundle = new Bundle();
        finishBundle.putInt(IntentKey.KEY_TYPE,OrderListFragment.TYPE_FINISH);
        finish.setArguments(finishBundle);
        finish.setOrderListModel(orderListModel);
        adapter.addFragment(finish,"已完成");


        adapter.notifyDataSetChanged();
        vTabLayout.setupWithViewPager(vViewPager);
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(orderListModel = new OrderListModelImpl());
        return list;
    }

    @Override
    public void refreshFragment(int index) {
        ((BaseFragment) adapter.getFragments().get(index)).refresh();
    }
}
