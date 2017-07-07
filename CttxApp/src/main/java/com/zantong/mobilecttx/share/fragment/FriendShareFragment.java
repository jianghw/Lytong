package com.zantong.mobilecttx.share.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountBean;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResult;
import com.zantong.mobilecttx.interf.IFahrschuleShareFtyContract;
import com.zantong.mobilecttx.presenter.fahrschule.FahrschuleSharePresenter;
import com.zantong.mobilecttx.share.activity.ShareParentActivity;

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

    }

    @Override
    public void dismissLoadingDialog() {

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
                break;
        }
    }

}
