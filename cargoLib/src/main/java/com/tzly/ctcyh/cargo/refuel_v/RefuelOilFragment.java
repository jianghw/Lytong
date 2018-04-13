package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IRefuelOilContract;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilAdapter;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilPresenter;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.java.response.oil.OilCardsResponse;
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
public class RefuelOilFragment extends RefreshFragment
        implements IRefuelOilContract.IRefuelOilView, View.OnClickListener {

    private IRefuelOilContract.IRefuelOilPresenter mPresenter;

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
     * 通讯接口
     */
    private IRechargeAToF iRechargeAToF;
    /**
     * 数据
     */
    private OilCardsResponse.DataBean data;
    /**
     * 当前选择项
     */
    private SINOPECBean selectBean;

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
        if (mPresenter != null) mPresenter.findOilCardsAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                boolean first = radioSinopec.isChecked();

                mEditOil.setFilters(new InputFilter[]{new InputFilter.LengthFilter(first ? 19 : 16)}); //最大输入长度
                setStrEditOil(first ? data.getSINOPECCard() : data.getCNPCCard());
                mEditOil.setHint("请填写正确的" + String.valueOf(first ? 19 : 16) + "位卡号");

                segmentedDisplayData(first);
            }
        });
        radioSinopec = (RadioButton) view.findViewById(R.id.radio_sinopec);
        radioPetro = (RadioButton) view.findViewById(R.id.radio_petro);

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
                if (!(data instanceof SINOPECBean)) return;
                selectItem((SINOPECBean) data);
            }
        });
        mXRecyclerView.setAdapter(mAdapter);

        mTvHint.setText(getClickableSpan());
        mTvHint.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void selectItem(SINOPECBean data) {
        for (SINOPECBean oilBean : mAdapter.getAll()) {
            boolean isMe = data.getId().equals(oilBean.getId());
            oilBean.setSelect(isMe);
            if (isMe) this.selectBean = oilBean;
        }
        mAdapter.notifyDataSetChanged();
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
                        + "5. 本服务由车行易和鼎信提供;\n"
                        + "6. 支付成功后将于2小时内到账，超过24小时未到账请与我们「联系」"
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
    public SINOPECBean getCardInfo() {
        return selectBean != null ? selectBean : new SINOPECBean();
    }

    @Override
    public String getStrEditOil() {
        return mEditOil.getText().toString().trim();
    }

    public void setStrEditOil(String str) {
        String string = TextUtils.isEmpty(str) ? "" : str;
        if (mEditOil != null) mEditOil.setText(string);
    }

    /**
     * 数据加载成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof OilCardsResponse) {
            OilCardsResponse oilResponse = (OilCardsResponse) response;
            OilCardsResponse.DataBean dataBean = oilResponse.getData();
            dataRendering(dataBean);
        } else
            ToastUtils.toastShort("数据类型出错,退出页面再试一试");
    }

    /**
     * 数据渲染
     * second -->中石油
     */
    private void dataRendering(OilCardsResponse.DataBean bean) {
        this.data = bean;
        //中石油
        String cnpcCard = data.getCNPCCard();
        //中石化
        String sinopecCard = data.getSINOPECCard();

        boolean second = TextUtils.isEmpty(sinopecCard) && !TextUtils.isEmpty(cnpcCard);
        setStrEditOil(second ? cnpcCard : sinopecCard == null ? "" : sinopecCard);
        //查看监听处代码
        if (second)
            radioPetro.setChecked(true);
        else
            radioSinopec.setChecked(true);
        //        radioGroup.check(second ? R.id.radio_petro : R.id.radio_sinopec);
    }

    /**
     * 显示默认的第一个价格数据
     *
     * @param b true--> 中石化
     */
    private void segmentedDisplayData(boolean b) {
        if (this.data == null) {
            loadingFirstData();
        } else {
            List<SINOPECBean> lis = b ? this.data.getSINOPEC() : this.data.getCNPC();
            if (!lis.isEmpty()) {//默认第一个
                for (int i = 0; i < lis.size(); i++) {
                    lis.get(i).setSelect(i == 0);
                    if (i == 0) this.selectBean = lis.get(i);
                }
            }
            setSimpleDataResult(lis);
            if (iRechargeAToF != null) iRechargeAToF.setCommitEnable(true);
        }
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

        if (message.contains("97折")) codeError();
    }

    public void codeError() {
        CustomDialog.createDialog(getActivity(),
                "温馨提示",
                "该卡号是97折卡号,请前往97折加油充值界面充值",
                "取消",
                "97充值",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CargoRouter.gotoDiscountOilActivity(getActivity());
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