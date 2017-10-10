package cn.qqtheme.framework.custom.picker;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.R;
import cn.qqtheme.framework.custom.picker.entity.City;
import cn.qqtheme.framework.custom.picker.entity.County;
import cn.qqtheme.framework.custom.picker.entity.Province;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 地区选择器
 */
public class AreaPicker extends LinkagePicker {

    //省市县数据
    private List<Province> mProvinceList = new ArrayList<>();

    public AreaPicker(Activity activity) {
        super(activity);
    }

    public AreaPicker(Activity activity, ArrayList<String> firstList,
                      ArrayList<ArrayList<String>> secondList) {
        super(activity, firstList, secondList);
    }

    public AreaPicker(Activity activity, ArrayList<String> firstList,
                      ArrayList<ArrayList<String>> secondList,
                      ArrayList<ArrayList<ArrayList<String>>> thirdList) {
        super(activity, firstList, secondList, thirdList);
    }



    public void initListData(List<Province> provinceList) {
        if (!mProvinceList.isEmpty()) this.mProvinceList.clear();
        this.mProvinceList.addAll(provinceList);

        int provinceSize = provinceList.size();
        //添加省 名字
        for (int x = 0; x < provinceSize; x++) {
            Province province = provinceList.get(x);
            firstList.add(province.getAreaName());

            ArrayList<String> xCities = new ArrayList<>();
            ArrayList<ArrayList<String>> xCounties = new ArrayList<>();

            ArrayList<City> cities = province.getCities();
            int citySize = cities.size();
            //添加地市 名字
            for (int y = 0; y < citySize; y++) {
                City city = cities.get(y);
                xCities.add(city.getAreaName());

                ArrayList<String> yCounties = new ArrayList<>();

                ArrayList<County> counties = city.getCounties();
                int countySize = counties.size();

                //添加区县 名字
                if (countySize == 0) {
                    yCounties.add(city.getAreaName());
                } else {
                    for (int z = 0; z < countySize; z++) {
                        yCounties.add(counties.get(z).getAreaName());
                    }
                }
                xCounties.add(yCounties);
            }
            secondList.add(xCities);
            thirdList.add(xCounties);
        }
    }

    /**
     * 覆盖父类  头部状态
     */
    @Nullable
    protected View makeHeaderView() {
        LinearLayout linearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.toPx(45f));
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(ConvertUtils.toPx(15f), 0, ConvertUtils.toPx(15f), 0);
        linearLayout.setClipToPadding(false);

        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setText("请选择地区");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextUtils.getContext().getResources().getColor(R.color.colorTvBlue_59b));

        Drawable drawableRight = ContextUtils.getContext().getResources().getDrawable(R.mipmap.ic_tv_close);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
//        textView.setCompoundDrawablePadding(ConvertUtils.toDp(45f));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onCancel();
            }
        });
        linearLayout.addView(textView);

        return linearLayout;
    }

}
