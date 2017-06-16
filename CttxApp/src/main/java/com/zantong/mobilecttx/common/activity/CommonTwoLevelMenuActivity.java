package com.zantong.mobilecttx.common.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.common.fragment.CommonTwoLevelMenuFragment;

import static android.R.attr.type;

/**
 * 公共的二级菜单 列表页面
 */

public class CommonTwoLevelMenuActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, CommonTwoLevelMenuActivity.class);
        intent.putExtra("type", type);
        return intent;
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_msg_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int mType = getIntent().getIntExtra("type", 0);
        showTitle(mType);
        transaction.replace(R.id.mine_msg_layout, CommonTwoLevelMenuFragment.newInstance(type));
        transaction.commit();
    }

    /**
     * 显示标题
     */
    private void showTitle(int mType) {
        String title = "";
        if (mType == 0) {  //婚姻状况
            title = "婚姻状况";
        } else if (mType == 1) {  //教育程度
            title = "教育程度";
        } else if (mType == 2) {  //住宅类型
            title = "住宅类型";
        } else if (mType == 3) {   //行业类别
            title = "行业类别";
        } else if (mType == 4) {   //职业
            title = "职业";
        } else if (mType == 5) {   //职务
            title = "职务";
        } else if (mType == 6) {   //单位性质
            title = "单位性质";
        } else if (mType == 7) {   //性别
            title = "性别";
        } else if (mType == 8) {   //联系人 与本人关系
            title = "与本人关系";
        } else if (mType == 9) {   //自动还款类型
            title = "自动还款类型";
        } else if (mType == 10) {   //电子对账单标识
            title = "电子对账单标识";
        }
        setTitleText(title);
    }
}
