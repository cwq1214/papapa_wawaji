package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.BaseRcvAdapter;
import com.jyt.baseapp.util.L;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by v7 on 2017/11/4.
 */

public class RefreshRecyclerView extends LinearLayout {
    @BindView(R.id.v_recyclerView)
    RecyclerView vRecyclerView;
    @BindView(R.id.v_refreshLayout)
    TwinklingRefreshLayout vRefreshLayout;
    @BindView(R.id.v_emptyView)
    RelativeLayout vEmptyView;
    @BindView(R.id.text_emptyHint)
    TextView textEmptyHint;

    private BaseRcvAdapter adapter;

    public boolean showEmptyHint = true;


    public boolean refreshable = true;

    public RefreshListenerAdapter refreshListener;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_recyclerview, this, true);
        ButterKnife.bind(this);


        vRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefreshCanceled() {
                super.onRefreshCanceled();
                if (refreshListener!=null){
                    refreshListener.onRefreshCanceled();
                }
            }

            @Override
            public void onLoadmoreCanceled() {
                super.onLoadmoreCanceled();
                if (refreshListener!=null){
                    refreshListener.onLoadmoreCanceled();
                }
            }

            @Override
            public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullingDown(refreshLayout, fraction);
                if (refreshListener!=null)
                    refreshListener.onPullingDown(refreshLayout,fraction);
            }

            @Override
            public void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullingUp(refreshLayout, fraction);
                if (refreshListener!=null)

                    refreshListener.onPullingUp(refreshLayout,fraction);
            }

            @Override
            public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullDownReleasing(refreshLayout, fraction);
                if (refreshListener!=null)

                    refreshListener.onPullDownReleasing(refreshLayout, fraction);

            }

            @Override
            public void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullUpReleasing(refreshLayout, fraction);
                if (refreshListener!=null)

                    refreshListener.onPullUpReleasing(refreshLayout, fraction);

            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
                if (refreshListener!=null)

                    refreshListener.onFinishRefresh();

            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
                if (refreshListener!=null)

                    refreshListener.onFinishLoadMore();
                showEmptyViewWhenListEmpty();
            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (refreshListener==null){
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            refreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    finishRefreshing();
                                }
                            });


                        };
                    }).start();



                }else {
                    refreshListener.onRefresh(refreshLayout);
                }
                showEmptyViewWhenListEmpty();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (refreshListener==null){
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            refreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    finishLoadMore();
                                }
                            });


                        };
                    }).start();
                }else {
                    refreshListener.onLoadMore(refreshLayout);
                }
                showEmptyViewWhenListEmpty();

            }
        });
    }

    public RefreshRecyclerView setEmptyHintText(String text){
        textEmptyHint.setText(text);
        return this;
    }

    public RefreshRecyclerView setLayoutManager(RecyclerView.LayoutManager layoutManager){
        vRecyclerView.setLayoutManager(layoutManager);
        return this;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        vRecyclerView.addItemDecoration(itemDecoration);
    }

    public RefreshRecyclerView setAdapter(BaseRcvAdapter adapter){
        this.adapter = adapter;
        vRecyclerView.setAdapter(adapter);
        return this;
    }

    public RefreshRecyclerView setRefreshListener(final RefreshListenerAdapter refreshListener){
        this.refreshListener = refreshListener;
        return this;
    }

    public void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
        showEmptyViewWhenListEmpty();
    }

    public void showEmptyViewWhenListEmpty(){
        if (!isShowEmptyHint()){
            vEmptyView.setVisibility(GONE);
            return;
        }
        if (adapter==null||adapter.getDataList()==null||adapter.getDataList().size()==0){
            vEmptyView.setVisibility(VISIBLE);
        }else {
            vEmptyView.setVisibility(GONE);
        }
    }

    public BaseRcvAdapter getAdapter() {
        return adapter;
    }

    public boolean isShowEmptyHint() {
        return showEmptyHint;
    }

    public void setShowEmptyHint(boolean showEmptyHint) {
        this.showEmptyHint = showEmptyHint;
    }
    public boolean isRefreshable() {
        return refreshable;
    }

    public void setRefreshable(boolean refreshable) {
        this.refreshable = refreshable;
        vRefreshLayout.setEnableRefresh(refreshable);
    }
    public void setLoadMoreEnable(boolean refreshable) {
        this.refreshable = refreshable;
        vRefreshLayout.setEnableLoadmore(refreshable);
    }

    public RecyclerView getRecyclerView(){
        return vRecyclerView;
    }

    public TwinklingRefreshLayout getRefreshLayout(){
        return vRefreshLayout;
    }

    public void setDataList(List dataList){

        adapter.setDataList(dataList);
        adapter.notifyDataSetChanged();

        showEmptyViewWhenListEmpty();


    }

    public void finishLoadMore(){
        vRefreshLayout.finishLoadmore();
    }
    public void finishRefreshing(){
        vRefreshLayout.finishRefreshing();
    }

}
