package com.zantong.mobilecttx.weizhang.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.bean.QueryHistoryBean;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.StateBarSetting;
import com.zantong.mobilecttx.weizhang.fragment.QueryFragment;
import com.zantong.mobilecttx.weizhang.fragment.QueryHistory;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryActivity extends FragmentActivity {

    @Bind(R.id.illegal_query_title)
    TextView illegalQueryTitle;
    @Bind(R.id.query_history_title)
    TextView queryHistoryTitle;

    private QueryFragment mQueryFragment;
    private QueryHistory mQueryHistory;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.query_activity);

        ButterKnife.bind(this);
        StateBarSetting.settingBar(this);
        PublicData.getInstance().mQueryHistoryBean = (QueryHistoryBean) UserInfoRememberCtrl.readObject("QueryHistory");
        if(null == PublicData.getInstance().mQueryHistoryBean){
            PublicData.getInstance().mQueryHistoryBean = new QueryHistoryBean();
            PublicData.getInstance().mQueryHistoryBean.setQueryCar(new LinkedList<QueryHistoryBean.QueryCarBean>());
        }
        illegalQueryTitle.setSelected(true);
        queryHistoryTitle.setSelected(false);
        mQueryFragment = new QueryFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.fragment_content, mQueryFragment).commit();

    }

    @OnClick({R.id.illegal_query_title, R.id.query_history_title, R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.illegal_query_title:
                illegalQueryTitle.setSelected(true);
                queryHistoryTitle.setSelected(false);
                choseCurrentItem(0);
                break;
            case R.id.query_history_title:
                illegalQueryTitle.setSelected(false);
                queryHistoryTitle.setSelected(true);
                choseCurrentItem(1);
                break;
            case R.id.tv_back:
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive() && this.getCurrentFocus() != null) {
                    if (this.getCurrentFocus().getWindowToken() != null) {
                        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                finish();
                break;
        }
    }
    private void choseCurrentItem(int item){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(mFragmentTransaction);
        if(item == 0){
            if(mQueryFragment == null){
                mQueryFragment = new QueryFragment();
                mFragmentTransaction.add(R.id.fragment_content, mQueryFragment);
            }else{
                mFragmentTransaction.show(mQueryFragment);
            }
        }else if(item == 1){
            if(mQueryHistory == null){
                mQueryHistory = new QueryHistory();
                mFragmentTransaction.add(R.id.fragment_content, mQueryHistory);
            }else{
                mFragmentTransaction.show(mQueryHistory);
            }
        }

        mFragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction){
        if(mQueryFragment != null){
            transaction.hide(mQueryFragment);
        }
        if(mQueryHistory != null){
            transaction.hide(mQueryHistory);
        }
    }
}
