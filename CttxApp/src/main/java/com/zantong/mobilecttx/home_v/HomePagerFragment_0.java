package com.zantong.mobilecttx.home_v;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.violation_v.LicenseCheckGradeActivity;
import com.zantong.mobilecttx.violation_v.LicenseDetailActivity;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import com.tzly.ctcyh.router.util.MobUtils;


/**
 * 页面
 */
public class HomePagerFragment_0 extends JxBaseRefreshFragment implements View.OnClickListener {

    private TextView mTvLicense;
    private TextView mTvAppraisement;
    private TextView mTvCheck;
    private TextView mTvDrive;

    /**
     * 是否可刷新
     */
    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected void onRefreshData() {}

    @Override
    protected void onLoadMoreData() {}

    @Override
    protected int initFragmentView() {
        return R.layout.fragment_home_pager_0;
    }

    @Override
    protected void bindFragmentView(View fragment) {
        initView(fragment);
    }

    @Override
    protected void onFirstDataVisible() {}

    public static HomePagerFragment_0 newInstance() {
        return new HomePagerFragment_0();
    }


    public void initView(View view) {
        mTvLicense = (TextView) view.findViewById(R.id.tv_license);
        mTvLicense.setOnClickListener(this);
        mTvAppraisement = (TextView) view.findViewById(R.id.tv_appraisement);
        mTvAppraisement.setOnClickListener(this);
        mTvCheck = (TextView) view.findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(this);
        mTvDrive = (TextView) view.findViewById(R.id.tv_drive);
        mTvDrive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_license://驾驶证查分
                MobUtils.getInstance().eventIdByUMeng(7);
                licenseCheckGrade();
                break;
            case R.id.tv_appraisement://爱车估值
                MobUtils.getInstance().eventIdByUMeng(34);
                carValuation();
                break;
            case R.id.tv_check://年检
                MobUtils.getInstance().eventIdByUMeng(4);
                gotoCheckHtml();
                break;
            case R.id.tv_drive://国际驾照
                MobUtils.getInstance().eventIdByUMeng(35);
                InternationalDrivingDocument();
                break;
        }
    }

    private void gotoCheckHtml() {
        MainRouter.gotoHtmlActivity(getActivity(), "年检服务",
                BuildConfig.App_Url ? "http://139.196.183.121:3000/myCar"
                        : "http://nianjian.liyingtong.com/myCar");
    }

    protected void licenseCheckGrade() {
        LicenseFileNumDTO bean = SPUtils.getInstance().getLicenseFileNumDTO();
        if (!TextUtils.isEmpty(MainRouter.getUserFilenum())
                && !TextUtils.isEmpty(MainRouter.getUserGetdate()) || bean != null) {
            LicenseFileNumDTO loginBean = new LicenseFileNumDTO();
            loginBean.setFilenum(MainRouter.getUserFilenum());
            loginBean.setStrtdt(MainRouter.getUserGetdate());

            Intent intent = new Intent(getActivity(), LicenseDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(LicenseCheckGradeActivity.KEY_BUNDLE, bean != null ? bean : loginBean);
            bundle.putBoolean(LicenseCheckGradeActivity.KEY_BUNDLE_FINISH, true);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Act.getInstance().gotoIntentLogin(getActivity(), LicenseCheckGradeActivity.class);
        }
    }

    protected void carValuation() {
        MainRouter.gotoHtmlActivity(getActivity(),
                "爱车估值", "http://m.jingzhengu.com/xiansuo/sellcar-changtongcheyouhui.html");
    }

    protected void InternationalDrivingDocument() {
        MainRouter.gotoHtmlActivity(getActivity(),
                "国际驾照", "https://m.huizuche.com/Cdl/Intro3/ctcyh");
    }
}
