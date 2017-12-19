package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.BaseModel;
import com.jyt.baseapp.model.PersonalInfoModel;
import com.jyt.baseapp.model.impl.PersonalInfoModelImpl;
import com.jyt.baseapp.util.FileUtil;
import com.jyt.baseapp.util.ImageLoader;
import com.jyt.baseapp.util.ImageUtil;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.T;
import com.linchaolong.android.imagepicker.ImagePicker;
import com.linchaolong.android.imagepicker.cropper.CropImage;
import com.linchaolong.android.imagepicker.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/12/12.
 */

public class ModifyUserInfoActivity extends BaseActivity {

    PersonalInfoModel personalInfoModel;

    String defaultName;
    String defaultImg;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.v_selImage)
    LinearLayout vSelImage;
    @BindView(R.id.input_nickName)
    EditText inputNickName;

    ImagePicker imagePicker;

    File localTempImage;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_user_info;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFunctionText("保存");

        defaultName = getIntent().getStringExtra(IntentKey.KEY_NAME);
        defaultImg = getIntent().getStringExtra(IntentKey.KEY_IMAGE_URL);

        imgHead.setDrawingCacheEnabled(true);

        loadHeadImg(defaultImg);
        inputNickName.setText(defaultName);

    }

    @Override
    public void onFunctionClick() {
        super.onFunctionClick();

        modifyUserInfo();
    }

    @Override
    public List<BaseModel> createModels() {
        List models= new ArrayList<>();
        models.add(personalInfoModel = new PersonalInfoModelImpl());
        return models;
    }

    public void modifyUserInfo() {

//        if (localTempImage!=null) {
//            Bitmap bitmap = imgHead.getDrawingCache();
//
//            saveBitmap(localTempImage.getAbsolutePath(), bitmap);
//        }
        personalInfoModel.modifyUserInfo(localTempImage == null ? null : localTempImage.getAbsolutePath(), inputNickName.getText().toString(), new BeanCallback<BaseJson>() {
            @Override
            public void response(boolean success, BaseJson response, int id) {
                if (response.isRet()){
                    finish();
                }else {
                    T.showShort(getContext(),response.getForUser());
                }
            }
        });

    }

    private void loadHeadImg(String img){
        ImageLoader.getInstance().loadHeader(imgHead,img);
    }

    @OnClick(R.id.v_selImage)
    public void onViewClicked() {
//        IntentHelper.openSelImageActivityForResult(this,1);
        imagePicker = new ImagePicker();
// 设置标题
        imagePicker.setTitle("设置头像");
// 设置是否裁剪图片
        imagePicker.setCropImage(true);
// 启动图片选择器
        imagePicker.startChooser(this, new ImagePicker.Callback() {
            // 选择图片回调
            @Override public void onPickImage(Uri imageUri) {

                L.e("选择图片回调");
//                ImageLoader.getInstance().loadHeader(imgHead,imageUri.toString());

            }

            // 裁剪图片回调
            @Override public void onCropImage(Uri imageUri) {
                L.e("裁剪图片回调");
                ImageLoader.getInstance().loadHeader(imgHead,imageUri.toString());
                localTempImage = new File(FileUtil.getPath(getContext(),imageUri));
//                localTempImage = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp.png");

//                draweeView.setImageURI(imageUri);
//                draweeView.getHierarchy().setRoundingParams(RoundingParams.asCircle());
            }

            // 自定义裁剪配置
            @Override public void cropConfig(CropImage.ActivityBuilder builder) {
                builder
                        // 是否启动多点触摸
                        .setMultiTouchEnabled(false)
                        // 设置网格显示模式
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        // 圆形/矩形
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        // 调整裁剪后的图片最终大小
                        .setRequestedSize(150, 150)
                        // 宽高比
                        .setAspectRatio(1, 1);
            }

            // 用户拒绝授权回调
            @Override public void onPermissionDenied(int requestCode, String[] permissions,
                                                     int[] grantResults) {
            }
        });
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (imagePicker!=null)
            imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (imagePicker!=null)
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /** 保存方法 */
    public void saveBitmap(String path,Bitmap bitmap) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (localTempImage != null && localTempImage.exists() && localTempImage.isFile()){
//            localTempImage.delete();
//        }
    }
}
