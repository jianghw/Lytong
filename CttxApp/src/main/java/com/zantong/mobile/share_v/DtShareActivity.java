package com.zantong.mobile.share_v;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jianghw.multi.state.layout.MultiState;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.annual.base.RefreshBaseActivity;
import com.tzly.annual.base.bean.response.StatistCountBean;
import com.tzly.annual.base.bean.response.StatistCountResult;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.model.repository.LocalData;
import com.zantong.mobile.share.activity.ShareParentActivity;
import com.zantong.mobile.share_p.IShareFtyContract;
import com.zantong.mobile.share_p.SharePresenter;
import com.zantong.mobile.share_p.StatisCountAdapter;
import com.zantong.mobile.utils.DialogMgr;
import com.zantong.mobile.utils.rsa.Des3;
import com.zantong.mobile.widght.SpaceItemDecoration;
import com.zantong.mobile.wxapi.WXEntryActivity;
import com.zantong.mobile.zxing.EncodingUtils;

import java.util.List;

/**
 * 畅通卡车友会
 */
public class DtShareActivity extends RefreshBaseActivity
        implements View.OnClickListener, IShareFtyContract.IShareFtyView {

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

    private IShareFtyContract.IShareFtyPresenter mPresenter;

    private XRecyclerView mXRecyclerView;
    private StatisCountAdapter mAdapter;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                new DialogMgr(this,
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

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    protected void backClickListener() {
        finish();
    }

    @Override
    protected void userRefreshContentData() {
        if (mPresenter != null) mPresenter.getStatisticsCount();
    }

    @Override
    protected int initChildView() {
        return R.layout.activity_dt_share;
    }

    @Override
    protected void bindChildView(View childView) {
        titleContent("分享返现");
        initView(childView);
        SharePresenter presenter = new SharePresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);
    }

    public void initView(View view) {
        mImgScan = (ImageView) view.findViewById(R.id.img_scan);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);

        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_list);
        GridLayoutManager manager = new GridLayoutManager(ContextUtils.getContext(), 2);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.addItemDecoration(
                new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.ds_30))
        );
        mXRecyclerView.noMoreLoadings();
    }

    @Override
    protected void initContentData() {
        mAdapter = new StatisCountAdapter();
        mXRecyclerView.setAdapter(mAdapter);

        String contentString = null;
        if (LocalData.getInstance().isLogin())
            contentString = ShareParentActivity.getShareAppUrl(1) + "?phoneNum="
                    + Des3.encode(LocalData.getInstance().getUserPhone());
        else
            contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";

        if (!TextUtils.isEmpty(contentString)) {
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(
                    contentString, 360, 360, BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));
            mImgScan.setImageBitmap(qrCodeBitmap);
        }
    }

    @Override
    public void setPresenter(IShareFtyContract.IShareFtyPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 微信分享
     *
     * @param flag (0 分享到微信好友 1 分享到微信朋友圈)
     */
    private void wechatShare(int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(this, WXEntryActivity.APP_ID, true);
        api.registerApp(WXEntryActivity.APP_ID);

        if (!api.isWXAppInstalled()) {
            ToastUtils.toastShort("您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if (LocalData.getInstance().isLogin()) {
            webpage.webpageUrl = ShareParentActivity.getShareAppUrl(1) + "?phoneNum="
                    + Des3.encode(LocalData.getInstance().getUserPhone());
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getResources().getString(R.string.tv_share_cash_weixin_title);
        msg.description = getResources().getString(R.string.tv_share_cash_weixin_content);
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sharelogo);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    @Override
    public String getPhone() {
        return LocalData.getInstance().getUserPhone();
    }

    @Override
    public void statisticsCountError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void statisticsCountSucceed(StatistCountResult result) {
        List<StatistCountBean> countBeanList = result.getData();
        setSimpleDataResult(countBeanList);
    }

    private void setSimpleDataResult(List<StatistCountBean> data) {
        mAdapter.removeAllOnly();
        if (data == null || data.isEmpty()) {
            ToastUtils.toastShort("当前统计数据为空");
        } else {
            mAdapter.append(data);
        }
    }
}
