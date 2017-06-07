package com.zantong.mobilecttx.user.activity;

import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.presenter.LoginPresenter;
import com.zantong.mobilecttx.interf.ILoginView;

import butterknife.Bind;

/**
 * 邀请成功
 * @author Sandy
 * create at 16/6/13 下午6:05
 */
public class RequestSuccActivity extends BaseMvpActivity<ILoginView,LoginPresenter> implements View.OnClickListener, ILoginView {

    @Bind(R.id.mine_invitation_name)
    TextView mName;
    @Bind(R.id.mine_invitation_date)
    TextView mDate;

    public static String REQUEST_NAME = "requestcode_name";
    public static String REQUEST_DATE = "requestcode_date";

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        setTitleText("邀请成功");
        mName.setText(PublicData.getInstance().mLoginInfoBean.getRecdphoe());
        String recddt = PublicData.getInstance().mLoginInfoBean.getRecddt();
        try{
            mDate.setText(recddt.substring(0, 4) + "-" + recddt.substring(4, 6) + "-" + recddt.substring(6, 8));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_requestsucc_activity;
    }

    @Override
    public void onClick(View v) {
        presenter.login(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public String getPhoenum() {
        return null;
    }

    @Override
    public String getCaptcha() {
        return null;
    }
}
