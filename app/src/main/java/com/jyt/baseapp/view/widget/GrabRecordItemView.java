package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.json.GrabHistory;
import com.jyt.baseapp.util.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class GrabRecordItemView extends FrameLayout {
    @BindView(R.id.img_userHeader)
    ImageView imgUserHeader;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_date)
    TextView textDate;

    public GrabRecordItemView(@NonNull Context context) {
        this(context, null);
    }

    public GrabRecordItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(getContext()).inflate(R.layout.layout_grab_record,this,true);
        ButterKnife.bind(this);
    }


    public void setBean(GrabHistory grabHistory){
        String image = grabHistory.getUserImg();
        String name = grabHistory.getNickname();
        String date = grabHistory.getCreatedTime();

        ImageLoader.getInstance().loadCircle(imgUserHeader,image);

        textName.setText(grabHistory.getNickname());

        textDate.setText(grabHistory.getCreatedTime());

    }
}
