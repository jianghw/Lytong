package com.zantong.mobilecttx.weizhang.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.widght.CustomLoader;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.contract.ILicenseGradeAtyContract;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.ViolationActivity;
import com.zantong.mobilecttx.weizhang.activity.ViolationDetails;
import com.zantong.mobilecttx.weizhang.adapter.LicenseDetailAdapter;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.RspInfoBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.util.List;


/**
 * 驾驶证查分详情
 */
public class LicenseDetailFragment extends BaseRefreshJxFragment
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
     * 小提示
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

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_license_detail;
    }

    /**
     * 可下拉刷新
     *
     * @return true
     */
    @Override
    protected boolean isRefresh() {
        return false;
    }

    /**
     * 不用
     */
    protected void onRefreshData() {
        if (mPresenter != null) mPresenter.driverLicenseCheckGrade();
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mCustomRecycler.setLayoutManager(layoutManager);
        mCustomRecycler.setPullRefreshEnabled(false);
        mCustomRecycler.setLoadingMoreEnabled(false);
//ScrollView 去滑动
        mCustomRecycler.setNestedScrollingEnabled(false);

        mCustomRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mCustomRecycler.postDelayed(new Runnable() {
                    public void run() {
                        if (mCustomRecycler == null) return;
                        mCurrentPage = 1;
                        onRefreshData();
                        mCustomRecycler.refreshComplete();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
            }
        });

        mAdapter = createAdapter();
        if (mAdapter == null) throw new IllegalArgumentException("adapter is must not null");
        mAdapter.setItemClickListener(new LicenseDetailAdapter.ItemClickListener() {
            @Override
            public void doClickViolation(String num) {//违章查询页面
                Act.getInstance().gotoIntent(getActivity(), ViolationDetails.class, num);
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

    public void initView(View view) {
        mRyBgLoader = (RelativeLayout) view.findViewById(R.id.lay_center_score);
        mImgLoader = (CustomLoader) view.findViewById(R.id.img_loader);
        mTvScore = (TextView) view.findViewById(R.id.tv_score);
        mTvScoreZ = (TextView) view.findViewById(R.id.tv_score_z);
        mLayScore = (RelativeLayout) view.findViewById(R.id.lay_score);
        mTvFinger = (TextView) view.findViewById(R.id.tv_finger);
        mTvFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act.getInstance().gotoIntent(getActivity(), ViolationActivity.class);
            }
        });
        mTvPromptWeep = (TextView) view.findViewById(R.id.tv_prompt_weep);
        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        mTvHint = (TextView) view.findViewById(R.id.tv_hint);
        mLayError = (LinearLayout) view.findViewById(R.id.lay_error);
        mCustomRecycler = (XRecyclerView) view.findViewById(R.id.custom_recycler);
    }

    public LicenseDetailAdapter createAdapter() {
        return new LicenseDetailAdapter();
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.driverLicenseCheckGrade();
    }

    @Override
    public void setPresenter(ILicenseGradeAtyContract.ILicenseGradeAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void DestroyViewAndThing() {
        setLayoutVisibilityByRefresh(false);
        mPresenter.unSubscribe();
    }

    /**
     * 加载数据时
     */
    @Override
    public void onShowDefaultData() {
        setLayoutVisibilityByRefresh(true);
        showErrorCryingFace(false);
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
    public void driverLicenseCheckGradeSucceed(LicenseResponseBean result) {
//        mTvScore.setText(rspInfo != null ? String.valueOf(rspInfo.getTotcent()) : "0");

        RspInfoBean rspInfo = result.getRspInfo();
        List<RspInfoBean.ViolationInfoBean> infoBeanList = rspInfo.getViolationInfo();
        setDataResult(infoBeanList);
        ToastUtils.toastShort(result.getSYS_HEAD().getReturnMessage());
    }

    @Override
    public void driverLicenseCheckGradeError(String message) {
        showErrorCryingFace(true);
        mLayError.setVisibility(View.VISIBLE);

        setLayoutVisibilityByRefresh(false);
        ToastUtils.toastShort(message);
    }

    /**
     * @param isRefresh true 刷新中~
     */
    @Override
    public void setLayoutVisibilityByRefresh(boolean isRefresh) {
        mImgLoader.AnimationControlCenter(isRefresh);
        mImgLoader.setVisibility(isRefresh ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 查询失败
     *
     * @param isCrying false 不显示库哭脸
     */
    @Override
    public void showErrorCryingFace(boolean isCrying) {
        mTvPromptWeep.setVisibility(isCrying ? View.VISIBLE : View.INVISIBLE);

        mTvScore.setVisibility(isCrying ? View.INVISIBLE : View.VISIBLE);
        mTvScoreZ.setVisibility(isCrying ? View.INVISIBLE : View.VISIBLE);
        mRyBgLoader.setBackgroundResource(isCrying ? R.mipmap.ic_loading_coupon_cry : R.mipmap.ic_loading_coupon_bg);
    }

    /**
     * 数据加载
     */
    protected void setDataResult(List<RspInfoBean.ViolationInfoBean> list) {
        mAdapter.append(list);
        mLayError.setVisibility(mAdapter.getItemCount() < 1 ? View.VISIBLE : View.GONE);

        setLayoutVisibilityByRefresh(false);
    }

}
