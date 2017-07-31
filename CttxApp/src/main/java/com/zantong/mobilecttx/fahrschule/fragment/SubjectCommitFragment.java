package com.zantong.mobilecttx.fahrschule.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectCommitContract;
import com.zantong.mobilecttx.presenter.fahrschule.SubjectCommitPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.Random;

import cn.qqtheme.framework.util.ToastUtils;

/**
 * 科目强化提交订单
 */
public class SubjectCommitFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISubjectCommitContract.ISubjectCommitView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FahrschuleActivity.SwitcherListener mSwitcherListener;

    private String mParam1;
    private String mParam2;

    /**
     * 上课地点
     */
    private TextView mTvAddress;
    /**
     * 请选择上课地点
     */
    private TextView mTvAddressSel;
    /**
     * 课程名称
     */
    private TextView mTvCourse;
    /**
     * 请选择课程
     */
    private TextView mTvCourseSel;
    /**
     * 来往交通
     */
    private TextView mTvTrafficTitle;
    private TextView mTvTraffic;
    /**
     * 课程价格
     */
    private TextView mTvPriceTitle;
    private TextView mTvPrice;
    /**
     * 课程礼包
     */
    private TextView mTvGiftTitle;
    private TextView mTvGift;
    /**
     * 课程详情
     */
    private TextView mTvInfoTitle;
    private TextView mTvInfo;
    /**
     * 课程介绍
     */
    private TextView mTvIntroduce;
    private Button mBtnCommint;
    /**
     * 请输入姓名
     */
    private EditText mEditName;
    /**
     * 请输入手机号
     */
    private EditText mEditPhone;
    /**
     * 请输入身份证号
     */
    private EditText mEditIdentityCard;
    /**
     * P
     */
    private ISubjectCommitContract.ISubjectCommitPresenter mPresenter;
    /**
     * 地区code
     */
    private int mAreaCode;
    private int mGoodsId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static SubjectCommitFragment newInstance() {
        return new SubjectCommitFragment();
    }

    public static SubjectCommitFragment newInstance(String param1, String param2) {
        SubjectCommitFragment fragment = new SubjectCommitFragment();
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
        return R.layout.fragment_subject_commit;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        SubjectCommitPresenter mPresenter = new SubjectCommitPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(ISubjectCommitContract.ISubjectCommitPresenter presenter) {
        mPresenter = presenter;
    }

    public void setSwitcherListener(FahrschuleActivity.SwitcherListener switcherListener) {
        mSwitcherListener = switcherListener;
    }

    @Override
    protected void onFirstDataVisible() {
        if (BuildConfig.DEBUG) {
            mEditName.setText("测试人员" + new Random().nextInt(10));
            mEditPhone.setText("1525252552" + new Random().nextInt(10));
            mEditIdentityCard.setText("342628198004160012");
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvAddressSel = (TextView) view.findViewById(R.id.tv_address_sel);
        mTvAddressSel.setOnClickListener(this);
        mTvCourse = (TextView) view.findViewById(R.id.tv_course);
        mTvCourseSel = (TextView) view.findViewById(R.id.tv_course_sel);
        mTvCourseSel.setOnClickListener(this);
        mTvTrafficTitle = (TextView) view.findViewById(R.id.tv_traffic_title);
        mTvTraffic = (TextView) view.findViewById(R.id.tv_traffic);
        mTvPriceTitle = (TextView) view.findViewById(R.id.tv_price_title);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvGiftTitle = (TextView) view.findViewById(R.id.tv_gift_title);
        mTvGift = (TextView) view.findViewById(R.id.tv_gift);
        mTvInfoTitle = (TextView) view.findViewById(R.id.tv_info_title);
        mTvInfo = (TextView) view.findViewById(R.id.tv_info);
        mTvInfo.setOnClickListener(this);
        mTvIntroduce = (TextView) view.findViewById(R.id.tv_introduce);
        mBtnCommint = (Button) view.findViewById(R.id.btn_commit);
        mBtnCommint.setOnClickListener(this);

        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mEditIdentityCard = (EditText) view.findViewById(R.id.edit_identity_card);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_info://官网
                PublicData.getInstance().webviewUrl = "http://www.antingjx.com/jianjie/";
                PublicData.getInstance().webviewTitle = "驾校报名官网";
                PublicData.getInstance().isCheckLogin = true;
                Act.getInstance().gotoIntent(getActivity(), BrowserActivity.class);
                break;
            case R.id.btn_commit:
                dataFormValidation();
                break;
            default:
                break;
        }
    }

    /**
     * 数据验证
     */
    private void dataFormValidation() {
        String addressSel = getTvAddressSel();
        if (TextUtils.isEmpty(addressSel)) {
            ToastUtils.toastShort("请选择上课地点");
            return;
        }
        String tvCourseSel = getTvCourseSel();
        if (TextUtils.isEmpty(tvCourseSel)) {
            ToastUtils.toastShort("请选择课程内容");
            return;
        }

    }

    public String getTvAddressSel() {
        return mTvAddressSel.getText().toString().trim();
    }

    public String getTvCourseSel() {
        return mTvCourseSel.getText().toString().trim();
    }


    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void dismissLoadingDialog() {

    }
}
