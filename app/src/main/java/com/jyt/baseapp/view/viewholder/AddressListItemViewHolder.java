package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class AddressListItemViewHolder extends BaseViewHolder {
    public AddressListItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_address_list_item,parent,false));
    }
}
