package com.zantong.mobilecttx.card.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
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
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.interf.ModelView;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.utils.AmountUtils;
import com.zantong.mobilecttx.utils.PullToRefreshLayout;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.ViolationResultAcitvity;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MyCardFragment extends BaseExtraFragment implements ModelView {

    @Bind(R.id.card_lose_help)
    LinearLayout cardLoseHelp;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout refresh_view;
    @Bind(R.id.bind_card_number_four)
    TextView bind_card_number_four;
    @Bind(R.id.bind_card_cen)
    TextView bind_card_cen;
    @Bind(R.id.bind_card_money)
    TextView bind_card_money;
    @Bind(R.id.refreshing_title_notice)
    TextView refreshing_title_notice;
    @Bind(R.id.mine_card_car_layout)
    XRecyclerView mCarsRecyclerView;

    PayCarAdapter mPayCarAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.mine_card_fragment;
    }

    @Override
    public void initView(View view) {
        refresh_view.setPullUpEnable(false);
        refresh_view.autoRefresh();
        refresh_view.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //mBindCardPresenter.loadView(1);
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
        mPayCarAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                PayCar payCar = (PayCar)data;
                PublicData.getInstance().mHashMap.put("enginenum","*****");

                List<UserCarInfoBean> userCars = PublicData.getInstance().mServerCars;
                for (int i = 0; i < userCars.size(); i++){
                    if (userCars.get(i).getCarnum().equals(payCar.getCarnum())){
                        if (!"".equals(userCars.get(i).getEnginenum())){
                            PublicData.getInstance().mHashMap.put("enginenum",userCars.get(i).getEnginenum());
                        }
                        break;
                    }
                }
                PublicData.getInstance().mHashMap.put("carnum",payCar.getCarnum());
                PublicData.getInstance().mHashMap.put("carnumtype",payCar.getCarnumtype());
                PublicData.getInstance().mHashMap.put("IllegalViolationName",payCar.getCarnum());

                ViolationDTO dto = new ViolationDTO();
                dto.setCarnum(RSAUtils.strByEncryption(MyCardFragment.this.getActivity(),payCar.getCarnum(),true));
                dto.setEnginenum(RSAUtils.strByEncryption(MyCardFragment.this.getActivity(),payCar.getEnginenum(),true));
                dto.setCarnumtype(payCar.getCarnumtype());
                Intent intent = new Intent(MyCardFragment.this.getActivity(),ViolationResultAcitvity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("params",dto);
                intent.putExtras(bundle);
                intent.putExtra("plateNum",payCar.getCarnum());
                startActivity(intent);
            }
        });
    }

    /**
     * 获取绑定车辆信息
     */
    private void getBangDingCar(){
        LogoutDTO dto = new LogoutDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        UserApiClient.getPayCars(this.getActivity(), dto, new CallBack<PayCarResult>() {
            @Override
            public void onSuccess(PayCarResult result) {
                try {
                    refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
                    if(result.getSYS_HEAD().getReturnCode().equals("000000")){
                        refreshing_title_notice.setVisibility(View.GONE);
                        List<PayCar> list = jieMi(result.getRspInfo().getUserCarsInfo());
                        mPayCarAdapter.append(list);
                    }else{
                        refreshing_title_notice.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                faildProgress();
            }
        });
    }

    //解密
    private List<PayCar> jieMi(List<PayCar> list){
        List<PayCar> payCarList = new ArrayList<PayCar>();
        for (PayCar payCar:list){
            payCar.setCarnum(Des3.decode(payCar.getCarnum()));
            payCar.setEnginenum(Des3.decode(payCar.getEnginenum()));
            payCarList.add(payCar);
        }
        return payCarList;
    }

    public HashMap<String, String> mapData(){
        HashMap<String, String>  mHashMap = new HashMap<>();
        return mHashMap;
    }

    @OnClick({ R.id.card_lose_help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_lose_help:
                if (!Tools.hasSimCard(MyCardFragment.this.getActivity())){
//                    Log.d(TAG,"请确认sim卡是否插入或者sim卡暂时不可用！");
                    ToastUtils.showShort(MyCardFragment.this.getActivity(), "请确认sim卡是否插入或者sim卡暂时不可用！");
                }else{
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:95588"));
                    startActivity(intent);
                }

                break;
        }
    }

    @Override
    public void showProgress() {
        if(refreshing_title_notice != null){
            refreshing_title_notice.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateView(Object object, int index) {
        refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
        switch (index){
            case 1:
                BindCardBean mBindCardBean = (BindCardBean) object;
                if(Tools.isStrEmpty(mBindCardBean.getRspInfo().getTotcent())){
                    bind_card_cen.setText("0 分");
                }else{
                    bind_card_cen.setText(mBindCardBean.getRspInfo().getTotcent()+"分");
                }
                if(Tools.isStrEmpty(mBindCardBean.getRspInfo().getTotamt())){
                    bind_card_money.setText("0.00 元");
                }else{
                    long money = Long.parseLong(mBindCardBean.getRspInfo().getTotamt());

                    try {
                        bind_card_money.setText(AmountUtils.changeF2Y(money)+"元");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            break;
        }

    }

    @Override
    public void hideProgress() {
        refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
        refreshing_title_notice.setVisibility(View.GONE);
    }

    public void faildProgress(){
        if(refresh_view != null){
            refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
        if(refreshing_title_notice != null){
            refreshing_title_notice.setVisibility(View.VISIBLE);
        }
    }
}
