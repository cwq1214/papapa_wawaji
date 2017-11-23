package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.AddressListAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.json.Address;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.itemDecoration.RecyclerViewDivider;
import com.jyt.baseapp.model.AddressModel;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.impl.AddressModelImpl;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/9.
 */

public class AddressListActivity extends BaseActivity {
    @BindView(R.id.v_recyclerView)
    RecyclerView vRecyclerView;
    @BindView(R.id.text_addAddress)
    TextView textAddAddress;

    AddressListAdapter adapter;

    AddressModel addressModel;

    boolean needSetResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        needSetResult = getIntent().getBooleanExtra(IntentKey.KEY_NEED_SET_RESULT, false);

        vRecyclerView.setAdapter(adapter = new AddressListAdapter());
        vRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL, R.drawable.divider_10dp, false));
        //选择地址
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                if (needSetResult) {
                    Intent intent = IntentHelper.getIntent();
                    intent.putExtra(IntentKey.KEY_ADDRESS, (Parcelable) holder.getData());
                    setResult(RESULT_OK, intent);
                    onBackPressed();
                }

            }
        });
        //编辑地址
        adapter.setOnModifyAddressClick(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                IntentHelper.openEditAddressActivity(getContext(), (Parcelable) holder.getData());
            }
        });
        //删除地址
        adapter.setOnDeleteAddressClick(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                deleteAddress((Address) holder.getData());
            }
        });
        //设置默认地址
        adapter.setOnSetDefaultAddressClick(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                setDefaultAddress((Address) holder.getData());
            }
        });

    }

    @Override
    public List<BaseModel> createModels() {
        List list = new ArrayList();
        list.add(addressModel = new AddressModelImpl());
        return list;
    }

    @OnClick(R.id.text_addAddress)
    public void onAddAddressClick(){
        IntentHelper.openEditAddressActivity(getContext(),null);
    }

    /**
     *  修改默认地址
     * @param address
     */
    private void setDefaultAddress(final Address address){
        addressModel.setDefaultAddress(address.getAddressId(), !address.isDefault(), new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()){
                    address.setIsDefault(!address.isDefault());
                    if (address.isDefault()){
                        for (Object a :
                                adapter.getDataList()) {
                            if (((Address) a).getAddressId().equals(address.getAddressId())){
                                continue;
                            }else {
                                ((Address) a).setIsDefault(false);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                T.showShort(getContext(),response.getForUser());
            }
        });
    }

    /**
     * 删除地址
     * @param address
     */
    private void deleteAddress(final Address address){
        addressModel.deleteAddress(address.getAddressId(), new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()){
                    for (Object a :
                            adapter.getDataList()) {
                        if (((Address) a).getAddressId().equals(address.getAddressId())){
                            adapter.getDataList().remove(a);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                T.showShort(getContext(),response.getForUser());
            }
        });
    }

    private void getAddressList(){
        addressModel.getAddressList(new BeanCallback<BaseJson<List<Address>>>() {
            @Override
            public void response(boolean success, BaseJson<List<Address>> response, int id) {
                if (response.isRet()){
                    adapter.setDataList(response.getData());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }
}
