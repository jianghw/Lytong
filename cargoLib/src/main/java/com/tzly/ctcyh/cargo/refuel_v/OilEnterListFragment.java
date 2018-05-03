package com.tzly.ctcyh.cargo.refuel_v;

import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IOilEnterContract;
import com.tzly.ctcyh.cargo.refuel_p.IRouterStatisticsId;
import com.tzly.ctcyh.cargo.refuel_p.OilEnterListAdapter;
import com.tzly.ctcyh.cargo.refuel_p.OilEnterPresenter;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 加油新列表
 */
public class OilEnterListFragment extends RecyclerListFragment<OilModuleResponse.DataBean>
        implements View.OnClickListener, IOilEnterContract.IOilEnterView, IRouterStatisticsId {


    private IOilEnterContract.IOilEnterPresenter mPresenter;

    public static OilEnterListFragment newInstance() {
        return new OilEnterListFragment();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void countError(String message) {

    }

    @Override
    public void countSucceed(OilEnterResponse response) {

    }

    @Override
    public void OilModuleError(String message) {

    }

    @Override
    public void OilModuleSucceed(OilModuleResponse response) {

    }

    @Override
    public void setPresenter(IOilEnterContract.IOilEnterPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseAdapter<OilModuleResponse.DataBean> createAdapter() {
        return new OilEnterListAdapter(OilEnterListFragment.this);
    }

    /**
     * 点击
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data != null && data instanceof OilModuleResponse.DataBean) {
            OilModuleResponse.DataBean dataBean = (OilModuleResponse.DataBean) data;

            gotoByStatistId(dataBean.getTargetPath(), dataBean.getTitle(),
                    dataBean.getStatisticsId());
        }
    }

    @Override
    protected void initPresenter() {
        OilEnterPresenter presenter = new OilEnterPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getOilModuleList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    /**
     * 数据处理
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof OilModuleResponse) {
            OilModuleResponse oilModuleResponse = (OilModuleResponse) response;
            List<OilModuleResponse.DataBean> beanList = oilModuleResponse.getData();
            setSimpleDataResult(beanList);
        } else
            responseError();
    }

    @Override
    public void gotoByStatistId(String url, String title, int statisticsId) {
        CargoRouter.gotoCustomerService(
                url, title, String.valueOf(statisticsId),
                getActivity());
    }
}