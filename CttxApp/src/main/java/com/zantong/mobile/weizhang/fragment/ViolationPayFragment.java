package com.zantong.mobile.weizhang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.Result;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.ui.FragmentUtils;
import com.zantong.mobile.BuildConfig;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.CarApiClient;
import com.zantong.mobile.api.UserApiClient;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.base.fragment.BaseJxFragment;
import com.zantong.mobile.browser.PayHtmlActivity;
import com.zantong.mobile.utils.AmountUtils;
import com.zantong.mobile.utils.DialogUtils;
import com.zantong.mobile.utils.NetUtils;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.weizhang.activity.ViolationPayActivity;
import com.zantong.mobile.weizhang.bean.ViolationBean;
import com.zantong.mobile.weizhang.dto.ViolationOrderDTO;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 违章支付页面
 */
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
    private static final String ARG_PARAM1 = "mViolationBean";
    private static final String ARG_PARAM2 = "enginenum";

    private ViolationBean mViolationBean;

    public static ViolationPayFragment newInstance() {
        return new ViolationPayFragment();
    }

    public static ViolationPayFragment newInstance(ViolationBean violationBean) {
        ViolationPayFragment fragment = new ViolationPayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, violationBean);
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
            mViolationBean = getArguments().getParcelable(ARG_PARAM1);
            if (mViolationBean != null) {
                String violationamt = mViolationBean.getViolationamt();
                mAmount.setText(AmountUtils.changeF2Y(violationamt) + "元");
                mVioNum.setText(mViolationBean.getViolationnum());
            }
        }

        FragmentActivity activity = getActivity();
        if (activity instanceof ViolationPayActivity) {
            ViolationPayActivity violationListActivity = (ViolationPayActivity) activity;
            int payType = violationListActivity.getPayType();
            if (payType == 1) {
                remark = "3|";
                mPayTypeText.setText("使用畅通卡缴费");
            } else {
                remark = "4|";
                mPayTypeText.setText("使用其他银行卡缴费");
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
                FragmentUtils.replaceFragment(fragmentManager, payTypeFragment, R.id.lay_base_frame, true);
                break;
            case R.id.fragment_violation_commit://提交
                if (MemoryData.getInstance().mHashMap.isEmpty()) {
                    getParentActivity().showLoadingDialog();
                    searchViolation();
                } else
                    createOrder();
                break;
            case R.id.fragment_violation_close:
                FragmentActivity activity = getActivity();
                if (activity instanceof ViolationPayActivity) {
                    ViolationPayActivity payActivity = (ViolationPayActivity) activity;
                    payActivity.closeFragment();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 43.生成违章缴费订单
     */
    private void createOrder() {
        getParentActivity().showLoadingDialog();
        ViolationOrderDTO dto = new ViolationOrderDTO();

        dto.setCarnum(String.valueOf(MemoryData.getInstance().mHashMap.get("carnum")));
        dto.setEnginenum(String.valueOf(MemoryData.getInstance().mHashMap.get("enginenum")));

        dto.setOrderprice(mViolationBean.getViolationamt());
        dto.setPeccancydate(mViolationBean.getViolationdate());
        dto.setPeccancynum(mViolationBean.getViolationnum());
        dto.setUsernum(RSAUtils.strByEncryption(MemoryData.getInstance().userID, true));

        CarApiClient.createOrder(ContextUtils.getContext(), dto, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                searchViolation();
            }

            @Override
            public void onError(String errorCode, String msg) {
                getParentActivity().dismissLoadingDialog();
                ToastUtils.toastShort(msg);
            }
        });
    }

    /**
     * cip.cfc.v004.01
     */
    private void searchViolation() {
        if (!TextUtils.isEmpty(MemoryData.getInstance().filenum)) {
            UserApiClient.setJiaoYiDaiMa(ContextUtils.getContext(),
                    MemoryData.getInstance().filenum, new CallBack<Result>() {
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
                            ToastUtils.toastShort(msg);
                        }
                    });
        } else {
            ToastUtils.toastShort("出错档案编号为空，请重新登录试试");
        }
    }

    /**
     * 跳转到缴费页面
     */
    private void gotoPay() {
        String merCustomIp = NetUtils.getPhontIP(ContextUtils.getContext());

        String violationnum = mViolationBean.getViolationnum();
        String violationamt = mViolationBean.getViolationamt();
        String merCustomId = MemoryData.getInstance().filenum;//畅通卡档案编号

        String payUrl = BuildConfig.APP_URL
                + "payment_payForViolation?orderid=" + violationnum
                + "&amount=" + violationamt
                + "&merCustomIp=" + merCustomIp
                + "&merCustomId=" + merCustomId
                + "&remark=" + remark;

        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.browser_title_extra, "支付");
        intent.putExtra(JxGlobal.putExtra.browser_url_extra, payUrl);
        intent.putExtra(JxGlobal.putExtra.violation_num_extra, violationnum);
        Act.getInstance().gotoLoginByIntent(getActivity(), PayHtmlActivity.class, intent);

        getActivity().finish();
    }

    public ViolationPayActivity getParentActivity() {
        FragmentActivity activity = getActivity();
        ViolationPayActivity violationListActivity = null;
        if (activity instanceof ViolationPayActivity) {
            violationListActivity = (ViolationPayActivity) activity;
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