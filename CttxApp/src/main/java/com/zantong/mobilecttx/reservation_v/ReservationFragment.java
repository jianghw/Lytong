package com.zantong.mobilecttx.reservation_v;

import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.java.response.reservation.ReservationResponse;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.reservation_p.IReservationContract;
import com.zantong.mobilecttx.reservation_p.ReservationAdapter;
import com.zantong.mobilecttx.reservation_p.ReservationPresenter;

import java.util.List;

/**
 * 预约列表
 */
public class ReservationFragment extends RecyclerListFragment<ReservationResponse.DataBean>
        implements IReservationContract.IReservationView {


    private IReservationContract.IReservationPresenter mPresenter;

    public static ReservationFragment newInstance() {
        return new ReservationFragment();
    }


    @Override
    public BaseAdapter<ReservationResponse.DataBean> createAdapter() {
        return new ReservationAdapter();
    }

    @Override
    protected void initPresenter() {
        ReservationPresenter presenter = new ReservationPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    public void setPresenter(IReservationContract.IReservationPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 点击 bean
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
//        if (data != null && data instanceof ReservationResponse.DataBean) {
//            ReservationResponse.DataBean dataBean = (ReservationResponse.DataBean) data;
//        }
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getBespeakList();
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
        if (response instanceof ReservationResponse) {
            ReservationResponse reservationResponse = (ReservationResponse) response;
            List<ReservationResponse.DataBean> beanList = reservationResponse.getData();
            setSimpleDataResult(beanList);
        } else
            responseError();
    }

}