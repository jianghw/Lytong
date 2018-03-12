package com.zantong.mobilecttx.share_v;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jianghw.multi.state.layout.MultiState;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.dialog.DialogMgr;
import com.tzly.ctcyh.router.custom.image.EncodingUtils;
import com.tzly.ctcyh.router.custom.rea.Des3;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountResponse;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_p.FahrschuleSharePresenter;
import com.zantong.mobilecttx.share_p.IFahrschuleShareFtyContract;
import com.zantong.mobilecttx.share_p.StatisCountAdapter;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;

import java.util.ArrayList;
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

    private IFahrschuleShareFtyContract.IFahrschuleShareFtyPresenter mPresenter;
    private ShareParentActivity.FragmentDestroy mCloseListener;
    private XRecyclerView mXRecyclerView;
    private StatisCountAdapter mAdapter;
    private TextView mTvRecommend;
    private TextView mTvTied;
    private LinearLayout mLayFalse;

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
                    contentString, 360, 360, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_global_app));
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
        List<StatistCountResponse.DataBean.ListBean> list = response.getData().getList();

        boolean isFirst = true;
        List<StatistCountResponse.DataBean.ListBean> newList = new ArrayList<>();
        for (StatistCountResponse.DataBean.ListBean map : list) {
            if (!TextUtils.isEmpty(map.getCoupon())) {
                newList.add(map);
            } else if (isFirst && !TextUtils.isEmpty(map.getName())) {
                String string = getResources().getString(R.string.main_tv_share);
                String text = String.format(string, map.getName(), map.getCount());
                mTvRecommend.setText(text);
                isFirst = false;
            } else if (!TextUtils.isEmpty(map.getName())) {
                String string = getResources().getString(R.string.main_tv_share);
                String text = String.format(string, map.getName(), map.getCount());
                mTvTied.setText(text);
            }
        }
        setSimpleDataResult(newList);

        boolean flag = response.getData().isFlag();
        //地推人员
        mImgScan.setVisibility(flag ? View.VISIBLE : View.GONE);
        mBtnPay.setVisibility(flag ? View.VISIBLE : View.GONE);

        mLayFalse.setVisibility(!flag ? View.VISIBLE : View.INVISIBLE);
        mXRecyclerView.setVisibility(!flag ? View.VISIBLE : View.INVISIBLE);

    }

    private void setSimpleDataResult(List<StatistCountResponse.DataBean.ListBean> data) {
        mAdapter.removeAllOnly();
        if (data == null || data.isEmpty()) {
            toastShort("当前统计数据为空");
        } else {
            mAdapter.append(data);
        }
    }

    public void initView(View view) {
        mImgScan = (ImageView) view.findViewById(R.id.img_scan_true);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay_true);
        mBtnPay.setOnClickListener(this);

        mLayFalse = (LinearLayout) view.findViewById(R.id.lay_false);

        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_list_false);
        LinearLayoutManager manager = new LinearLayoutManager(Utils.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        //        mXRecyclerView.addItemDecoration(
        //                new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.res_x_30))
        //        );
        mXRecyclerView.noMoreLoadings();
        mAdapter = new StatisCountAdapter();
        mXRecyclerView.setAdapter(mAdapter);
        mXRecyclerView.setNestedScrollingEnabled(false);

        mTvRecommend = (TextView) view.findViewById(R.id.tv_recommend);
        mTvTied = (TextView) view.findViewById(R.id.tv_tied);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay_true:
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
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_global_app);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

}
