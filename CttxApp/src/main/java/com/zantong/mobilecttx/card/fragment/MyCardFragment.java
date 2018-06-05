package com.zantong.mobilecttx.card.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.java.response.card.CancelCardResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.rea.Des3;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Tools;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.car.adapter.PayCarAdapter;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.dto.LogoutDTO;

import java.util.ArrayList;
import java.util.List;

public class MyCardFragment extends RefreshFragment implements View.OnClickListener {


    PayCarAdapter mPayCarAdapter;

    private RecyclerView mCarsRecyclerView;
    private ImageView mImgRight;
    private LinearLayout mCardLoseHelp;
    /**
     * 畅通卡解绑
     */
    private Button mBtnCommit;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    public static MyCardFragment newInstance() {
        return new MyCardFragment();
    }

    @Override
    protected int fragmentView() {
        return R.layout.mine_card_fragment;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCarsRecyclerView.setLayoutManager(layoutManager);

        mPayCarAdapter = new PayCarAdapter();
        mCarsRecyclerView.setAdapter(mPayCarAdapter);
    }

    public void initView(View view) {
        mCarsRecyclerView = (RecyclerView) view.findViewById(R.id.mine_card_car_layout);
        mImgRight = (ImageView) view.findViewById(R.id.img_right);

        mCardLoseHelp = (LinearLayout) view.findViewById(R.id.card_lose_help);
        mCardLoseHelp.setOnClickListener(this);
        mBtnCommit = (Button) view.findViewById(R.id.btn_commit);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    protected void loadingFirstData() {
        getBangDingCar();
    }

    /**
     * 获取绑定车辆信息
     * cip.cfc.c002.01
     */
    private void getBangDingCar() {
        mPayCarAdapter.removeAll();

        LogoutDTO dto = new LogoutDTO();
        dto.setUsrid(MainRouter.getUserID());
        UserApiClient.getPayCars(Utils.getContext(), dto, new CallBack<PayCarResult>() {
            @Override
            public void onSuccess(PayCarResult result) {
                if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
                    List<PayCar> list = jieMi(result.getRspInfo().getUserCarsInfo());
                    mPayCarAdapter.append(list);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                ToastUtils.toastShort(msg);
            }
        });
    }
    //解密

    private List<PayCar> jieMi(List<PayCar> list) {
        List<PayCar> payCarList = new ArrayList<PayCar>();
        for (PayCar payCar : list) {
            payCar.setCarnum(Des3.decode(payCar.getCarnum()));
            payCar.setEnginenum(Des3.decode(payCar.getEnginenum()));
            payCarList.add(payCar);
        }
        return payCarList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_lose_help:
                if (!Tools.hasSimCard(MyCardFragment.this.getActivity())) {
                    ToastUtils.toastShort("请确认sim卡是否插入或者sim卡暂时不可用！");
                } else {
                    //用intent启动拨打电话
                    Intent intent = new Intent(); // 意图对象：动作 + 数据
                    intent.setAction(Intent.ACTION_DIAL); // 设置动作
                    Uri data = Uri.parse("tel:" + "95588"); // 设置数据
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            case R.id.btn_commit:
                uBindCard();
                break;
            default:
                break;
        }
    }

    private void uBindCard() {
        UserApiClient.cancelCard(Utils.getContext(), 2, new CallBack<CancelCardResponse>() {
            @Override
            public void onSuccess(CancelCardResponse result) {
                if (result != null && result.getResponseCode() == 2000) {
                    ToastUtils.toastShort("注销成功");
                    getActivity().finish();
                } else {
                    ToastUtils.toastShort("注销失败");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                ToastUtils.toastShort(msg);
            }
        });
    }
}
