package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.ctcyh.cargo.BuildConfig;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IRefuelOilContract;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilAdapter;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilPresenter;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 加油充值
 */
public class RefuelOilFragment extends RefreshFragment
        implements IRefuelOilContract.IRefuelOilView, View.OnClickListener {

    private IRefuelOilContract.IRefuelOilPresenter mPresenter;

    private ImageView mImgBanner;
    private RadioGroup radioGroup;
    private RadioButton radioSinopec;
    private RadioButton radioPetro;

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
    private RefuelOilBean.CardInfoBean infoBean;
    /**
     * 通讯接口
     */
    private IRechargeAToF iRechargeAToF;

    public static RefuelOilFragment newInstance() {
        return new RefuelOilFragment();
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
        return R.layout.cargo_fragment_refuel_oil;
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
        if (mPresenter != null) mPresenter.findAndSaveCards();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        radioSinopec = (RadioButton) view.findViewById(R.id.radio_sinopec);
        radioSinopec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setStrEditOil("");
                mEditOil.setHint("请填写正确的" + String.valueOf(b ? 19 : 16) + "位卡号");
            }
        });
        radioPetro = (RadioButton) view.findViewById(R.id.radio_petro);

        mImgBanner = (ImageView) view.findViewById(R.id.img_banner);
        mEditOil = (EditText) view.findViewById(R.id.edit_oil);
        mEditOil.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            /**
             * 输入监听
             */
            @Override
            public void afterTextChanged(Editable editable) {
                int len = editable != null ? editable.length() : 0;
                if (radioSinopec.isChecked() && len == 19 || !radioSinopec.isChecked() && len == 16)
                    if (mPresenter != null) mPresenter.findAndSaveCards();
            }
        });
        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_list);
        mTvHint = (TextView) view.findViewById(R.id.tv_hint);
        mTvAgreement = (TextView) view.findViewById(R.id.tv_agreement);
        mTvAgreement.setOnClickListener(this);

        GridLayoutManager manager = new GridLayoutManager(Utils.getContext(), 3);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.noMoreLoadings();
//        mXRecyclerView.addItemDecoration(
//                new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.res_x_30))
//        );

        mAdapter = new RefuelOilAdapter();
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (!(data instanceof RefuelOilBean.CardInfoBean)) return;

                RefuelOilBean.CardInfoBean cardInfoBean = (RefuelOilBean.CardInfoBean) data;
                for (RefuelOilBean.CardInfoBean oilBean : mAdapter.getAll()) {
                    boolean isMe = cardInfoBean.getGoodsId().equals(oilBean.getGoodsId());
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
                CargoRouter.gotoProblemFeedbackActivity(getActivity());
            }
        };

        SpannableString spanableInfo = new SpannableString(
                "1. 充值成功后，在加油前把加油卡交给油站工作人员协助圈存；\n"
                        + "2. 副卡、增值税票卡不支持在线充值；\n"
                        + "3. 22：00--03：00进行系统维护，充值后将延迟到账;\n"
                        + "4. 本服务为全国加油卡代充服务，故暂不支持开具发票;\n"
                        + "5. 本服务由车行易提供;\n"
                        + "6. 支付成功后将于30分钟内到账，超过24小时未到账请与我们「联系」"
        );
        //可以为多部分设置超链接
        spanableInfo.setSpan(
                new Clickable(listener),
                spanableInfo.length() - 4, spanableInfo.length(),
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
    public RefuelOilBean.CardInfoBean getCardInfo() {
        return infoBean != null ? infoBean : new RefuelOilBean.CardInfoBean();
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
        if (response instanceof RefuelOilResponse) {
            RefuelOilResponse oilResponse = (RefuelOilResponse) response;
            RefuelOilBean bean = oilResponse.getData();
            dataRendering(bean);
        } else
            responseError();
    }

    /**
     * 数据错误时
     */
    public void responseError(String message) {
        toastShort(message);
    }


    /**
     * 数据渲染
     */
    private void dataRendering(RefuelOilBean bean) {
        String url = bean.getImg();
        if (!BuildConfig.App_Url) ImageLoadUtils.loadTwoRectangle(url, mImgBanner);

        String oilType = bean.getOilType();
        radioGroup.check(TextUtils.isEmpty(oilType) || (oilType.contains("化"))
                ? R.id.radio_sinopec : R.id.radio_petro);

        String oilCard = bean.getOilCard();
        if (!TextUtils.isEmpty(oilCard)) setStrEditOil(oilCard);

        List<RefuelOilBean.CardInfoBean> lis = bean.getCardInfo();
        if (!lis.isEmpty()) {//默认第一个
            RefuelOilBean.CardInfoBean cardInfoBean = lis.get(0);
            cardInfoBean.setSelect(true);
            infoBean = cardInfoBean;
        }
        setSimpleDataResult(lis);

        if (iRechargeAToF != null) iRechargeAToF.setCommitEnable(true);
    }

    private void setSimpleDataResult(List<RefuelOilBean.CardInfoBean> data) {
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
        } else if (radioSinopec.isChecked() && card.length() != 19) {
            toastShort("请填写正确的19位卡号");
        } else if (!radioSinopec.isChecked() && card.length() != 16) {
            toastShort("请填写正确的16位卡号");
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