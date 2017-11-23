package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Address;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.AddressModel;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.impl.AddressModelImpl;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.dialog.SelAddressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.text_name)
    EditText textName;
    @BindView(R.id.text_tel)
    EditText textTel;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.v_selAddress)
    RelativeLayout vSelAddress;
    @BindView(R.id.text_addressDetail)
    EditText textAddressDetail;
    @BindView(R.id.img_setDefault)
    ImageView imgSetDefault;

    Address address;
    AddressModel addressModel;
    SelAddressDialog selAddressDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        address = getIntent().getParcelableExtra(IntentKey.KEY_ADDRESS);

        if (address!=null){
            initView(address);
        }else {
            address = new Address();
        }

        setFunctionText("保存");
        showFunctionText();

    }

    @Override
    public void onFunctionClick() {
        super.onFunctionClick();
        if (TextUtils.isEmpty(textName.getText())){
            T.showShort(getContext(),"请输入收件人姓名");
            return;
        }
        if (TextUtils.isEmpty(textTel.getText())){
            T.showShort(getContext(),"请输入收件人联系电话");
            return;
        }
        if (TextUtils.isEmpty(textAddress.getText())){
            T.showShort(getContext(),"请选择地址");
            return;
        }
        if (TextUtils.isEmpty(textAddressDetail.getText())){
            T.showShort(getContext(),"请填写详细地址");
            return;
        }
        submitAddress();
    }

    private void submitAddress(){
        addressModel.modifyAddress(address.getAddressId(), textName.getText().toString(), textTel.getText().toString(), textAddressDetail.getText().toString(), address.getPr(), address.getCity(), address.getArea(),address.isDefault(), new BeanCallback<BaseJson>(getContext()) {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()){

                    onBackPressed();
                }
                T.showShort(getContext(),response.getForUser());
            }
        });
    }

    private void initView(Address address){
        textName.setText(address.getContactPerson());
        textTel.setText(address.getContactMobile());
        textAddress.setText(address.getPr()+address.getCity()+address.getArea());
        textAddressDetail.setText(address.getAddress());
        imgSetDefault.setImageDrawable(getResources().getDrawable(address.isDefault()?R.mipmap.selector_sel:R.mipmap.selector_unsel));
    }

    @OnClick(R.id.v_selAddress)
    public void onSelAddressClick(){
        if (selAddressDialog==null){
            selAddressDialog = new SelAddressDialog(getContext());
            selAddressDialog.setOnSelFinishCallback(new SelAddressDialog.OnSelFinishCallback() {
                @Override
                public void onSelFinish(String province, String city, String district) {
                    address.setPr(province);
                    address.setCity(city);
                    address.setArea(district);
                    textAddress.setText(province+city+district);
                }
            });
        }
        if (address.getPr()!=null&&address.getCity()!=null&&address.getArea()!=null){
            selAddressDialog.setSelectLocation(address.getPr(),address.getCity(),address.getArea());
        }

        selAddressDialog.show();
    }
    @OnClick(R.id.img_setDefault)
    public void onSetIsDefaultClick(){
        address.setIsDefault(!address.isDefault());
        imgSetDefault.setImageDrawable(getResources().getDrawable(address.isDefault()?R.mipmap.selector_sel:R.mipmap.selector_unsel));
    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(addressModel = new AddressModelImpl());
        return list;
    }
}
