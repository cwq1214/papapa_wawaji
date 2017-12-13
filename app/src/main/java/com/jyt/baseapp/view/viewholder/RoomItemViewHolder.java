package com.jyt.baseapp.view.viewholder;

import android.support.v7.widget.StaggeredGridLayoutManager;
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

    public RoomItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_room_item, parent, false));
        vImgLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(right-left,right-left);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(((LinearLayout) v.getParent()).getWidth(),((LinearLayout) v.getParent()).getWidth());
                vImgLayout.setLayoutParams(params);

                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(((LinearLayout) v.getParent()).getWidth(),((LinearLayout) v.getParent()).getWidth());
                imgGoods.setLayoutParams(params1);

                vImgLayout.removeOnLayoutChangeListener(this);
            }
        });

//        itemView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//                StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(right-left,right-left+DensityUtil.dpToPx(v.getContext(),33));
//                itemView.setLayoutParams(params);
//
//                itemView.removeOnLayoutChangeListener(this);
//            }
//        });

    }

    @Override
    public void setData(HomeToyResult data) {
        super.setData(data);
        textName.setText(data.getToyName());
        textPrice.setText(data.getNeedPay()+"币/次");
        if (Integer.valueOf(data.getLeisure())==0){
            textStatus.setText("游戏中 "+data.getLeisure()+"空闲/"+data.getUseing()+"占用");
            textStatus.setTextColor(itemView.getResources().getColor(R.color.t3Color));
        }else {
            textStatus.setText("空闲中 "+data.getLeisure()+"空闲/"+data.getUseing()+"占用");
            textStatus.setTextColor(itemView.getResources().getColor(R.color.t4Color));
        }
        String url = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1510122506&di=fb7a112f7b5d08b696f7762815478c08&src=http://img2.shangxueba.com/img/uploadfile/20141022/12/7244D67D1CE5C5EB8F526A72EB0F0D33.jpg";

//        ImageLoader.getInstance().loadWithRadiusBorder(imgGoods, data.getToyImg(), DensityUtil.dpToPx(itemView.getContext(),4),DensityUtil.dpToPx(itemView.getContext(),1),itemView.getResources().getColor(R.color.colorPrimary));
        ImageLoader.getInstance().load(imgGoods, data.getToyImg());
    }
}
