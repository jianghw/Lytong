package com.zantong.mobilecttx.share.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountBean;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResult;
import com.zantong.mobilecttx.interf.IFahrschuleShareFtyContract;
import com.zantong.mobilecttx.presenter.fahrschule.FahrschuleSharePresenter;
import com.zantong.mobilecttx.share.activity.ShareParentActivity;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;
import com.zantong.mobilecttx.zxing.EncodingUtils;

import java.util.List;

import cn.qqtheme.framework.util.ToastUtils;

/**
 * 分享返现页面
 */
public class FriendShareFragment extends BaseRefreshJxFragment
        implements IFahrschuleShareFtyContract.IFahrschuleShareFtyView, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView mImgScan;

    /**
     * 去邀请好友
     */
    private Button mBtnPay;
    private TextView mTvPrompt;
    /**
     * 0
     */
    private TextView mTvPeopleCount;
    /**
     * 已经邀请
     */
    private TextView mTvInvited;
    /**
     * 0
     */
    private TextView mTvPeoplePay;
    /**
     * 已成功支付
     */
    private TextView mTvPayed;

    private IFahrschuleShareFtyContract.IFahrschuleShareFtyPresenter mPresenter;
    private ShareParentActivity.FragmentDestroy mCloseListener;

    public static FriendShareFragment newInstance() {
        return new FriendShareFragment();
    }

    public static FriendShareFragment newInstance(String param1, String param2) {
        FriendShareFragment fragment = new FriendShareFragment();
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
        if (mPresenter != null) mPresenter.getRecordCount();
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_friend_share;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        FahrschuleSharePresenter presenter = new FahrschuleSharePresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(IFahrschuleShareFtyContract.IFahrschuleShareFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getRecordCount();

        String contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        if (PublicData.getInstance().loginFlag && PublicData.getInstance().mLoginInfoBean != null)
            contentString = BuildConfig.SHARE_APP_URL_3 + "?phoneNum="
                    + Des3.encode(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        else
            contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        if (!TextUtils.isEmpty(contentString)) {
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(
                    contentString, 360, 360, BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));
            mImgScan.setImageBitmap(qrCodeBitmap);
        }
    }

    public void setCloseListener(ShareParentActivity.FragmentDestroy fragmentDestroy) {
        mCloseListener = fragmentDestroy;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCloseListener != null) mCloseListener.closeListener(0);
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    @Override
    public String getType() {
        return "分享返现";
    }

    /**
     * N 7.获取用户指定活动的统计总数
     */
    @Override
    public void getRecordCountError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    /**
     * 1 分享数，2 注册数，3 绑卡数，4 驾校报名数
     */
    @Override
    public void getRecordCountSucceed(RecordCountResult result) {
        List<RecordCountBean> countBeanList = result.getData();
        if (countBeanList != null && countBeanList.size() > 0) {
            for (RecordCountBean bean : countBeanList) {
                if (bean.getStatisticalType() == 3) {
                    mTvPeoplePay.setText(String.valueOf(bean.getStatisticalNum()));
                } else if (bean.getStatisticalType() == 1) {
                    mTvPeopleCount.setText(String.valueOf(bean.getStatisticalType()));
                }
            }
        }
    }

    public void initView(View view) {
        mImgScan = (ImageView) view.findViewById(R.id.img_scan);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        mTvPeopleCount = (TextView) view.findViewById(R.id.tv_people_count);
        mTvInvited = (TextView) view.findViewById(R.id.tv_invited);
        mTvPeoplePay = (TextView) view.findViewById(R.id.tv_people_pay);
        mTvPayed = (TextView) view.findViewById(R.id.tv_payed);
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
            webpage.webpageUrl = "http://liyingtong.com:8081/h5/share/share.html?phoneNum="
                    + Des3.encode(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "掌上违章缴费，销分一步到位，与你只有一个App的距离";
        msg.description = "畅通车友会——有我在手，一路畅通畅通车友会由工银安盛与中国工商银行" +
                "上海分行联手打造，旨在为牡丹畅通卡用户提供便捷的驾乘金融服务体验。功能覆盖了" +
                "交通违章缴费、驾乘人员保险保障、特色增值服务等多项方便快捷的在线服务，" +
                "使车主的驾车生活更便捷、更丰富，更畅通!";
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