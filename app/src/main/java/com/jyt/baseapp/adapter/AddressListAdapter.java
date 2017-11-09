package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.AddressListItemViewHolder;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class AddressListAdapter extends BaseRcvAdapter {
    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressListItemViewHolder(parent);
    }
}
