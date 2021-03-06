package com.zantong.mobilecttx.huodong.activity;

import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;

/**
 * 活动规则说明
 * @author zyb
 *
 *  
 *    *  *   *  *     
 *  *      *      *   
 *  *             *   
 *   *           *    
 *      *     *       
 *         *          
 * 
 *
 * create at 17/1/11 下午4:10
 */
public class HundredRuleActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements View.OnClickListener, IBaseView {


    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.hundred_rule_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void initView() {
        setTitleText("活动积分规则");
    }

    @Override
    public void initData() {
    }
}
