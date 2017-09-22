package com.zantong.mobilecttx.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.car.activity.CarChooseActivity;
import com.zantong.mobilecttx.car.adapter.CarChooseXiAdapter;
import com.zantong.mobilecttx.car.bean.CarLinkageResult;
import com.zantong.mobilecttx.car.bean.CarXiBean;
import com.zantong.mobilecttx.car.dto.CarLinkageDTO;

import com.tzly.annual.base.util.ContextUtils;

public class CarChooseXiFragment extends BaseRecyclerListJxFragment<CarXiBean> {

    private int id;

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
    protected void initFragmentView(View view) {
        Bundle bundle = getArguments();
        id = bundle.getInt("id", 0);
        carXingList(id);
    }

    private void carXingList(int id) {
        CarLinkageDTO carLinkageDTO = new CarLinkageDTO();
        carLinkageDTO.setModelsId("");
        carLinkageDTO.setSeriesId("");
        carLinkageDTO.setBrandId(String.valueOf(id));
        CarApiClient.liYingCarLinkage(ContextUtils.getContext(), carLinkageDTO,
                new CallBack<CarLinkageResult>() {
                    @Override
                    public void onSuccess(CarLinkageResult result) {
                        if (result.getResponseCode() == 2000) {
                            setDataResult(result.getData().getSeries());
                        }
                    }
                });
    }

    @Override
    protected void onFirstDataVisible() {
    }

    @Override
    protected void onRefreshData() {
        carXingList(id);
    }

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
    protected void DestroyViewAndThing() {

    }
}
