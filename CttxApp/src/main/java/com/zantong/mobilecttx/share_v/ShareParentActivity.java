package com.zantong.mobilecttx.share_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FragmentUtils;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.fahrschule_v.FahrschuleShareFragment;

import com.tzly.ctcyh.router.global.JxGlobal;

/**
 * Created by jianghw on 2017/7/7.
 * Description: 推荐 送好礼
 * Update by:
 * Update day:
 */

public class ShareParentActivity extends JxBaseActivity implements View.OnClickListener {
    private ImageView mImgAdmissions;
    private ImageView mImgViolation;
    private ImageView mImgShare;
    private FrameLayout mContent;
    private ImageView mImgSparring;
    private ImageView mImgSubject;

    private int mCurPosition;

    private FahrschuleShareFragment mFahrschuleShareFragment = null;
    private CreditShareFragment mCreditShareFragment = null;
    private FriendShareFragment mFriendShareFragment = null;
    private SparringShareFragment mSparringShareFragment = null;
    private SubjectShareFragment mSubjectShareFragment = null;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCurPosition = intent.getIntExtra(JxGlobal.putExtra.share_position_extra, 0);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_share_parent;
    }

    @Override
    protected void bindContentView(View childView) {
        initTitle(mCurPosition);

        initView(childView);

        if (mCurPosition != 0) initFragment();
    }

    @Override
    protected void initContentData() {

    }

    protected void initTitle(int curPosition) {
        switch (curPosition) {
            case 0:
                titleContent("推荐送豪礼");
                break;
            case 1:
                titleContent("分享返现");
                break;
            case 2:
                titleContent("百日无违章");
                break;
            case 3:
                titleContent("分享返现");
                break;
            case 4:
                titleContent("教练陪练");
                break;
            case 5:
                titleContent("科目强化");
                break;
            default:
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
    protected void onDestroy() {
        super.onDestroy();

        mFahrschuleShareFragment = null;
        mCreditShareFragment = null;
        mFriendShareFragment = null;
        mSparringShareFragment = null;
        mSubjectShareFragment = null;
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (mCurPosition) {
            case 1://驾校报名页面
                if (mFahrschuleShareFragment == null) {
                    mFahrschuleShareFragment = FahrschuleShareFragment.newInstance();
                }
                FragmentUtils.add(fragmentManager, mFahrschuleShareFragment, R.id.content, false, true);

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
                FragmentUtils.add(fragmentManager, mCreditShareFragment, R.id.content, false, true);
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
                FragmentUtils.add(fragmentManager, mFriendShareFragment, R.id.content, false, true);
                mFriendShareFragment.setCloseListener(new FragmentDestroy() {
                    @Override
                    public void closeListener(int position) {
                        initTitle(position);
                    }
                });
                break;
            case 4:
                if (mSparringShareFragment == null) {
                    mSparringShareFragment = SparringShareFragment.newInstance();
                }
                FragmentUtils.add(fragmentManager, mSparringShareFragment, R.id.content, false, true);
                mSparringShareFragment.setCloseListener(new FragmentDestroy() {
                    @Override
                    public void closeListener(int position) {
                        initTitle(position);
                    }
                });
                break;
            case 5:
                if (mSubjectShareFragment == null) {
                    mSubjectShareFragment = SubjectShareFragment.newInstance();
                }
                FragmentUtils.add(fragmentManager, mSubjectShareFragment, R.id.content, false, true);
                mSubjectShareFragment.setCloseListener(new FragmentDestroy() {
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
        mImgSparring = (ImageView) view.findViewById(R.id.img_sparring);
        mImgSparring.setOnClickListener(this);
        mImgSubject = (ImageView) view.findViewById(R.id.img_subject);
        mImgSubject.setOnClickListener(this);
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
            case R.id.img_sparring:
                mCurPosition = 4;
                initFragment();
                break;
            case R.id.img_subject:
                mCurPosition = 5;
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

    public static String getShareAppUrl(int postion) {

        return BuildConfig.CAR_MANGER_URL + "h5/share" + postion + "/share.html";
    }

}
