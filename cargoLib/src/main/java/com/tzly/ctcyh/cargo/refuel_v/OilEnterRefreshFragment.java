package com.tzly.ctcyh.cargo.refuel_v;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IOilEnterContract;
import com.tzly.ctcyh.cargo.refuel_p.OilEnterPresenter;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.util.RudenessScreenHelper;
import com.tzly.ctcyh.router.util.ScreenUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 加油进入
 */
public class OilEnterRefreshFragment extends RefreshFragment
        implements IOilEnterContract.IOilEnterView {


    private IOilEnterContract.IOilEnterPresenter mPresenter;
    private LinearLayout mLayList;

    @Override
    protected int fragmentView() {
        return R.layout.cargo_fragment_oil_list;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        OilEnterPresenter presenter = new OilEnterPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    protected void initView(View view) {
        mLayList = (LinearLayout) view.findViewById(R.id.ll_list);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getOilModuleList();
    }

    public static OilEnterRefreshFragment newInstance() {
        return new OilEnterRefreshFragment();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(IOilEnterContract.IOilEnterPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void countError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void countSucceed(OilEnterResponse response) {
        OilEnterResponse.DataBean data = response.getData();

    }

    /**
     * 加油活动资讯
     */
    @Override
    public void OilModuleError(String message) {
        toastShort(message);
        showStateError();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void OilModuleSucceed(OilModuleResponse response) {
        showStateContent();

        if (mLayList != null && mLayList.getChildCount() > 0) {
            mLayList.removeAllViewsInLayout();
        }

        List<OilModuleResponse.DataBean> lsit = response.getData();
        for (final OilModuleResponse.DataBean dataBean : lsit) {

            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoadUtils.loadTwoRectangle(dataBean.getImg(), imageView);

            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dataBean.getHeight() * ScreenUtils.widthPixels(Utils.getContext()) / dataBean.getWidth());
            imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL, imageView.getId());
//+ image
            RelativeLayout relativeLayout = new RelativeLayout(getActivity());
            relativeLayout.addView(imageView, imageParams);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CargoRouter.gotoCustomerService(
                            dataBean.getTargetPath(), dataBean.getTitle(),
                            String.valueOf(dataBean.getStatisticsId()), getActivity());
                }
            });

            if (dataBean.getChildren() != null && dataBean.getChildren().size() > 0) {
                final OilModuleResponse.DataBean.ChildrenBean childBean = dataBean.getChildren().get(0);

                TextView title = new TextView(getActivity());
                int padding = RudenessScreenHelper.ptInpx(10);
                title.setPadding(padding, padding, padding, padding);
                title.setTextColor(getContext().getColor(R.color.res_color_blue_0b));
                title.setText(childBean.getTitle());
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CargoRouter.gotoCustomerService(
                                childBean.getTargetPath(), childBean.getTitle(),
                                String.valueOf(childBean.getStatisticsId()), getActivity());
                    }
                });

                RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvParams.setMargins(0, RudenessScreenHelper.ptInpx(90), ScreenUtils.widthPixels(Utils.getContext()) * 34 / 100, 0);
                title.setLayoutParams(tvParams);
                tvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, title.getId());
                relativeLayout.addView(title);
            }

            if (!TextUtils.isEmpty(dataBean.getSubTitle())) {
                TextView subTitle = new TextView(getActivity());
                subTitle.setTextColor(getContext().getColor(R.color.res_color_black_b3));
                subTitle.setText(dataBean.getSubTitle());
                int padding = RudenessScreenHelper.ptInpx(10);
                subTitle.setPadding(padding, padding, padding, padding);

                RelativeLayout.LayoutParams countParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                countParams.setMargins(0, RudenessScreenHelper.ptInpx(180), ScreenUtils.widthPixels(Utils.getContext()) * 34 / 100, 0);
                countParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, subTitle.getId());
                subTitle.setLayoutParams(countParams);
                relativeLayout.addView(subTitle);
            }

            mLayList.addView(relativeLayout, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
}