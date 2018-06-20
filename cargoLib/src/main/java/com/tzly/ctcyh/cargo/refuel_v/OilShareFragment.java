package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.global.CargoGlobal;
import com.tzly.ctcyh.cargo.refuel_p.IAdapterClick;
import com.tzly.ctcyh.cargo.refuel_p.IOilShareContract;
import com.tzly.ctcyh.cargo.refuel_p.OilShareAdapter;
import com.tzly.ctcyh.cargo.refuel_p.OilSharePresenter;
import com.tzly.ctcyh.java.request.ShareGsonDTO;
import com.tzly.ctcyh.java.response.oil.OilAccepterInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilShareResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.dialog.BitmapDialogFragment;
import com.tzly.ctcyh.router.custom.dialog.DialogUtils;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 加油分享
 */
public class OilShareFragment extends RefreshFragment
        implements View.OnClickListener, IOilShareContract.IOilShareView, IAdapterClick {

    private static final String USER_ID = "user_id";
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
    private XRecyclerView mRvList;
    private TextView mTvContent;

    private OilShareAdapter mAdapter;
    private int mCurPosition = 0;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    /**
     * 只传type
     */
    public static OilShareFragment newInstance(String type) {
        OilShareFragment fragment = new OilShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CargoGlobal.putExtra.oil_share_type_extra, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static OilShareFragment newInstance(String banner, String img, String json) {
        OilShareFragment fragment = new OilShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CargoGlobal.putExtra.oil_share_banner_extra, banner);
        bundle.putString(CargoGlobal.putExtra.oil_share_img_extra, img);
        bundle.putString(CargoGlobal.putExtra.oil_share_json_extra, json);
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
        mCurPosition = 0;

        String type = getBusinessType();
        if (TextUtils.isEmpty(type)) {
            loadMoreData();
        } else {
            if (mPresenter != null) mPresenter.shareModuleInfo();
        }
    }

    private void loadMoreData() {
        if (mPresenter != null) mPresenter.shareInfo();
        if (mPresenter != null) mPresenter.getShareInfo();
        if (mPresenter != null) mPresenter.getAccepterInfoList(mCurPosition);

        String banner = getArguments().getString(CargoGlobal.putExtra.oil_share_banner_extra);
        ImageLoadUtils.loadTwoRectangle(banner, mImgTop);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void onClick(View v) {
        String imgUrl = getArguments().getString(CargoGlobal.putExtra.oil_share_img_extra);
        String json = getArguments().getString(CargoGlobal.putExtra.oil_share_json_extra);
        ShareGsonDTO gsonDTO = new Gson().fromJson(json, ShareGsonDTO.class);
        String codeUrl = gsonDTO.getUrl();
        int userId = getArguments().getInt(USER_ID);
        codeUrl = codeUrl + "?shareUserId=" + userId + "&stage=0";

        if (v.getId() == R.id.tv_wechat || v.getId() == R.id.tv_status) {//微信
            ShareUtils.showWechat(getActivity(), imgUrl, codeUrl, 0);
        } else if (v.getId() == R.id.tv_friend) {//朋友圈
            ShareUtils.showWechat(getActivity(), imgUrl, codeUrl, 1);
        } else if (v.getId() == R.id.tv_face) {//二维码
            if (TextUtils.isEmpty(codeUrl)) {
                ToastUtils.toastShort("获取数据失败,下拉刷新");
            } else {
                BitmapDialogFragment fragment = BitmapDialogFragment.newInstance(codeUrl);
                DialogUtils.showDialog(getActivity(), fragment, "code_dialog");
            }
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
        mRvList = (XRecyclerView) view.findViewById(R.id.rv_list);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Utils.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setNestedScrollingEnabled(true);

        mRvList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRvList.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRvList.setArrowImageView(com.tzly.ctcyh.router.R.mipmap.ic_refresh_loading);
        mRvList.setPullRefreshEnabled(false);
        mRvList.setLoadingMoreEnabled(true);

        mRvList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                mRvList.postDelayed(new Runnable() {
                    public void run() {
                        if (mRvList == null) return;
                        mRvList.loadMoreComplete();
                        if (mPresenter != null) mPresenter.getAccepterInfoList(mCurPosition);
                        mRvList.refreshComplete();
                    }
                }, 3000);
            }
        });

        mAdapter = new OilShareAdapter(this);
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public String getConfigId() {
        String json = getArguments().getString(CargoGlobal.putExtra.oil_share_json_extra);
        ShareGsonDTO gsonDTO = new Gson().fromJson(json, ShareGsonDTO.class);
        return String.valueOf(gsonDTO.getConfigId());
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
        int shareUserId = response.getData();
        getArguments().putInt(USER_ID, shareUserId);
    }

    @Override
    public void shareInfoError(String message) {
        errorToast(message);
    }

    @Override
    public void shareInfoSucceed(OilShareInfoResponse response) {
        OilShareInfoResponse.DataBean data = response.getData();
        if (data != null) {
            int accepters = data.getAccepters();
            mTvCountPerson.setText(String.valueOf(accepters));

            int coupons = data.getCoupons();
            mTvCountPrice.setText(String.valueOf(coupons));
        }
    }

    @Override
    public void accepterInfoError(String message) {
        errorToast(message);
    }

    @Override
    public void accepterInfoSucceed(OilAccepterInfoResponse response) {
        List<OilAccepterInfoResponse.DataBean> data = response.getData();
        showListViewLayout(data);
    }

    private void showListViewLayout(List<OilAccepterInfoResponse.DataBean> list) {
        if (list == null || list.isEmpty()) {
            if (mCurPosition == 0) mAdapter.removeAll();
        } else {
            if (mCurPosition == 0) mAdapter.cleanListData();

            mAdapter.append(list);
            mCurPosition += 1;
        }
        if (mRvList != null) mRvList.refreshComplete();
    }

    /**
     * 提醒下单
     */
    @Override
    public void clickItem(View view) {
        onClick(view);
    }

    /**
     * 分享
     */
    @Override
    public String getBusinessType() {
        return getArguments().getString(CargoGlobal.putExtra.oil_share_type_extra);
    }

    @Override
    public void shareModuleInfoError(String message) {
        toastShort("获取分享图片失败" + message);
    }

    @Override
    public void shareModuleInfoSucceed(OilShareModuleResponse response) {
        OilShareModuleResponse.DataBean data = response.getData();
        if (data == null) return;
//        String topImg = data.getTopImg();
        String banner = data.getBanner();
        String imgUrl = data.getImg();
        String json = data.getExtraParam();

        if (TextUtils.isEmpty(banner) || TextUtils.isEmpty(imgUrl) || TextUtils.isEmpty(json))
            return;

//        ImageLoadUtils.loadTwoRectangle(topImg, mImgBanner);
        getArguments().putString(CargoGlobal.putExtra.oil_share_banner_extra, banner);
        getArguments().putString(CargoGlobal.putExtra.oil_share_img_extra, imgUrl);
        getArguments().putString(CargoGlobal.putExtra.oil_share_json_extra, json);

        loadMoreData();
    }
}