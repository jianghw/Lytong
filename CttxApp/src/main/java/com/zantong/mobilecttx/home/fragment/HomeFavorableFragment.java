package com.zantong.mobilecttx.home.fragment;

import android.os.Bundle;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;

import cn.qqtheme.framework.util.log.LogUtils;

/**
 * 优惠页面
 */
public class HomeFavorableFragment extends BaseRefreshJxFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static HomeFavorableFragment newInstance(String param1, String param2) {
        HomeFavorableFragment fragment = new HomeFavorableFragment();
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
        return R.layout.fragment_home_favorable;
    }

    @Override
    protected void initFragmentView(View view) {

    }

    @Override
    protected void onFirstDataVisible() {
        LogUtils.e("onFirstDataVisible");
    }

    @Override
    protected void DestroyViewAndThing() {

    }

}
