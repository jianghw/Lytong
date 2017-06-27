package com.zantong.mobilecttx.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarsBean;
import com.zantong.mobilecttx.user.bean.UserCarsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghw on 2017/6/27.
 * Description:
 * Update by:
 * Update day:
 */

public class HorizontalCarViolationAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<UserCarInfoBean> mUserCarInfoBeanList = new ArrayList<>();
    /**
     * 车的数量
     */
    private int carCount;

    public HorizontalCarViolationAdapter(final Context context, UserCarsResult userCarsResult) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        UserCarsBean bean = userCarsResult.getRspInfo();
        if (bean != null) {
            List<UserCarInfoBean> infoBeanList = bean.getUserCarsInfo();
            carCount = infoBeanList.size();
            if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();
            mUserCarInfoBeanList.addAll(infoBeanList);
        }
        if (carCount < 3) mUserCarInfoBeanList.add(new UserCarInfoBean());
    }

    @Override
    public int getCount() {
        return mUserCarInfoBeanList.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    /**
     * 1、当原本车辆小于3时，最后一个位置为查询功能
     */
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        if (mUserCarInfoBeanList.size() != carCount && position == carCount) {
            view = mLayoutInflater.inflate(R.layout.vp_car_default_item, container, false);
            setupDefaultCarItem(view, mUserCarInfoBeanList.get(position));
        } else {
            view = mLayoutInflater.inflate(R.layout.vp_car_violation_item, container, false);
            setupViolationCarItem(view, mUserCarInfoBeanList.get(position));
        }
        container.addView(view);
        return view;
    }

    private void setupDefaultCarItem(View view, UserCarInfoBean userCarInfoBean) {
        ImageView imageView = (ImageView) view.findViewById(R.id.img_query);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setupViolationCarItem(View view, UserCarInfoBean userCarInfoBean) {
        TextView tvPlateNumber = (TextView) view.findViewById(R.id.tv_plate_number);
        TextView tvType = (TextView) view.findViewById(R.id.tv_motorcycle_type);
        TextView tvBrand = (TextView) view.findViewById(R.id.tv_motorcycle_brand);
        tvPlateNumber.setText(userCarInfoBean.getCarframenum());
        tvType.setText(userCarInfoBean.getCarmodel());
        tvBrand.setText(userCarInfoBean.getCarframenum());

        TextView tvTotamt = (TextView) view.findViewById(R.id.tv_totamt);
        TextView tvTotcent = (TextView) view.findViewById(R.id.tv_totcent);
        TextView tvTotcount = (TextView) view.findViewById(R.id.tv_totcount);

        tvTotamt.setText(userCarInfoBean.getCarframenum());
        tvTotcent.setText(userCarInfoBean.getCarframenum());
        tvTotcount.setText(userCarInfoBean.getCarframenum());
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
