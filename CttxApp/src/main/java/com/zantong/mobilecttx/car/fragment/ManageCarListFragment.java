package com.zantong.mobilecttx.car.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.car.adapter.ManageCarListAdapter;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.home.bean.HomeCarResult;
import com.zantong.mobilecttx.interf.IManageCarFtyContract;
import com.zantong.mobilecttx.presenter.car.ManageCarFtyPresenter;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;

import java.util.List;

import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 车辆列表
 */
public class ManageCarListFragment extends BaseRecyclerListJxFragment<UserCarInfoBean>
        implements IManageCarFtyContract.IManageCarFtyView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private IManageCarFtyContract.IManageCarFtyPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static ManageCarListFragment newInstance() {
        return new ManageCarListFragment();
    }

    public static ManageCarListFragment newInstance(String param1, String param2) {
        ManageCarListFragment fragment = new ManageCarListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取RecyclerHeader
     */
    protected View customViewHeader() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(getActivity());
        textView.setText("可缴费罚款车辆");
        textView.setTextSize(14);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.ds_45),
                (int) getResources().getDimension(R.dimen.ds_30),
                (int) getResources().getDimension(R.dimen.ds_45),
                (int) getResources().getDimension(R.dimen.ds_30));
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    /**
     * adapter
     */
    @Override
    public BaseAdapter<UserCarInfoBean> createAdapter() {

        return new ManageCarListAdapter();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof UserCarInfoBean) {
            UserCarInfoBean bean = (UserCarInfoBean) data;
        }
    }

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    @Override
    protected void initFragmentView(View view) {

        ManageCarFtyPresenter mPresenter = new ManageCarFtyPresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);
    }

    @Override
    public void setPresenter(IManageCarFtyContract.IManageCarFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getTextNoticeInfo();
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void loadingProgress() {
        showDialogLoading();
    }

    @Override
    public void hideLoadingProgress() {
        hideDialogLoading();
    }

    @Override
    public void textNoticeInfoError(String message) {
        setSimpleDataResult(null);
        ToastUtils.toastShort(message);
    }

    @Override
    public void textNoticeInfoSucceed(HomeCarResult result) {
        List<UserCarInfoBean> userCarInfoBeen = result.getData();
        setSimpleDataResult(userCarInfoBeen);
    }
}