package com.zantong.mobilecttx.fahrschule.fragment;

import android.os.Bundle;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;

/**
 * 驾校报名页面
 */
public class FahrschuleApplyFragment extends BaseRefreshJxFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static FahrschuleApplyFragment newInstance() {
        return new FahrschuleApplyFragment();
    }

    public static FahrschuleApplyFragment newInstance(String param1, String param2) {
        FahrschuleApplyFragment fragment = new FahrschuleApplyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_fahrschule_apply;
    }

    @Override
    protected void initFragmentView(View view) {

    }

    @Override
    protected void onFirstDataVisible() {

    }

    @Override
    protected void DestroyViewAndThing() {

    }

}