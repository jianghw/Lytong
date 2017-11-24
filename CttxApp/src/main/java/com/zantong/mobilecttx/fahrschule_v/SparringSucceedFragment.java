package com.zantong.mobilecttx.fahrschule_v;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.card.activity.ApplyCardFirstActivity;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;

/**
 * 陪练支付 成功
 */
public class SparringSucceedFragment extends JxBaseRefreshFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FahrschuleActivity.SwitcherListener mSwitcherListener;

    private String mParam1;
    private String mParam2;

    private ImageView mImgGift;
    private TextView mTvPrompt;
    /**
     * 分享活动给好友
     */
    private TextView mTvCommit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static SparringSucceedFragment newInstance() {
        return new SparringSucceedFragment();
    }

    public static SparringSucceedFragment newInstance(String param1, String param2) {
        SparringSucceedFragment fragment = new SparringSucceedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected int initFragmentView() {
        return R.layout.fragment_sparring_succeed;
    }

    @Override
    protected void bindFragmentView(View fragment) {
        initView(fragment);
    }

    @Override
    protected void onRefreshData() {}

    @Override
    protected void onLoadMoreData() {}

    @Override
    protected void onFirstDataVisible() {
        //未办卡
        if (TextUtils.isEmpty(MainRouter.getUserFilenum())) {
            StringBuffer sb = new StringBuffer();
            sb.append("<font color=\"#f3362b\">");
            sb.append("<u>");
            sb.append("在线办理牡丹畅通卡");
            sb.append("</u>");
            sb.append("</font>");
            sb.append("<br>");
            sb.append("可再获得免费陪练2小时课程");

            mTvPrompt.setText(Html.fromHtml(sb.toString()));
        } else {
            String isBandCard = getResources().getString(R.string.tv_sparring_prompt);
            mTvPrompt.setText(isBandCard);
        }
    }


    public void initView(View view) {
        mImgGift = (ImageView) view.findViewById(R.id.img_gift);
        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        mTvPrompt.setOnClickListener(this);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
    }

    /**
     * 优惠页面
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        MainRouter.gotoMainActivity(getActivity(), 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_prompt://绑卡去
                if (TextUtils.isEmpty(MainRouter.getUserFilenum()))
                    Act.getInstance().gotoIntent(getActivity(), ApplyCardFirstActivity.class);
                break;
            case R.id.tv_commit:
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
        if (MainRouter.isUserLogin()) {
            webpage.webpageUrl = ShareParentActivity.getShareAppUrl(4) + "?phoneNum="
                    + Des3.encode(MainRouter.getUserPhoenum());
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
