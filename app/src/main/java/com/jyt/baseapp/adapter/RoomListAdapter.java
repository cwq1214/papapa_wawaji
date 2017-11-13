package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.RoomItemViewHolder;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class RoomListAdapter extends BaseRcvAdapter {

    private BaseViewHolder.OnViewHolderClickListener ClickListener;
    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        RoomItemViewHolder holder = new RoomItemViewHolder(parent);
        holder.setOnViewHolderClickListener(ClickListener);
        return holder;
    }

    public void setOnViewHolderClickListener(BaseViewHolder.OnViewHolderClickListener listener){
        ClickListener=listener;
    }
}
