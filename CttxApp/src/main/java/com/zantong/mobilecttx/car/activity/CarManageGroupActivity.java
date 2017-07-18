package com.zantong.mobilecttx.car.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.adapter.superadapter.DataHolder;
import com.zantong.mobilecttx.base.adapter.superadapter.LayoutWrapper;
import com.zantong.mobilecttx.base.adapter.superadapter.SuperAdapter;
import com.zantong.mobilecttx.base.adapter.superadapter.SuperViewHolder;
import com.zantong.mobilecttx.base.bean.SuperBean;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.AddCarInfoEvent;
import com.zantong.mobilecttx.eventbus.BenDiCarInfoEvent;
import com.zantong.mobilecttx.eventbus.EditCarInfoEvent;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.Des3;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 车辆管理
 */
public class CarManageGroupActivity extends BaseMvpActivity<IBaseView, HelpPresenter>
        implements View.OnClickListener, IBaseView {

    @Bind(R.id.manage_cars_loading)
    TextView mLoadingView;
    @Bind(R.id.manage_cars_empty_layout)
    View mEmptyView;
    @Bind(R.id.context_all)
    LinearLayout contextAll;
    @Bind(R.id.vehicles_change_car_btn)
    TextView mChangePayCar;
    @Bind(R.id.manage_cars_recyclerview)
    XRecyclerView mXRecyclerView;

    List<UserCarInfoBean> mServerList = new ArrayList<>();
    SuperAdapter adapter;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_manage_cars;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void initView() {
        setTitleText("车辆管理");
        setEnsureImg(R.mipmap.icon_addcar);

        AddCarActivity.isFrom = false;
    }

    @Override
    public void initData() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getEnsureView().getLayoutParams();
        lp.width = 36;
        lp.height = 36;
        lp.setMargins(0, 0, 20, 0);//左上右下
        getEnsureView().setLayoutParams(lp);
        getEnsureView().setBackgroundResource(R.mipmap.icon_add_car);
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int[] layoutIds = {R.layout.item_manage_vehicles, R.layout.item_manage_vehicles_group};

        adapter = new SuperAdapter(this, layoutIds);
        mXRecyclerView.setAdapter(adapter);

        getCarsInfo();
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                adapter.removeAll();
                getCarsInfo();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @OnClick(R.id.vehicles_change_car_btn)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Act.getInstance().gotoIntent(this, SetPayCarActivity.class);
    }

    @Override
    protected void baseGoEnsure() {
        if (mLoadingView.getVisibility() == View.VISIBLE) {
            ToastUtils.showShort(getApplicationContext(), "数据加载中,请稍后再点击");
        } else if (mServerList.size() < 3) {
            Act.getInstance().gotoIntent(this, AddCarActivity.class);
        } else {
            DialogUtils.createDialog(this, "您的车辆数量已达上限（最多3辆）");
        }
    }

    private void getCarsInfo() {
        mLoadingView.setVisibility(View.VISIBLE);

        if (!PublicData.getInstance().loginFlag) {
            List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
            testSuperAdapter(list);
        } else {
            UserCarsDTO params = new UserCarsDTO();
            params.setUsrid(PublicData.getInstance().userID);
            UserApiClient.getCarInfo(this, params, new CallBack<UserCarsResult>() {
                @Override
                public void onSuccess(UserCarsResult result) {
                    hideDialogLoading();

                    mServerList = result.getRspInfo().getUserCarsInfo();
                    PublicData.getInstance().mCarNum = mServerList.size();
                    PublicData.getInstance().mServerCars = listU(result.getRspInfo().getUserCarsInfo());

                    int canPayNum = 0;
                    int notPayNum = 0;
                    for (int i = 0; i < mServerList.size(); i++) {
                        if ("1".equals(mServerList.get(i).getIspaycar())) {
                            canPayNum++;
                        } else {
                            notPayNum++;
                        }
                    }
                    PublicData.getInstance().mPayCarNum = canPayNum;
                    PublicData.getInstance().mNorCarNum = notPayNum;
                    if (canPayNum > 0 && notPayNum > 0) {
                        mChangePayCar.setVisibility(View.VISIBLE);
                    } else {
                        mChangePayCar.setVisibility(View.GONE);
                    }
                    if (result.getRspInfo().getUserCarsInfo() != null && result.getRspInfo().getUserCarsInfo().size() > 0) {
                        List<CarInfoDTO> list = new ArrayList<CarInfoDTO>();
                        int len = result.getRspInfo().getUserCarsInfo().size();
                        for (int i = 0; i < len; i++) {
                            UserCarInfoBean info = result.getRspInfo().getUserCarsInfo().get(i);
                            CarInfoDTO dto = new CarInfoDTO();
                            dto.setCarnum(info.getCarnum());
                            dto.setCarmodel(info.getCarmodel());
                            dto.setEnginenum(info.getEnginenum());
                            dto.setIspaycar(info.getIspaycar());
                            dto.setCarnumtype(info.getCarnumtype());
                            list.add(dto);
                        }
                        getActivityCarInfo(list);
                    }
                }

                @Override
                public void onError(String errorCode, String msg) {

                    mLoadingView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    hideDialogLoading();
                }
            });
        }


    }

    //获取活动车辆
    private void getActivityCarInfo(final List<CarInfoDTO> carInfoDTOList) {
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(PublicData.getInstance().userID);
        CarApiClient.getActivityCar(this, activityCarDTO, new CallBack<ActivityCarResult>() {
            @Override
            public void onSuccess(ActivityCarResult result) {
                if (result.getResponseCode() == 2000) {
                    setData(result.getData().getPlateNo(), carInfoDTOList);
                }
                if (result.getResponseCode() == 4000) {
                    testSuperAdapter(carInfoDTOList);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                testSuperAdapter(carInfoDTOList);
            }
        });
    }

    //比较活动车辆和我的车辆
    private void setData(String plateNo, List<CarInfoDTO> carInfoDTOList) {
        List<CarInfoDTO> norCarList = new ArrayList<CarInfoDTO>();
        for (CarInfoDTO carInfoDTO : carInfoDTOList) {
            carInfoDTO.setActivityCar("2");
            if (plateNo.equals(carInfoDTO.getCarnum())) {
                carInfoDTO.setActivityCar("1");
            } else {
                carInfoDTO.setActivityCar("2");
            }
            norCarList.add(carInfoDTO);
        }
        testSuperAdapter(norCarList);
    }


    private List<UserCarInfoBean> listU(List<UserCarInfoBean> listUcar) {
        List<UserCarInfoBean> listUcb = new ArrayList<UserCarInfoBean>();
        for (UserCarInfoBean userCarInfoBean : listUcar) {
            userCarInfoBean.setEnginenum(Des3.decode(userCarInfoBean.getEnginenum()));
            userCarInfoBean.setCarnum(Des3.decode(userCarInfoBean.getCarnum()));
            userCarInfoBean.setCarframenum(Des3.decode(userCarInfoBean.getCarframenum()));
            listUcb.add(userCarInfoBean);
        }
        return listUcb;
    }

    //多布局多实体的Adapter
    private void testSuperAdapter(List<CarInfoDTO> list) {
        mXRecyclerView.refreshComplete();
        mLoadingView.setVisibility(View.GONE);
//        if (list.size() >= 3){
//            getEnsureView().setVisibility(View.GONE);
//        }
        if (list == null || list.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        }

        DataHolder<CarInfoDTO> holderSimple = new DataHolder<CarInfoDTO>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, final CarInfoDTO item, int position) {
                View mLayout = holder.getView(R.id.item_manage_vehicles_layout);
                TextView mCarNum = holder.getView(R.id.item_manage_vehicles_num);
                TextView mCarPayFlag = holder.getView(R.id.item_manage_vehicles_flag);
                TextView mCarSignFlag = holder.getView(R.id.item_manage_activity_car);
                mCarNum.setText(item.getCarnum());
                if ("1".equals(item.getIspaycar())) {
                    mCarPayFlag.setVisibility(View.VISIBLE);
                } else {
                    mCarPayFlag.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(item.getActivityCar())) {
                    if (item.getActivityCar().equals("1")) {
                        mCarSignFlag.setVisibility(View.VISIBLE);
                    } else {
                        mCarSignFlag.setVisibility(View.GONE);
                    }
                }
                mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PublicData.getInstance().mHashMap.put("ConsummateInfo", item);
                        AddCarActivity.isFrom = true;
                        Act.getInstance().gotoIntent(CarManageGroupActivity.this, AddCarActivity.class);
                    }
                });
            }
        };

        DataHolder<SuperBean> holderSuper = new DataHolder<SuperBean>() {
            @Override
            public void bind(Context context, SuperViewHolder holder, SuperBean item, int position) {
                TextView mTitle = holder.getView(R.id.item_manage_vehicles_title);
                mTitle.setText(item.getName());
            }
        };
        List<LayoutWrapper> data = new ArrayList<>();

        List<CarInfoDTO> list1 = new ArrayList<>();
        List<CarInfoDTO> list2 = new ArrayList<>();
