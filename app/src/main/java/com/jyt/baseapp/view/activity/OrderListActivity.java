package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TableLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.view.fragment.OrderListFragment;
import com.jyt.baseapp.view.widget.NoScrollViewPager;

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
        adapter.addFragment(new OrderListFragment(),"待配送");
        adapter.addFragment(new OrderListFragment(),"配送中");
        adapter.addFragment(new OrderListFragment(),"已完成");
        adapter.notifyDataSetChanged();
        vTabLayout.setupWithViewPager(vViewPager);
    }
}
