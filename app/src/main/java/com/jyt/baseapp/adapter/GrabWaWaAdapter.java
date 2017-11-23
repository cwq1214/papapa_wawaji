package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.GrabWaWaItemViewHolder;

/**
 * Created by chenweiqi on 2017/11/17.
 */

public class GrabWaWaAdapter extends BaseRcvAdapter {
    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        return new GrabWaWaItemViewHolder(parent);
    }
}
