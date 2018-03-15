package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.bean.response.NorOilBean;
import com.tzly.ctcyh.cargo.bean.response.NorOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IRefuelOilContract;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilAdapter;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilPresenter;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.custom.popup.CustomDialog;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 加油充值
 */
public class DiscountOilFragment extends RefreshFragment
        implements IRefuelOilContract.IRefuelOilView, View.OnClickListener {

    private IRefuelOilContract.IRefuelOilPresenter mPresenter;

    private ImageView mImgBanner;

    /**
     * 请输入19位加油卡号
     */
    private EditText mEditOil;
    private XRecyclerView mXRecyclerView;
    private TextView mTvHint;
    /**
     * 我已阅读用户充值协议
     */
    private TextView mTvAgreement;

    private RefuelOilAdapter mAdapter;
    /**
     * 当前选择项
     */
    private NorOilBean.CNPCBean infoBean;
    /**
     * 通讯接口
     */
    private IRechargeAToF iRechargeAToF;

    public static DiscountOilFragment newInstance() {
        return new DiscountOilFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IRechargeAToF) {
            iRechargeAToF = (IRechargeAToF) activity;
        }
    }

    @Override
    protected int fragmentView() {
        return R.layout.cargo_fragment_discount_oil;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        RefuelOilPresenter presenter = new RefuelOilPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    public void setPresenter(IRefuelOilContract.IRefuelOilPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 初始话数据
     */
    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.findCaiNiaoCard();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {

        mImgBanner = (ImageView) view.findViewById(R.id.img_banner);
        mImgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CargoRouter.gotoBidOilActivity(getActivity());
            }
        });
        mEditOil = (EditText) view.findViewById(R.id.edit_oil);

        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_list);
        mTvHint = (TextView) view.findViewById(R.id.tv_hint);
        mTvAgreement = (TextView) view.findViewById(R.id.tv_agreement);
        mTvAgreement.setOnClickListener(this);

        GridLayoutManager manager = new GridLayoutManager(Utils.getContext(), 3);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.noMoreLoadings();

        mAdapter = new RefuelOilAdapter();
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (!(data instanceof NorOilBean.CNPCBean)) return;

                NorOilBean.CNPCBean cardInfoBean = (NorOilBean.CNPCBean) data;
                for (NorOilBean.CNPCBean oilBean : mAdapter.getAll()) {
                    boolean isMe = cardInfoBean.getId().equals(oilBean.getId());
                    oilBean.setSelect(isMe);
                    if (isMe) infoBean = oilBean;
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        mXRecyclerView.setAdapter(mAdapter);

        mTvHint.setText(getClickableSpan());
        mTvHint.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 说明文字
     */
    private SpannableString getClickableSpan() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                CargoRouter.gotoProblemFeedbackActivity(getActivity());
                Intent intent = new Intent(); // 意图对象：动作 + 数据
                intent.setAction(Intent.ACTION_DIAL); // 设置动作
                Uri data = Uri.parse("tel:" + "4008216158"); // 设置数据
                intent.setData(data);
                startActivity(intent);
            }
        };

        String app_name = getResources().getString(R.string.main_app_name);
        SpannableString spanableInfo = new SpannableString(
                "1、畅通97折油卡由中石化官方发行，使用工行卡在" + app_name + "APP线充值享97折，其他渠道不能充值;\n"
                        + "2、非" + app_name + "油卡，不支持此页面充值;\n"
                        + "3、每月15号，30号开放两次充值服务，可提前支付下单，自动于15号，30号当天充值到账;\n"
                        + "4、充值成功后，需加油站圈存后方可使用，详询加油站工作人员;\n"
                        + "5、目前黑吉辽、陕甘宁、新疆、内蒙、西藏、青海省不支持当地圈存，可在外省圈存后在以上城市使用加油;\n"
                        + "6、本服务为全国加油卡代充，顾不支持开具发票;\n"
                        + "7、本服务由" + app_name + "加油服务商提供，如有问题请致电4008216158"
        );
        //可以为多部分设置超链接
        spanableInfo.setSpan(
                new Clickable(listener),
                spanableInfo.length() - 10, spanableInfo.length(),
                Spanned.SPAN_MARK_MARK);
        return spanableInfo;
    }

    private class Clickable extends ClickableSpan implements View.OnClickListener {

        private final View.OnClickListener mListener;

        Clickable(View.OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_agreement) {//加油协议
            CargoRouter.gotoRechargeAgreementActivity(getActivity());
        }
    }

    @Override
    public NorOilBean.CNPCBean getCardInfo() {
        return infoBean != null ? infoBean : new NorOilBean.CNPCBean();
    }

    @Override
    public String getOilCard() {
        return TextUtils.isEmpty(getStrEditOil()) ? "0" : getStrEditOil();
    }

    public String getStrEditOil() {
        return mEditOil.getText().toString().trim();
    }

    public void setStrEditOil(String str) {
        if (mEditOil != null) mEditOil.setText(str);
    }

    /**
     * 数据加载成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof NorOilResponse) {
            NorOilResponse oilResponse = (NorOilResponse) response;
            NorOilBean bean = oilResponse.getData();
            dataRendering(bean);
        } else
            responseError();
    }

    /**
     * 数据渲染
     */
    private void dataRendering(NorOilBean bean) {
        String url = bean.getImg();
        ImageLoadUtils.loadTwoRectangle(url, mImgBanner);

        String oilCard = bean.getOilCard();
        if (!TextUtils.isEmpty(oilCard)) setStrEditOil(oilCard);

        List<NorOilBean.CNPCBean> lis = bean.getSINOPEC();
        if (!lis.isEmpty()) {//默认第一个
            for (int i = 0; i < lis.size(); i++) {
                lis.get(i).setSelect(i == 0);
                if (i == 0) infoBean = lis.get(i);
            }
        }
        setSimpleDataResult(lis);

        if (iRechargeAToF != null) iRechargeAToF.setCommitEnable(true);
    }

    private void setSimpleDataResult(List<NorOilBean.CNPCBean> data) {
        mAdapter.removeAllOnly();
        if (data == null || data.isEmpty()) {
            showStateEmpty();
        } else {
            mAdapter.append(data);
        }
    }

    /**
     * 出错时
     */
    protected void errorData(String message) {
        if (iRechargeAToF != null) iRechargeAToF.setCommitEnable(false);
    }

    /**
     * 提交数据验证
     */
    public void verificationSubmitData() {
        String card = getStrEditOil();
        if (TextUtils.isEmpty(card)) {
            toastShort("请填写正确的卡号");
        } else if (card.length() != 19) {
            toastShort("请填写正确的19位卡号");
        } else {
            if (mPresenter != null) mPresenter.createOrder();
        }
    }

    /**
     * 创建订单
     */
    @Override
    public void createOrderError(String message) {
        toastShort(message);

        if (message.contains("97折")) codeError();
    }

    public void codeError() {
        CustomDialog.createDialog(getActivity(),
                "温馨提示",
                "该卡号不是97折卡号,请前往普通加油充值界面充值如无卡97折加油卡,请去申购页面购买",
                "取消",
                "前往",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CargoRouter.gotoRechargeActivity(getActivity());
                    }
                });
    }

    @Override
    public void createOrderSucceed(RefuelOrderResponse response) {
        toastShort(response.getResponseDesc());

        RefuelOrderBean bean = response.getData();
        if (bean != null)
            CargoRouter.gotoPayTypeActivity(getActivity(), bean.getOrderId());
        else
            toastShort("数据出错,创建订单未知错误");
    }

}