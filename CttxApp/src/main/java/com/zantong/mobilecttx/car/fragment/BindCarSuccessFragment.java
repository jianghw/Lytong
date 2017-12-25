package com.zantong.mobilecttx.car.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.ctcyh.router.util.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.fragment.BaseExtraFragment;
import com.zantong.mobilecttx.car.activity.ManageCarActivity;
import com.zantong.mobilecttx.car.adapter.PayCarAdapter;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class BindCarSuccessFragment extends BaseExtraFragment {

    PayCarAdapter mPayCarAdapter;

    @Bind(R.id.bind_card_succ_list)
    XRecyclerView mCarsRecyclerView;
    @Bind(R.id.bind_card_success_empty)
    TextView mEmptyView;
    @Bind(R.id.bind_card_success_desc)
    TextView mDescView;
    @Bind(R.id.bind_card_success_carmanager)
    TextView mCarManager;
    @Bind(R.id.bind_card_success_finish)
    TextView mFinish;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_bind_card_success;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initData() {
        getData();
    }

    protected void getData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCarsRecyclerView.setLayoutManager(layoutManager);
        mCarsRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mCarsRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mCarsRecyclerView.setArrowImageView(R.mipmap.loading);
        mCarsRecyclerView.setLoadingMoreEnabled(false);
        mCarsRecyclerView.setPullRefreshEnabled(false);
        mPayCarAdapter = new PayCarAdapter();
        mCarsRecyclerView.setAdapter(mPayCarAdapter);
        LogoutDTO dto = new LogoutDTO();
        dto.setUsrid(MainRouter.getUserID());
        onShowLoading();

        UserApiClient.getPayCars(this.getActivity(), dto, new CallBack<PayCarResult>() {
            @Override
            public void onSuccess(PayCarResult result) {
                onShowContent();

                List<PayCar> list = result.getRspInfo().getUserCarsInfo();
                if (list == null || list.size() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    mCarManager.setVisibility(View.GONE);
                    mCarsRecyclerView.setVisibility(View.GONE);
                    mDescView.setText("按交管局规定，每张畅通卡可为2辆绑定车辆缴付罚款，修改绑定车辆要12小时后才能生效。为保障您及时处理违章，建议现在就绑定您的爱车。");
                } else if (list.size() == 1) {
                    mPayCarAdapter.append(jieMi(list));
                    mDescView.setText("按交管局规定，每张畅通卡可为2辆绑定车辆缴付罚款，修改绑定车辆要12小时后才能生效。为保障您及时处理违章，建议现在就绑定您的爱车。");
                } else if (list.size() == 2) {
                    mPayCarAdapter.append(jieMi(list));
                    mDescView.setText("按交管局规定，每张畅通卡可为2辆绑定车辆缴付罚款，修改绑定车辆要12小时后才能生效。");
                }
            }
        });
    }

    //解密
    private List<PayCar> jieMi(List<PayCar> list) {
        List<PayCar> payCarList = new ArrayList<>();
        for (PayCar payCar : list) {
            payCar.setCarnum(Des3.decode(payCar.getCarnum()));
            payCar.setEnginenum(Des3.decode(payCar.getEnginenum()));
            payCarList.add(payCar);
        }
        return payCarList;
    }

    @OnClick({R.id.bind_card_success_carmanager, R.id.bind_card_success_finish})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bind_card_success_carmanager:
                Act.getInstance().gotoIntentLogin(getActivity(), ManageCarActivity.class);
                break;
            case R.id.bind_card_success_finish:
                getActivity().finish();
                break;
        }
    }

    public static BindCarSuccessFragment newInstance() {
        return new BindCarSuccessFragment();
    }
}
