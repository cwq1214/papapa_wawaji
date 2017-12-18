package com.jyt.baseapp.view.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.HomeToyResult;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.view.widget.RoundRelativeLayout;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/11/8.
 */

public class RoomItemViewHolder extends BaseViewHolder<HomeToyResult> {
    @BindView(R.id.img_goods)
    ImageView imgGoods;
    @BindView(R.id.text_status)
    TextView textStatus;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.v_imgLayout)
    RelativeLayout vImgLayout;
    @BindView(R.id.v_roundLayout1)
    RoundRelativeLayout vRoundLayout1;


    public RoomItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_room_item, parent, false));

        Context context = itemView.getContext();
        int windowWidth = ScreenUtils.getScreenWidth(itemView.getContext());
        int itemWidth = (windowWidth - DensityUtil.dpToPx(context, 16 + 16 + 16)) / 2;


        ViewGroup.LayoutParams vRoundLayout1Params = vRoundLayout1.getLayoutParams();
        vRoundLayout1Params.height = itemWidth + DensityUtil.dpToPx(context,33);
        vRoundLayout1Params.width = itemWidth;

        vRoundLayout1.setLayoutParams(vRoundLayout1Params);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( itemWidth - DensityUtil.dpToPx(context,1) , itemWidth - DensityUtil.dpToPx(context,1));
        vImgLayout.setLayoutParams(params);

//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(itemWidth - DensityUtil.dpToPx(context,1), itemWidth - DensityUtil.dpToPx(context,1) );
//        imgGoods.setLayoutParams(params1);




    }

    @Override
    public void setData(HomeToyResult data) {
        super.setData(data);
        textName.setText(data.getToyName());
        textPrice.setText(data.getNeedPay() + "币/次");
        if (Integer.valueOf(data.getLeisure()) == 0) {
            textStatus.setText("游戏中 " + data.getLeisure() + "空闲/" + data.getUseing() + "占用");
            textStatus.setTextColor(itemView.getResources().getColor(R.color.t3Color));
        } else {
            textStatus.setText("空闲中 " + data.getLeisure() + "空闲/" + data.getUseing() + "占用");
            textStatus.setTextColor(itemView.getResources().getColor(R.color.t4Color));
        }
        String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1510122506&di=fb7a112f7b5d08b696f7762815478c08&src=http://img2.shangxueba.com/img/uploadfile/20141022/12/7244D67D1CE5C5EB8F526A72EB0F0D33.jpg";

//        ImageLoader.getInstance().loadWithRadiusBorder(imgGoods, data.getToyImg(), DensityUtil.dpToPx(itemView.getContext(),4),DensityUtil.dpToPx(itemView.getContext(),1),itemView.getResources().getColor(R.color.colorPrimary));
        ImageLoader.getInstance().load(imgGoods, data.getToyImg());
    }
}