//        if (PublicData.getInstance().mPayCarNum >= 2 && PublicData.getInstance().mNorCarNum > 0){
        for (CarInfoDTO dto : list) {
            if ("1".equals(dto.getIspaycar())) {
                list1.add(dto);
            } else {
                list2.add(dto);
            }
        }
//        }else{
//
//        }
        if (list1 != null && list1.size() > 0) {
            data.add(new LayoutWrapper(R.layout.item_manage_vehicles_group, new SuperBean("可缴费车辆", false), holderSuper));
            for (CarInfoDTO dto : list1) {
                data.add(new LayoutWrapper(R.layout.item_manage_vehicles, dto, holderSimple));
            }
        }
        if (list2 != null && list2.size() > 0) {
            data.add(new LayoutWrapper(R.layout.item_manage_vehicles_group, new SuperBean("仅限违章查询车辆", false), holderSuper));
            for (CarInfoDTO dto : list2) {
                data.add(new LayoutWrapper(R.layout.item_manage_vehicles, dto, holderSimple));
            }
        }
        adapter.setData(data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(BenDiCarInfoEvent event) {

        List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
        adapter.removeAll();
        testSuperAdapter(list);
    }

    //修改车辆
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(EditCarInfoEvent event) {
        if (event.isStatus()) {

            getCarsInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(AddCarInfoEvent event) {
        if (event.getStatus()) {
            getCarsInfo();
        }
    }

}
