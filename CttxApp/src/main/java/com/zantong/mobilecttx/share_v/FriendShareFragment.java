package com.zantong.mobilecttx.share_v;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jianghw.multi.state.layout.MultiState;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.SpaceItemDecoration;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountBean;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountBean;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountResponse;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_p.FahrschuleSharePresenter;
import com.zantong.mobilecttx.share_p.IFahrschuleShareFtyContract;
import com.zantong.mobilecttx.share_p.StatisCountAdapter;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;
import com.zantong.mobilecttx.zxing.EncodingUtils;

import java.util.List;

/**
 * 分享返现页面
 */
public class FriendShareFragment extends RefreshFragment
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
    private XRecyclerView mXRecyclerView;
    private StatisCountAdapter mAdapter;

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

    /**
     * 是否可刷新
     */
    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected int fragmentView() {
        return R.layout.fragment_friend_share;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        FahrschuleSharePresenter presenter = new FahrschuleSharePresenter(
                Injection.provideRepository(Utils.getContext()), this);

        String contentString;
        if (MainRouter.isUserLogin())
            contentString = ShareParentActivity.getShareAppUrl(1) + "?phoneNum="
                    + Des3.encode(MainRouter.getUserPhoenum());
        else
            contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        if (!TextUtils.isEmpty(contentString)) {
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(
                    contentString, 360, 360, BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));
            mImgScan.setImageBitmap(qrCodeBitmap);
        }
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    public void setPresenter(IFahrschuleShareFtyContract.IFahrschuleShareFtyPresenter presenter) {
        mPresenter = presenter;
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
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getStatisticsCount();
    }

    @Override
    protected void responseData(Object response) {
    }

    @Override
    public String getType() {
        return "1";
    }

    /**
     * N 7.获取用户指定活动的统计总数
     */
    @Override
    public void recordCountError(String message) {
        toastShort(message);
    }

    /**
     * 1 分享数，2 注册数，3 绑卡数，4 驾校报名数
     */
    @Override
    public void recordCountSucceed(Object result) {
        if (!(result instanceof StatistCountResponse)) return;

        StatistCountResponse response = (StatistCountResponse) result;
        List<StatistCountBean> countBeanList = response.getData();
        setSimpleDataResult(countBeanList);

//        if (countBeanList != null && countBeanList.size() > 0) {
//            for (RecordCountBean bean : countBeanList) {
//                if (bean.getStatisticalType() == 3) {
//                    mTvPeoplePay.setText(String.valueOf(bean.getStatisticalNum()));
//                } else if (bean.getStatisticalType() == 2) {
//                    mTvPeopleCount.setText(String.valueOf(bean.getStatisticalNum()));
//                }
//            }
//        }
    }

    private void setSimpleDataResult(List<StatistCountBean> data) {
        mAdapter.removeAllOnly();
        if (data == null || data.isEmpty()) {
            toastShort("当前统计数据为空");
        } else {
            mAdapter.append(data);
        }
    }

    public void initView(View view) {
        mImgScan = (ImageView) view.findViewById(R.id.img_scan);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);

        mXRecyclerView = (XRecyclerView) view.findViewById(com.tzly.ctcyh.cargo.R.id.rv_list);
        GridLayoutManager manager = new GridLayoutManager(Utils.getContext(), 2);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.addItemDecoration(
                new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.res_x_30))
        );
        mXRecyclerView.noMoreLoadings();
        mAdapter = new StatisCountAdapter();
        mXRecyclerView.setAdapter(mAdapter);

//        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
//        mTvPeopleCount = (TextView) view.findViewById(R.id.tv_people_count);
//        mTvInvited = (TextView) view.findViewById(R.id.tv_invited);
//        mTvPeoplePay = (TextView) view.findViewById(R.id.tv_people_pay);
//        mTvPayed = (TextView) view.findViewById(R.id.tv_payed);
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
        if (MainRouter.isUserLogin()) {
            webpage.webpageUrl = ShareParentActivity.getShareAppUrl(1) + "?phoneNum="
                    + Des3.encode(MainRouter.getUserPhoenum());
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = getResources().getString(R.string.tv_share_cash_weixin_title);
        msg.description = getResources().getString(R.string.tv_share_cash_weixin_content);
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

}
