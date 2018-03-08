package com.zantong.mobilecttx.fahrschule_v;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.tzly.ctcyh.router.util.FormatUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.IFahrschuleOrderNumFtyContract;
import com.zantong.mobilecttx.eventbus.FahrschuleApplyEvent;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.fahrschule_p.FahrschuleOrderNumPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.tzly.ctcyh.router.util.MobUtils;

/**
 * 驾校订单确认页面
 */
public class FahrschuleOrderNumFragment extends JxBaseRefreshFragment
        implements View.OnClickListener, IFahrschuleOrderNumFtyContract.IFahrschuleOrderNumFtyView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * 订单号：
     */
    private TextView mTvOrderTitle;
    private TextView mTvOrder;
    /**
     * 报名人：
     */
    private TextView mTvSignUpTitle;
    private TextView mTvSignUp;
    /**
     * 报名人手机：
     */
    private TextView mTvPhoneTitle;
    private TextView mTvPhone;
    /**
     * 报名人身份证：
     */
    private TextView mTvIdentityCardTitle;
    private TextView mTvIdentityCard;
    /**
     * 报名课程：
     */
    private TextView mTvCourseTitle;
    private TextView mTvCourse;
    /**
     * 支付方式：
     */
    private TextView mTvPayTypeTitle;
    private TextView mTvPayType;
    private LinearLayout mLayLine;
    /**
     * 订单金额：
     */
    private TextView mTvMoneyTitle;
    private TextView mTvMoney;
    /**
     * 订单确认
     */
    private TextView mTvIntroduce;
    /**
     * 去支付
     */
    private Button mBtnPay;
    /**
     * P
     */
    private IFahrschuleOrderNumFtyContract.IFahrschuleOrderNumFtyPresenter mPresenter;

    public static FahrschuleOrderNumFragment newInstance() {
        return new FahrschuleOrderNumFragment();
    }

    public static FahrschuleOrderNumFragment newInstance(String param1, String param2) {
        FahrschuleOrderNumFragment fragment = new FahrschuleOrderNumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected int initFragmentView() {
        return R.layout.fragment_fahrschule_order_num;
    }

    @Override
    protected void bindFragmentView(View fragment) {
        initView(fragment);

        FahrschuleOrderNumPresenter mPresenter = new FahrschuleOrderNumPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    protected void onRefreshData() {}

    @Override
    protected void onLoadMoreData() {

    }


    @Override
    public void setPresenter(IFahrschuleOrderNumFtyContract.IFahrschuleOrderNumFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().removeStickyEvent(FahrschuleApplyEvent.class);
        EventBus.getDefault().unregister(this);
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(FahrschuleApplyEvent event) {
        if (event != null) initData(event);
    }

    private void initData(FahrschuleApplyEvent event) {
        String orderId = event.getOrderId();
        CreateOrderDTO createOrder = event.getCreateOrder();
        String course = event.getCourseSel();

        mTvOrder.setText(orderId);
        mTvSignUp.setText(createOrder.getUserName());
        mTvPhone.setText(createOrder.getPhone());
        mTvIdentityCard.setText(createOrder.getIdCard());
        mTvCourse.setText(course);

        StringBuffer sb = new StringBuffer();
        sb.append("<font color=\"#ffffff\">");
        sb.append("银联卡支付&#160;&#160;");
        sb.append("</font>");
        sb.append("<font color=\"#0f536f\">");
        sb.append("*不可改");
        sb.append("</font>");
        mTvPayType.setText(Html.fromHtml(sb.toString()));

        mTvMoney.setText(FormatUtils.submitPrice(createOrder.getPrice()));
    }

    public void initView(View view) {
        mTvOrderTitle = (TextView) view.findViewById(R.id.tv_order_title);
        mTvOrder = (TextView) view.findViewById(R.id.tv_order);
        mTvSignUpTitle = (TextView) view.findViewById(R.id.tv_sign_up_title);
        mTvSignUp = (TextView) view.findViewById(R.id.tv_sign_up);
        mTvPhoneTitle = (TextView) view.findViewById(R.id.tv_phone_title);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mTvIdentityCardTitle = (TextView) view.findViewById(R.id.tv_identity_card_title);
        mTvIdentityCard = (TextView) view.findViewById(R.id.tv_identity_card);
        mTvCourseTitle = (TextView) view.findViewById(R.id.tv_course_title);
        mTvCourse = (TextView) view.findViewById(R.id.tv_course);
        mTvPayTypeTitle = (TextView) view.findViewById(R.id.tv_pay_type_title);
        mTvPayType = (TextView) view.findViewById(R.id.tv_pay_type);
        mLayLine = (LinearLayout) view.findViewById(R.id.lay_line);
        mTvMoneyTitle = (TextView) view.findViewById(R.id.tv_money_title);
        mTvMoney = (TextView) view.findViewById(R.id.tv_money);
        mTvIntroduce = (TextView) view.findViewById(R.id.tv_introduce);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay://支付
                MainRouter.gotoPayTypeActivity(getActivity(), mTvOrder.getText().toString());
                if (getActivity() != null) getActivity().finish();

                if (mPresenter != null) {
                    //                    String moneyString = mTvMoney.getText().toString();
                    //                    double money = Double.parseDouble(moneyString);
                    //                    int intMoney = (int) money * 100;
                    //                    String stringMoney = String.valueOf(intMoney);
                    //                    mPresenter.getBankPayHtml(
                    //                            mTvOrder.getText().toString(),
                    //                            stringMoney);

                    MobUtils.getInstance().eventIdByUMeng(30);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 支付调用成功
     */
    @Override
    public void onPayOrderByCouponError(String message) {
        toastShort(message);
    }

    @Override
    public void onPayOrderByCouponSucceed(PayOrderResponse result) {
        //        if (!LoginData.getInstance().loginFlag && !TextUtils.isEmpty(LoginData.getInstance().userID)) {
        //            Intent intent = new Intent(getActivity(), LoginActivity.class);
        //            getActivity().startActivity(intent);
        //        } else {
        //            Intent intent = new Intent(getActivity(), PayBrowserActivity.class);
        //            intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付");
        //            intent.putExtra(JxGlobal.putExtra.web_url_extra, result.getData());
        //            intent.putExtra(JxGlobal.putExtra.web_order_id_extra, mTvOrder.getText().toString());
        //            getActivity().startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
        //        }
    }
}