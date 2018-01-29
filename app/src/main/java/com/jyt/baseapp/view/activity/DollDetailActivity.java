package com.jyt.baseapp.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Address;
import com.jyt.baseapp.bean.json.Order;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.AddressModel;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.OrderListModel;
import com.jyt.baseapp.model.impl.AddressModelImpl;
import com.jyt.baseapp.model.impl.OrderListModelImpl;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.dialog.CenterImageDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by v7 on 2017/11/9.
 */
public class DollDetailActivity extends BaseActivity {
    @BindView(R.id.img_doll)
    ImageView imgDoll;
    @BindView(R.id.text_dollName)
    TextView textDollName;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.v_noAddress)
    LinearLayout vNoAddress;
    @BindView(R.id.text_receiver)
    TextView textReceiver;
    @BindView(R.id.text_tel)
    TextView textTel;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.v_addressLayout)
    LinearLayout vAddressLayout;
    @BindView(R.id.v_selAddress)
    FrameLayout vSelAddress;
    @BindView(R.id.text_send)
    TextView textSend;
    @BindView(R.id.text_coin)
    TextView textCoin;

    AddressModel addressModel;
    OrderListModel orderListModel;
    Order order;
    Address address;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_doll_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        order = getIntent().getParcelableExtra(IntentKey.KEY_DOLL);
        initView(order);
        addressModel.getAddressList(new BeanCallback<BaseJson<List<Address>>>() {
            @Override
            public void response(boolean success, BaseJson<List<Address>> response, int id) {
                if (response.isRet()) {
                    for (Address a :
                            response.getData()) {
                        if (a.isDefault()) {
                            address = a;
                            break;
                        }

                    }
                }
                initView(address);

            }
        });
    }

    public void initView(Order order) {
        ImageLoader.getInstance().loadWithRadiusBorder(imgDoll, order.getToyImg(), DensityUtil.dpToPx(getContext(), 4), DensityUtil.dpToPx(getContext(), 1), getResources().getColor(R.color.colorPrimary));
        textDollName.setText(order.getToyName());
        textDate.setText(order.getCreatedTime());
        textCoin.setText(order.getFreight()+"币");
    }

    public void initView(Address address) {
        if (address != null) {
            vNoAddress.setVisibility(View.GONE);
            vAddressLayout.setVisibility(View.VISIBLE);
            textReceiver.setText(address.getContactPerson());
            textTel.setText(address.getContactMobile());
            textAddress.setText(address.getPr() + address.getCity() + address.getArea() + address.getAddress());
        } else {
            vNoAddress.setVisibility(View.VISIBLE);
            vAddressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(addressModel = new AddressModelImpl());
        list.add(orderListModel = new OrderListModelImpl());
        return list;
    }

    @OnClick(R.id.v_selAddress)
    public void onSelAddressClick() {
        IntentHelper.openAddressListActivityForResult(getContext(), 1);
    }

    @OnClick(R.id.text_send)
    public void onSendClick() {
        submitOrder();
    }

    private void submitOrder() {
        if (address == null || TextUtils.isEmpty(address.getAddressId())) {
            T.showShort(getContext(), "请选择地址");
            return;
        }
        final CenterImageDialog payDialog = new CenterImageDialog(getContext());
        payDialog.setDialogTitle("确认支付运费");
        payDialog.setDialogCenterImgResId(R.mipmap.coin_tow);
        payDialog.setDialogDescText(order.getFreight()+"娃娃币");
        payDialog.setOnButtonClickListener(new CenterImageDialog.OnButtonClickListener() {
            @Override
            public void onClick(final Dialog dialog , int index) {
                if (index==0){
                    orderListModel.submitOrder(order.getOrderNo(), address.getAddressId(), new BeanCallback<BaseJson>() {
                        @Override
                        public void response(boolean success, BaseJson response, int id) {
                            if (response.isRet()){
                                dialog.dismiss();

                                onBackPressed();
                                T.showShort(getContext(),"提交成功！ 请在个人中心查看订单");
                            }else {
                                CenterImageDialog noEnought = new CenterImageDialog(getContext());
                                noEnought.setDialogTitle("确认支付运费");
                                noEnought.setDialogCenterImgResId(R.mipmap.fail);
                                noEnought.setLeftBtnText("去充值");
                                noEnought.setOnButtonClickListener(new CenterImageDialog.OnButtonClickListener() {
                                    @Override
                                    public void onClick(Dialog dialog, int index) {
                                        if ( index == 0){
                                            IntentHelper.openMyCoinActivity(getContext());
                                        }else if (index == 1){

                                        }
                                        dialog.dismiss();
                                        payDialog.dismiss();
                                    }
                                });

                                noEnought.show();
                            }
                        }
                    });
                }else if (index == 1){
                    dialog.dismiss();
                }
            }
        });
        payDialog.show();
//
//        orderListModel.submitOrder(order.getOrderNo(), address.getAddressId(), new BeanCallback<BaseJson>(getContext()) {
//            @Override
//            public void response(boolean success, BaseJson response, int id) {
//                T.showShort(getContext(), response.getForUser());
//                if (response.isRet()) {
//                    onBackPressed();
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            address = data.getParcelableExtra(IntentKey.KEY_ADDRESS);
            initView(address);
        }
    }
}
