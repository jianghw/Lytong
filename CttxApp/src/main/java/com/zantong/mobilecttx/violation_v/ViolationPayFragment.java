package com.zantong.mobilecttx.violation_v;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.java.response.BankResponse;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.custom.dialog.IOnCouponSubmitListener;
import com.tzly.ctcyh.router.custom.dialog.MessageDialogFragment;
import com.tzly.ctcyh.router.util.FormatUtils;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.tzly.ctcyh.router.util.NetUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.car.activity.ManageCarActivity;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarBean;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.card.activity.UnblockedCardActivity;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.violation_p.IViolationPayContract;
import com.zantong.mobilecttx.violation_p.IViolationPayPresenter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.dto.ViolationOrderDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 违章支付页面
 */
public class ViolationPayFragment extends RefreshFragment
        implements View.OnClickListener, IViolationPayContract.IViolationPayView {

    private String remark = "3|";
    private static final String VIOLATION_BEAN = "violation_bean";

    private ImageView mFragmentViolationClose;
    /**
     * 10000000078965
     */
    private TextView mFragmentViolationNum;
    /**
     * 工银e支付
     */
    private TextView mFragmentViolationPaytypeText;
    private LinearLayout mFragmentViolationPaytypeLayout;
    /**
     * 滞纳金
     */
    private TextView mPayLateFeeMoneyTextS;
    private TextView mFragmentViolationPaytypeAmount;
    /**
     * 确认付款
     */
    private Button mFragmentViolationCommit;

    private IViolationPayContract.IViolationPayPresenter mPresenter;

    private List<String> payCarList = new ArrayList<>();
    private IViolationPayUi mIViolationPayUi;
    /**
     * 是否加载绑定车辆数据
     */
    private boolean isPayCarData = false;

    public static ViolationPayFragment newInstance(ViolationBean violationBean) {
        ViolationPayFragment fragment = new ViolationPayFragment();
        Bundle args = new Bundle();
        args.putParcelable(VIOLATION_BEAN, violationBean);
        fragment.setArguments(args);
        return fragment;
    }

    public ViolationBean getViolationBean() {
        return getArguments().getParcelable(VIOLATION_BEAN);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof IViolationPayUi)
            mIViolationPayUi = (IViolationPayUi) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!payCarList.isEmpty()) payCarList.clear();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected int fragmentView() {
        return R.layout.fragment_violation_pay;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        IViolationPayPresenter mPresenter = new IViolationPayPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    public void setPresenter(IViolationPayContract.IViolationPayPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getPayCars();
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof PayCarResult) {
            PayCarResult carResult = (PayCarResult) response;
            PayCarBean payCarBean = carResult.getRspInfo();
            setSimpleDataResult(payCarBean);
        } else
            responseError();
    }

    private void setSimpleDataResult(PayCarBean payCarBean) {
        isPayCarData = true;
        //车辆
        if (!payCarList.isEmpty()) payCarList.clear();

        if (!payCarBean.getUserCarsInfo().isEmpty())
            for (PayCar car : payCarBean.getUserCarsInfo()) {
                payCarList.add(Des3.decode(car.getCarnum()));
            }

        succeedData();
    }

    private void succeedData() {
        ViolationBean violationBean = getViolationBean();
        String violationamt = violationBean.getViolationamt();
        if (violationamt.contains("元")) {
            mFragmentViolationPaytypeAmount.setText(violationamt);
        } else {//除以100
            mFragmentViolationPaytypeAmount.setText(FormatUtils.displayPrice(violationamt) + "元");
        }

        mFragmentViolationNum.setText(violationBean.getViolationnum());
        mFragmentViolationCommit.setText("确 认 支 付");

        businessCard();
    }

    @Override
    public void responseError(String message) {
        isPayCarData = false;
        showStateContent();

        if (!payCarList.isEmpty()) payCarList.clear();
        succeedData();
    }

    public int getPayType() {
        return remark.equals("3|") ? 3 : 4;
    }

    public void setPayType(int payType) {
        if (payType == 3) {
            remark = "3|";
        } else {
            remark = "4|";
        }
    }

    private void businessCard() {
        if (remark.equals("3|")) {
            mFragmentViolationPaytypeText.setText("使用畅通卡缴费");

            String carNum = getViolationBean().getCarnum();
            if (TextUtils.isEmpty(MainRouter.getUserFilenum())) {
                toastShort("您未绑定畅通卡，请绑定后缴费");
                mFragmentViolationCommit.setText("去绑定畅通卡");
            } else if (!isPayCarData) {
                toastShort("获取绑定车辆数据出错,请下拉刷新或使用工行卡");
            } else if (payCarList.size() >= 2 && !payCarList.contains(carNum)) {
                String enginenum = getViolationBean().getEnginenum();
                if (!TextUtils.isEmpty(enginenum)) {
                    toastShort("当前车辆为" + carNum + "请改绑车辆或使用工行卡");
                    mFragmentViolationCommit.setText("去改绑车辆");
                }
            }
        } else {
            mFragmentViolationPaytypeText.setText("使用工行卡缴费");
        }
    }

    public void initView(View view) {
        mFragmentViolationClose = (ImageView) view.findViewById(R.id.fragment_violation_close);
        mFragmentViolationClose.setOnClickListener(this);
        mFragmentViolationNum = (TextView) view.findViewById(R.id.fragment_violation_num);
        mFragmentViolationPaytypeText = (TextView) view.findViewById(R.id.fragment_violation_paytype_text);
        mFragmentViolationPaytypeLayout = (LinearLayout) view.findViewById(R.id.fragment_violation_paytype_layout);
        mFragmentViolationPaytypeLayout.setOnClickListener(this);
        mPayLateFeeMoneyTextS = (TextView) view.findViewById(R.id.pay_late_fee_money_text_s);
        mFragmentViolationPaytypeAmount = (TextView) view.findViewById(R.id.fragment_violation_paytype_amount);
        mFragmentViolationCommit = (Button) view.findViewById(R.id.fragment_violation_commit);
        mFragmentViolationCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_violation_commit:
                if (mFragmentViolationCommit.getText().toString().contains("车辆")) {
                    //管理车辆
                    Act.getInstance().gotoIntentLogin(getActivity(), ManageCarActivity.class);
                } else if (mFragmentViolationCommit.getText().toString().contains("畅通卡")) {
                    //去邦卡
                    Act.getInstance().gotoIntentLogin(getActivity(), UnblockedCardActivity.class);
                } else if (remark.equals("3|")) {
                    String violationnum = getViolationBean().getViolationnum();
                    String penaltyNum = violationnum.substring(6, 7);
                    if ("1".equals(penaltyNum) || "2".equals(penaltyNum)) {
                        toastShort("当前并非电子警察通知书编号,请选择工行卡缴费");
                    } else {
                        createDialog();
                    }
                } else if (remark.equals("4|")) {
                    String violationnum = getViolationBean().getViolationnum();
                    String penaltyNum = violationnum.substring(6, 7);
                    if (!"1".equals(penaltyNum) && !"2".equals(penaltyNum)) {
                        toastShort("当前为电子警察通知书编号,请选择畅通卡缴费");
                    } else {
                        createDialog();
                    }
                } else {
                    createDialog();
                }
                break;
            case R.id.fragment_violation_paytype_layout://支付方式
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                ViolationPayTypeFragment payTypeFragment = ViolationPayTypeFragment.newInstance();
                FragmentUtils.replace(fragmentManager, payTypeFragment, R.id.lay_base_frame, true);
                break;
            case R.id.fragment_violation_close:
                if (mIViolationPayUi != null) mIViolationPayUi.closeFragment();
                break;
            default:
                break;
        }
    }

    private void createDialog() {
        String msg = remark.equals("3|") ? "使用通卡请预先在卡里充值相应金额" : "使用工行其他卡支付请先开通E支付";
        MessageDialogFragment dialogFragment = MessageDialogFragment.newInstance(msg);
        dialogFragment.setClickListener(new IOnCouponSubmitListener() {
            @Override
            public void submit(String couponId) {
                createOrder();
            }

            @Override
            public void cancel() {
            }
        });
        dialogFragment.show(getChildFragmentManager(), "message_dialog");
    }

    /**
     * 43.生成违章缴费订单
     */
    private void createOrder() {
        String enginenum = getViolationBean().getEnginenum();
        if (TextUtils.isEmpty(enginenum)) {
            gotoPay();
        } else {
            ViolationOrderDTO dto = new ViolationOrderDTO();
            String carNum = getViolationBean().getCarnum();
            dto.setCarnum(carNum);
            dto.setEnginenum(enginenum);

            dto.setOrderprice(mFragmentViolationPaytypeAmount.getText().toString().split("元")[0]);
            dto.setPeccancydate(getViolationBean().getViolationdate());
            dto.setPeccancynum(getViolationBean().getViolationnum());
            dto.setUsernum(MainRouter.getRASUserID());

            showLoading();
            CarApiClient.createOrder(Utils.getContext(), dto, new CallBack<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                    searchViolation();
                }

                @Override
                public void onError(String errorCode, String msg) {
                    dismissLoading();
                    toastShort(msg);
                }
            });
        }
    }

    /**
     * cip.cfc.v004.01
     */
    private void searchViolation() {
        if (!TextUtils.isEmpty(MainRouter.getUserFilenum())) {
            UserApiClient.setJiaoYiDaiMa(Utils.getContext(), MainRouter.getUserFilenum(),
                    new CallBack<BankResponse>() {
                        @Override
                        public void onSuccess(BankResponse bankResponse) {
                            dismissLoading();
                            if (bankResponse != null &&
                                    bankResponse.getSYS_HEAD().getReturnCode().equals("000000")) {
                                gotoPay();
                            } else {
                                toastShort(bankResponse != null
                                        ? bankResponse.getSYS_HEAD().getReturnMessage()
                                        : "未知错误(cip.cfc.v004.01)");
                            }
                        }

                        @Override
                        public void onError(String errorCode, String msg) {
                            toastShort(msg);
                            dismissLoading();
                        }
                    });
        } else {
            dismissLoading();
            gotoPay();
        }
    }


    /**
     * 跳转到缴费页面
     */
    private void gotoPay() {
        String violationnum = mFragmentViolationNum.getText().toString();
        String violationamt = mFragmentViolationPaytypeAmount.getText().toString().split("元")[0];

        String merCustomId = MainRouter.getUserFilenum();//畅通卡档案编号
        String merCustomIp = NetUtils.getPhontIP(Utils.getContext());

        int amt = (int) (Float.valueOf(violationamt) * 100);
        String value = String.valueOf(amt);

        String payUrl = BuildConfig.bank_app_url
                + "payment_payForViolation?orderid=" + violationnum
                + "&amount=" + value
                + "&merCustomIp=" + merCustomIp
                + "&merCustomId=" + merCustomId
                + "&remark=" + remark;

        MainRouter.gotoWebHtmlActivity(getActivity(), "支付页面", payUrl,
                violationnum, getViolationBean().getEnginenum());
    }

}
