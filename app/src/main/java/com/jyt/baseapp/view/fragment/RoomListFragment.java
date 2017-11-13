package com.jyt.baseapp.view.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.RoomListAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.HomeToyResult;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.RcvGridSpaceItemDecoration;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.HomeToyModel;
import com.jyt.baseapp.model.impl.HomeToyModelImpl;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MainRefreshBottomView;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class RoomListFragment extends BaseFragment {
    @BindView(R.id.v_refreshLayout)
    LinearLayout vRefreshLayout;
    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;

    private String type;
    private HomeToyModel mToyModel;

    private List<HomeToyResult> mlist;
    private RoomListAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_list;
    }


    public RoomListFragment(int type){
        this.type=type+"";
    }

    @Override
    protected void firstInit() {
        vRefreshRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        vRefreshRecyclerView.addItemDecoration(new RcvGridSpaceItemDecoration(2,DensityUtil.dpToPx(getContext(),20),true));
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
                IntentHelper.openRoomActivity(getContext(), null);
            }
        });

        mlist = new ArrayList();
        adapter.setDataList(mlist);
        adapter.notifyDataSetChanged();
        getToyDatas("8");

    }

    private void getToyDatas(String count){
        mToyModel.getHomeToyData(count,type, new BeanCallback<BaseJson<List<HomeToyResult>>>() {


            @Override
            public void response(boolean success, BaseJson<List<HomeToyResult>> response, int id) {
                mlist=response.getData();
                adapter.notifyData(mlist);
            }
        });
    }

    @Override
    public List<BaseModel> CreateModels() {
        List base = new ArrayList();
        base.add(mToyModel=new HomeToyModelImpl());
        return base;
    }
}
