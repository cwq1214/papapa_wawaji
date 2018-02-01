package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Banner;
import com.jyt.baseapp.bean.json.SignResult;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.MainActModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.impl.MainActModelImpl;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;
import com.jyt.baseapp.view.dialog.SignDialog;
import com.jyt.baseapp.view.fragment.PlayerShareExpFragment;
import com.jyt.baseapp.view.fragment.RoomListFragment;
import com.jyt.baseapp.view.widget.NoScrollViewPager;
import com.jyt.baseapp.view.widget.RoundRelativeLayout;
import com.jyt.baseapp.zego.ZegoApiManager;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.v_banner)
    BGABanner vBanner;
    @BindView(R.id.v_tabLayout)
    TabLayout vTabLayout;
    @BindView(R.id.v_viewPager)
    NoScrollViewPager vViewPager;
    @BindView(R.id.img_showHideBanner)
    ImageView imgShowHideBanner;
    @BindView(R.id.img_gifppp)
    ImageView imgGifppp;
    @BindView(R.id.v_bannerLayout)
    RoundRelativeLayout vBannerLayout;
    @BindView(R.id.img_qiandao)
    ImageView imgQiandao;
    private RoomListFragment dollFragment;
    private RoomListFragment cabbageFragment;
    private PlayerShareExpFragment playerShareExpFragment;
    FragmentViewPagerAdapter adapter;

    MainActModel model;
    PersonalInfoModel personalInfoModel;

    Banner banners;

    View.OnClickListener bannerImgClickListener;

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

        ZegoApiManager.getInstance().initSDK();

        Glide.with(this).load(R.mipmap.start).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgGifppp);

        dollFragment = new RoomListFragment();
        Bundle dollFragmentBundle = new Bundle();
        dollFragmentBundle.putInt(IntentKey.KEY_TYPE, 0);
        dollFragment.setArguments(dollFragmentBundle);

        cabbageFragment = new RoomListFragment();
        Bundle cabbageFragmentBundle = new Bundle();
        cabbageFragmentBundle.putInt(IntentKey.KEY_TYPE, 1);
        cabbageFragment.setArguments(cabbageFragmentBundle);

        playerShareExpFragment = new PlayerShareExpFragment();


        vViewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()));
        adapter.addFragment(dollFragment, "娃娃首页");
        adapter.addFragment(cabbageFragment, "白菜特抓");
//        adapter.addFragment(playerShareExpFragment, "玩家分享");
        adapter.notifyDataSetChanged();
        vTabLayout.setupWithViewPager(vViewPager);

        int bannerWidth = ScreenUtils.getScreenWidth(getContext()) - DensityUtil.dpToPx(getContext(), 10);
        RelativeLayout.LayoutParams bannerParams = new RelativeLayout.LayoutParams(bannerWidth, bannerWidth * 170/ 730);
        vBanner.setLayoutParams(bannerParams);

        vBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner bgaBanner, View view, Object o, int i) {

                IntentHelper.openBrowserWebActivity(getContext(), banners.getSlide().get(i).getLink());
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri content_url = Uri.parse(banners.getSlide().get(i).getLink());
//                intent.setData(content_url);
//                startActivity(intent);


            }
        });

        model.getBanner(new BeanCallback<BaseJson<Banner>>() {
            @Override
            public void response(boolean success, BaseJson<Banner> response, int id) {
                if (response.isRet()) {
                    banners = response.getData();

                    List<View> imgs = new ArrayList<View>();
                    for (int i = 0; i < response.getData().getSlide().size(); i++) {
                        ImageView imageView = new ImageView(getContext());
                        ImageLoader.getInstance().loadRectangle(imageView, banners.getSlide().get(i).getImg());
                        imgs.add(imageView);
                    }
                    vBanner.setData(imgs);
                }
                T.showShort(getContext(), response.getForUser());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (UserInfo.isLogin()){
            personalInfoModel.isSign(new BeanCallback<BaseJson<Boolean>>() {
                @Override
                public void response(boolean success, BaseJson<Boolean> response, int id) {
                    if (response.isRet()){
                        setSignDrawable(response.getData());

                    }
                }
            });
        }

    }

    @Override
    public List<BaseModel> createModels() {
        List models = new ArrayList();
        models.add(model= new MainActModelImpl());
        models.add(personalInfoModel = new PersonalInfoModelImpl());
        return models;
    }

    @OnClick(R.id.img_setting)
    public void onSettingClick() {
        IntentHelper.openPersonalActivity(getContext());
    }

    @OnClick(R.id.img_qiandao)
    public void onQianDaoClick(){
        if(!UserInfo.isLogin()){
            IntentHelper.openLoginActivity(getContext());
            return;
        }

        personalInfoModel.sign(new BeanCallback<BaseJson<SignResult>>() {
            @Override
            public void response(boolean success, BaseJson<SignResult> response, int id) {
                if (response.isRet()){
                    setSignDrawable(true);
                    SignDialog dialog = new SignDialog(getContext());
                    dialog.setCoin(response.getData().getSignGive());
                    dialog.show();
                }else {
                    T.showShort(getContext(),response.getForUser());
                }
            }
        });
    }

    @OnClick(R.id.img_showHideBanner)
    public void onShowHideBannerClick() {
        if (vBannerLayout.getVisibility() == View.VISIBLE) {
            imgShowHideBanner.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_down_pink));
            vBannerLayout.setVisibility(View.GONE);
        } else {
            imgShowHideBanner.setImageDrawable(getResources().getDrawable(R.mipmap.arrow_up_pink));
            vBannerLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setSignDrawable(boolean isSign){
        if (isSign){
            imgQiandao.setImageDrawable(getResources().getDrawable(R.mipmap.sign_signed));
        }else {
            imgQiandao.setImageDrawable(getResources().getDrawable(R.mipmap.sign_nor));

        }
    }
}
