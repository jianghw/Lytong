package com.zantong.mobilecttx.weizhang.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.fragment.BaseJxFragment;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.AmountUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.NetUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.PayWebActivity;
import com.zantong.mobilecttx.weizhang.activity.ViolationListActivity;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.ui.FragmentUtils;

public class ViolationPayFragment extends BaseJxFragment {

    @Bind(R.id.fragment_violation_paytype_layout)
    View mPayTypeLayout;
    @Bind(R.id.fragment_violation_paytype_text)
    TextView mPayTypeText;
    @Bind(R.id.fragment_violation_paytype_amount)
    TextView mAmount;       //总计
    @Bind(R.id.fragment_violation_num)
    TextView mVioNum;

    private String remark = "3|";
    private ViolationBean violationBean;

    private static final String ARG_PARAM1 = "violationBean";

    public static ViolationPayFragment newInstance() {
        return new ViolationPayFragment();
    }

    public static ViolationPayFragment newInstance(ViolationBean param1) {
        ViolationPayFragment fragment = new ViolationPayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_violation_pay;
    }

    @Override
    protected int getContentLayoutResID() {
        return 0;
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void onForceRefresh() {

    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected void onFirstUserVisible() {
        if (getArguments() != null) {
            violationBean = getArguments().getParcelable(ARG_PARAM1);
            if (violationBean != null) {
                String violationamt = violationBean.getViolationamt();
                mAmount.setText(AmountUtils.changeF2Y(violationamt) + "元");
                mVioNum.setText(violationBean.getViolationnum());
            }
        }
        FragmentActivity activity = getActivity();
        if (activity instanceof ViolationListActivity) {
            ViolationListActivity violationListActivity = (ViolationListActivity) activity;
            int payType = violationListActivity.getPayType();
            if (payType == 1) {
                remark = "3|";
                mPayTypeText.setText("使用畅通卡缴费");
            } else {
                remark = "4|";
                mPayTypeText.setText("使用其他工行银行卡缴费");
            }
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        getParentActivity().dismissLoadingDialog();
    }

    @OnClick({R.id.fragment_violation_paytype_layout, R.id.fragment_violation_commit, R.id.fragment_violation_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_violation_paytype_layout:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                ViolationPayTypeFragment payTypeFragment = ViolationPayTypeFragment.newInstance();
                FragmentUtils.replaceFragment(
                        fragmentManager, payTypeFragment, R.id.lay_content, true);
                break;
            case R.id.fragment_violation_commit:
                searchViolation();
                break;
            case R.id.fragment_violation_close:
                FragmentActivity activity = getActivity();
                if (activity instanceof ViolationListActivity) {
                    ViolationListActivity violationListActivity = (ViolationListActivity) activity;
                    violationListActivity.closeFragment();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到缴费页面
     */
    private void gotoPay() {
        String merCustomIp = NetUtils.getPhontIP(ContextUtils.getContext());

        String violationnum = violationBean.getViolationnum();
        String violationamt = violationBean.getViolationamt();
        String merCustomId = PublicData.getInstance().filenum;//畅通卡档案编号

        String payUrl = BuildConfig.APP_URL
                + "payment_payForViolation?orderid=" + violationnum
                + "&amount=" + violationamt
                + "&merCustomIp=" + merCustomIp
                + "&merCustomId=" + merCustomId
                + "&remark=" + remark;

        PublicData.getInstance().mHashMap.put("PayWebActivity", payUrl);
        Act.getInstance().lauchIntent(getActivity(), PayWebActivity.class);
    }

    /**
     * cip.cfc.v004.01
     */
    private void searchViolation() {
        if (!TextUtils.isEmpty(PublicData.getInstance().filenum)) {
            getParentActivity().showLoadingDialog();
            UserApiClient.setJiaoYiDaiMa(ContextUtils.getContext(),
                    PublicData.getInstance().filenum, new CallBack<Result>() {
                        @Override
                        public void onSuccess(Result result) {
                            getParentActivity().dismissLoadingDialog();
                            if (result != null &&
                                    result.getSYS_HEAD().getReturnCode().equals("000000")) {
                                gotoPay();
                            } else {
                                ToastUtils.toastShort(result != null
                                        ? result.getSYS_HEAD().getReturnMessage()
                                        : "未知错误(cip.cfc.v004.01)");
                            }
                        }

                        @Override
                        public void onError(String errorCode, String msg) {
                            getParentActivity().dismissLoadingDialog();
                        }
                    });
        } else {
            ToastUtils.toastShort("出错档案编号为空，请重新登录试试");
        }
    }

    public ViolationListActivity getParentActivity() {
        FragmentActivity activity = getActivity();
        ViolationListActivity violationListActivity = null;
        if (activity instanceof ViolationListActivity) {
            violationListActivity = (ViolationListActivity) activity;
        }
        return violationListActivity;
    }

    /**
     * 滞纳金dialog
     */
    private void lateFeeDialog() {
        DialogUtils.createLateFeeDialog(getActivity(), "滞纳金说明", "根据《中华人民共和国道" +
                "路交通安全法》108条:当事人应当自收到行政处罚决定书" +
                "之日起15日内，到指定的银行缴纳罚款。\n" +
                "109条：到期不缴纳罚款的，每日按罚款数额的3%加处罚款；\n" +
                "\n" +
                "*滞纳金总额不会超过罚款本金的100%。\n" +
                "*没有去开罚单的“电子警察”记录不会产生滞纳金。");
    }
}
