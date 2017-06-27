package com.zantong.mobilecttx.weizhang.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.adapter.MyCarAdapter;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.weizhang.bean.AddVehicleBean;
import com.zantong.mobilecttx.car.bean.MyCarBean;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.weizhang.bean.QueryHistoryBean;
import com.zantong.mobilecttx.presenter.IllegalQueryPresenter;
import com.zantong.mobilecttx.utils.AllCapTransformationMethod;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.TextPopupKeyboardUtil;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.VehicleTypeTools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.popwindow.KeyWordPop;
import com.zantong.mobilecttx.common.activity.CommonListActivity;
import com.zantong.mobilecttx.weizhang.activity.QueryResultActivity;
import com.zantong.mobilecttx.interf.ModelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryFragment extends Fragment implements ModelView {

    @Bind(R.id.engine_number_image)
    ImageView engine_number_image;
    @Bind(R.id.privince_edit_rl)
    RelativeLayout privince_edit_rl;
    @Bind(R.id.illegal_query_content)
    View illegal_query_content;
    @Bind(R.id.car_type_choose_rl)
    RelativeLayout car_type_choose_rl;
    @Bind(R.id.privince_edit)
    TextView privince_edit;
    @Bind(R.id.car_number_tv)
    EditText car_number_tv;
    @Bind(R.id.engine_number_edit)
    EditText engine_number_edit;
    @Bind(R.id.vehicles_type_text)
    TextView vehicles_type_text;
    @Bind(R.id.fragment_query_xrecyclerview_title)
    TextView mRecyclerViewTitle;
    @Bind(R.id.illegal_query_xrecyclerview)
    XRecyclerView mRecyclerView;

    private TextPopupKeyboardUtil mTextPopupKeyboardUtil;
    private Context context;
    private View view;
    private final int VEHICLETYPE = 4;
    private IllegalQueryPresenter mIllegalQueryPresenter;
    private AddVehicleBean mIllegalQueryBean;
    private QueryHistoryBean.QueryCarBean mQueryCarBean;
    private boolean addFlag = false;
    private Dialog mDialog;
    MyCarAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_query, container, false);
        InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        ButterKnife.bind(this, view);

        mIllegalQueryPresenter = new IllegalQueryPresenter(this);
        illegal_query_content.setFocusable(true);
        car_number_tv.setTransformationMethod(new AllCapTransformationMethod());
        car_number_tv.setSelection(car_number_tv.getText().toString().length());
        engine_number_edit.setSelection(engine_number_edit.getText().toString().length());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.loading);
        mAdapter = new MyCarAdapter();
        mRecyclerView.setAdapter(mAdapter);
        List<MyCarBean> list = new ArrayList<>();
        list.clear();

        if (PublicData.getInstance().loginFlag) {
            for (int i = 0; i < PublicData.getInstance().mServerCars.size(); i++) {
                MyCarBean bean = new MyCarBean();
                bean.setCar_name(PublicData.getInstance().mServerCars.get(i).getCarnum());
                bean.setEngine_num(PublicData.getInstance().mServerCars.get(i).getEnginenum());
                bean.setCar_type(PublicData.getInstance().mServerCars.get(i).getCarnumtype());
                list.add(bean);
            }
        } else {
            for (int i = 0; i < PublicData.getInstance().mLocalCars.size(); i++) {
                MyCarBean bean = new MyCarBean();
                bean.setCar_name(PublicData.getInstance().mLocalCars.get(i).getCarnum());
                bean.setEngine_num(PublicData.getInstance().mLocalCars.get(i).getEnginenum());
                bean.setCar_type(PublicData.getInstance().mLocalCars.get(i).getCarnumtype());
                list.add(bean);
            }
        }
        if (list.size() > 0) {
            mRecyclerViewTitle.setVisibility(View.VISIBLE);
        } else {
            mRecyclerViewTitle.setVisibility(View.GONE);
        }
        mAdapter.append(list);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                MyCarBean bean = (MyCarBean) data;

                PublicData.getInstance().mHashMap.put("carnum", bean.getCar_name());
                PublicData.getInstance().mHashMap.put("enginenum", bean.getEngine_num());
                PublicData.getInstance().mHashMap.put("carnumtype", bean.getCar_type());
                PublicData.getInstance().mHashMap.put("IllegalViolationName", bean.getCar_name());//标题
                Act.getInstance().gotoIntent(QueryFragment.this.getActivity(), QueryResultActivity.class);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        vehicles_type_text.setText(PublicData.getInstance().commonListData);
    }

    public HashMap<String, String> mapData() {

        HashMap<String, String> mHashMap = new HashMap<>();
        mHashMap.put("carnum", privince_edit.getText().toString() + car_number_tv.getTransformationMethod().getTransformation(car_number_tv.getText(), car_number_tv));
        mHashMap.put("enginenum", engine_number_edit.getText().toString());
        mHashMap.put("carnumtype", VehicleTypeTools.switchVehicleCode(vehicles_type_text.getText().toString()));

        return mHashMap;
    }

    @OnClick({R.id.engine_number_image, R.id.privince_edit_rl, R.id.car_type_choose_rl, R.id.next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.engine_number_image:
                new DialogMgr(context, R.mipmap.engine_number_image);
                break;
            case R.id.privince_edit_rl:
                InputMethodManager imm = (InputMethodManager)
                        context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                KeyWordPop mKeyWordPop = new KeyWordPop(context, view, new KeyWordPop.KeyWordLintener() {
                    @Override
                    public void onKeyWordLintener(String cityStr) {
                        privince_edit.setText(cityStr);
                    }
                });

                break;
            case R.id.car_type_choose_rl:
//                PublicData.getInstance().mHashMap.put("VehicleType", vehicles_type_text.getText().toString());
//                Act.getInstance().lauchIntentForResult(getActivity(), SelectCarTypeActivity.class,VEHICLETYPE);
                PublicData.getInstance().commonListType = 3;
                startActivityForResult(new Intent(this.getActivity(), CommonListActivity.class), CommonListActivity.REQUEST_COMMON_TYPE);

                break;
            case R.id.next_btn:
//                Act.getInstance().gotoIntent(this.getActivity(),ViolationResultAcitvity.class);


                mIllegalQueryPresenter.loadView(1);
                break;
        }
    }


    @Override
    public void showProgress() {
        mDialog = DialogUtils.showLoading(context);
    }

    @Override
    public void updateView(Object object, int index) {
        mIllegalQueryBean = (AddVehicleBean) object;
        if (!PublicData.getInstance().success.equals(mIllegalQueryBean.getSYS_HEAD().getReturnCode())) {
            ToastUtils.showShort(context, mIllegalQueryBean.getSYS_HEAD().getReturnMessage());
            return;
        }

        PublicData.getInstance().mHashMap.put("IllegalViolationName", privince_edit.getText().toString() + car_number_tv.getTransformationMethod().getTransformation(car_number_tv.getText(), car_number_tv));
        PublicData.getInstance().mHashMap.put("carnum", privince_edit.getText().toString() + car_number_tv.getTransformationMethod().getTransformation(car_number_tv.getText(), car_number_tv));
        PublicData.getInstance().mHashMap.put("enginenum", engine_number_edit.getText().toString());
        PublicData.getInstance().mHashMap.put("carnumtype", VehicleTypeTools.switchVehicleCode(vehicles_type_text.getText().toString()));
        List<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean> mViolationInfoBean = (List<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean>) mIllegalQueryBean.getRspInfo().getViolationInfo();
        PublicData.getInstance().mHashMap.put("IllegalViolation", mViolationInfoBean);
        PublicData.getInstance().DialogCarNotice = true;
        Act.getInstance().lauchIntent(getActivity(), QueryResultActivity.class);
        mQueryCarBean = new QueryHistoryBean.QueryCarBean();
        mQueryCarBean.setCarNumber(privince_edit.getText().toString() + car_number_tv.getTransformationMethod().getTransformation(car_number_tv.getText(), car_number_tv));
        mQueryCarBean.setEngineNumber(engine_number_edit.getText().toString());
        mQueryCarBean.setCarnumtype(VehicleTypeTools.switchVehicleCode(vehicles_type_text.getText().toString()));
        mQueryCarBean.setQueryTime(Tools.getYearDateFormat("yyyy-MM-dd"));
        int size = PublicData.getInstance().mQueryHistoryBean.getQueryCar().size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (PublicData.getInstance().mQueryHistoryBean.getQueryCar().get(i).getCarNumber().equals(mQueryCarBean.getCarNumber())) {
                    PublicData.getInstance().mQueryHistoryBean.getQueryCar().remove(i);
                    PublicData.getInstance().mQueryHistoryBean.getQueryCar().addFirst(mQueryCarBean);
                    addFlag = true;
                    break;
                }
            }
            if (!addFlag) {
                PublicData.getInstance().mQueryHistoryBean.getQueryCar().addFirst(mQueryCarBean);
            }
        } else {
            PublicData.getInstance().mQueryHistoryBean.getQueryCar().addFirst(mQueryCarBean);
        }

        UserInfoRememberCtrl.saveObject(getActivity(), "QueryHistory", PublicData.getInstance().mQueryHistoryBean);

    }

    @Override
    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
