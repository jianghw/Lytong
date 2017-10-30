package com.zantong.mobilecttx.common.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.common.fragment.CommonListFragment;

/**
 * 公共列表类 用于选择
 *
 * @author Sandy
 *         create at 16/10/13 上午1:15
 */
public class CommonListActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.common_list_activity;
    }

    @Override
    public void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CommonListFragment listFragment = new CommonListFragment();
        transaction.replace(R.id.common_list_activity, listFragment);
        transaction.commit();
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            switch (LoginData.getInstance().commonListType) {
                case 0:
                    setTitleText("");
                    break;
                case 1:
                    setTitleText("有效期限");
                    break;
                case 2://准驾类型
                    setTitleText("准驾类型");
                    break;
                case 3://准驾类型
                    setTitleText("车辆类型");
                    break;
            }

        }
    }
}
