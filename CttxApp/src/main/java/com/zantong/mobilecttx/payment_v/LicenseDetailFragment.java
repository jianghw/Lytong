package com.zantong.mobilecttx.payment_v;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.CustomLoader;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.violation_p.ILicenseGradeAtyContract;
import com.zantong.mobilecttx.violation_p.LicenseGradeAtyPresenter;
import com.zantong.mobilecttx.violation_v.ViolationDetailsActivity;
import com.zantong.mobilecttx.weizhang.adapter.LicenseDetailAdapter;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.RspInfoBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.util.List;


/**
 * 驾驶证查分详情
 */
public class LicenseDetailFragment extends RefreshFragment
        implements ILicenseGradeAtyContract.ILicenseGradeAtyView {

    private RelativeLayout mRyBgLoader;
    private CustomLoader mImgLoader;
    private TextView mTvScore;
    private TextView mTvScoreZ;
    private TextView mTvFinger;

    private static String BUNDLE_TYPE = "LicenseFileNumDTO";
    private ILicenseGradeAtyContract.ILicenseGradeAtyPresenter mPresenter;

    private RelativeLayout mLayScore;
    private TextView mTvPrompt;
    /**
     * 小提示 布局
     */
    private TextView mTvHint;
    private LinearLayout mLayError;
    private XRecyclerView mCustomRecycler;

    /**
     * 默认页数
     */
    protected int mCurrentPage = 1;

    /**
     * 数据源
     */
    protected LicenseDetailAdapter mAdapter;
    private TextView mTvPromptWeep;

    public static LicenseDetailFragment newInstance(LicenseFileNumDTO bean) {
        LicenseDetailFragment fragment = new LicenseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_TYPE, bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 可下拉刷新
     */
    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    protected int fragmentView() {
        return R.layout.fragment_license_detail;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);
        bindExtraView();
    }

    public void initView(View view) {
        mRyBgLoader = (RelativeLayout) view.findViewById(R.id.lay_center_score);
        mImgLoader = (CustomLoader) view.findViewById(R.id.img_loader);
        mTvScore = (TextView) view.findViewById(R.id.tv_score);
        mTvScoreZ = (TextView) view.findViewById(R.id.tv_score_z);
        mLayScore = (RelativeLayout) view.findViewById(R.id.lay_score);
        mTvFinger = (TextView) view.findViewById(R.id.tv_finger);

        mTvPromptWeep = (TextView) view.findViewById(R.id.tv_prompt_weep);
        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        mTvHint = (TextView) view.findViewById(R.id.tv_hint);
        mLayError = (LinearLayout) view.findViewById(R.id.lay_error);
        mCustomRecycler = (XRecyclerView) view.findViewById(R.id.custom_recycler);
    }

    protected void bindExtraView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mCustomRecycler.setLayoutManager(layoutManager);
        mCustomRecycler.setPullRefreshEnabled(false);
        mCustomRecycler.setLoadingMoreEnabled(false);
        //ScrollView 去滑动
        mCustomRecycler.setNestedScrollingEnabled(true);

        mAdapter = createAdapter();
        if (mAdapter == null) throw new IllegalArgumentException("adapter is must not null");
        mAdapter.setItemClickListener(new LicenseDetailAdapter.ItemClickListener() {
            @Override
            public void doClickViolation(String num) {//违章查询页面
                Act.getInstance().gotoIntent(getActivity(), ViolationDetailsActivity.class, num);
            }

            /**
             * 分数加减 处理
             */
            @Override
            public void doClickSwitchBtn(boolean isChecked, String violationcent) {
                scoreCalculation(isChecked, violationcent);
            }
        });

        mAdapter.setLicenseFileNumDTO(initLicenseFileNumDTO());
        mCustomRecycler.setAdapter(mAdapter);

        LicenseGradeAtyPresenter mPresenter = new LicenseGradeAtyPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    public LicenseDetailAdapter createAdapter() {
        return new LicenseDetailAdapter();
    }

    @Override
    public void setPresenter(ILicenseGradeAtyContract.ILicenseGradeAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        animationRefresh(false);
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.driverLicenseCheckGrade();
    }

    /**
     * 数字计算
     */
    protected synchronized void scoreCalculation(boolean isChecked, String violationcent) {
        int cent = TextUtils.isEmpty(violationcent) ? 0 : Integer.valueOf(violationcent);
        int score = Integer.valueOf(mTvScore.getText().toString());
        int newScore;
        if (isChecked) newScore = score + cent;
        else newScore = score - cent;
        if (mTvScore != null) mTvScore.setText(String.valueOf(newScore));
    }

    /**
     * 加载数据时
     */
    @Override
    public void onShowDefaultData() {
        animationRefresh(true);
        errorCryingFace(false);
    }

    /**
     * 注意：这个值改变后当前页getArguments()将是变化后的值
     */
    @Override
    public LicenseFileNumDTO initLicenseFileNumDTO() {
        return getArguments().getParcelable(BUNDLE_TYPE);
    }

    /**
     * 数据加载成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof LicenseResponseBean) {
            LicenseResponseBean responseBean = (LicenseResponseBean) response;
            RspInfoBean rspInfo = responseBean.getRspInfo();
            List<RspInfoBean.ViolationInfoBean> infoBeanList = null;
            if (rspInfo != null) infoBeanList = rspInfo.getViolationInfo();
            setDataResult(infoBeanList);
            toastShort(responseBean.getSYS_HEAD().getReturnMessage());
        } else
            responseError();
    }

    /**
     * 数据加载
     */
    protected void setDataResult(List<RspInfoBean.ViolationInfoBean> list) {
        mCurrentPage = 1;
        mAdapter.replace(list);
        mLayError.setVisibility(mAdapter.getItemCount() < 1 ? View.VISIBLE : View.GONE);

        mTvFinger.setText(mAdapter.getItemCount() == 0 ? "你暂无扣分,请继续保持" : "你有扣分记录");

        animationRefresh(false);
    }

    @Override
    public void driverLicenseCheckGradeError(String message) {
        showStateContent();

        errorCryingFace(true);
        mLayError.setVisibility(View.VISIBLE);

        animationRefresh(false);
        toastShort(message);
    }

    /**
     * @param isRefresh true 刷新中~
     */
    @Override
    public void animationRefresh(boolean isRefresh) {
        mImgLoader.AnimationControlCenter(isRefresh);
        mImgLoader.setVisibility(isRefresh ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 查询失败
     *
     * @param isCrying false 不显示库哭脸
     */
    @Override
    public void errorCryingFace(boolean isCrying) {
        mTvPromptWeep.setVisibility(isCrying ? View.VISIBLE : View.INVISIBLE);

        mTvScore.setVisibility(isCrying ? View.INVISIBLE : View.VISIBLE);
        mTvScoreZ.setVisibility(isCrying ? View.INVISIBLE : View.VISIBLE);

        if (isCrying) mTvFinger.setText("本功能需要申办畅通卡并开通使用才能正确显示你的分数");

        mRyBgLoader.setBackgroundResource(isCrying
                ? R.mipmap.ic_loading_coupon_cry : R.mipmap.ic_loading_coupon_bg);
    }

}
