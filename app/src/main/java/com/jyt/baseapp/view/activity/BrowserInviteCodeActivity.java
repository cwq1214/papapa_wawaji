package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Api;
import com.jyt.baseapp.bean.json.PersonalInfo;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UserInfo;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/12/25.
 */

public class BrowserInviteCodeActivity extends BaseActivity {
    @BindView(R.id.v_code)
    LinearLayout vCode;
    @BindView(R.id.text_shareInviteCodeDesc)
    TextView textShareInviteCodeDesc;
    @BindView(R.id.text_submit)
    TextView textSubmit;
    @BindView(R.id.text_shareInviteCodeDesc2)
    TextView textShareInviteCodeDesc2;
    @BindView(R.id.text_domain)
    TextView textDomain;

    PersonalInfo personalInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browser_invite_code;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textDomain.setText(Api.domainText);

        personalInfo = getIntent().getParcelableExtra(IntentKey.KEY_DATA);
        if (personalInfo != null) {


            textShareInviteCodeDesc.setText(personalInfo.getInviteContentSubmit());
            textShareInviteCodeDesc2.setText(personalInfo.getInviteContentSubmitDown());
            fillCode(personalInfo.getUserCode());

        }
    }


    public void fillCode(String inviteCode) {
        char[] codes = inviteCode.toCharArray();

        vCode.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = DensityUtil.dpToPx(getContext(), 4);
        int padding = DensityUtil.dpToPx(getContext(), 8);
        for (char b : codes) {
            TextView textView = new TextView(getContext());
            textView.setText(String.valueOf(b));
            textView.setTextSize(24);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(padding, padding, padding, padding);
            textView.setLayoutParams(params);
            textView.setBackground(getResources().getDrawable(R.drawable.bg_pink_solid_4radius));
            vCode.addView(textView);
        }
    }

    @OnClick(R.id.text_submit)
    public void onViewClicked() {
        String shareUrl = UserInfo.getShareLink();
        if (TextUtils.isEmpty(shareUrl)){
            T.showShort(getContext(),"无分享链接");
            return;
        }
        UMWeb umWeb = new UMWeb(shareUrl);
        umWeb.setTitle("PPP抓娃娃");
        umWeb.setDescription("不一样的线上抓娃娃机");
        new ShareAction(getActivity()).withMedia(umWeb)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        L.e("[onStart]" + share_media);
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        L.e("[onResult]" + share_media);

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        L.e("[onError]" + share_media);

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        L.e("[onCancel]" + share_media);

                    }
                }).open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
