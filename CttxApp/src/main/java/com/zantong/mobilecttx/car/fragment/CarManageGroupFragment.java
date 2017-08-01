package com.zantong.mobilecttx.car.fragment;

import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.car.activity.AddCarActivity;
import com.zantong.mobilecttx.car.activity.CarManageActivity;
import com.zantong.mobilecttx.car.activity.SetPayCarActivity;
import com.zantong.mobilecttx.car.adapter.CarManageGroupAdapter;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.car.dto.UserCarsDTO;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.AddCarInfoEvent;
import com.zantong.mobilecttx.eventbus.BenDiCarInfoEvent;
import com.zantong.mobilecttx.eventbus.EditCarInfoEvent;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.Des3;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ContextUtils;

public class CarManageGroupFragment extends BaseListFragment<CarInfoDTO>{

    private CarManageGroupAdapter mCarManageAdapter;
    private OpenQueryBean.RspInfoBean mRspInfoBean;
    private List<UserCarInfoBean> payData;
    private List<UserCarInfoBean> noPayData;
    TextView mChangeCarBtn,mAddBtn;

    List<UserCarInfoBean> mServerList = new ArrayList<>();

    public CarManageGroupFragment() {
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {
        initListData();
    }

    @Override
    protected void onForceRefresh() {
        initListData();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (PublicData.getInstance().isSetPayCar == true){
            PublicData.getInstance().isSetPayCar = false;
            mCarManageAdapter.removeAll();
            initListData();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        payData.clear();
        noPayData.clear();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        PublicData.getInstance().mHashMap.put("ConsummateInfo", data);
        AddCarActivity.isFrom = true;
        Act.getInstance().lauchIntent(getActivity(), AddCarActivity.class);
    }

    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    public BaseAdapter<CarInfoDTO> createAdapter() {
        mCarManageAdapter = new CarManageGroupAdapter();
        return mCarManageAdapter;
    }


    @Override
    public void initData() {
        super.initData();

        CarManageActivity carManageActivity = (CarManageActivity) getActivity();
        this.mChangeCarBtn = carManageActivity.getChangePayCar();
        this.mAddBtn = carManageActivity.getEnsureView();
        mAddBtn.setOnClickListener(this);
        mChangeCarBtn.setOnClickListener(this);

        if (PublicData.getInstance().loginFlag && !"".equals(PublicData.getInstance().filenum)){
            getBangDingCar();
        }


        initListData();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(BenDiCarInfoEvent event) {

        List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
        mCarManageAdapter.removeAll();
        setDataResult(list);
    }
    //修改车辆
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(EditCarInfoEvent event) {
        if (event.isStatus()) {

            initListData();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataSynEvent(AddCarInfoEvent event) {
        if (event.getStatus()) {
            mCarManageAdapter.append(event.getTag());
        }
    }

    private void initListData(){
        onShowLoading();
        mRspInfoBean = (OpenQueryBean.RspInfoBean) UserInfoRememberCtrl.readObject(PublicData.getInstance().CarLocalFlag);
        payData = new ArrayList<>();
        noPayData = new ArrayList<>();

        if(!PublicData.getInstance().loginFlag){
            List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
            setDataResult(list);
            return;
        }

        UserCarsDTO params = new UserCarsDTO();
        params.setUsrid(PublicData.getInstance().userID);
        UserApiClient.getCarInfo(ContextUtils.getContext(), params, new CallBack<UserCarsResult>() {
            @Override
            public void onSuccess(UserCarsResult result) {
                onShowContent();

                mServerList = result.getRspInfo().getUserCarsInfo();

                PublicData.getInstance().mCarNum = mServerList.size();
                PublicData.getInstance().mServerCars = listU(result.getRspInfo().getUserCarsInfo());

                int canPayNum = 0;
                int notPayNum =  0;
                for (int i = 0; i < mServerList.size(); i++){
                    if ("1".equals(mServerList.get(i).getIspaycar())){
                        canPayNum++;
                    }else{
                        notPayNum++;
                    }
                }
                PublicData.getInstance().mPayCarNum = canPayNum;
                PublicData.getInstance().mNorCarNum = notPayNum;
                if (canPayNum > 0 && notPayNum > 0){
                    mChangeCarBtn.setVisibility(View.VISIBLE);
                }else{
                    mChangeCarBtn.setVisibility(View.GONE);
                }
                if (result.getRspInfo().getUserCarsInfo() != null && result.getRspInfo().getUserCarsInfo().size() > 0){
                    List<CarInfoDTO> list = new ArrayList<CarInfoDTO>();
                    int len = result.getRspInfo().getUserCarsInfo().size();
                    for (int i = 0; i < len; i++){
                        UserCarInfoBean info = result.getRspInfo().getUserCarsInfo().get(i);
                        CarInfoDTO dto = new CarInfoDTO();
                        dto.setCarnum(info.getCarnum());
                        dto.setCarmodel(info.getCarmodel());
                        dto.setEnginenum(info.getEnginenum());
                        dto.setIspaycar(info.getIspaycar());
                        dto.setCarnumtype(info.getCarnumtype());
                        list.add(dto);
                    }
                    if (list.size() == 0){
                        getEmptyView().setVisibility(View.VISIBLE);
                    }
                    getActivityCarInfo(list);
                }else{
                    getEmptyView().setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //获取活动车辆
    private void getActivityCarInfo(final List<CarInfoDTO> carInfoDTOList){
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(PublicData.getInstance().userID);
        CarApiClient.getActivityCar(getActivity(), activityCarDTO, new CallBack<ActivityCarResult>() {
            @Override
            public void onSuccess(ActivityCarResult result) {
                if(result.getResponseCode() == 2000){
                    setData(result.getData().getPlateNo(), carInfoDTOList);
                }
                if(result.getResponseCode() == 4000){
                    setDataResult(carInfoDTOList);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                setDataResult(carInfoDTOList);
            }
        });
    }

    //比较活动车辆和我的车辆
    private void setData(String plateNo, List<CarInfoDTO> carInfoDTOList){
        List<CarInfoDTO> norCarList = new ArrayList<CarInfoDTO>();
        for(CarInfoDTO carInfoDTO : carInfoDTOList){
            carInfoDTO.setActivityCar("2");
            if(plateNo.equals(carInfoDTO.getCarnum())){
                carInfoDTO.setActivityCar("1");
            }else{
                carInfoDTO.setActivityCar("2");
            }
            norCarList.add(carInfoDTO);
        }
        mAdapter.removeAll();
        setDataResult(norCarList);
    }

    private List<UserCarInfoBean> listU(List<UserCarInfoBean> listUcar){
        List<UserCarInfoBean> listUcb = new ArrayList<UserCarInfoBean>();
        for (UserCarInfoBean userCarInfoBean:listUcar){
            userCarInfoBean.setEnginenum(Des3.decode(userCarInfoBean.getEnginenum()));
            userCarInfoBean.setCarnum(Des3.decode(userCarInfoBean.getCarnum()));
            userCarInfoBean.setCarframenum(Des3.decode(userCarInfoBean.getCarframenum()));
            listUcb.add(userCarInfoBean);
        }
        return listUcb;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mAddBtn){
            if (mServerList.size() < 3){
                Act.getInstance().gotoIntent(this.getActivity(), AddCarActivity.class);
            }else{
                DialogUtils.createDialog(this.getActivity(),"您的车辆数量已达上限");
            }
        }else if(v == mChangeCarBtn){
            Act.getInstance().gotoIntent(this.getActivity(), SetPayCarActivity.class);
        }
    }
    int mTimes = 0;
    /**
     * 获取绑定车辆信息
     */
    private void getBangDingCar(){
        mTimes++;
        LogoutDTO dto = new LogoutDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        UserApiClient.getPayCars(this.getActivity(), dto, new CallBack<PayCarResult>() {
            @Override
            public void onSuccess(PayCarResult result) {
                if (!"000000".equals(result.getSYS_HEAD().getReturnCode()) && mTimes < 3) {
                    getBangDingCar();
                }
            }
            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }
}
