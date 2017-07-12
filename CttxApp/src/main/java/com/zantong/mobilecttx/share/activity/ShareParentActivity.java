package com.zantong.mobilecttx.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.fahrschule.fragment.FahrschuleShareFragment;
import com.zantong.mobilecttx.share.fragment.CreditShareFragment;
import com.zantong.mobilecttx.share.fragment.FriendShareFragment;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ui.FragmentUtils;

/**
 * Created by jianghw on 2017/7/7.
 * Description: 推荐 送好礼
 * Update by:
 * Update day:
 */

public class ShareParentActivity extends BaseJxActivity implements View.OnClickListener {
    private ImageView mImgAdmissions;
    private ImageView mImgViolation;
    private ImageView mImgShare;
    private FrameLayout mContent;

    private int mCurPosition;

    private FahrschuleShareFragment mFahrschuleShareFragment = null;
    private CreditShareFragment mCreditShareFragment = null;
    private FriendShareFragment mFriendShareFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCurPosition = intent.getIntExtra(GlobalConstant.putExtra.share_position_extra, 0);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_share_parent;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitle(mCurPosition);

        initView(view);

        if (mCurPosition != 0) initFragment();
    }

    protected void initTitle(int curPosition) {
        switch (curPosition) {
            case 0:
                initTitleContent("推荐送豪礼");
                break;
            case 1:
                initTitleContent("分享返现");
                break;
            case 2:
                initTitleContent("百日无违章");
                break;
            case 3:
                initTitleContent("分享返现");
                break;

        }
    }

    /**
     * 覆盖父类实现
     */
    protected void backClickListener() {
        closeFragment(0);
    }

    @Override
    protected void DestroyViewAndThing() {
        mFahrschuleShareFragment = null;
        mCreditShareFragment = null;
        mFriendShareFragment = null;
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurPosition) {
            case 1://驾校报名页面
                if (mFahrschuleShareFragment == null) {
                    mFahrschuleShareFragment = FahrschuleShareFragment.newInstance();
                }
                FragmentUtils.replaceFragment(fragmentManager, mFahrschuleShareFragment, R.id.content, true);

                mFahrschuleShareFragment.setCloseListener(new FragmentDestroy() {
                    @Override
                    public void closeListener(int position) {
                        initTitle(position);
                    }
                });
                break;
            case 2://驾校订单页面
                if (mCreditShareFragment == null) {
                    mCreditShareFragment = CreditShareFragment.newInstance();
                }
                FragmentUtils.replaceFragment(fragmentManager, mCreditShareFragment, R.id.content, true);
                mCreditShareFragment.setCloseListener(new FragmentDestroy() {
                    @Override
                    public void closeListener(int position) {
                        initTitle(position);
                    }
                });
                break;
            case 3:
                if (mFriendShareFragment == null) {
                    mFriendShareFragment = FriendShareFragment.newInstance();
                }
                FragmentUtils.replaceFragment(fragmentManager, mFriendShareFragment, R.id.content, true);
                mFriendShareFragment.setCloseListener(new FragmentDestroy() {
                    @Override
                    public void closeListener(int position) {
                        initTitle(position);
                    }
                });
                break;
            default:
                break;
        }
    }

    public void initView(View view) {
        mImgAdmissions = (ImageView) view.findViewById(R.id.img_admissions);
        mImgAdmissions.setOnClickListener(this);
        mImgViolation = (ImageView) view.findViewById(R.id.img_violation);
        mImgViolation.setOnClickListener(this);
        mImgShare = (ImageView) view.findViewById(R.id.img_share);
        mImgShare.setOnClickListener(this);
        mContent = (FrameLayout) view.findViewById(R.id.content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_admissions:
                mCurPosition = 1;
                initFragment();
                break;
            case R.id.img_violation:
                mCurPosition = 2;
                initFragment();
                break;
            case R.id.img_share:
                mCurPosition = 3;
                initFragment();
                break;
            default:
                break;
        }
        initTitle(mCurPosition);
    }

    public interface FragmentDestroy {
        void closeListener(int position);
    }

}
