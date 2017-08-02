package com.zantong.mobilecttx.fahrschule.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.fahrschule.ISparringSubscribeContract;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaBean;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResult;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.SubjectGoodsResult;
import com.zantong.mobilecttx.presenter.fahrschule.SparringSubscribePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.contract.bean.SparringGoodsBean;
import cn.qqtheme.framework.contract.custom.IAreaDialogListener;
import cn.qqtheme.framework.util.CustomDialog;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.ViewUtils;

/**
 * 陪练预约 页面
 */
public class SparringSubscribeFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISparringSubscribeContract.ISparringSubscribeView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ISubjectSwitcherListener mSwitcherListener;

    private String mParam1;
    private String mParam2;

    /**
     * mPresenter
     */
    private ISparringSubscribeContract.ISparringSubscribePresenter mPresenter;

    private ImageView mImgDate;
    /**
     * 请选择地区
     */
    private TextView mTvCourseTitle;
    /**
     * 请填写详细的服务地址
     */
    private EditText mEditAddress;
    private ImageView mImgMotorcycleType;
    /**
     * 请选择车型
     */
    private TextView mTvMotorcycleType;
    private ImageView mImgTime;
    /**
     * 请选择时间段
     */
    private TextView mTvTime;
    /**
     * 请输入姓名
     */
    private EditText mEditName;
    /**
     * 请输入手机号码
     */
    private EditText mEditPhone;
    /**
     * 请输入驾驶证号
     */
    private EditText mEditLicense;
    /**
     * 可备注需要男教练或女教练等信息
     */
    private EditText mEditRemark;
    private TextView mTvCoupon;
    private RelativeLayout mLayCoupon;
    /**
     * 立即预约
     */
    private TextView mTvCommit;
    private RelativeLayout mLayArea;
    private RelativeLayout mLayMotorcycleType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static SparringSubscribeFragment newInstance() {
        return new SparringSubscribeFragment();
    }

    public static SparringSubscribeFragment newInstance(String param1, String param2) {
        SparringSubscribeFragment fragment = new SparringSubscribeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected void onRefreshData() {
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_sparring_subscribe;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        SparringSubscribePresenter mPresenter = new SparringSubscribePresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);

        ViewUtils.editTextInputSpace(mEditAddress);
    }

    @Override
    public void setPresenter(ISparringSubscribeContract.ISparringSubscribePresenter presenter) {
        mPresenter = presenter;
    }

    public void setSwitcherListener(ISubjectSwitcherListener switcherListener) {
        mSwitcherListener = switcherListener;
    }

    @Override
    protected void onFirstDataVisible() {
        if (BuildConfig.DEBUG) {

        }
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mLayArea = (RelativeLayout) view.findViewById(R.id.lay_area);
        mLayArea.setOnClickListener(this);
        mImgDate = (ImageView) view.findViewById(R.id.img_date);
        mTvCourseTitle = (TextView) view.findViewById(R.id.tv_course_title);
        mEditAddress = (EditText) view.findViewById(R.id.edit_address);
        mLayMotorcycleType = (RelativeLayout) view.findViewById(R.id.lay_motorcycle_type);
        mLayMotorcycleType.setOnClickListener(this);
        mImgMotorcycleType = (ImageView) view.findViewById(R.id.img_motorcycle_type);
        mTvMotorcycleType = (TextView) view.findViewById(R.id.tv_motorcycle_type);
        mImgTime = (ImageView) view.findViewById(R.id.img_time);
        mTvTime = (TextView) view.findViewById(R.id.tv_time);
        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mEditLicense = (EditText) view.findViewById(R.id.edit_license);
        mEditRemark = (EditText) view.findViewById(R.id.edit_remark);
        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mLayCoupon = (RelativeLayout) view.findViewById(R.id.lay_coupon);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_area:
                if (mPresenter != null) mPresenter.getServiceArea();
                break;
            case R.id.lay_motorcycle_type://车型 变速
                if (mPresenter != null) mPresenter.getGoods();
                break;
            case R.id.tv_commit:
                break;
            default:
                break;
        }
    }

    /**
     * 数据验证
     */
    private void dataFormValidation() {
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    /**
     * 地区接口
     */
    @Override
    public void serviceAreaError(String message) {
        serverError(message);
    }

    protected void serverError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void serviceAreaSucceed(SparringAreaResult result) {
        final List<SparringAreaBean> beanList = result.getData();

        final ArrayList<String> areaList = new ArrayList<>();
        for (SparringAreaBean areaBean : beanList) {
            areaList.add(areaBean.getFullName());
        }

        ArrayList<String> firstList = new ArrayList<>();
        firstList.add("上海市");
        ArrayList<ArrayList<String>> secondList = new ArrayList<>();
        secondList.add(areaList);

        CustomDialog.popupBottomArea(getActivity(),
                firstList, secondList, new IAreaDialogListener() {
                    @Override
                    public void setCurPosition(String area) {
                        for (SparringAreaBean areaBean : beanList) {
                            if (areaBean.getFullName().equals(area)) {
                                mTvCourseTitle.setText(area);
                            }
                        }
                    }
                });
    }

    /**
     * 地区
     */
    @Override
    public void getGoodsError(String message) {
        serverError(message);
    }

    @Override
    public void getGoodsSucceed(SubjectGoodsResult result) {

    }

    /**
     * 车型
     */
    @Override
    public void goodsSucceed(SparringGoodsResult result) {
        List<SparringGoodsBean> beanList = result.getData();
        CustomDialog.popupBottomCarType(getActivity(),beanList);
    }

    @Override
    public void goodsError(String message) {
        serverError(message);
    }


}
