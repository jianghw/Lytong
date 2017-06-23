package com.zantong.mobilecttx.weizhang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.home.activity.Codequery;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.UiHelpers;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;
import com.zantong.mobilecttx.weizhang.fragment.ViolationPayFragment;
import com.zantong.mobilecttx.weizhang.fragment.ViolationPayTypeFragment;
import com.zantong.mobilecttx.weizhang.fragment.ViolationResultFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 违章信息显示页面
 */
public class ViolationResultAcitvity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.violation_query_topbtn_layou1)
    View mView1;
    @Bind(R.id.violation_query_topbtn_layou2)
    View mView2;
    @Bind(R.id.violation_query_topbtn_layou3)
    View mView3;

    @Bind(R.id.violation_query_topbtn_all)
    TextView mAllTitle;
    @Bind(R.id.violation_query_topbtn_unfinished)
    TextView mUnfinishedTitle;
    @Bind(R.id.violation_query_topbtn_finished)
    TextView mfinishedTitle;
    @Bind(R.id.violation_result_pay_layout)
    View mPayLayout;

    TextView[] tvs;
    int mScreenwidth;
    private int payType = 1;

    // 2全部 0 未处理 1 已处理
    private static final int STATUS_ALL = 2;
    private static final int STATUS_FINISHED = 1;
    private static final int STATUS_UNFINISHED = 0;
    private static int TEMP_STATE = 2;

    private FragmentTransaction transaction;
    private FragmentManager mFragmentManager;

    private ViolationResultFragment mFragment;
    private ViolationPayFragment payFragment;
    private ViolationPayTypeFragment payTypeFragment;

    ViolationDTO dto;

    @Override
    protected int getContentResId() {
        return R.layout.activity_violation_result;
    }


    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            setTitleText(intent.getStringExtra("plateNum"));
            Bundle bundle = getIntent().getExtras();
            dto = (ViolationDTO) bundle.getSerializable("params");
        }
    }

    @Override
    public void initData() {
        payTypeFragment = new ViolationPayTypeFragment();

        mFragmentManager = getSupportFragmentManager();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenwidth = dm.widthPixels;
        tvs = new TextView[]{mAllTitle, mUnfinishedTitle, mfinishedTitle};
        TEMP_STATE = 2;
        commitFragment();
        selectTab(0);
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }


    @OnClick({R.id.violation_query_topbtn_layou1, R.id.violation_query_topbtn_layou2, R.id.violation_query_topbtn_layou3})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.violation_query_topbtn_layou1://2
                TEMP_STATE = STATUS_ALL;
                selectTab(0);
                commitFragment();
                break;
            case R.id.violation_query_topbtn_layou2://0
                TEMP_STATE = STATUS_UNFINISHED;
                selectTab(1);
                commitFragment();
                break;
            case R.id.violation_query_topbtn_layou3://1
                selectTab(2);
                TEMP_STATE = STATUS_FINISHED;
                commitFragment();
                break;

        }
        super.onClick(v);
    }

    private void selectTab(int index) {

        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setTextColor(Color.parseColor("#252525"));
            UiHelpers.setTextViewColor(this, tvs[i], R.color.white, 0, 0,
                    UiHelpers.DRAWABLE_BOTTOM);
        }
        tvs[index].setTextColor(Color.parseColor("#F53E2A"));
        if (tvs[index].getWidth() == 0) {
            UiHelpers.setTextViewColor(this, tvs[index], R.color.red,
                    mScreenwidth / 10, 5, UiHelpers.DRAWABLE_BOTTOM);
        } else
            UiHelpers.setTextViewColor(this, tvs[index], R.color.red,
                    tvs[index].getWidth(), 5, UiHelpers.DRAWABLE_BOTTOM);
    }

    private void commitFragment() {
        transaction = mFragmentManager.beginTransaction();
        mFragment = ViolationResultFragment.newInstance(TEMP_STATE, dto);
        transaction.replace(R.id.violation_query_layout, mFragment);
        transaction.commit();
    }

    public int getPayLayoutId() {
        return R.id.violation_result_pay_layout;
    }


    public FragmentManager getSurePayFragmentManager() {
        return mFragmentManager;
    }


    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayFragment(ViolationBean data) {
        payFragment = new ViolationPayFragment(data);
    }

    public ViolationPayFragment getPayFragment() {
        return payFragment;
    }

    public ViolationPayTypeFragment getPayTypeFragment() {
        return payTypeFragment;
    }

    /**
     * 罚单号
     */
    public void showDialogToCodequery() {
        DialogUtils.remindDialog(this, "温馨提示", "请使用处罚决定书编号进行缴纳", "取消", "罚单编号",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Act.getInstance().lauchIntent(ViolationResultAcitvity.this, Codequery.class);
                    }
                });
    }
}
