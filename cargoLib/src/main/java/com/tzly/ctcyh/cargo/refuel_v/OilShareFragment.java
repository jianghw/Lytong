package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.cargo.refuel_p.IOilShareContract;
import com.tzly.ctcyh.cargo.refuel_p.OilShareAdapter;
import com.tzly.ctcyh.cargo.refuel_p.OilSharePresenter;
import com.tzly.ctcyh.java.response.oil.OilAccepterInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilShareResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.Utils;

/**
 * 加油分享
 */
public class OilShareFragment extends RefreshFragment
        implements View.OnClickListener, IOilShareContract.IOilShareView {

    private IOilShareContract.IOilSharePresenter mPresenter;

    private ImageView mImgTop;
    /**
     * 微信
     */
    private TextView mTvWechat;
    /**
     * 朋友圈
     */
    private TextView mTvFriend;
    /**
     * 面对面邀请
     */
    private TextView mTvFace;
    private TextView mTvCountPerson;
    private TextView mTvCountPrice;
    private RecyclerView mRvList;
    private TextView mTvContent;

    private OilShareAdapter mAdapter;
    private int mCurPosition = 1;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    public static OilShareFragment newInstance(int configId) {
        OilShareFragment fragment = new OilShareFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CargoGlobal.putExtra.oil_share_extra, configId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    protected int fragmentView() {
        return R.layout.cargo_fragment_oil_share;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        OilSharePresenter presenter = new OilSharePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    public void setPresenter(IOilShareContract.IOilSharePresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 初始话数据
     */
    @Override
    protected void loadingFirstData() {
        mCurPosition = 1;

        if (mPresenter != null) mPresenter.getShareInfo();
        if (mPresenter != null) mPresenter.getAccepterInfoList(mCurPosition);
        if (mPresenter != null) mPresenter.shareModule();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_wechat) {//微信

        } else if (v.getId() == R.id.tv_friend) {//朋友圈

        } else if (v.getId() == R.id.tv_face) {//二维码

        }
    }

    public void initView(View view) {
        mImgTop = (ImageView) view.findViewById(R.id.img_top);
        mTvWechat = (TextView) view.findViewById(R.id.tv_wechat);
        mTvWechat.setOnClickListener(this);
        mTvFriend = (TextView) view.findViewById(R.id.tv_friend);
        mTvFriend.setOnClickListener(this);
        mTvFace = (TextView) view.findViewById(R.id.tv_face);
        mTvFace.setOnClickListener(this);
        mTvCountPerson = (TextView) view.findViewById(R.id.tv_count_person);
        mTvCountPrice = (TextView) view.findViewById(R.id.tv_count_price);
        mRvList = (RecyclerView) view.findViewById(R.id.rv_list);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Utils.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        manager.setAutoMeasureEnabled(true);
        mRvList.setLayoutManager(layoutManager);
        mAdapter = new OilShareAdapter();
    }

    @Override
    public int getConfigId() {

        return getArguments().getInt(CargoGlobal.putExtra.oil_share_extra, -1);
    }

    public void errorToast(String msg) {
        if (TextUtils.isEmpty(msg)) toastShort(msg);
    }

    @Override
    public void shareError(String message) {
        errorToast(message);
    }

    @Override
    public void shareSucceed(OilShareResponse response) {

    }

    @Override
    public void shareInfoError(String message) {
        errorToast(message);
    }

    @Override
    public void shareInfoSucceed(OilShareInfoResponse response) {
        OilShareInfoResponse.DataBean data = response.getData();
        if (data != null) {
            OilShareInfoResponse.DataBean.AcceptersBean accepters = data.getAccepters();

            OilShareInfoResponse.DataBean.CouponsBean coupons = data.getCoupons();
        }
    }

    @Override
    public void accepterInfoError(String message) {
        errorToast(message);
    }

    @Override
    public void accepterInfoSucceed(OilAccepterInfoResponse response) {

    }

    @Override
    public void ashareModuleError(String message) {
        errorToast(message);
    }

    @Override
    public void ashareModuleSucceed(OilShareModuleResponse response) {

    }
}