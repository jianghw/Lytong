package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IRefuelOilContract;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilAdapter;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilPresenter;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.java.response.oil.NorOilResponse;
import com.tzly.ctcyh.java.response.oil.OilRemainderResponse;
import com.tzly.ctcyh.java.response.oil.SINOPECBean;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.popup.CustomDialog;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 加油充值
 */
public class DiscountOilFragment extends RefreshFragment
        implements IRefuelOilContract.IRefuelOilView, View.OnClickListener {

    private IRefuelOilContract.IRefuelOilPresenter mPresenter;

    /**
     * 请输入19位加油卡号
     */
    private EditText mEditOil;
    private RecyclerView mXRecyclerView;
    private TextView mTvHint;
    /**
     * 我已阅读用户充值协议
     */
    private TextView mTvAgreement;

    private RefuelOilAdapter mAdapter;
    /**
     * 当前选择项
     */
    private SINOPECBean infoBean;
    /**
     * 通讯接口
     */
    private IRechargeAToF iRechargeAToF;
    private TextView mTvGou;
    private ImageView mImgBanner;

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
                CargoRouter.gotoOilShareActivity(getActivity(), 1);
            }
        });
        mEditOil = (EditText) view.findViewById(R.id.edit_oil);

        mTvGou = (TextView) view.findViewById(R.id.tv_gou_card);
        mTvGou.setOnClickListener(this);

        mXRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mTvHint = (TextView) view.findViewById(R.id.tv_hint);
        mTvAgreement = (TextView) view.findViewById(R.id.tv_agreement);
        mTvAgreement.setOnClickListener(this);

        GridLayoutManager manager = new GridLayoutManager(Utils.getContext(), 3);
        mXRecyclerView.setLayoutManager(manager);

        mAdapter = new RefuelOilAdapter();
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (!(data instanceof SINOPECBean)) return;

                SINOPECBean cardInfoBean = (SINOPECBean) data;
                for (SINOPECBean oilBean : mAdapter.getAll()) {
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
                "1、畅通9.7折油卡由中石化官方发行，使用工行卡在" + app_name + "APP线充值享9.7折，其他渠道不能充值;\n"
                        + "2、非" + app_name + "油卡，不支持此页面充值;\n"
                        + "3、每月15号，30号开放两次充值服务，可提前支付下单，自动于15号，30号当天充值到账;\n"
                        + "4、充值成功后，需加油站圈存后方可使用，详询加油站工作人员;\n"
                        + "5、目前黑吉辽、陕甘宁、新疆、内蒙、西藏、青海省不支持当地圈存，可在外省圈存后在以上城市使用加油;\n"
                        + "6、本服务为全国加油卡代充，故不支持开具发票;\n"
                        + "7、本服务由" + app_name + "加油服务商提供"
        );
        //可以为多部分设置超链接
        //        spanableInfo.setSpan(
        //                new Clickable(listener),
        //                spanableInfo.length() - 10, spanableInfo.length(),
        //                Spanned.SPAN_MARK_MARK);

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
        } else if (v.getId() == R.id.tv_gou_card) {//购卡
            CargoRouter.gotoCustomerService(
                    "native_app_97buyCard", "9.7折购卡", "151", getActivity());
        }
    }

    @Override
    public SINOPECBean getCardInfo() {
        return infoBean != null ? infoBean : new SINOPECBean();
    }

    @Override
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
            NorOilResponse.DataBean bean = oilResponse.getData();
            dataRendering(bean);
        } else
            ToastUtils.toastShort("数据类型出错,退出页面再试一试");
    }

    /**
     * 数据渲染
     *
     * @param bean
     */
    private void dataRendering(NorOilResponse.DataBean bean) {
        /*String url = bean.getImg();

        if (!url.contains("http")) {
            url = (BuildConfig.isDeta ? BuildConfig.beta_base_url : BuildConfig.release_base_url) + url;
        }
        ImageLoadUtils.loadTwoRectangle(url, mImgBanner);*/

        String oilCard = bean.getOilCard();
        if (!TextUtils.isEmpty(oilCard)) setStrEditOil(oilCard);

        List<SINOPECBean> lis = bean.getSINOPEC();
        if (!lis.isEmpty()) {//默认第一个
            for (int i = 0; i < lis.size(); i++) {
                lis.get(i).setSelect(i == 0);
                if (i == 0) infoBean = lis.get(i);
            }
        }
        setSimpleDataResult(lis);

        if (iRechargeAToF != null) iRechargeAToF.setCommitEnable(true);
    }

    private void setSimpleDataResult(List<SINOPECBean> data) {
        mAdapter.cleanListData();
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

        if (message.contains("97折") || message.contains("折")) codeError();
    }

    public void codeError() {
        CustomDialog.createDialog(getActivity(),
                "温馨提示",
                "该卡号不是畅通9.7折中石化加油卡，请先购买畅通9.7折中石化加油卡",
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
                        CargoRouter.gotoBidOilActivity(getActivity());
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

    @Override
    public void remainderSucceed(OilRemainderResponse response) {
    }

    @Override
    public void remainderError(String message) {
    }

}