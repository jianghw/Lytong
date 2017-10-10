package com.zantong.mobilecttx.fahrschule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponBean;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.fahrschule.ISparringSubscribeContract;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.eventbus.SparringOrderEvent;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderBean;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaBean;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.order.activity.CouponListActivity;
import com.zantong.mobilecttx.presenter.fahrschule.SparringSubscribePresenter;
import com.zantong.mobilecttx.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.qqtheme.framework.bean.response.SubjectGoodsBean;
import cn.qqtheme.framework.bean.response.SubjectGoodsData;
import cn.qqtheme.framework.imple.IAreaDialogListener;
import cn.qqtheme.framework.imple.ISpeedDialogListener;
import cn.qqtheme.framework.imple.ITimeDialogListener;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.CustomDialog;
import cn.qqtheme.framework.util.RegexUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.ViewUtils;

/**
 * 陪练预约 页面
 */
public class SparringSubscribeFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISparringSubscribeContract.ISparringSubscribeView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;

    private String mParam2;
    private ImageView mImgDate;

    /**
     * 请选择地区
     */
    private TextView mTvCourseTitle;

    /**
     * 请填写详细的服务地址
     */
    private EditText mEditAddress;

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
    private RelativeLayout mLayTime;

    /**
     * 是否点击获取优惠
     */
    private boolean isChoiceCoupon = true;
    /**
     * 优惠券id
     */
    private int mCouponId = -1;
    private ISubjectSwitcherListener mSwitcherListener;
    private CreateOrderDTO orderDTO = new CreateOrderDTO();
    /**
     * 优惠券 弹出框布局
     */
    private List<RechargeCouponBean> mCouponBeanList = new ArrayList<>();
    /**
     * mPresenter
     */
    private ISparringSubscribeContract.ISparringSubscribePresenter mPresenter;
    /**
     * 商品价格
     */
    private int mPrice = -1;

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
        ViewUtils.editTextInputSpace(mEditName);
        ViewUtils.editTextInputSpace(mEditPhone);
        ViewUtils.editTextInputSpace(mEditLicense);
        ViewUtils.editTextInputSpace(mEditRemark);
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
            setEditAddress("城市车师傅师傅");
            setEditName("测试人员");
            setEditPhone("15252565532");
            setEditLicense("362226198408270049");
            setEditRemark("要大波");
        }

        //优惠劵
        if (mPresenter != null) mPresenter.getCouponByType();
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();

        orderDTO = null;
        if (!mCouponBeanList.isEmpty()) mCouponBeanList.clear();
    }

    public void initView(View view) {
        mLayArea = (RelativeLayout) view.findViewById(R.id.lay_area);
        mLayArea.setOnClickListener(this);
        mImgDate = (ImageView) view.findViewById(R.id.img_date);
        mTvCourseTitle = (TextView) view.findViewById(R.id.tv_course_title);
        mEditAddress = (EditText) view.findViewById(R.id.edit_address);
        mLayMotorcycleType = (RelativeLayout) view.findViewById(R.id.lay_motorcycle_type);
        mLayMotorcycleType.setOnClickListener(this);

        mTvMotorcycleType = (TextView) view.findViewById(R.id.tv_motorcycle_type);
        mLayTime = (RelativeLayout) view.findViewById(R.id.lay_time);
        mLayTime.setOnClickListener(this);
        mImgTime = (ImageView) view.findViewById(R.id.img_time);
        mTvTime = (TextView) view.findViewById(R.id.tv_time);
        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mEditLicense = (EditText) view.findViewById(R.id.edit_license);
        mEditRemark = (EditText) view.findViewById(R.id.edit_remark);
        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mLayCoupon = (RelativeLayout) view.findViewById(R.id.lay_coupon);
        mLayCoupon.setOnClickListener(this);
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
            case R.id.lay_time://选择时间
                if (mPresenter != null) mPresenter.getServerTime();
                break;
            case R.id.tv_commit:
                dataFormValidation();
                break;
            case R.id.lay_coupon://优惠价
                if (mCouponBeanList == null || mCouponBeanList.size() < 1) {
                    if (mPresenter != null) mPresenter.getCouponByType();
                } else {
                    Intent intent = new Intent(getActivity(), CouponListActivity.class);
                    Bundle bundle = new Bundle();

                    ArrayList<RechargeCouponBean> arrayList = new ArrayList<>();
                    arrayList.addAll(mCouponBeanList);
                    bundle.putParcelableArrayList(JxGlobal.putExtra.recharge_coupon_extra, arrayList);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, JxGlobal.requestCode.recharge_coupon_list);
                }
                break;
            default:
                break;
        }
    }

    public String getTextViewArea() {
        return mTvCourseTitle.getText().toString().trim();
    }

    public String getTextMotorcycleType() {
        return mTvMotorcycleType.getText().toString().trim();
    }

    public String getTextTime() {
        return mTvTime.getText().toString().trim();
    }

    public String getEditAddress() {
        return mEditAddress.getText().toString().trim();
    }

    public void setEditAddress(String editAddress) {
        mEditAddress.setText(editAddress);
    }

    public String getEditName() {
        return mEditName.getText().toString().trim();
    }

    public void setEditName(String editName) {
        mEditName.setText(editName);
    }

    public String getEditPhone() {
        return mEditPhone.getText().toString().trim();
    }

    public void setEditPhone(String editPhone) {
        mEditPhone.setText(editPhone);
    }

    public String getEditLicense() {
        return mEditLicense.getText().toString().trim();
    }

    public void setEditLicense(String editLicense) {
        mEditLicense.setText(editLicense);
    }

    public String getEditRemark() {
        return mEditRemark.getText().toString().trim();
    }

    public void setEditRemark(String editRemark) {
        mEditRemark.setText(editRemark);
    }

    /**
     * 数据验证
     */
    private void dataFormValidation() {

        if (editTextEmpty(getTextViewArea(), "请选择服务地区")) return;
        if (editTextEmpty(getTextMotorcycleType(), "请选择车型/变速箱")) return;
        if (editTextEmpty(getTextTime(), "请选择服务时间")) return;
        if (editTextEmpty(getEditAddress(), "请输入服务地址")) return;
        if (editTextEmpty(getEditName(), "请输入姓名")) return;
        if (editTextEmpty(getEditPhone(), "请输入手机号码")) return;

        if (!RegexUtils.isMobileSimple(getEditPhone())) {
            ToastUtils.toastShort("请确保手机号真实准确");
            return;
        }
        if (editTextEmpty(getEditLicense(), "请输入驾驶证号")) return;

        getCreateOrderDTO().setServiceAddress(getEditAddress());
        getCreateOrderDTO().setDriveNum(getEditLicense());
        getCreateOrderDTO().setUserName(getEditName());
        getCreateOrderDTO().setPhone(getEditPhone());
        getCreateOrderDTO().setRemark(getEditRemark());
        getCreateOrderDTO().setPrice(getPriceValue());

        if (mPresenter != null) mPresenter.createOrder();
    }

    private boolean editTextEmpty(String edit, String message) {
        if (TextUtils.isEmpty(edit)) {
            ToastUtils.toastShort(message);
            return true;
        }
        return false;
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    public String getPriceValue() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        String start = getCreateOrderDTO().getStartTime();
        String end = getCreateOrderDTO().getEndTime();
        int difference = 2;
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            try {
                long time = dateFormat.parse(end).getTime() - dateFormat.parse(start).getTime();
                difference = (int) (time % (1000 * 24 * 60 * 60) / (1000 * 60 * 60));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String finalPrice = String.valueOf(mPrice * difference);
        if (isChoiceCoupon) {
            for (RechargeCouponBean bean : mCouponBeanList) {
                if (bean.isChoice()) {
                    double discount = (bean.getCouponValue() / 100.00d);
                    int couponType = bean.getCouponType();
                    finalPrice = displayPriceValue(discount, couponType, mPrice);
                    break;
                }
            }
        }
        return finalPrice;
    }

    /**
     * 优惠 价格处理
     */
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
    public void serviceAreaSucceed(SparringAreaResponse result) {
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
                        mTvCourseTitle.setText(area);
                        getCreateOrderDTO().setServiceArea(area);
                    }
                });
    }

    /**
     * 选择车型
     */
    @Override
    public void goodsSucceed(SparringGoodsResponse result) {
        SubjectGoodsData resultData = result.getData();

        CustomDialog.popupBottomCarType(getActivity(), resultData, new ISpeedDialogListener() {
            @Override
            public void setCurPosition(
                    SubjectGoodsBean goodsBean, SubjectGoodsBean sparringGoodsBean) {

                mTvMotorcycleType.setText(goodsBean.getGoods().getName() + "-" + sparringGoodsBean.getGoods().getName());
                mPrice = goodsBean.getGoods().getPrice();
                getCreateOrderDTO().setGoodsId(String.valueOf(goodsBean.getGoods().getGoodsId()));
                getCreateOrderDTO().setSpeedType(sparringGoodsBean.getGoods().getName());
            }
        });
    }

    @Override
    public void goodsError(String message) {
        serverError(message);
    }

    /**
     * 获取时间
     */
    @Override
    public void serverTimeError(String message) {
        serverError(message);
    }

    @Override
    public void serverTimeSucceed(ArrayList<String> dateList, final Date date) {

        ArrayList<ArrayList<String>> secondLis = new ArrayList<>();

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 7; i <= 17; i++) {
            String time = String.valueOf(i < 10 ? "0" + i : i);
            strings.add(time + ":00");
        }
        secondLis.add(strings);

        CustomDialog.popupBottomTime(
                getActivity(), dateList, secondLis, new ITimeDialogListener() {
                    @Override
                    public void setCurPosition(String first, String second, String third) {
                        mTvTime.setText(first + "/" + second + "-" + third);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM月dd日 EHH:mm", Locale.SIMPLIFIED_CHINESE);

                        Calendar startCalendar = Calendar.getInstance();
                        startCalendar.setTime(date);
                        int month = startCalendar.get(Calendar.MONTH);
                        int day = startCalendar.get(Calendar.DAY_OF_MONTH);
                        int year = month != 11 && day != 31
                                ? startCalendar.get(Calendar.YEAR) : startCalendar.get(Calendar.YEAR) + 1;
                        String startTime = year + first + second;
                        String endTime = year + first + third;

                        Date startDate = null;
                        Date endDate = null;
                        try {
                            startDate = sdf.parse(startTime);
                            endDate = sdf.parse(endTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
                        if (startDate != null)
                            getCreateOrderDTO().setStartTime(dateFormat.format(startDate));
                        if (endDate != null)
                            getCreateOrderDTO().setEndTime(dateFormat.format(endDate));
                    }
                });
    }

    /**
     * 优惠卷使用
     */
    @Override
    public void couponByTypeSucceed(RechargeCouponResponse result) {
        if (result.getData() != null && result.getData().size() > 0) {
            List<RechargeCouponBean> resultData = result.getData();
            if (!mCouponBeanList.isEmpty()) mCouponBeanList.clear();
            mCouponBeanList.addAll(resultData);

            RechargeCouponBean bean = resultData.get(0);
            if (bean == null) return;
            bean.setChoice(true);//默认手动选择第一条优惠劵

            displayCouponByBean(bean);
        } else {
            displayCouponState("无优惠劵");
        }
    }

    @Override
    public void couponByTypeError(String message) {
        displayCouponState("获取失败,点击加载");
        serverError(message);
    }

    @Override
    public CreateOrderDTO getCreateOrderDTO() {
        return orderDTO;
    }

    /**
     * 创建订单
     */
    @Override
    public void createOrderError(String message) {
        serverError(message);
    }

    @Override
    public void createOrderSucceed(CreateOrderResponse result) {
        CreateOrderBean resultData = result.getData();
        String orderId = resultData.getOrderId();

        if (mSwitcherListener != null) mSwitcherListener.setCurPosition(1);

        EventBus.getDefault().postSticky(
                new SparringOrderEvent(orderId, getCreateOrderDTO(), getTextTime(), getTextMotorcycleType()));
    }

    @Override
    public boolean getUseCoupon() {
        return isChoiceCoupon;
    }

    @Override
    public String getCouponId() {
        return String.valueOf(mCouponId);
    }

    private void displayCouponByBean(RechargeCouponBean bean) {
        mCouponId = bean.getId();

        StringBuilder sb = new StringBuilder();
        sb.append(bean.getCouponContent());
        sb.append("\n");
        sb.append(bean.getCouponValidityEnd() + "到期");

        displayCouponState(sb.toString());
    }

    private void displayCouponState(String msg) {
        isChoiceCoupon = msg.contains("到期");
        setTvCoupon(msg);
    }

    public void setTvCoupon(String tvCoupon) {
        mTvCoupon.setText(tvCoupon);
    }

    /**
     * 页面回掉
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == JxGlobal.requestCode.recharge_coupon_list
                && resultCode == JxGlobal.resultCode.recharge_coupon_choice) {
            Bundle bundle = data.getExtras();
            RechargeCouponBean couponBean = bundle.getParcelable(
                    JxGlobal.putExtra.recharge_coupon_bean_extra);
            couponActivityResult(couponBean);
        } else if (data != null && requestCode == JxGlobal.requestCode.recharge_coupon_list
                && resultCode == JxGlobal.resultCode.recharge_coupon_unchoice) {

            couponActivityResult(null);
        }
    }

    /**
     * 选择优惠后显示
     */
    private void couponActivityResult(RechargeCouponBean couponBean) {
        if (couponBean != null && mCouponBeanList != null && mCouponBeanList.size() > 0) {
            for (RechargeCouponBean bean : mCouponBeanList) {
                if (bean.getId() == couponBean.getId()) {
                    bean.setChoice(true);
                    displayCouponByBean(bean);
                } else {
                    bean.setChoice(false);
                }
            }
        } else {
            displayCouponState("不使用优惠劵");
        }
    }


}
