package com.zantong.mobilecttx.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.PullableBaseFragment;
import com.zantong.mobilecttx.interf.ILicenseGradeAtyContract;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import cn.qqtheme.framework.widght.CustomLoader;


/**
 * 驾驶证查分详情
 */

public class LicenseDetailFragment extends PullableBaseFragment
        implements ILicenseGradeAtyContract.ILicenseGradeAtyView {

    private RelativeLayout mRyBgLoader;
    private CustomLoader mImgLoader;
    private TextView mTvScore;
    private TextView mTvScoreZ;
    private TextView mTvErrorMeg;
    private TextView mTvFinger;


    private static String BUNDLE_TYPE = "LicenseFileNumDTO";
    private ILicenseGradeAtyContract.ILicenseGradeAtyPresenter mPresenter;

    public static LicenseDetailFragment newInstance(LicenseFileNumDTO bean) {
        LicenseDetailFragment fragment = new LicenseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_TYPE, bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_license_detail;
    }

    @Override
    protected void initFragmentView(View view) {
        mRyBgLoader = (RelativeLayout) view.findViewById(R.id.ry_bg_loader);
        mImgLoader = (CustomLoader) view.findViewById(R.id.img_loader);
        mTvScore = (TextView) view.findViewById(R.id.tv_score);
        mTvScoreZ = (TextView) view.findViewById(R.id.tv_score_z);
        mTvErrorMeg = (TextView) view.findViewById(R.id.tv_error_meg);
        mTvFinger = (TextView) view.findViewById(R.id.tv_finger);
        mTvFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void setPresenter(ILicenseGradeAtyContract.ILicenseGradeAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onLoadMoreData() {
        loadingData();
    }

    @Override
    protected void onRefreshData() {
        setLayoutVisibilityByRefresh(true);
        showErrorCryingFace(false);
        loadingData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mImgLoader.AnimationControlCenter(false);
        mPresenter.unSubscribe();
    }

    /**
     * 可下拉刷新
     *
     * @return true
     */
    @Override
    protected boolean isRefresh() {
        return true;
    }

    /**
     * 不可加载更多
     *
     * @return false
     */
    @Override
    protected boolean isLoadMore() {
        return false;
    }


    @Override
    protected void loadingData() {
        if (mPresenter != null) mPresenter.driverLicenseCheckGrade();
    }

    @Override
    public void onShowDefaultData() {
        onShowContent();
    }

    /**
     * 注意：这个值改变后当前页getArguments()将是变化后的值
     */
    @Override
    public LicenseFileNumDTO initLicenseFileNumDTO() {
        return getArguments().getParcelable(BUNDLE_TYPE);
    }

    @Override
    public void driverLicenseCheckGradeSucceed(LicenseResponseBean result) {
        setLayoutVisibilityByRefresh(false);
        showErrorCryingFace(false);
        mTvScore.setText(String.valueOf(result.getRspInfo().getTotcent()));
        ToastUtils.showShort(getContext().getApplicationContext(), result.getSYS_HEAD().getReturnMessage());
    }

    /**
     * @param isRefresh true 刷新
     */
    @Override
    public void setLayoutVisibilityByRefresh(boolean isRefresh) {
        mImgLoader.AnimationControlCenter(isRefresh);
        mImgLoader.setVisibility(isRefresh ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 查询失败
     *
     * @param isCrying
     */
    @Override
    public void showErrorCryingFace(boolean isCrying) {
        mTvScore.setVisibility(isCrying ? View.INVISIBLE : View.VISIBLE);
        mTvScoreZ.setVisibility(isCrying ? View.INVISIBLE : View.VISIBLE);
        mRyBgLoader.setBackgroundResource(isCrying ? R.mipmap.ic_loading_coupon_cry : R.mipmap.ic_loading_coupon_bg);
        mTvErrorMeg.setText(isCrying ? "查询失败,请确定档案编号正确" : "本记分周期累计扣分");
    }

    @Override
    public void driverLicenseCheckGradeError(String message) {
        setLayoutVisibilityByRefresh(false);
        showErrorCryingFace(true);
        ToastUtils.showShort(getContext().getApplicationContext(), message);
    }
}
