package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.view.fragment.RoomListFragment;
import com.jyt.baseapp.view.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by chenweiqi on 2017/11/6.
 */
@ActivityAnnotation(showActionBar = false)
public class MainActivity extends BaseActivity {
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.img_personalCenter)
    ImageView imgPersonalCenter;
    @BindView(R.id.v_banner)
    BGABanner vBanner;
    @BindView(R.id.v_tabLayout)
    TabLayout vTabLayout;
    @BindView(R.id.v_viewPager)
    NoScrollViewPager vViewPager;
    @BindView(R.id.img_showHideBanner)
    ImageView imgShowHideBanner;
    private RoomListFragment dollFragment;
    private RoomListFragment cabbageFragment;

    FragmentViewPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dollFragment=new RoomListFragment(0);
        cabbageFragment=new RoomListFragment(1);

        vViewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()));
        adapter.addFragment(dollFragment,"娃娃首页");
        adapter.addFragment(cabbageFragment,"白菜特抓");
        adapter.addFragment(new RoomListFragment(0),"玩家分享");
        adapter.notifyDataSetChanged();
        vTabLayout.setupWithViewPager(vViewPager);

    }
    @OnClick(R.id.img_setting)
    public void onSettingClick(){
        IntentHelper.openPersonalActivity(getContext());
    }

    @OnClick(R.id.img_showHideBanner)
    public void onShowHideBannerClick(){
        if (vBanner.getVisibility()== View.VISIBLE){
            imgShowHideBanner.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_up_pink));
            vBanner.setVisibility(View.GONE);
        }else {
            imgShowHideBanner.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_down_pink));
            vBanner.setVisibility(View.VISIBLE);
        }
    }
}
