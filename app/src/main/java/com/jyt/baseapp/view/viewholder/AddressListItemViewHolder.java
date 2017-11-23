package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.Address;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class AddressListItemViewHolder extends BaseViewHolder {
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_tel)
    TextView textTel;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.img_defaultImg)
    ImageView imgDefaultImg;
    @BindView(R.id.v_setDefault)
    LinearLayout vSetDefault;
    @BindView(R.id.text_modify)
    TextView textModify;
    @BindView(R.id.text_delete)
    TextView textDelete;


    OnViewHolderClickListener onSetDefaultAddressClick;
    OnViewHolderClickListener onModifyAddressClick;
    OnViewHolderClickListener onDeleteAddressClick;


    public AddressListItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_address_list_item, parent, false));

    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        Address address = (Address) data;
        textName.setText(address.getContactPerson());
        textTel.setText(address.getContactMobile());
        textAddress.setText(address.getPr()+address.getCity()+address.getArea()+address.getAddress());
        imgDefaultImg.setImageDrawable(itemView.getResources().getDrawable(address.isDefault()?R.mipmap.circle_check_sel:R.mipmap.circle_check_nor));

    }

    @OnClick({R.id.v_setDefault, R.id.text_modify, R.id.text_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_setDefault:
                if (onSetDefaultAddressClick!=null)
                    onSetDefaultAddressClick.onClick(this);
                break;
            case R.id.text_modify:
                if (onModifyAddressClick!=null)
                    onModifyAddressClick.onClick(this);
                break;
            case R.id.text_delete:
                if (onDeleteAddressClick!=null)
                    onDeleteAddressClick.onClick(this);
                break;
        }
    }

    public void setOnSetDefaultAddressClick(OnViewHolderClickListener onSetDefaultAddressClick) {
        this.onSetDefaultAddressClick = onSetDefaultAddressClick;
    }

    public void setOnModifyAddressClick(OnViewHolderClickListener onModifyAddressClick) {
        this.onModifyAddressClick = onModifyAddressClick;
    }

    public void setOnDeleteAddressClick(OnViewHolderClickListener onDeleteAddressClick) {
        this.onDeleteAddressClick = onDeleteAddressClick;
    }
}
