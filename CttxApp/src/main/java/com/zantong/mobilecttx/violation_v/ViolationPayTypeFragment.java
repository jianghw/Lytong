package com.zantong.mobilecttx.violation_v;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.AbstractBaseFragment;
import com.zantong.mobilecttx.R;

public class ViolationPayTypeFragment extends AbstractBaseFragment
        implements View.OnClickListener {

    private IViolationPayUi mIViolationPayUi;

    private ImageView mFragmentViolationPaytypeClose;
    private ImageView mFragmentViolationPaytypeImg1;
    private LinearLayout mFragmentViolationPaytypeLayout1;
    private ImageView mFragmentViolationPaytypeImg2;
    private LinearLayout mFragmentViolationPaytypeLayout2;

    public static ViolationPayTypeFragment newInstance() {
        return new ViolationPayTypeFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof IViolationPayUi)
            mIViolationPayUi = (IViolationPayUi) activity;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int contentView() {
        return R.layout.fragment_violation_paytype;
    }

    @Override
    protected void bindContent(View contentView) {
        initView(contentView);
    }

    @Override
    protected void loadingFirstData() {
        if (mIViolationPayUi != null) {
            int payType = mIViolationPayUi.getPayType();
            mFragmentViolationPaytypeImg1.setVisibility(payType == 3 ? View.VISIBLE : View.GONE);
            mFragmentViolationPaytypeImg2.setVisibility(payType == 3 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void clickRefreshData() {}

    @Override
    protected void responseData(Object response) {}

    public void initView(View view) {
        mFragmentViolationPaytypeClose = (ImageView) view.findViewById(R.id.fragment_violation_paytype_close);
        mFragmentViolationPaytypeClose.setOnClickListener(this);
        mFragmentViolationPaytypeImg1 = (ImageView) view.findViewById(R.id.fragment_violation_paytype_img1);
        mFragmentViolationPaytypeLayout1 = (LinearLayout) view.findViewById(R.id.fragment_violation_paytype_layout1);
        mFragmentViolationPaytypeLayout1.setOnClickListener(this);
        mFragmentViolationPaytypeImg2 = (ImageView) view.findViewById(R.id.fragment_violation_paytype_img2);
        mFragmentViolationPaytypeLayout2 = (LinearLayout) view.findViewById(R.id.fragment_violation_paytype_layout2);
        mFragmentViolationPaytypeLayout2.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_violation_paytype_close:
                closeFragment();
                break;
            case R.id.fragment_violation_paytype_layout1:
                choicePayType(View.GONE, View.VISIBLE, 3);
                break;
            case R.id.fragment_violation_paytype_layout2:
                choicePayType(View.VISIBLE, View.GONE, 4);
                break;
            default:
                break;
        }
    }

    protected void choicePayType(int gone, int visible, int payType) {
        mFragmentViolationPaytypeImg1.setVisibility(visible);
        mFragmentViolationPaytypeImg2.setVisibility(gone);

        if (mIViolationPayUi != null) mIViolationPayUi.setPayType(payType);

        closeFragment();
    }

    private void closeFragment() {
        if (mIViolationPayUi != null) mIViolationPayUi.closeFragment();
    }
}
