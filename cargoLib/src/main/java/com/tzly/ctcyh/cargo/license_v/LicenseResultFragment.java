package com.tzly.ctcyh.cargo.license_v;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.RefreshFragment;

/**
 * 驾照查分
 */
public class LicenseResultFragment extends RefreshFragment {

    private static final String TAG_SCORE = "tag_score";

    private TextView mTvScore;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected int fragmentView() {
        return R.layout.cargo_fragment_license_result;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);
    }

    @Override
    protected void loadingFirstData() {
        Bundle bundle = getArguments();
        String score = bundle.getString(TAG_SCORE);
        showLicenseData(score);
    }

    public static LicenseResultFragment newInstance(String score) {
        LicenseResultFragment fragment = new LicenseResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_SCORE, score);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initView(View view) {
        ImageView mImgBack = (ImageView) view.findViewById(R.id.img_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClickListener();
            }
        });
        mTvScore = (TextView) view.findViewById(R.id.tv_score);

        /*FragmentManager manager = getChildFragmentManager();
        Fragment fragment = CargoRouter.getAdvModuleFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_child, fragment, "scroll_child_fragment");
        transaction.commit();*/
    }

    /**
     * 回退监听功能
     */
    protected void backClickListener() {
        CargoRouter.gotoMainActivity(getContext(), 1);
    }

    public void showLicenseData(String score) {
        mTvScore.setText(score);
    }
}