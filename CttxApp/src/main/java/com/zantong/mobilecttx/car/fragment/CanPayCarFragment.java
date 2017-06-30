package com.zantong.mobilecttx.car.fragment;

import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.car.adapter.CanPayCarAdapter;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.interf.ModelView;
import com.zantong.mobilecttx.presenter.CanPayCarPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.ViolationQueryAcitvity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CanPayCarFragment extends BaseListFragment<OpenQueryBean.RspInfoBean.UserCarsInfoBean>
        implements ModelView{

    private CanPayCarPresenter mBindCardPresenter;
    private CanPayCarAdapter mCanPayCarAdapter;
    private OpenQueryBean mOpenQueryBean;
    private List<OpenQueryBean.RspInfoBean.UserCarsInfoBean> datas = new ArrayList<>();

    public CanPayCarFragment(){
        mBindCardPresenter = new CanPayCarPresenter(this);
    }


    @Override
    public BaseAdapter<OpenQueryBean.RspInfoBean.UserCarsInfoBean> createAdapter() {
        mCanPayCarAdapter = new CanPayCarAdapter();
        return mCanPayCarAdapter;
    }

    public HashMap<String, String> mapData(){
        HashMap<String, String>  mHashMap = new HashMap<>();
        return mHashMap;
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        OpenQueryBean.RspInfoBean.UserCarsInfoBean mUserCarsInfoBean = (OpenQueryBean.RspInfoBean.UserCarsInfoBean) data;
        PublicData.getInstance().mHashMap.put("IllegalViolation", mUserCarsInfoBean.getViolationInfo());
        PublicData.getInstance().mHashMap.put("carnum", mUserCarsInfoBean.getCarnum());
        PublicData.getInstance().mHashMap.put("enginenum", mUserCarsInfoBean.getEnginenum());
        PublicData.getInstance().mHashMap.put("carnumtype", mUserCarsInfoBean.getCarnumtype());
        PublicData.getInstance().mHashMap.put("IllegalViolationName", mUserCarsInfoBean.getCarnum());
        Act.getInstance().lauchIntent(getActivity(), ViolationQueryAcitvity.class);
    }

    @Override
    public void initData() {
//        mBindCardPresenter.loadView(1);
        getData();
    }

    @Override
    public void showProgress() {

    }

    @Override
    protected void getData() {
        super.getData();
        mBindCardPresenter.loadView(1);
    }

    @Override
    public void updateView(Object object, int index) {
        mOpenQueryBean = (OpenQueryBean) object;
        List<OpenQueryBean.RspInfoBean.UserCarsInfoBean> mUserCarsInfoBeans = mOpenQueryBean.getRspInfo().getUserCarsInfo();
        for(int i = 0; i < mUserCarsInfoBeans.size(); i ++){
            if("1".equals(mUserCarsInfoBeans.get(i).getIspaycar())){
                datas.add(mUserCarsInfoBeans.get(i));
            }
        }
        setDataResult(datas);
    }

    @Override
    public void hideProgress() {

    }
}
