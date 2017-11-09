package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;

/**
 * Created by v7 on 2017/11/8.
 */

public class TransactionDetailViewHolder extends BaseViewHolder {
    public TransactionDetailViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_transaction_detail_item,parent,false));
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
    }
}
