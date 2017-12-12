package com.zantong.mobilecttx.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.car.activity.CarChooseActivity;
import com.zantong.mobilecttx.car.adapter.CarChooseXiAdapter;
import com.zantong.mobilecttx.car.bean.CarLinkageResponse;
import com.zantong.mobilecttx.car.bean.CarXiBean;
import com.zantong.mobilecttx.car.dto.CarLinkageDTO;

import java.util.List;

public class CarChooseXiFragment extends RecyclerListFragment<CarXiBean> {

    public static CarChooseXiFragment newInstance(int typeId) {
        CarChooseXiFragment fragment = new CarChooseXiFragment();
        Bundle args = new Bundle();
        args.putInt("id", typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BaseAdapter<CarXiBean> createAdapter() {
        return new CarChooseXiAdapter();
    }

    @Override
    protected void initPresenter() {}

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        CarXiBean carXiBean = (CarXiBean) data;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CarChooseActivity.CAR_LINE_BEAN, carXiBean);
        intent.putExtras(bundle);
        getActivity().setResult(CarChooseActivity.RESULT_L_CODE, intent);
        getActivity().finish();
    }

    @Override
    protected void loadingFirstData() {
        carXingList(getArguments().getInt("id", 0));
    }

    private void carXingList(int id) {
        CarLinkageDTO carLinkageDTO = new CarLinkageDTO();
        carLinkageDTO.setModelsId("");
        carLinkageDTO.setSeriesId("");
        carLinkageDTO.setBrandId(String.valueOf(id));

        CarApiClient.liYingCarLinkage(Utils.getContext(), carLinkageDTO,
                new CallBack<CarLinkageResponse>() {
                    @Override
                    public void onSuccess(CarLinkageResponse result) {
                        if (result.getResponseCode() == 2000) {
                            responseSucceed(result);
                        } else {
                            responseError();
                        }
                    }

                    @Override
                    public void onError(String errorCode, String msg) {
                        responseError(msg);
                    }
                });
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof CarLinkageResponse) {
            CarLinkageResponse couponResponse = (CarLinkageResponse) response;
            CarLinkageResponse.CarInfo carInfo = couponResponse.getData();
            List<CarXiBean> couponList = null;
            if (carInfo != null) couponList = carInfo.getSeries();
            setSimpleDataResult(couponList);
        } else
            responseError();
    }
}
