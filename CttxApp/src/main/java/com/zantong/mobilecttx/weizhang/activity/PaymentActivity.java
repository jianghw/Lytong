package com.zantong.mobilecttx.weizhang.activity;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;

public class PaymentActivity extends BaseMvpActivity<IBaseView,HelpPresenter> implements IBaseView{


    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.pay_ment_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void initView() {
        setTitleText("缴费须知");
    }

    @Override
    public void initData() {
//        mVersion.setText(getVersion());
    }

//    /**
//     * 获取版本号
//     * @return 当前应用的版本号
//     */
//    private String getVersion() {
//        try {
//            PackageManager manager = this.getPackageManager();
//            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
//            String version = "  "+info.versionName;
//            return this.getString(R.string.version_name) + version;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return this.getString(R.string.can_not_find_version_name);
//        }
//    }
}
