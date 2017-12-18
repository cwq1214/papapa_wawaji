package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.TransactionDetail;

import butterknife.BindView;

/**
 * Created by v7 on 2017/11/8.
 */

public class TransactionDetailViewHolder extends BaseViewHolder {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_coin)
    TextView textCoin;

    public TransactionDetailViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_transaction_detail_item, parent, false));
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        TransactionDetail transactionDetail = (TransactionDetail) data;
        textTitle.setText(transactionDetail.getContent());
        textDate.setText(transactionDetail.getCreatedTime());
        textCoin.setText(transactionDetail.getFeeTypeConverter()+transactionDetail.getFee());
    }
}
