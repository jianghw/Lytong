package com.zantong.mobilecttx.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.home.activity.Codequery;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.utils.AllCapTransformationMethod;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.VehicleTypeTools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.violation_v.ViolationActivity;
import com.zantong.mobilecttx.weizhang.activity.ViolationListActivity;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.ArrayList;
import java.util.List;

import com.tzly.ctcyh.router.custom.popup.MoreWindow;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.ScreenUtils;

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
    /**
     * 违章查询选择页面
     */
    private MoreWindow mMoreWindow;

    public HorizontalCarViolationAdapter(final Context context, List<UserCarInfoBean> infoBeanList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        notifyListData(infoBeanList);
    }

    private void notifyListData(List<UserCarInfoBean> infoBeanList) {
        if (infoBeanList != null) {
            carCount = infoBeanList.size();
            if (!mUserCarInfoBeanList.isEmpty()) mUserCarInfoBeanList.clear();
            mUserCarInfoBeanList.addAll(infoBeanList);
        }
//        if (carCount <= 3) 需求不要
            mUserCarInfoBeanList.add(new UserCarInfoBean());
    }

    public void notifyDataSetChanged(List<UserCarInfoBean> infoBeanList) {
        notifyListData(infoBeanList);
        notifyDataSetChanged();
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

    /**
     * 违章查询
     */
    private void setupDefaultCarItem(View view, UserCarInfoBean userCarInfoBean) {
        ImageView imageView = (ImageView) view.findViewById(R.id.img_query);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobUtils.getInstance().eventIdByUMeng(11);

                showMoreWindow(v);
            }
        });
    }

    private void showMoreWindow(View view) {
        if (!(mContext instanceof Activity)) return;

        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow((Activity) mContext);
            mMoreWindow.init();
        }
        mMoreWindow.showMoreWindow(view, ScreenUtils.widthPixels(mContext) / 2);
        mMoreWindow.initClickListener(new MoreWindow.onClickListener() {
            @Override
            public void clickInquire() {//违章查询
                MobUtils.getInstance().eventIdByUMeng(12);
                Act.getInstance().gotoIntent(mContext, ViolationActivity.class);
            }

            @Override
            public void clickScan() {
                MobUtils.getInstance().eventIdByUMeng(14);
                Act.getInstance().gotoIntent(mContext, Codequery.class);
            }
        });
    }


    private void setupViolationCarItem(View view, final UserCarInfoBean userCarInfoBean) {
        LinearLayout layContent = (LinearLayout) view.findViewById(R.id.lay_content);
        layContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLayContent(userCarInfoBean);
            }
        });
        TextView tvPlateNumber = (TextView) view.findViewById(R.id.tv_plate_number);
        TextView tvType = (TextView) view.findViewById(R.id.tv_motorcycle_type);
        TextView tvBrand = (TextView) view.findViewById(R.id.tv_motorcycle_brand);

        tvPlateNumber.setText(userCarInfoBean.getCarnum());
        //小写转化为大写
        tvPlateNumber.setTransformationMethod(new AllCapTransformationMethod());

        String carType = VehicleTypeTools.switchVehicleType(userCarInfoBean.getCarnumtype());
        tvType.setText(carType);
        tvBrand.setText(userCarInfoBean.getEnginenum());

        TextView tvTotamt = (TextView) view.findViewById(R.id.tv_totamt);
        TextView tvTotcent = (TextView) view.findViewById(R.id.tv_totcent);
        TextView tvTotcount = (TextView) view.findViewById(R.id.tv_totcount);
        //未处理总金额
        String price = StringUtils.getPriceString(userCarInfoBean.getUntreatamt());
        tvTotamt.setText(price);
        //未处理总分值
        tvTotcent.setText(TextUtils.isEmpty(userCarInfoBean.getUntreatcent())
                ? "0" : userCarInfoBean.getUntreatcent());
        //未处理总笔数
        tvTotcount.setText(TextUtils.isEmpty(userCarInfoBean.getUntreatcount())
                ? "0" : userCarInfoBean.getUntreatcount());
    }

    /**
     * 点击监听
     */
    private void onClickLayContent(UserCarInfoBean userCarInfoBean) {
        MobclickAgent.onEvent(mContext, Config.getUMengID(1));

        LoginData.getInstance().mHashMap.put("IllegalViolationName", userCarInfoBean.getCarnum());
        LoginData.getInstance().mHashMap.put("carnum", userCarInfoBean.getCarnum());
        LoginData.getInstance().mHashMap.put("enginenum", userCarInfoBean.getEnginenum());
        LoginData.getInstance().mHashMap.put("carnumtype", userCarInfoBean.getCarnumtype());

        ViolationDTO dto = new ViolationDTO();
        dto.setCarnum(RSAUtils.strByEncryption(userCarInfoBean.getCarnum(), true));
        dto.setEnginenum(RSAUtils.strByEncryption(userCarInfoBean.getEnginenum(), true));
        dto.setCarnumtype(userCarInfoBean.getCarnumtype());

        Intent intent = new Intent(mContext, ViolationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("params", dto);
        intent.putExtras(bundle);
        intent.putExtra("plateNum", userCarInfoBean.getCarnum());
        mContext.startActivity(intent);
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
