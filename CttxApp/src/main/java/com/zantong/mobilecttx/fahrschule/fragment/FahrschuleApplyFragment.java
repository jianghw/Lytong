package com.zantong.mobilecttx.fahrschule.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.eventbus.FahrschuleApplyEvent;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.fahrschule.adapter.FahrschulePopupAresAdapter;
import com.zantong.mobilecttx.fahrschule.adapter.FahrschulePopupGoodsAdapter;
import com.zantong.mobilecttx.fahrschule.bean.AresGoodsBean;
import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderBean;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresBean;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResult;
import com.zantong.mobilecttx.interf.IFahrschuleApplyFtyContract;
import com.zantong.mobilecttx.presenter.fahrschule.FahrschuleApplyPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.RegexUtils;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 驾校报名页面
 */
public class FahrschuleApplyFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, IFahrschuleApplyFtyContract.IFahrschuleApplyFtyView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FahrschuleActivity.SwitcherListener mSwitcherListener;

    // TODO: Rename and change types of parameters
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
    private IFahrschuleApplyFtyContract.IFahrschuleApplyFtyPresenter mPresenter;
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

    public static FahrschuleApplyFragment newInstance() {
        return new FahrschuleApplyFragment();
    }

    public static FahrschuleApplyFragment newInstance(String param1, String param2) {
        FahrschuleApplyFragment fragment = new FahrschuleApplyFragment();
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
        return R.layout.fragment_fahrschule_apply;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        FahrschuleApplyPresenter mPresenter = new FahrschuleApplyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(IFahrschuleApplyFtyContract.IFahrschuleApplyFtyPresenter presenter) {
        mPresenter = presenter;
    }

    public void setSwitcherListener(FahrschuleActivity.SwitcherListener switcherListener) {
        mSwitcherListener = switcherListener;
    }

    @Override
    protected void onFirstDataVisible() {

    }

    @Override
    protected void DestroyViewAndThing() {
        hideDialogLoading();
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
            case R.id.tv_address_sel://地点
                if (mPresenter != null) mPresenter.getMerchantArea();
                break;
            case R.id.tv_course_sel://课程
                if (mAreaCode == 0) {
                    ToastUtils.toastShort("请先选择上课地点");
                } else if (mPresenter != null) mPresenter.getAreaGoods();
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
        String editName = getEditName();
        if (TextUtils.isEmpty(editName)) {
            ToastUtils.toastShort("请输入用户姓名");
            return;
        }
        String editPhone = getEditPhone();
        if (TextUtils.isEmpty(editPhone) || !RegexUtils.isMobileExact(editPhone)) {
            ToastUtils.toastShort("请输入正确手机号码");
            return;
        }
        String identityCard = getEditIdentityCard();
        if (TextUtils.isEmpty(identityCard) ||
                !RegexUtils.isIDCard15(identityCard) && !RegexUtils.isIDCard18(identityCard)) {
            ToastUtils.toastShort("请输入正确身份证号码");
            return;
        }

        if (mPresenter != null) mPresenter.createOrder();
    }

    public String getTvAddressSel() {
        return mTvAddressSel.getText().toString().trim();
    }

    public String getTvCourseSel() {
        return mTvCourseSel.getText().toString().trim();
    }

    @Override
    public String getEditName() {
        return mEditName.getText().toString().trim();
    }

    @Override
    public String getEditPhone() {
        return mEditPhone.getText().toString().trim();
    }

    @Override
    public String getEditIdentityCard() {
        return mEditIdentityCard.getText().toString().trim();
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    /**
     * 3.获取商户区域列表 成功
     */
    @Override
    public void getMerchantAreaSucceed(MerchantAresResult result) {
        if (result != null && result.getData() != null) {
            List<MerchantAresBean> list = result.getData();
            selectArea(list, mTvAddressSel);
        } else {
            ToastUtils.toastShort("地点数据为空，请稍后再试一试");
        }
    }

    /**
     * 下拉选择框
     */
    protected void selectArea(final List<MerchantAresBean> aresBeanList, final TextView textView) {
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(textView.getMeasuredWidth());

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.custom_listview_tv, null);
        ListView mListView = (ListView) inflate.findViewById(R.id.lv_list);
        mListView.setDivider(null);
        mListView.setItemsCanFocus(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MerchantAresBean bean = aresBeanList.get(position);
                popupWindow.dismiss();
                if (bean == null) return;
                textView.setText(bean.getFullName());
                mAreaCode = bean.getCode();
            }
        });
        FahrschulePopupAresAdapter adapter = new FahrschulePopupAresAdapter(getActivity(), aresBeanList);
        mListView.setAdapter(adapter);

        popupShowAs(textView, popupWindow, mListView);
    }

    private void popupShowAs(TextView textView, PopupWindow popupWindow, ListView mListView) {
        popupWindow.setContentView(mListView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(16);
            popupWindow.setBackgroundDrawable(
                    ContextCompat.getDrawable(ContextUtils.getContext(), R.drawable.shape_bg_popu_white));
        } else {
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(ContextUtils.getContext(),
                    R.drawable.shape_bg_popu_white));
        }
        popupWindow.showAsDropDown(textView, 0, 30);
    }

    @Override
    public void getMerchantAreaError(String message) {
        hideDialogLoading();
        ToastUtils.toastShort(message);
    }

    @Override
    public int getAreaCode() {
        return mAreaCode;
    }

    /**
     * 4.获取区域商品列表
     */
    @Override
    public void getAreaGoodsError(String message) {
        hideDialogLoading();
        ToastUtils.toastShort(message);
    }

    @Override
    public void getAreaGoodsSucceed(AresGoodsResult result) {
        if (result != null && result.getData() != null) {
            List<AresGoodsBean> list = result.getData();
            selectGoods(list, mTvCourseSel);
        } else {
            ToastUtils.toastShort("地点数据为空，请稍后再试一试");
        }
    }

    @Override
    public String getGoodsId() {
        return String.valueOf(mGoodsId);
    }

    private void selectGoods(final List<AresGoodsBean> goodsBeanList, final TextView textView) {
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWidth(textView.getMeasuredWidth());

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.custom_listview_tv, null);
        ListView mListView = (ListView) inflate.findViewById(R.id.lv_list);
        mListView.setDivider(null);
        mListView.setItemsCanFocus(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AresGoodsBean bean = goodsBeanList.get(position);
                popupWindow.dismiss();
                if (bean == null) return;
                textView.setText(bean.getName() + "" + bean.getPrice() + "元");
                mTvPrice.setText(bean.getPrice() + "元");
                mTvGift.setText(bean.getDescription());
                mGoodsId = bean.getGoodsId();
            }
        });
        FahrschulePopupGoodsAdapter adapter = new FahrschulePopupGoodsAdapter(getActivity(), goodsBeanList);
        mListView.setAdapter(adapter);

        popupShowAs(textView, popupWindow, mListView);
    }

    /**
     * 创建订单
     */
    @Override
    public void createOrderError(String message) {
        hideDialogLoading();
        ToastUtils.toastShort(message);
    }

    @Override
    public void createOrderSucceed(CreateOrderResult result) {
        CreateOrderBean bean = result.getData();
        if (bean != null && mPresenter != null) {
            if (mSwitcherListener != null) mSwitcherListener.setCurPosition(1);
            EventBus.getDefault().postSticky(
                    new FahrschuleApplyEvent(
                            bean.getOrderId(), mPresenter.getCreateOrder(), getTvCourseSel()));
        } else {
            ToastUtils.toastShort("订单号创建失败，稍后再试一试");
        }
    }
}
