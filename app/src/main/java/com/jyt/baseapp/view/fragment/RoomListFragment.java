package com.jyt.baseapp.view.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.RoomListAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.HomeToyResult;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.itemDecoration.RcvGridSpaceItemDecoration;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.HomeToyModel;
import com.jyt.baseapp.model.impl.HomeToyModelImpl;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MainRefreshBottomView;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class RoomListFragment extends BaseFragment {
    @BindView(R.id.v_refreshLayout)
    LinearLayout vRefreshLayout;
    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;
    @BindView(R.id.img_loading)
    ImageView imgLoading;


    Unbinder unbinder;

    private int type;
    private HomeToyModel mToyModel;

    private List<HomeToyResult> mlist;
    private RoomListAdapter adapter;


    ObjectAnimator animator;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_list;
    }


    @Override
    protected void firstInit() {
        type = getArguments().getInt(IntentKey.KEY_TYPE);

        animator =  ObjectAnimator.ofFloat(imgLoading,"rotation",0,-360);
        animator.setDuration(1000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);

        vRefreshRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        vRefreshRecyclerView.addItemDecoration(new RcvGridSpaceItemDecoration(2, DensityUtil.dpToPx(getContext(),16), true));
        vRefreshRecyclerView.getRefreshLayout().setEnableRefresh(false);
        vRefreshRecyclerView.getRefreshLayout().setBottomView(new MainRefreshBottomView(getContext()));
        vRefreshRecyclerView.setAdapter(adapter = new RoomListAdapter());
//        设置样式
        vRefreshRecyclerView.getRecyclerView().setBackground(getResources().getDrawable(R.drawable.bg_4radius_bottom_left_bottom_right));
        vRefreshRecyclerView.getRefreshLayout().setEnableOverScroll(false);
        vRefreshRecyclerView.getRefreshLayout().setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                HomeToyResult homeToyResult = (HomeToyResult) holder.getData();
                IntentHelper.openRoomActivity(getContext(), homeToyResult);
            }
        });
        int dp16 = DensityUtil.dpToPx(getContext(),16);

//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vRefreshRecyclerView.getRecyclerView().getLayoutParams();
//        if (params==null){
//            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        }
//        params.setMargins(dp24,0,dp24,0);
        vRefreshRecyclerView.getRecyclerView().setPadding(dp16,0,dp16,0);
                //.getRecyclerView().setLayoutParams(params);




        vRefreshRecyclerView.setRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                getToyDatas(false,"8");
            }
        });
        onRefreshLayoutClick();

    }

    @OnClick(R.id.v_refreshLayout)
    public void onRefreshLayoutClick() {
        startRefreshAnim();
        vRefreshLayout.setEnabled(false);
        getToyDatas(true,"8");
    }

    private void getToyDatas(final boolean isTop, String count) {
        mToyModel.getHomeToyData(count, type + "", new BeanCallback<BaseJson<List<HomeToyResult>>>() {


            @Override
            public void response(boolean success, BaseJson<List<HomeToyResult>> response, int id) {
                if (response.isRet()) {
                    mlist = response.getData();
                    vRefreshRecyclerView.setDataList(mlist);
                }
                if (isTop){
                    stopRefreshAnim();
                    vRefreshLayout.setEnabled(true);
                }else {
                    vRefreshRecyclerView.finishLoadMore();

                }
//                T.showShort(getContext(),response.getForUser());

            }
        });
    }

    @Override
    public void createModels(List<BaseModel> models) {
        models.add(mToyModel = new HomeToyModelImpl());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);



        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void startRefreshAnim(){
        animator.start();
    }

    public void stopRefreshAnim(){
        if (animator.isRunning()){
            animator.cancel();
            imgLoading.setRotation(0);
        }

    }
}
