package com.zantong.mobilecttx.car.fragment;

import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.car.adapter.CarChooseXingAdapter;
import com.zantong.mobilecttx.car.bean.CarLinkageResult;
import com.zantong.mobilecttx.car.bean.CarStyleInfoBean;
import com.zantong.mobilecttx.car.dto.CarLinkageDTO;

public class CarChooseXingFragment extends BaseListFragment<CarStyleInfoBean> {

    public static CarChooseXingFragment newInstance(int typeId, int idB) {
        CarChooseXingFragment f = new CarChooseXingFragment();
        Bundle args = new Bundle();
        args.putInt("id", typeId);
        args.putInt("idB", idB);
        f.setArguments(args);
        return f;
    }

    private int id;
    private int idB;

    @Override
    public void initData() {
        super.initData();
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
        CarApiClient.liYingCarLinkage(getActivity(), carLinkageDTO, new CallBack<CarLinkageResult>() {
            @Override
            public void onSuccess(CarLinkageResult result) {
                if(result.getResponseCode() == 2000){
                    setDataResult(result.getData().getCarModels());
                }
            }
        });
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {
        carXingList();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {

    }

    @Override
    public BaseAdapter<CarStyleInfoBean> createAdapter() {
        return new CarChooseXingAdapter();
    }
}
