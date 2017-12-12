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
import com.zantong.mobilecttx.car.adapter.CarChooseXingAdapter;
import com.zantong.mobilecttx.car.bean.CarLinkageResponse;
import com.zantong.mobilecttx.car.bean.CarStyleInfoBean;
import com.zantong.mobilecttx.car.dto.CarLinkageDTO;

import java.util.List;

public class CarChooseXingFragment extends RecyclerListFragment<CarStyleInfoBean> {

    private int id;
    private int idB;

    public static CarChooseXingFragment newInstance(int typeId, int idB) {
        CarChooseXingFragment f = new CarChooseXingFragment();
        Bundle args = new Bundle();
        args.putInt("id", typeId);
        args.putInt("idB", idB);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        CarStyleInfoBean carStyleBean = (CarStyleInfoBean) data;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CarChooseActivity.CAR_XING_BEAN, carStyleBean);
        intent.putExtras(bundle);
        getActivity().setResult(CarChooseActivity.RESULT_X_CODE, intent);
        getActivity().finish();
    }

    @Override
    protected void initPresenter() {}

    @Override
    public BaseAdapter<CarStyleInfoBean> createAdapter() {
        return new CarChooseXingAdapter();
    }

    @Override
    protected void loadingFirstData() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id", 0);
        idB = bundle.getInt("idB", 0);
        carXingList();
    }

    private void carXingList() {
        CarLinkageDTO carLinkageDTO = new CarLinkageDTO();
        carLinkageDTO.setModelsId("");
        carLinkageDTO.setBrandId(String.valueOf(idB));
        carLinkageDTO.setSeriesId(String.valueOf(id));

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
            List<CarStyleInfoBean> couponList = null;
            if (carInfo != null) couponList = carInfo.getCarModels();
            setSimpleDataResult(couponList);
        } else
            responseError();
    }
}
