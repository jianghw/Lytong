package com.zantong.mobilecttx.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.adapter.CarBrandAdapter;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.car.bean.CarBrandBean;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.CarBrandParser;
import com.zantong.mobilecttx.utils.CharacterParser;
import com.zantong.mobilecttx.utils.PinyinComparator;
import com.zantong.mobilecttx.widght.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class CarBrandActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements IBaseView {
    public static final int REQUEST_CODE = 10001;
    public static final int RESULT_CODE = 10002;
    public static final String CAR_BRAND_BEAN = "CarBrandBean";


    private ListView sortListView;
    private SideBar sideBar;
    private CarBrandAdapter mAdapter;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<CarBrandBean> sourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public void initView() {
        setTitleText("品牌");
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Intent intent = new Intent(CarBrandActivity.this, AddCarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CAR_BRAND_BEAN, (CarBrandBean)mAdapter.getItem(position));
                intent.putExtras(bundle);
                setResult(RESULT_CODE, intent);
                finish();
            }
        });

    }

    @Override
    public void initData() {
        sourceDateList = new ArrayList<CarBrandBean>();
        try {
            sourceDateList = CarBrandParser.parse(getAssets().open("car_brand.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sourceDateList = filledData(sourceDateList);

        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);
        mAdapter = new CarBrandAdapter(this, sourceDateList);
        sortListView.setAdapter(mAdapter);
    }

    /**
     * 为ListView填充数据
     * @param list
     * @return
     */
    private List<CarBrandBean> filledData(List<CarBrandBean> list){
        List<CarBrandBean> mSortList = new ArrayList<CarBrandBean>();

        for(int i = 0; i < list.size(); i ++){
            CarBrandBean sortModel = new CarBrandBean();
            sortModel.setId(list.get(i).getId());
            sortModel.setBrandName(list.get(i).getBrandName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(list.get(i).getBrandName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setBrandPinYin(sortString.toUpperCase());
            }else{
                sortModel.setBrandPinYin("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        List<CarBrandBean> filterDateList = new ArrayList<CarBrandBean>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList = sourceDateList;
        }else{
            filterDateList.clear();
            for(CarBrandBean sortModel : sourceDateList){
                String name = sortModel.getBrandName();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        mAdapter.updateListView(filterDateList);
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_car_brand;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
