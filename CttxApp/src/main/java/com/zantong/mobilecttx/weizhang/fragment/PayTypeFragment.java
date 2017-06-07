package com.zantong.mobilecttx.weizhang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.activity.ViolationDetails;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class PayTypeFragment extends Fragment {

    @Bind(R.id.pay_type_choose_rl)
    RelativeLayout payTypeChooseRl;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.back_sure_pay)
    ImageView back_sure_pay;
    @Bind(R.id.e_pay_choose_image)
    ImageView e_pay_choose_image;
    @Bind(R.id.bank_pay_choose_image)
    ImageView bank_pay_choose_image;
    @Bind(R.id.e_pay_choose_rl)
    RelativeLayout e_pay_choose_rl;
    @Bind(R.id.bank_pay_choose_rl)
    RelativeLayout bank_pay_choose_rl;
    private Context mContext;
    private View mView;
    private FragmentTransaction transaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.pay_type_popupwindows, container, false);
        ButterKnife.bind(this, mView);
        transaction = ((ViolationDetails) mContext).getSurePayFragmentManager().beginTransaction();
        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();
        switch (((ViolationDetails) mContext).getPayType()){
            case 1:
                bank_pay_choose_image.setVisibility(View.GONE);
                e_pay_choose_image.setVisibility(View.VISIBLE);
                break;
            case 2:
                bank_pay_choose_image.setVisibility(View.VISIBLE);
                e_pay_choose_image.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.pay_type_choose_rl, R.id.back_sure_pay, R.id.e_pay_choose_rl, R.id.bank_pay_choose_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.e_pay_choose_rl:
                bank_pay_choose_image.setVisibility(View.GONE);
                e_pay_choose_image.setVisibility(View.VISIBLE);
                ((ViolationDetails) mContext).setPayType(1);
                break;
            case R.id.bank_pay_choose_rl:
                bank_pay_choose_image.setVisibility(View.VISIBLE);
                e_pay_choose_image.setVisibility(View.GONE);
                ((ViolationDetails) mContext).setPayType(2);
                break;
            case R.id.back_sure_pay:
                ((ViolationDetails) mContext).setIndexFlag(1);
                transaction
                .setCustomAnimations(R.anim.left_to_right_enter, R.anim.right_to_left_out)
                        .replace(R.id.sure_pay_frame, ((ViolationDetails) mContext).getSurePayFragment()).commit();
                break;
        }
    }
}
