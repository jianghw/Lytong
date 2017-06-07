package com.zantong.mobilecttx.weizhang.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.adapter.QueryHistoryAdapter;
import com.zantong.mobilecttx.weizhang.bean.AddVehicleBean;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.weizhang.bean.QueryHistoryBean;
import com.zantong.mobilecttx.presenter.QueryHistoryPresenter;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.QueryResultActivity;
import com.zantong.mobilecttx.interf.ModelView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
public class QueryHistory extends Fragment implements QueryHistoryAdapter.OnRecyclerviewItemListener, ModelView{
    @Bind(R.id.query_history_recycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.query_history_empty_layout)
    View mEmptyView;
    private Context context;
    private View view;
    private LinkedList<QueryHistoryBean.QueryCarBean> mDatas = new LinkedList<>();
    private QueryHistoryAdapter mQuseryHistoryAdapter;
    private Dialog mDialog;
    private QueryHistoryPresenter mQueryHistoryPresenter;
    private QueryHistoryBean.QueryCarBean mQueryCarBean;
    private AddVehicleBean mIllegalQueryBean;
    private boolean addFlag;
    private String carnum;
    private String enginenum;
    private String carnumtype;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.query_history, container, false);
        ButterKnife.bind(this, view);
        showData();
        mQueryHistoryPresenter = new QueryHistoryPresenter(this);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, R.layout.splite_line_nomal));
        return view;
    }

    public HashMap<String, String> mapData(){
        HashMap<String, String>  mHashMap = new HashMap<>();
//        mHashMap.put("carnum", PublicData.getInstance().mQueryHistoryBean.getQueryCar().get);
//        mHashMap.put("enginenum", engine_number_edit.getText().toString());
//        mHashMap.put("carnumtype", "01");
//        mHashMap.put("enginenum", vehicles_type_text.getText().toString());
        return mHashMap;
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    private void showData(){
        if(null == PublicData.getInstance().mQueryHistoryBean.getQueryCar() || PublicData.getInstance().mQueryHistoryBean.getQueryCar().size() == 0){
            mQuseryHistoryAdapter = new QueryHistoryAdapter(context, mDatas);
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }else{
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mQuseryHistoryAdapter = new QueryHistoryAdapter(context, PublicData.getInstance().mQueryHistoryBean.getQueryCar());
        }
//        else if(PublicData.getInstance().loginFlag){
//            LinkedList<QueryHistoryBean.QueryCarBean> list = new LinkedList<>();
//            for (int i = 0; i < PublicData.getInstance().mServerCars.size(); i++){
//                QueryHistoryBean.QueryCarBean bean = new QueryHistoryBean.QueryCarBean();
//                bean.setCarNumber(PublicData.getInstance().mServerCars.get(i).getCarnum());
//                bean.setEngineNumber(PublicData.getInstance().mServerCars.get(i).getEnginenum());
//                bean.setCarnumtype(PublicData.getInstance().mServerCars.get(i).getCarnumtype());
//                bean.setQueryTime("");
//                list.add(bean);
//            }
//            mQuseryHistoryAdapter = new QueryHistoryAdapter(context, list);
//        }
        mQuseryHistoryAdapter.setListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mQuseryHistoryAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showProgress() {
        mDialog = DialogUtils.showLoading(context);
    }

    @Override
    public void updateView(Object object, int index) {
        mIllegalQueryBean = (AddVehicleBean) object;
        if(!PublicData.getInstance().success.equals(mIllegalQueryBean.getSYS_HEAD().getReturnCode())){
            ToastUtils.showShort(context, ((AddVehicleBean) object).getSYS_HEAD().getReturnMessage());
            return;
        }
        switch (index){
            case 1:
                List<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean> mViolationInfoBean = (List<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean>) mIllegalQueryBean.getRspInfo().getViolationInfo();
                PublicData.getInstance().mHashMap.put("IllegalViolation", mViolationInfoBean);
                PublicData.getInstance().DialogCarNotice = true;
                Act.getInstance().lauchIntent(getContext(), QueryResultActivity.class);
                mQueryCarBean = new QueryHistoryBean.QueryCarBean();
                mQueryCarBean.setCarNumber(carnum);
                mQueryCarBean.setEngineNumber(enginenum);
                mQueryCarBean.setCarnumtype(carnumtype);
                mQueryCarBean.setQueryTime(DateUtils.getDateFormat());
                int size = PublicData.getInstance().mQueryHistoryBean.getQueryCar().size();
                if(size != 0){
                    for (int i = 0; i < size; i++){
                        if(PublicData.getInstance().mQueryHistoryBean.getQueryCar().get(i).getCarNumber().equals(mQueryCarBean.getCarNumber())){
                            PublicData.getInstance().mQueryHistoryBean.getQueryCar().remove(i);
                            PublicData.getInstance().mQueryHistoryBean.getQueryCar().addFirst(mQueryCarBean);
                            addFlag = true;
                            break;
                        }
                    }
                    if(!addFlag){
                        PublicData.getInstance().mQueryHistoryBean.getQueryCar().addFirst(mQueryCarBean);
                    }
                }else{
                    PublicData.getInstance().mQueryHistoryBean.getQueryCar().addFirst(mQueryCarBean);
                }

                UserInfoRememberCtrl.saveObject(getContext(), "QueryHistory", PublicData.getInstance().mQueryHistoryBean);
                break;
        }
    }

    @Override
    public void hideProgress() {
        if(mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        carnum = PublicData.getInstance().mQueryHistoryBean.getQueryCar().get(position).getCarNumber();
        enginenum = PublicData.getInstance().mQueryHistoryBean.getQueryCar().get(position).getEngineNumber();
        carnumtype = PublicData.getInstance().mQueryHistoryBean.getQueryCar().get(position).getCarnumtype();
        mapData().put("carnum", carnum);
        mapData().put("enginenum", enginenum);
        mapData().put("carnumtype", carnumtype);
        PublicData.getInstance().mHashMap.put("IllegalViolationName", carnum);
        PublicData.getInstance().mHashMap.put("carnum", carnum);
        PublicData.getInstance().mHashMap.put("enginenum", enginenum);
        PublicData.getInstance().mHashMap.put("carnumtype", carnumtype);
        LogUtils.i(carnum+"<<<"+enginenum+"<<<"+carnumtype);
        mQueryHistoryPresenter.loadView(1);
    }


    @Override
    public void OnItemLongClick(int position) {
    }
}
