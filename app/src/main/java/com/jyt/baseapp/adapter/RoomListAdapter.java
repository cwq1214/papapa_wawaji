package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.RoomItemViewHolder;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class RoomListAdapter extends BaseRcvAdapter {

    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        RoomItemViewHolder holder = new RoomItemViewHolder(parent);
        return holder;
    }


}
