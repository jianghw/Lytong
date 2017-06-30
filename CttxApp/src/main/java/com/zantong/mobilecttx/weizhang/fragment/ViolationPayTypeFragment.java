package com.zantong.mobilecttx.weizhang.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseExtraFragment;
import com.zantong.mobilecttx.weizhang.activity.ViolationResultAcitvity;

import butterknife.Bind;
import butterknife.OnClick;

public class ViolationPayTypeFragment extends BaseExtraFragment {

    @Bind(R.id.fragment_violation_paytype_img1)
    ImageView mImg1;
    @Bind(R.id.fragment_violation_paytype_img2)
    ImageView mImg2;

    private FragmentTransaction mTransaction;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_violation_paytype;
    }

    @Override
    public void initView(View view) {
        mTransaction = ((ViolationResultAcitvity) getActivity()).getSurePayFragmentManager().beginTransaction();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        switch (((ViolationResultAcitvity) getActivity()).getPayType()){
            case 1:
                mImg2.setVisibility(View.GONE);
                mImg1.setVisibility(View.VISIBLE);
                break;
            case 2:
                mImg2.setVisibility(View.VISIBLE);
                mImg1.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick({R.id.fragment_violation_paytype_close, R.id.fragment_violation_paytype_layout1, R.id.fragment_violation_paytype_layout2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_violation_paytype_close:
                closeFragment();
                break;
            case R.id.fragment_violation_paytype_layout1:
                mImg2.setVisibility(View.GONE);
                mImg1.setVisibility(View.VISIBLE);
                ((ViolationResultAcitvity) getActivity()).setPayType(1);
                closeFragment();
                break;
            case R.id.fragment_violation_paytype_layout2:
                mImg2.setVisibility(View.VISIBLE);
                mImg1.setVisibility(View.GONE);
                ((ViolationResultAcitvity) getActivity()).setPayType(2);
                closeFragment();
                break;
        }
    }
    private void closeFragment(){
        mTransaction.setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
                .replace(((ViolationResultAcitvity)getActivity()).getPayLayoutId(),((ViolationResultAcitvity)getActivity()).getPayFragment()).commit();
    }

}
