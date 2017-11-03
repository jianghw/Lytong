package com.zantong.mobilecttx.fahrschule.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tzly.ctcyh.router.util.FormatUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectOrderContract;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.eventbus.SubjectOrderEvent;
import com.zantong.mobilecttx.presenter.fahrschule.SubjectOrderPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.qqtheme.framework.bean.response.SubjectGoodsBean;

/**
 * 科目强化订单确认页面
 */
public class SubjectOrderFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISubjectOrderContract.ISubjectOrderView {

    private TextView mTvOrderNum;

    private TextView mTvCourseName;
    private TextView mTvUser;
    private TextView mTvPhone;
    /**
     * 订单金额
     */
    private TextView mTvPriceTitle;
    /**
     * 订单金额
     */
    private TextView mTvPrice;
    /**
     * 提交订单
     */
    private TextView mTvCommit;

    private ISubjectOrderContract.ISubjectOrderPresenter mPresenter;
    private ISubjectSwitcherListener mSwitcherListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SubjectOrderFragment newInstance() {
        return new SubjectOrderFragment();
    }

    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected void onRefreshData() {}

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_subject_order;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        SubjectOrderPresenter mPresenter = new SubjectOrderPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(ISubjectOrderContract.ISubjectOrderPresenter presenter) {
        mPresenter = presenter;
    }

    public void setSwitcherListener(ISubjectSwitcherListener switcherListener) {
        mSwitcherListener = switcherListener;
    }

    @Override
    protected void onFirstDataVisible() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void DestroyViewAndThing() {
        EventBus.getDefault().removeStickyEvent(SubjectOrderEvent.class);
        EventBus.getDefault().unregister(this);

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        mTvCourseName = (TextView) view.findViewById(R.id.tv_course_name);
        mTvUser = (TextView) view.findViewById(R.id.tv_user);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);

        mTvPriceTitle = (TextView) view.findViewById(R.id.tv_price_title);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMainEvent(SubjectOrderEvent event) {
        if (event != null) initData(event);
    }

    /**
     * 优惠卷 type  优惠券类型：1 无；2 折扣；3 代金券
     */
    private void initData(SubjectOrderEvent event) {
        mTvOrderNum.setText(event.getOrderId());
        SubjectGoodsBean goodsBean = event.getGoodsBean();

        if (goodsBean != null) {
            mTvCourseName.setText(goodsBean.getGoods().getName());
            mTvUser.setText(event.getEditName());
            mTvPhone.setText(event.getPhone());
        }
        String price = event.getPrice();
        mTvPrice.setText(FormatUtils.submitPrice(Float.valueOf(price)));
    }

    private String displayPriceValue(double discount, int couponType, int price) {
        if (couponType == 2) {
            return StringUtils.getPriceDouble(price * discount);
        } else if (couponType == 3) {
            double value = discount * 100.00d;
            double submitPrice = (price - value) <= 0 ? 0 : (price - value);
            return StringUtils.getPriceDouble(submitPrice);
        }
        return StringUtils.getPriceDouble(price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_commit:
                dataFormValidation();
                break;
        }
    }

    /**
     * 数据验证
     */
    private void dataFormValidation() {
        MainRouter.gotoPayTypeActivity(getActivity(), mTvOrderNum.getText().toString());
        if (getActivity() != null) getActivity().finish();

        //        String moneyString = mTvPrice.getText().toString();
        //        double money = Double.parseDouble(moneyString);
        //        int intMoney = (int) money * 100;
        //        String stringMoney = String.valueOf(intMoney);
        //        if (mPresenter != null) mPresenter.getBankPayHtml(
        //                mTvOrderNum.getText().toString(),
        //                stringMoney);
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
     * 支付页面
     */
    @Override
    public void bankPayHtmlError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void bankPayHtmlSucceed(PayOrderResponse result) {
        //        if (!LoginData.getInstance().loginFlag && !TextUtils.isEmpty(LoginData.getInstance().userID)) {
        //            Intent intent = new Intent(getActivity(), LoginActivity.class);
        //            getActivity().startActivity(intent);
        //        } else {
        //            Intent intent = new Intent(getActivity(), PayBrowserActivity.class);
        //            intent.putExtra(JxGlobal.putExtra.web_title_extra, "支付");
        //            intent.putExtra(JxGlobal.putExtra.web_url_extra, result.getData());
        //            intent.putExtra(JxGlobal.putExtra.web_order_id_extra, mTvOrderNum.getText().toString());
        //            getActivity().startActivityForResult(intent, JxGlobal.requestCode.fahrschule_order_num_web);
        //        }
    }

}
