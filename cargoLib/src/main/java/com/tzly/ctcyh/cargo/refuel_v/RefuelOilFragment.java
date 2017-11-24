package com.tzly.ctcyh.cargo.refuel_v;

import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
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
import com.tzly.ctcyh.router.custom.SpaceItemDecoration;
import com.tzly.ctcyh.router.custom.popup.CustomDialog;
import com.tzly.ctcyh.router.imple.IPayTypeListener;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 加油充值
 */
public class RefuelOilFragment extends RefreshFragment
        implements IRefuelOilContract.IRefuelOilView, View.OnClickListener {

    private IRefuelOilContract.IRefuelOilPresenter mPresenter;
    private TextView mTvTitle;
    private ImageView mImgType;
    /**
     * 小型汽车
     */
    private TextView mTvType;
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
    /**
     * 充    值
     */
    private Button mBtnCommit;
    private RefuelOilAdapter mAdapter;
    /**
     * oilType：1 中石化  2 中石油
     */
    private int mOilType = 1;
    /**
     * 当前选择项
     */
    private RefuelOilBean mRefuelOilBean;

    public static RefuelOilFragment newInstance() {
        return new RefuelOilFragment();
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

        String oilNum = SPUtils.getInstance(SPUtils.FILENAME).getString(SPUtils.USER_OIL_NUM);
        if (!TextUtils.isEmpty(oilNum)) {
            mOilType = oilNum.length() == 19 ? 1 : 2;
            mTvType.setText(mOilType == 1 ? "中石化" : "中石油");
            setEditOil(oilNum);
        }
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getGoods();
    }

    @Override
    public void setPresenter(IRefuelOilContract.IRefuelOilPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImgType = (ImageView) view.findViewById(R.id.img_type);
        mTvType = (TextView) view.findViewById(R.id.tv_type);
        RelativeLayout mLayType = (RelativeLayout) view.findViewById(R.id.lay_0);
        mLayType.setOnClickListener(this);
        mEditOil = (EditText) view.findViewById(R.id.edit_oil);

        mXRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_list);
        mTvHint = (TextView) view.findViewById(R.id.tv_hint);
        mTvAgreement = (TextView) view.findViewById(R.id.tv_agreement);
        mTvAgreement.setOnClickListener(this);
        mBtnCommit = (Button) view.findViewById(R.id.btn_commit);
        mBtnCommit.setOnClickListener(this);

        GridLayoutManager manager = new GridLayoutManager(Utils.getContext(), 3);
        mXRecyclerView.setLayoutManager(manager);
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.noMoreLoadings();
        mXRecyclerView.addItemDecoration(
                new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.res_x_30)));

        mAdapter = new RefuelOilAdapter();
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (!(data instanceof RefuelOilBean)) return;
                setEnableCommit(true);
                RefuelOilBean bean = (RefuelOilBean) data;
                for (RefuelOilBean oilBean : mAdapter.getAll()) {
                    boolean me = bean.getGoodsId() == oilBean.getGoodsId();
                    oilBean.setSelect(me);
                    if (me) mRefuelOilBean = oilBean;
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
                        + "5. 支付成功后将于30分钟内到账，超过24小时未到账请与我们「联系」"
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

    private void setEnableCommit(boolean enable) {
        mBtnCommit.setEnabled(enable);
    }

    private String getEditOil() {
        return mEditOil.getText().toString().trim();
    }

    private void setEditOil(String num) {
        mEditOil.setText(num);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_commit) {
            formDataValidation();
        } else if (v.getId() == R.id.tv_agreement) {//加油协议
            CargoRouter.RechargeAgreementActivity(getActivity());
        } else if (v.getId() == R.id.lay_0) {//提供商
            CustomDialog.payTypeDialog(getActivity(), new IPayTypeListener() {
                @Override
                public void submitPayType(boolean type) {
                    mOilType = type ? 1 : 2;
                    mTvType.setText(type ? "中石化" : "中石油");
                    mEditOil.setHint("请输入" + (type ? 19 : 16) + "位加油卡号");
                    setEditOil("");
                    setEnableCommit(false);
                    loadingFirstData();
                }
            });
        }
    }

    /**
     * 表单验证
     */
    private void formDataValidation() {
        String card = getEditOil();
        if (TextUtils.isEmpty(card)) {
            toastShort("请填写正确的卡号");
        } else if (mOilType == 1 && card.length() != 19) {
            toastShort("请填写正确的19位卡号");
        } else if (mOilType == 2 && card.length() != 16) {
            toastShort("请填写正确的16位卡号");
        } else {
            if (mPresenter != null) mPresenter.createOrder();
        }
    }

    /**
     * 数据加载成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof RefuelOilResponse) {
            RefuelOilResponse oilResponse = (RefuelOilResponse) response;
            List<RefuelOilBean> data = oilResponse.getData();
            if (mPresenter != null) mPresenter.dataDistribution(mOilType, data);
        } else
            responseError();
    }

    private void setSimpleDataResult(List<RefuelOilBean> data) {
        mAdapter.removeAllOnly();
        if (data == null || data.isEmpty()) {
            showStateEmpty();
        } else {
            mAdapter.append(data);
        }
    }

    @Override
    public void dataDistributionSucceed(List<RefuelOilBean> beanList) {
        setSimpleDataResult(beanList);
    }

    @Override
    public void dataDistributionError(Throwable throwable) {
        toastShort(throwable.getMessage());
        responseError();
    }

    @Override
    public String getRechargeMoney() {
        return String.valueOf(mRefuelOilBean.getPrice());
    }

    @Override
    public String getGoodsId() {
        return String.valueOf(mRefuelOilBean.getGoodsId());
    }

    @Override
    public String getOilCardNum() {
        return getEditOil();
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
        SPUtils.getInstance(SPUtils.FILENAME).put(SPUtils.USER_OIL_NUM, getEditOil());

        RefuelOrderBean bean = response.getData();
        if (bean != null)
            CargoRouter.gotoPayTypeActivity(getActivity(), bean.getOrderId());
        else
            toastShort("数据出错,创建订单未知错误");
    }
}