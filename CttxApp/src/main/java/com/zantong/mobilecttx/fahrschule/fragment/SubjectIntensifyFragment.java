package com.zantong.mobilecttx.fahrschule.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectIntensifyContract;
import com.zantong.mobilecttx.contract.fahrschule.ISubjectSwitcherListener;
import com.zantong.mobilecttx.eventbus.SubjectCommitEvent;
import com.zantong.mobilecttx.fahrschule.adapter.SubjectGoodsAdapter;
import cn.qqtheme.framework.bean.response.SubjectGoodsBean;
import cn.qqtheme.framework.bean.response.SubjectGoodsData;
import cn.qqtheme.framework.bean.response.SubjectGoodsResponse;
import com.zantong.mobilecttx.presenter.fahrschule.SubjectIntensifyPresenter;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 科目强化页面
 */
public class SubjectIntensifyFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, ISubjectIntensifyContract.ISubjectIntensifyView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private XRecyclerView mCustomRecycler;
    private TextView mTvContent;
    private TextView mTvPrice;
    /**
     * 选择套餐
     */
    private TextView mTvCommit;

    /**
     * mPresenter
     */
    private ISubjectIntensifyContract.ISubjectIntensifyPresenter mPresenter;

    private ISubjectSwitcherListener mSwitcherListener;
    private SubjectGoodsAdapter mAdapter;
    private SubjectGoodsData.RemarkBean mRemarkBean;

    public static SubjectIntensifyFragment newInstance() {
        return new SubjectIntensifyFragment();
    }

    public static SubjectIntensifyFragment newInstance(String param1, String param2) {
        SubjectIntensifyFragment fragment = new SubjectIntensifyFragment();
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

    /**
     * 不下拉刷新
     */
    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_subject_intensify;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        SubjectIntensifyPresenter mPresenter = new SubjectIntensifyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);

        GridLayoutManager manager = new GridLayoutManager(ContextUtils.getContext(), 2);
        mCustomRecycler.setLayoutManager(manager);
        mCustomRecycler.setPullRefreshEnabled(false);
        mCustomRecycler.setLoadingMoreEnabled(false);
        mCustomRecycler.noMoreLoadings();

        mAdapter = new SubjectGoodsAdapter();
        mCustomRecycler.setAdapter(mAdapter);
//点击事件
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (data instanceof SubjectGoodsBean) {
                    dataListShow((SubjectGoodsBean) data);
                }
            }
        });
    }

    protected void dataListShow(SubjectGoodsBean goodsBean) {
        ArrayList<SubjectGoodsBean> beanArrayList = mAdapter.getAll();
        SubjectGoodsBean.GoodsBean goodsBeanGoods = goodsBean.getGoods();

        for (SubjectGoodsBean bean : beanArrayList) {
            bean.setChoice(goodsBeanGoods.getGoodsId() == bean.getGoods().getGoodsId());

            if (goodsBeanGoods.getGoodsId() == bean.getGoods().getGoodsId()) {
                displayDescription(goodsBean);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void displayDescription(SubjectGoodsBean goodsBean) {
        mTvContent.setText(goodsBean.getGoods().getDescription());
        String valueString = new DecimalFormat("#0.00").format(goodsBean.getGoods().getPrice());
        mTvPrice.setText(valueString);
    }

    @Override
    public void setPresenter(ISubjectIntensifyContract.ISubjectIntensifyPresenter presenter) {
        mPresenter = presenter;
    }

    public void setSwitcherListener(ISubjectSwitcherListener switcherListener) {
        mSwitcherListener = switcherListener;
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getGoods();
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mCustomRecycler = (XRecyclerView) view.findViewById(R.id.custom_recycler);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
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
        ArrayList<SubjectGoodsBean> beanArrayList = mAdapter.getAll();

        for (SubjectGoodsBean bean : beanArrayList) {
            if (bean.isChoice()) {
                EventBus.getDefault().postSticky(new SubjectCommitEvent(bean,mRemarkBean));
                if (mSwitcherListener != null) mSwitcherListener.setCurPosition(1);
                return;
            }
        }
        ToastUtils.toastShort("请选择套餐~");
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
     * 获取商品失败
     */
    @Override
    public void getGoodsError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort("获取数据失败，请下拉刷新" + message);
    }

    @Override
    public void getGoodsSucceed(SubjectGoodsResponse result) {
        SubjectGoodsData subjectGoodsData = result.getData();
        if (subjectGoodsData == null) return;
        mRemarkBean =subjectGoodsData.getRemark();
        List<SubjectGoodsBean> beanList = subjectGoodsData.getGoodsList();

        if (beanList != null && !beanList.isEmpty()) {
            beanList.get(0).setChoice(true);
            displayDescription(beanList.get(0));
        }

        mAdapter.removeAllOnly();
        mAdapter.append(beanList);
    }

}
