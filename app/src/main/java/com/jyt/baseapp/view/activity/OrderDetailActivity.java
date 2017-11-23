package com.jyt.baseapp.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Order;
import com.jyt.baseapp.bean.json.OrderProgress;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.OrderListModel;
import com.jyt.baseapp.model.impl.OrderListModelImpl;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.widget.OrderProgressItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/16.
 */

public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.text_toyName)
    TextView textToyName;
    @BindView(R.id.text_state)
    TextView textState;
    @BindView(R.id.img_toyImg)
    ImageView imgToyImg;
    @BindView(R.id.text_orderNo)
    TextView textOrderNo;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_receiver)
    TextView textReceiver;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.v_receive)
    LinearLayout vReceive;
    @BindView(R.id.v_orderListLayout)
    LinearLayout vOrderListLayout;


    OrderListModel orderListModel;
    Order order;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = getIntent().getParcelableExtra(IntentKey.KEY_ORDER);
        initWithOrder(order);
        getOrderDetail(order);
    }

    private void initWithOrder(Order order){
        if (!TextUtils.isEmpty(order.getToyName())){
            textToyName.setText(order.getToyName());
            textState.setText(order.getOrderTypeText());
            ImageLoader.getInstance().loadWithRadiusBorder(imgToyImg,order.getToyImg(), DensityUtil.dpToPx(getContext(),4),DensityUtil.dpToPx(getContext(),1),getResources().getColor(R.color.colorPrimary));
            textOrderNo.setText(order.getOrderNo());
            if (order.getOrderType()==2){
                vReceive.setVisibility(View.VISIBLE);
            }else {
                vReceive.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(order.getAddress()) || !TextUtils.isEmpty(order.getContactPerson()) || !TextUtils.isEmpty(order.getContactMobile()) ){
            textReceiver.setText(order.getContactPerson());
            textAddress.setText(order.getAddress());
            textDate.setText(order.getCreatedTime());
            createOrderProgressList(order.getLogistics(),vOrderListLayout);
        }
    }

    private void createOrderProgressList(List<OrderProgress> orderProgresses,LinearLayout content){
        content.removeAllViews();
        for (int i=0;i<orderProgresses.size();i++){
            content.addView(new OrderProgressItem(getContext()).setData(orderProgresses.get(i),i+1));
        }
    }

    private void getOrderDetail(final Order order){
        orderListModel.getOrderDetail(order.getOrderNo(), new BeanCallback<BaseJson<Order>>(getContext()) {
            @Override
            public void response(boolean success, BaseJson<Order> response, int id) {
                if (response.isRet()){
                    initWithOrder(response.getData());
                }
            }
        });
    }

    @OnClick(R.id.v_receive)
    public void onReceiveClick(){
        new AlertDialog.Builder(getContext()).setMessage("是否要确认收货").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                orderListModel.receiveOrder(order.getOrderNo(), new BeanCallback<BaseJson>() {
                    @Override
                    public void response(boolean success, BaseJson response, int id) {
                        if (response.isRet()){
                            getOrderDetail(order);
                        }
                        T.showShort(getContext(),response.getForUser());
                    }
                });
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(orderListModel = new OrderListModelImpl());
        return super.createModels();
    }
}
