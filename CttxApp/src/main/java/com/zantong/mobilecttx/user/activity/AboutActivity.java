package com.zantong.mobilecttx.user.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;

import butterknife.Bind;

public class AboutActivity extends BaseMvpActivity<IBaseView,HelpPresenter> implements IBaseView{

    @Bind(R.id.mine_about_version)
    TextView mVersion;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_about_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void initView() {
        setTitleText("关于我们");
    }

    @Override
    public void initData() {
        mVersion.setText(getVersion());
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    private String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = "  "+info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }
}
