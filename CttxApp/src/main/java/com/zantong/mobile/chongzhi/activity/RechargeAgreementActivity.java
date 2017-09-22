package com.zantong.mobile.chongzhi.activity;

import android.view.View;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseMvpActivity;
import com.zantong.mobile.base.interf.IBaseView;
import com.zantong.mobile.presenter.HelpPresenter;

/**
 * 充值协议
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
public class RechargeAgreementActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements View.OnClickListener, IBaseView {


    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.recharge_agreement_activity;
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
        setTitleText("油卡充值服务协议");
    }

    @Override
    public void initData() {
    }
}
