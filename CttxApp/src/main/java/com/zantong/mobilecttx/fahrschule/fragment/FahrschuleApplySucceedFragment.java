package com.zantong.mobilecttx.fahrschule.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;

import cn.qqtheme.framework.util.ToastUtils;

/**
 * 驾校支付成功页面
 */
public class FahrschuleApplySucceedFragment extends BaseRefreshJxFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView mImgGift;
    /**
     * 恭喜你获得一份大礼包
     */
    private TextView mTvPrompt;
    /**
     * 礼品请到个人--优惠劵中查看
     */
    private TextView mTvPromptMine;
    private TextView mTvPromptContact;
    /**
     * 分享活动给好友
     */
    private Button mBtnPay;

    public static FahrschuleApplySucceedFragment newInstance() {
        return new FahrschuleApplySucceedFragment();
    }

    public static FahrschuleApplySucceedFragment newInstance(String param1, String param2) {
        FahrschuleApplySucceedFragment fragment = new FahrschuleApplySucceedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_fahrschule_apply_succeed;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);
    }

    @Override
    protected void onFirstDataVisible() {

    }

    @Override
    protected void DestroyViewAndThing() {

    }

    public void initView(View view) {
        mImgGift = (ImageView) view.findViewById(R.id.img_gift);
        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        mTvPromptMine = (TextView) view.findViewById(R.id.tv_prompt_mine);
        mTvPromptContact = (TextView) view.findViewById(R.id.tv_prompt_contact);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:

                new DialogMgr(getActivity(),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wechatShare(0);
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                wechatShare(1);
                            }
                        });
                break;
            default:
                break;
        }
    }

    /**
     * 微信分享
     *
     * @param flag (0 分享到微信好友 1 分享到微信朋友圈)
     */
    private void wechatShare(int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), WXEntryActivity.APP_ID, true);
        api.registerApp(WXEntryActivity.APP_ID);

        if (!api.isWXAppInstalled()) {
            ToastUtils.toastShort("您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if (PublicData.getInstance().loginFlag) {
            webpage.webpageUrl = BuildConfig.SHARE_APP_URL_3 + "?phoneNum="
                    + Des3.encode(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getResources().getString(R.string.tv_share_fahrschule_weixin_title);
        msg.description = getResources().getString(R.string.tv_share_fahrschule_weixin_content);
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sharelogo);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
