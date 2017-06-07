package com.zantong.mobilecttx.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zantong.mobilecttx.car.adapter.CarChooseXiAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.car.bean.CarLinkageResult;
import com.zantong.mobilecttx.car.bean.CarXiBean;
import com.zantong.mobilecttx.car.dto.CarLinkageDTO;
import com.zantong.mobilecttx.car.activity.AddCarActivity;
import com.zantong.mobilecttx.car.activity.CarChooseActivity;

public class CarChooseXiFragment extends BaseListFragment<CarXiBean> {

    public static CarChooseXiFragment newInstance(int typeId) {
        CarChooseXiFragment f = new CarChooseXiFragment();
        Bundle args = new Bundle();
        args.putInt("id", typeId);
        f.setArguments(args);
        return f;
    }

    private int id;

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getArguments();
        id = bundle.getInt("id", 0);
        carXingList(id);
    }

    private void carXingList(int id) {
        CarLinkageDTO carLinkageDTO = new CarLinkageDTO();
        carLinkageDTO.setModelsId("");
        carLinkageDTO.setSeriesId("");
        carLinkageDTO.setBrandId(String.valueOf(id));
        CarApiClient.liYingCarLinkage(getActivity(), carLinkageDTO, new CallBack<CarLinkageResult>() {
            @Override
            public void onSuccess(CarLinkageResult result) {
                if(result.getResponseCode() == 2000) {
                    setDataResult(result.getData().getSeries());
                }
            }
        });
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {
        carXingList(id);
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        CarXiBean carXiBean = (CarXiBean) data;
        Intent intent = new Intent(getActivity(), AddCarActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CarChooseActivity.CAR_LINE_BEAN, carXiBean);
        intent.putExtras(bundle);
        getActivity().setResult(CarChooseActivity.RESULT_L_CODE, intent);
        getActivity().finish();
    }

    @Override
    public BaseAdapter<CarXiBean> createAdapter() {
        return new CarChooseXiAdapter();
    }
}
