package com.zantong.mobilecttx.fahrschule_v;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountBean;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_p.FahrschuleSharePresenter;
import com.zantong.mobilecttx.share_p.IFahrschuleShareFtyContract;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;
import com.zantong.mobilecttx.zxing.EncodingUtils;

import java.util.List;

/**
 * 驾校报名分享页面
 */
public class FahrschuleShareFragment extends JxBaseRefreshFragment
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

    public static FahrschuleShareFragment newInstance() {
        return new FahrschuleShareFragment();
    }

    public static FahrschuleShareFragment newInstance(String param1, String param2) {
        FahrschuleShareFragment fragment = new FahrschuleShareFragment();
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
    protected void onLoadMoreData() {}

    @Override
    protected int initFragmentView() {
        return R.layout.fragment_fahrschule_share;
    }

    @Override
    protected void bindFragmentView(View fragment) {
        initView(fragment);

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
        if (MainRouter.isUserLogin())
            contentString = ShareParentActivity.getShareAppUrl(3) + "?phoneNum="
                    + Des3.encode(MainRouter.getUserPhoenum());
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
        if (mPresenter != null) mPresenter.unSubscribe();
        if (mCloseListener != null) mCloseListener.closeListener(0);
    }

    @Override
    public String getType() {
        return "3";
    }

    /**
     * N 7.获取用户指定活动的统计总数
     */
    @Override
    public void recordCountError(String message) {
        ToastUtils.toastShort(message);
    }

    /**
     * 1 分享数，2 注册数，3 绑卡数，4 驾校报名数
     */
    @Override
    public void recordCountSucceed(Object result) {
        if (!(result instanceof RecordCountResponse)) return;
        RecordCountResponse response = (RecordCountResponse) result;

        List<RecordCountBean> countBeanList = response.getData();
        if (countBeanList != null && countBeanList.size() > 0) {
            for (RecordCountBean bean : countBeanList) {
                if (bean.getStatisticalType() == 4) {
                    mTvPeoplePay.setText(String.valueOf(bean.getStatisticalNum()));
                } else if (bean.getStatisticalType() == 2) {
                    mTvPeopleCount.setText(String.valueOf(bean.getStatisticalNum()));
                }
            }
        }
    }

    public void initView(View view) {
        mImgScan = (ImageView) view.findViewById(R.id.img_scan);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        mTvPrompt.setOnClickListener(this);
        mTvPeopleCount = (TextView) view.findViewById(R.id.tv_people_count);
        mTvInvited = (TextView) view.findViewById(R.id.tv_invited);
        mTvPeoplePay = (TextView) view.findViewById(R.id.tv_people_pay);
        mTvPayed = (TextView) view.findViewById(R.id.tv_payed);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_prompt:
                MobUtils.getInstance().eventIdByUMeng(33);

                customImageDialog();
                break;
            case R.id.btn_pay:
                MobUtils.getInstance().eventIdByUMeng(32);

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

    protected void customImageDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.CustomImageDialog);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog_image_content, null);
        ImageView imageClose = (ImageView) layout.findViewById(R.id.img_close);
        dialog.setView(layout);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
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
            webpage.webpageUrl = ShareParentActivity.getShareAppUrl(3) + "?phoneNum="
                    + Des3.encode(MainRouter.getUserPhoenum());
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getResources().getString(R.string.tv_share_fahrschule_weixin_title);
        msg.description = getResources().getString(R.string.tv_share_fahrschule_weixin_content);
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
                : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
