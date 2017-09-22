package com.zantong.mobile.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.car.adapter.CarBrandAdapter;
import com.zantong.mobile.car.bean.CarBrandBean;
import com.zantong.mobile.utils.CarBrandParser;
import com.zantong.mobile.utils.CharacterParser;
import com.zantong.mobile.utils.PinyinComparator;
import com.zantong.mobile.widght.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CarBrandActivity extends BaseJxActivity {

    public static final int REQUEST_CODE = 10001;
    public static final int RESULT_CODE = 10002;
    public static final String CAR_BRAND_BEAN = "CarBrandBean";

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private List<CarBrandBean> sourceDateList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private CarBrandAdapter mAdapter;
    private ListView mSortListView;
    private SideBar mSideBar;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_car_brand;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("品牌");

        initView();

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        initData();
    }

    protected void initView() {
        mSortListView = (ListView) findViewById(R.id.country_lvcountry);
        mSideBar = (SideBar) findViewById(R.id.sidrbar);
    }

    protected void initData() {
        if (sourceDateList == null) sourceDateList = new ArrayList<>();

        try {
            List<CarBrandBean> list = CarBrandParser.parse(getAssets().open("car_brand.xml"));
            List<CarBrandBean> dateList = filledData(list);
            if (!sourceDateList.isEmpty()) sourceDateList.clear();
            sourceDateList.addAll(dateList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);
        mAdapter = new CarBrandAdapter(this, sourceDateList);
        mSortListView.setAdapter(mAdapter);

        //设置右侧触摸监听
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mSortListView.setSelection(position);
                }
            }
        });

        mSortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = mAdapter.getItem(position);
                if (obj instanceof CarBrandBean) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(CAR_BRAND_BEAN, (CarBrandBean) obj);
                    intent.putExtras(bundle);
                    setResult(RESULT_CODE, intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void DestroyViewAndThing() {
        if (sourceDateList != null) sourceDateList.clear();
    }

    /**
     * 为ListView填充数据
     */
    private List<CarBrandBean> filledData(List<CarBrandBean> beanList) {
        List<CarBrandBean> mSortList = new ArrayList<>();

        for (int i = 0; i < beanList.size(); i++) {
            CarBrandBean carBrandBean = new CarBrandBean();
            carBrandBean.setId(beanList.get(i).getId());
            carBrandBean.setBrandName(beanList.get(i).getBrandName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(beanList.get(i).getBrandName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                carBrandBean.setBrandPinYin(sortString.toUpperCase());
            } else {
                carBrandBean.setBrandPinYin("#");
            }
            mSortList.add(carBrandBean);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     */
    private void filterData(String filterStr) {
        List<CarBrandBean> filterDateList = new ArrayList<CarBrandBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sourceDateList;
        } else {
            filterDateList.clear();
            for (CarBrandBean sortModel : sourceDateList) {
                String name = sortModel.getBrandName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        mAdapter.updateListView(filterDateList);
    }

}
