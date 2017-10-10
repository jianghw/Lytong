package com.zantong.mobilecttx.card.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.fragment.BaseExtraFragment;
import com.zantong.mobilecttx.car.adapter.PayCarAdapter;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.card.bean.BindCardBean;
import com.zantong.mobilecttx.application.MemoryData;
import com.zantong.mobilecttx.contract.ModelView;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.widght.refresh.OnPullListener;
import com.zantong.mobilecttx.widght.refresh.PullToRefreshLayout;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.Des3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;

public class MyCardFragment extends BaseExtraFragment implements ModelView {

    @Bind(R.id.card_lose_help)
    LinearLayout cardLoseHelp;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout refresh_view;

    @Bind(R.id.refreshing_title_notice)
    TextView refreshing_title_notice;
    @Bind(R.id.mine_card_car_layout)
    XRecyclerView mCarsRecyclerView;

    PayCarAdapter mPayCarAdapter;

    public static MyCardFragment newInstance() {
        return new MyCardFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.mine_card_fragment;
    }

    @Override
    public void initView(View view) {
        refresh_view.setPullDownEnable(true);
        refresh_view.setPullUpEnable(false);

        refresh_view.setOnPullListener(new OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                mPayCarAdapter.removeAll();
                getBangDingCar();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
    }

    @Override
    public void initData() {
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
        mCarsRecyclerView.noMoreLoadings();
        refreshing_title_notice.setVisibility(View.GONE);

//        mPayCarAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, Object data) {
//                PayCar payCar = (PayCar) data;
//                MemoryData.getInstance().mHashMap.put("enginenum", "*****");
//
//                List<UserCarInfoBean> userCars = MemoryData.getInstance().mServerCars;
//                for (int i = 0; i < userCars.size(); i++) {
//                    if (userCars.get(i).getCarnum().equals(payCar.getCarnum())) {
//                        if (!"".equals(userCars.get(i).getEnginenum())) {
//                            MemoryData.getInstance().mHashMap.put("enginenum", userCars.get(i).getEnginenum());
//                        }
//                        break;
//                    }
//                }
//                MemoryData.getInstance().mHashMap.put("carnum", payCar.getCarnum());
//                MemoryData.getInstance().mHashMap.put("carnumtype", payCar.getCarnumtype());
//                MemoryData.getInstance().mHashMap.put("IllegalViolationName", payCar.getCarnum());
//
//                ViolationDTO dto = new ViolationDTO();
//                dto.setCarnum(RSAUtils.strByEncryption(payCar.getCarnum(), true));
//                dto.setEnginenum(RSAUtils.strByEncryption(payCar.getEnginenum(), true));
//                dto.setCarnumtype(payCar.getCarnumtype());
//                Intent intent = new Intent(getActivity(), ViolationListActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("params", dto);
//                intent.putExtras(bundle);
//                intent.putExtra("plateNum", payCar.getCarnum());
//                startActivity(intent);
//            }
//        });
    }

    /**
     * 获取绑定车辆信息
     * cip.cfc.c002.01
     */
    private void getBangDingCar() {
        LogoutDTO dto = new LogoutDTO();
        dto.setUsrid(MemoryData.getInstance().userID);
        UserApiClient.getPayCars(ContextUtils.getContext(), dto, new CallBack<PayCarResult>() {
            @Override
            public void onSuccess(PayCarResult result) {
                try {
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                    if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
                        refreshing_title_notice.setVisibility(View.GONE);

                        List<PayCar> list = jieMi(result.getRspInfo().getUserCarsInfo());
                        mPayCarAdapter.append(list);
                    } else {
                        refreshing_title_notice.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                faildProgress();
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

    public HashMap<String, String> mapData() {
        HashMap<String, String> mHashMap = new HashMap<>();
        return mHashMap;
    }

    @OnClick({R.id.card_lose_help})
    public void onClick(View view) {
        switch (view.getId()) {
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
        }
    }

    @Override
    public void showProgress() {
        if (refreshing_title_notice != null) {
            refreshing_title_notice.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateView(Object object, int index) {
        refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
        switch (index) {
            case 1:
                BindCardBean mBindCardBean = (BindCardBean) object;
                break;
        }
    }

    @Override
    public void hideProgress() {
        refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
        refreshing_title_notice.setVisibility(View.GONE);
    }

    public void faildProgress() {
        if (refresh_view != null) {
            refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
        if (refreshing_title_notice != null) {
            refreshing_title_notice.setVisibility(View.VISIBLE);
        }
    }
}
