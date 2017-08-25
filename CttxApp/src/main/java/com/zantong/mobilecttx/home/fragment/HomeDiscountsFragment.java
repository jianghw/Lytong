package com.zantong.mobilecttx.home.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.home.adapter.HomeDiscountsAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.bean.ModuleBean;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.widght.banner.CBViewHolderCreator;
import cn.qqtheme.framework.widght.banner.ConvenientBanner;


/**
 * 优惠页面
 */
public class HomeDiscountsFragment extends BaseRecyclerListJxFragment<ModuleBean> {

    private ConvenientBanner mCustomConvenientBanner;

    public static HomeDiscountsFragment newInstance() {
        return new HomeDiscountsFragment();
    }

    @Override
    public BaseAdapter<ModuleBean> createAdapter() {
        return new HomeDiscountsAdapter();
    }

    /**
     * 无需使用
     */
    protected boolean isNeedItemClick() {
        return false;
    }

    /**
     * @deprecated 不用
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
    }

    /**
     * 获取RecyclerHeader
     */
    protected View customViewHeader() {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.custom_recycler_header_banner, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(layoutParams);

        mCustomConvenientBanner = (ConvenientBanner) rootView.findViewById(R.id.custom_convenientBanner);

        //广告页本地加载
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.mipmap.banner);
        mCustomConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);

        return rootView;
    }

    @Override
    protected void onRefreshData() {
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
