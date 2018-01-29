package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.GrabWaWaItemViewHolder;

/**
 * Created by chenweiqi on 2017/11/17.
 */

public class GrabWaWaAdapter extends BaseRcvAdapter {
    BaseViewHolder.OnViewHolderClickListener onCheckClickListener;

    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        GrabWaWaItemViewHolder holder = new GrabWaWaItemViewHolder(parent);
        holder.setOnCheckClickListener(onCheckClickListener);
        return holder;
    }

    public void setOnCheckClickListener(BaseViewHolder.OnViewHolderClickListener onCheckClickListener) {
        this.onCheckClickListener = onCheckClickListener;
    }
}
