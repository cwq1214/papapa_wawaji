package com.jyt.baseapp.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.SelAreaAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.json.Area;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/8/3.
 */

public class SelAddressDialog extends AlertDialog {

    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    SelAreaAdapter adapter;

    String province;
    String city;
    String district;

    /**
     * 0省 1市 2区
     */
    int type;

    List<Area> provinces;
    List<Area> cities;
    List<Area> districts;


    OnSelFinishCallback onSelFinishCallback;

    public SelAddressDialog(@NonNull Context context) {
        super(context, R.style.customDialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.style.AnimBottomInOut);
        getWindow().setLayout(ScreenUtils.getScreenWidth(getContext()), (int) (ScreenUtils.getScreenHeight(getContext()) * 0.5));
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.dialog_sel_location);
        ButterKnife.bind(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerview.setAdapter(adapter = new SelAreaAdapter());
        type = 0;
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                //region viewholder 点击
                Area data = (Area) holder.getData();
                if (type == 0){//设置省

                    province = ((Area) data).name;
                    getCityList(((Area) data).id+"");

                    tabLayout.removeAllTabs();
                    tabLayout.addTab(tabLayout.newTab().setText(province));
                    recyclerview.scrollToPosition(0);
                    type=1;
                }else if (type == 1){//设置市

                    city = ((Area) data).areaName;

                    TabLayout.Tab tab = tabLayout.getTabAt(1);
                    if (tab==null){
                        tabLayout.addTab(tabLayout.newTab().setText(city));
                    }else{
                        tabLayout.removeTabAt(1);
                        if (tabLayout.getTabCount()==2){
                            tabLayout.removeTabAt(1);
                        }
                        tabLayout.addTab(tabLayout.newTab().setText(city));
                    }
                    tabLayout.getTabAt(1).select();
                    getDistrictList(((Area) data).areaId+"");
                    type=2;
                    recyclerview.scrollToPosition(0);
                }else if (type == 2){//设置区

                    district = ((Area) data).areaName;

                    if (onSelFinishCallback!=null)
                        onSelFinishCallback.onSelFinish(province,city,district);
                    dismiss();

                }
                //endregion
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
//                if (position==type){
//                    return;
//                }
                if (position==0) {
                    type = 0;
                    adapter.setDataList(provinces);
                    selProvince(tab.getText().toString());
                }else if (position ==1 ){
                    type = 1;
                    adapter.setDataList(cities);
                    selCity(tab.getText().toString());
                }else if (position ==2 ){
                    type = 2;
                    adapter.setDataList(districts);
                    selDistrict(tab.getText().toString());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        if (!TextUtils.isEmpty(province)){
//            tabLayout.addTab(tabLayout.newTab().setText(province));
//        }

        if (TextUtils.isEmpty(province)||TextUtils.isEmpty(city)||TextUtils.isEmpty(district)){
            getProvinceList();
        }else {
            getProvinceCityDistrictList(getContext(), province, city, district, new BeanCallback<HashMap>(getContext(),true) {
                @Override
                public void response(boolean success, HashMap response, int id) {
                    provinces = (List<Area>) response.get("province");
                    cities = (List<Area>) response.get("city");
                    districts = (List<Area>) response.get("district");
                    tabLayout.removeAllTabs();

                    tabLayout.addTab(tabLayout.newTab().setText(province));
                    tabLayout.addTab(tabLayout.newTab().setText(city));
                    tabLayout.addTab(tabLayout.newTab().setText(district));

                    tabLayout.getTabAt(2).select();
                    adapter.setDataList(districts);
                    adapter.notifyDataSetChanged();

                    for (int i=0;i<districts.size();i++){
                        districts.get(i).isSel = false;
                        if (districts.get(i).areaName.equals(district)){
                            districts.get(i).isSel = true;
                            recyclerview.scrollToPosition(i);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();

                }
            });

        }
//
//        if (!TextUtils.isEmpty(city)){
//            tabLayout.addTab(tabLayout.newTab().setText(city));
//        }
//
//        if(!TextUtils.isEmpty(district)){
//            tabLayout.addTab(tabLayout.newTab().setText(district));
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void setSelectLocation(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }

    @OnClick(R.id.btn_close)
    public void onCloseClick() {
        dismiss();
    }




    private void getProvinceList() {
        getProvinceList(getContext(), new BeanCallback<String>(getContext(),false) {
            @Override
            public void response(boolean success, String response, int id) {
                if (success) {
                    JSONArray json = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        json = jsonObject.optJSONObject("result").optJSONObject("jingdong_area_province_get_responce").optJSONArray("province_areas");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    if (json != null) {
                        provinces = new Gson().fromJson(json.toString(), new TypeToken<List<Area>>() {
                        }.getType());

                        adapter.setDataList(provinces);
                        adapter.notifyDataSetChanged();
                        selProvince(province);
                    }
                }
            }
        });
    }

    private void getCityList(String parentId){
        getCityList(getContext(),parentId,new BeanCallback<String>(getContext(),false) {
            @Override
            public void response(boolean success, String response, int id) {
                if (success) {
                    JSONArray json = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        json = jsonObject.optJSONObject("result").optJSONObject("jingdong_areas_city_get_responce").optJSONObject("baseAreaServiceResponse").optJSONArray("data");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    if (json != null) {
                        cities = new Gson().fromJson(json.toString(), new TypeToken<List<Area>>() {
                        }.getType());


                        adapter.setDataList(cities);
                        adapter.notifyDataSetChanged();

                        selCity(city);

                    }
                }
            }
        });
    }

    private void getDistrictList(String parentId){
        getCountryList(getContext(),parentId,new BeanCallback<String>(getContext(),false) {
            @Override
            public void response(boolean success, String response, int id) {
                if (success) {
                    JSONArray json = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        json = jsonObject.optJSONObject("result").optJSONObject("jingdong_areas_county_get_responce").optJSONObject("baseAreaServiceResponse").optJSONArray("data");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    if (json != null) {
                        districts= new Gson().fromJson(json.toString(), new TypeToken<List<Area>>() {
                        }.getType());


                        adapter.setDataList(districts);
                        adapter.notifyDataSetChanged();

                        selDistrict(district);

                    }
                }
            }
        });
    }

    /**
     * 选中省
     * @param province
     */
    private void selProvince(String province){
        if (TextUtils.isEmpty(province)){
            return;
        }
        List<Area> areas = adapter.getDataList();
        if (areas==null){
            return;
        }
        int scrollTo = -1;
        for (int i=0,max = areas.size();i<max;i++){
            areas.get(i).isSel = false;
            if (areas.get(i).name.equals(province)){
                areas.get(i).isSel = true;
                scrollTo = i;
                TabLayout.Tab tab=tabLayout.getTabAt(0);
                if (tab!=null)
                    tab.select();
            }
        }
        adapter.notifyDataSetChanged();
        if (scrollTo != -1){
            recyclerview.scrollToPosition(scrollTo);
        }
//        if (cities==null&&!TextUtils.isEmpty(city)){
//            getCityList(areas.get(scrollTo).id+"");
//        }
    }

    /**
     * 选中市
     * @param city
     */
    private void selCity(String city){
        if (TextUtils.isEmpty(city)){
            return;
        }
        List<Area> areas = adapter.getDataList();
        if (areas==null){
            return;
        }
        int scrollTo = -1;
        for (int i=0,max = areas.size();i<max;i++){
            areas.get(i).isSel = false;
            if (areas.get(i).areaName.equals(city)){
                areas.get(i).isSel = true;
                scrollTo = i;
                TabLayout.Tab tab=tabLayout.getTabAt(1);
                if (tab!=null)
                    tab.select();
            }
        }
        adapter.notifyDataSetChanged();
        if (scrollTo != -1){
            recyclerview.scrollToPosition(scrollTo);
        }

//        if (districts==null&&!TextUtils.isEmpty(district)){
//            getDistrictList(areas.get(scrollTo).areaId+"");
//        }
    }

    /**
     * 选中区
     * @param district
     */
    private void selDistrict(String district){
        if (TextUtils.isEmpty(district)){
            return;
        }
        List<Area> areas = adapter.getDataList();
        if (areas==null){
            return;
        }
        int scrollTo = -1;
        for (int i=0,max = areas.size();i<max;i++){
            areas.get(i).isSel = false;
            if (areas.get(i).areaName.equals(district)){
                areas.get(i).isSel = true;
                scrollTo = i;
                TabLayout.Tab tab=tabLayout.getTabAt(2);
                if (tab!=null)
                    tab.select();
            }
        }
        adapter.notifyDataSetChanged();
//        if (scrollTo != -1){
//            recyclerview.scrollToPosition(scrollTo);
//        }
    }

    public void setOnSelFinishCallback(OnSelFinishCallback onSelFinishCallback) {
        this.onSelFinishCallback = onSelFinishCallback;
    }

    public interface OnSelFinishCallback {
        void onSelFinish(String province, String city, String district);
    }



    public static void getProvinceCityDistrictList(final Context context, final String province, final String city, String district, final BeanCallback callback){
        final HashMap<String,List> location = new HashMap<>();
        getProvinceList(context,new BeanCallback<String>(context,true){

            @Override
            public void response(boolean success, String response, int id) {
                if (success) {
                    JSONArray json = null;
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        json = jsonObject.optJSONObject("result").optJSONObject("jingdong_area_province_get_responce").optJSONArray("province_areas");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    if (json != null) {
                        List<Area> provinces = new Gson().fromJson(json.toString(), new TypeToken<List<Area>>() {
                        }.getType());
                        location.put("province",provinces);

                        for (int i=0,maxP = provinces.size();i<maxP;i++){
                            if (provinces.get(i).name.equals(province)){
                                getCityList(context, provinces.get(i).id+"", new BeanCallback<String>(context,true) {
                                    @Override
                                    public void response(boolean success, String response, int id) {
                                        if (success) {
                                            JSONArray json = null;
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                json = jsonObject.optJSONObject("result").optJSONObject("jingdong_areas_city_get_responce").optJSONObject("baseAreaServiceResponse").optJSONArray("data");
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                            if (json != null) {
                                                List<Area> cities = new Gson().fromJson(json.toString(), new TypeToken<List<Area>>() {
                                                }.getType());
                                                location.put("city",cities);
                                                for (int i1=0,maxC=cities.size();i1<maxC;i1++){
                                                    if (cities.get(i1).areaName.equals(city)){
                                                        getCountryList(context, cities.get(i1).areaId+"", new BeanCallback<String>(context,true) {
                                                            @Override
                                                            public void response(boolean success, String response, int id) {
                                                                if (success) {
                                                                    JSONArray json = null;
                                                                    try {
                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                        json = jsonObject.optJSONObject("result").optJSONObject("jingdong_areas_county_get_responce").optJSONObject("baseAreaServiceResponse").optJSONArray("data");
                                                                    } catch (JSONException e1) {
                                                                        e1.printStackTrace();
                                                                    }
                                                                    if (json != null) {
                                                                        List<Area> districts= new Gson().fromJson(json.toString(), new TypeToken<List<Area>>() {
                                                                        }.getType());
                                                                        location.put("district",districts);
                                                                        callback.response(true,location,id);

                                                                    }
                                                                }
                                                            }
                                                        });
                                                        break;
                                                    }
                                                }

                                            }
                                        }
                                    }
                                });
                                break;
                            }
                        }

                    }
                }
            }
        });
    }

    //获取省列表
    public static void getProvinceList(Context context,BeanCallback callback){
        OkHttpUtils.get().url("https://way.jd.com/JDCloud/getProvince?appkey=98a367c4465dd4efb99ca7b072033244").build().execute(callback);
    }
    //获取市列表
    public static void getCityList(Context context,String parentId,BeanCallback callback){
        OkHttpUtils.get().url("https://way.jd.com/JDCloud/getCity").addParams("parent_id",parentId).addParams("appkey","98a367c4465dd4efb99ca7b072033244").build().execute(callback);
    }
    //获取区列表
    public static void getCountryList(Context context,String parentId,BeanCallback callback){
        OkHttpUtils.get().url("https://way.jd.com/JDCloud/getCountry").addParams("parent_id",parentId).addParams("appkey","98a367c4465dd4efb99ca7b072033244").build().execute(callback);
    }
}
