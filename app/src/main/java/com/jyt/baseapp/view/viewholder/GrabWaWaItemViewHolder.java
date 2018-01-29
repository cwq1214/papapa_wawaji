package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.Order;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/17.
 */

public class GrabWaWaItemViewHolder extends BaseViewHolder {
    @BindView(R.id.img_toy)
    ImageView imgToy;
    @BindView(R.id.text_toyName)
    TextView textToyName;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_state)
    TextView textState;
    @BindView(R.id.img_check)
    ImageView imgCheck;
    @BindView(R.id.v_checkLayout)
    LinearLayout vCheckLayout;
    OnViewHolderClickListener onCheckClickListener;

    int borderRadius;
    int borderWidth;



    public GrabWaWaItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_grab_item, parent, false));
        borderRadius = DensityUtil.dpToPx(itemView.getContext(), 4);
        borderWidth = DensityUtil.dpToPx(itemView.getContext(), 1);
    }

    @OnClick(R.id.v_checkLayout)
    public void onCheckClick() {
        if (data instanceof Order) {
            ((Order) data).setCheck(!((Order) data).isCheck());

            if (((Order) data).isCheck()) {
                imgCheck.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.checked));
            } else {
                imgCheck.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.uncheck));
            }
        }
        if (onCheckClickListener != null) {
            onCheckClickListener.onClick(this);
        }
    }

    public void setOnCheckClickListener(OnViewHolderClickListener onCheckClickListener) {
        this.onCheckClickListener = onCheckClickListener;
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof Order) {
            Order order = (Order) data;
            ImageLoader.getInstance().loadWithRadiusBorder(imgToy, order.getToyImg(), borderRadius, borderWidth, itemView.getResources().getColor(R.color.colorPrimary));
            textToyName.setText(order.getToyName());
            textDate.setText(order.getCreatedTime());
            textState.setText("抓取成功");

            if (((Order) data).isCheck()) {
                imgCheck.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.checked));
            } else {
                imgCheck.setImageDrawable(itemView.getContext().getResources().getDrawable(R.mipmap.uncheck));
            }
        }
    }


}
