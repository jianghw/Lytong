package com.zantong.mobilecttx.payment_v;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.payment_p.IPaymentContract;
import com.zantong.mobilecttx.payment_p.PaymentAdapter;
import com.zantong.mobilecttx.payment_p.PaymentPresenter;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.bean.RspInfoBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.util.ArrayList;
import java.util.List;

import drawthink.expandablerecyclerview.bean.RecyclerViewData;
import drawthink.expandablerecyclerview.listener.OnRecyclerViewListener;

/**
 * 加油充值
 */
public class PaymentFragment extends RefreshFragment
        implements IPaymentContract.IPaymentView, View.OnClickListener,
        OnRecyclerViewListener.OnItemClickListener {

    private static final String FILE_NUM_DOT = "FILE_NUM_DOT";
    /**
     * 控制器p
     */
    private IPaymentContract.IPaymentPresenter mPresenter;

    private TextView mTvPrice;
    /**
     * 总缴费
     */
    private TextView mTvTotamt;
    /**
     * 元
     */
    private TextView mTvUnit;
    /**
     * 12个月
     */
    private TextView mTvMonth;
    /**
     * 车辆总数:
     */
    private TextView mTvCarTitle;
    /**
     * 0
     */
    private TextView mTvCarCount;
    private RecyclerView mRecyclerview;
    private PaymentAdapter mAdapter;

    private List<RspInfoBean.ViolationInfoBean> mTempDatas = new ArrayList<>();
    private List<RecyclerViewData> mCarDatas = new ArrayList<>();
    private TextView mTvEmpty;

    public static PaymentFragment newInstance(LicenseFileNumDTO numDTO) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putParcelable(FILE_NUM_DOT, numDTO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentView() {
        return R.layout.main_fragment_payment;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        PaymentPresenter presenter = new PaymentPresenter(
                Injection.provideRepository(Utils.getContext()), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setNestedScrollingEnabled(false);

        mAdapter = new PaymentAdapter(getActivity(), mCarDatas);
        mAdapter.setOnItemClickListener(this);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void initView(View view) {
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvTotamt = (TextView) view.findViewById(R.id.tv_totamt);
        mTvUnit = (TextView) view.findViewById(R.id.tv_unit);
        mTvMonth = (TextView) view.findViewById(R.id.tv_month);
        mTvMonth.setOnClickListener(this);
        mTvCarTitle = (TextView) view.findViewById(R.id.tv_carTitle);
        mTvCarCount = (TextView) view.findViewById(R.id.tv_carCount);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.rv_car);
        mTvEmpty = (TextView) view.findViewById(R.id.tv_empty);
    }

    /**
     * 是否可刷新
     */
    protected boolean isRefresh() {
        return true;
    }

    @Override
    public void setPresenter(IPaymentContract.IPaymentPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.bank_v001_01();
    }

    @Override
    public LicenseFileNumDTO licenseFileNumDTO() {
        return getArguments().getParcelable(FILE_NUM_DOT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (!mCarDatas.isEmpty()) mCarDatas.clear();
        if (!mTempDatas.isEmpty()) mTempDatas.clear();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof LicenseResponseBean) {
            LicenseResponseBean responseBean = (LicenseResponseBean) response;
            RspInfoBean rspInfo = responseBean.getRspInfo();
            dataOpenUp(rspInfo);
        } else
            responseError();
    }

    private void dataOpenUp(RspInfoBean rspInfo) {
        if (rspInfo != null && rspInfo.getViolationInfo() != null && !rspInfo.getViolationInfo().isEmpty()) {
            mTvPrice.setText(String.valueOf(rspInfo.getTotamt() / 100));
            mTvMonth.setText("12个月");
            mTvCarCount.setText(rspInfo.getTotcarcount());

            //数据备份
            if (!mTempDatas.isEmpty()) mTempDatas.clear();
            mTempDatas.addAll(rspInfo.getViolationInfo());
            //数据处理
            dataByDate();
        } else {
            showEmptyData(true);
        }
    }

    /**
     * 根据日期过滤数据
     */
    private void dataByDate() {
        if (mPresenter != null)
            mPresenter.dataProcessing(mTempDatas, mTvMonth.getText().toString());
    }

    private void showEmptyData(boolean b) {
        mTvEmpty.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    /**
     * 数据成功
     */
    @Override
    public void processingSucceed(List<RecyclerViewData> dataList) {
        showEmptyData(dataList == null || dataList.isEmpty());

        if (!mCarDatas.isEmpty()) mCarDatas.clear();
        if (dataList != null) mCarDatas.addAll(dataList);
        //在对元数据mDatas进行增删操作时
        mAdapter.notifyRecyclerViewData();
    }

    /**
     * 数据处理失败
     */
    @Override
    public void dataProcessingError(String message) {
        toastShort(message);
        showEmptyData(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_month)
            showPopWindow();
    }

    /**
     * 显示popupWindow
     */
    private void showPopWindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) return;
        View view = inflater.inflate(R.layout.popwindow_date, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        final PopupWindow window = new PopupWindow(view, mTvMonth.getWidth(),
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 在底部显示
        window.showAsDropDown(mTvMonth, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        Button first = (Button) view.findViewById(R.id.popwindow_first);
        Button second = (Button) view.findViewById(R.id.popwindow_second);
        Button third = (Button) view.findViewById(R.id.popwindow_third);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvMonth.setText("12个月");
                dataByDate();
                window.dismiss();
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvMonth.setText("6个月");
                dataByDate();
                window.dismiss();

            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvMonth.setText("3个月");
                dataByDate();
                window.dismiss();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });
    }

    /**
     * 父类控件 点击
     */
    @Override
    public void onGroupItemClick(int position, int groupPosition, View view) {
        TextView status = (TextView) view.findViewById(R.id.tv_status);
        ImageView arrow = (ImageView) view.findViewById(R.id.img_arrow);

        if (status.getText().equals("详情")) {
            arrow.setImageResource(R.mipmap.arrow_up);
            status.setText("收起");
        } else {
            arrow.setImageResource(R.mipmap.arrow_down);
            status.setText("详情");
        }
    }

    @Override
    public void onChildItemClick(int position, int groupPosition, int childPosition, View view) {
    }
}