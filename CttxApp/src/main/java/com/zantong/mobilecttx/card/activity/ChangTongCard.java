package com.zantong.mobilecttx.card.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.eventbus.ChangTongEvent;
import com.zantong.mobilecttx.presenter.ChatongTongCardPresenter;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.card.fragment.MyCardFragment;
import com.zantong.mobilecttx.card.fragment.UnbindCardFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChangTongCard extends BaseMvpActivity<IBaseView,ChatongTongCardPresenter> implements View.OnClickListener, IBaseView{


    @Override
    public ChatongTongCardPresenter initPresenter() {
        return new ChatongTongCardPresenter(ChangTongCard.this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_help_activity;
    }

    @Override
    public void initView() {
        setTitleText("我的畅通卡");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initContent();
    }

    public void initContent(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        UnbindCardFragment mUnbindCardFragment = new UnbindCardFragment();
        MyCardFragment mBindCardFragment = new MyCardFragment();
        if(Tools.isStrEmpty(PublicData.getInstance().filenum)){
            transaction.replace(R.id.mine_help_layout, mUnbindCardFragment);
        }else{
            transaction.replace(R.id.mine_help_layout, mBindCardFragment);
        }
        transaction.commit();
    }

    @Override
    public void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onChangtongEvent(ChangTongEvent event) {
        Log.e("why", "畅通卡");
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
