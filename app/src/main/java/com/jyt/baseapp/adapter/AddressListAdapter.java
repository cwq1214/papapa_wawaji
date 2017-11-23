package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.AddressListItemViewHolder;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class AddressListAdapter extends BaseRcvAdapter {
    BaseViewHolder.OnViewHolderClickListener onSetDefaultAddressClick;
    BaseViewHolder.OnViewHolderClickListener onModifyAddressClick;
    BaseViewHolder.OnViewHolderClickListener onDeleteAddressClick;
    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        AddressListItemViewHolder holder = new AddressListItemViewHolder(parent);
        holder.setOnDeleteAddressClick(onDeleteAddressClick);
        holder.setOnModifyAddressClick(onModifyAddressClick);
        holder.setOnSetDefaultAddressClick(onSetDefaultAddressClick);
        return holder;
    }

    public void setOnSetDefaultAddressClick(BaseViewHolder.OnViewHolderClickListener onSetDefaultAddressClick) {
        this.onSetDefaultAddressClick = onSetDefaultAddressClick;
    }

    public void setOnModifyAddressClick(BaseViewHolder.OnViewHolderClickListener onModifyAddressClick) {
        this.onModifyAddressClick = onModifyAddressClick;
    }

    public void setOnDeleteAddressClick(BaseViewHolder.OnViewHolderClickListener onDeleteAddressClick) {
        this.onDeleteAddressClick = onDeleteAddressClick;
    }
}
