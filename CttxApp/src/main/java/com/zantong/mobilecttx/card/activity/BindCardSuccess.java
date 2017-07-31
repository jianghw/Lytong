package com.zantong.mobilecttx.card.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.eventbus.ChangTongEvent;
import com.zantong.mobilecttx.presenter.BindCardSuccessPresenter;
import com.zantong.mobilecttx.car.fragment.BindCarSuccessFragment;
import com.zantong.mobilecttx.contract.ModelView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class BindCardSuccess extends BaseMvpActivity<IBaseView, BindCardSuccessPresenter> implements View.OnClickListener, IBaseView, ModelView {

    private FragmentManager fragmentManager;
    private BindCardSuccessPresenter mBindCardSuccessPresenter;
    @Override
    public BindCardSuccessPresenter initPresenter() {
        mBindCardSuccessPresenter = new BindCardSuccessPresenter(BindCardSuccess.this);
        return mBindCardSuccessPresenter;
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_help_activity;
    }

    @Override
    public void initView() {
        setTitleText("绑定畅通卡");
        setEnsureText("完成");
        getBaseBack().setVisibility(View.GONE);
//        mBindCardSuccessPresenter.loadView(1);
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BindCarSuccessFragment mBindCarSuccessFragment = new BindCarSuccessFragment();
        transaction.replace(R.id.mine_help_layout, mBindCarSuccessFragment);
        transaction.commit();
    }

    public HashMap<String, String> mapData(){
        HashMap<String, String>  mHashMap = new HashMap<>();
//        mHashMap.put("getdate", nickNameEdit.getText().toString());
        return mHashMap;
    }

    @Override
    protected void baseGoEnsure() {
        EventBus.getDefault().post(new ChangTongEvent(true, "成功"));
        finish();
    }


    @Override
    protected void baseGoBack() {
//        presenter.loadView(1);
//        super.baseGoBack();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }



    @Override
    public void showProgress() {

    }

    @Override
    public void updateView(Object object, int index) {
        switch (index) {
            case 1:

                break;
        }
    }

    @Override
    public void hideProgress() {

    }

}
