package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.TransactionDetailViewHolder;

/**
 * Created by v7 on 2017/11/8.
 */

public class CoinTransactionDetailsAdapter extends BaseRcvAdapter {
    @Override
    BaseViewHolder CreateViewHolder(ViewGroup parent, int viewType) {
        return new TransactionDetailViewHolder(parent);
    }
}
