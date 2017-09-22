package com.zantong.mobile.weizhang.fragment;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobile.R;
import com.zantong.mobile.base.fragment.BaseJxFragment;
import com.zantong.mobile.weizhang.activity.ViolationPayActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class ViolationPayTypeFragment extends BaseJxFragment {

    @Bind(R.id.fragment_violation_paytype_img1)
    ImageView mImg1;
    @Bind(R.id.fragment_violation_paytype_img2)
    ImageView mImg2;

    public static ViolationPayTypeFragment newInstance() {
        return new ViolationPayTypeFragment();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_violation_paytype;
    }

    @Override
    protected int getContentLayoutResID() {
        return 0;
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void onForceRefresh() {
    }

    @Override
    protected void initViewsAndEvents(View view) {
    }

    @Override
    protected void onFirstUserVisible() {
        ViolationPayActivity payActivity = getParentActivity();
        if (payActivity != null) {
            int payType = payActivity.getPayType();
            if (payType == 1) {
                mImg2.setVisibility(View.GONE);
                mImg1.setVisibility(View.VISIBLE);
            } else {
                mImg2.setVisibility(View.VISIBLE);
                mImg1.setVisibility(View.GONE);
            }
        }
    }

    public ViolationPayActivity getParentActivity() {
        FragmentActivity activity = getActivity();
        ViolationPayActivity violationListActivity = null;
        if (activity instanceof ViolationPayActivity) {
            violationListActivity = (ViolationPayActivity) activity;
        }
        return violationListActivity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void DestroyViewAndThing() {

    }

    @OnClick({R.id.fragment_violation_paytype_close,
            R.id.fragment_violation_paytype_layout1, R.id.fragment_violation_paytype_layout2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_violation_paytype_close:
                closeFragment();
                break;
            case R.id.fragment_violation_paytype_layout1:
                choicePayType(View.GONE, View.VISIBLE, 1);
                break;
            case R.id.fragment_violation_paytype_layout2:
                choicePayType(View.VISIBLE, View.GONE, 2);
                break;
            default:
                break;
        }
    }

    protected void choicePayType(int gone, int visible, int payType) {
        mImg2.setVisibility(gone);
        mImg1.setVisibility(visible);
        ViolationPayActivity payActivity = getParentActivity();
        if (payActivity != null) {
            payActivity.setPayType(payType);
        }
        closeFragment();
    }

    private void closeFragment() {
        ViolationPayActivity payActivity = getParentActivity();
        if (payActivity != null) {
            payActivity.closeFragment();
        }
    }

}
