package com.zantong.mobilecttx.utils.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zantong.mobilecttx.application.MemoryData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.card.bean.CityModel;
import com.zantong.mobilecttx.daijia.bean.DistrictModel;
import com.zantong.mobilecttx.utils.dialog.WheelView.OnSelectListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * container 3 wheelView implement timePicker
 * Created by JiangPing on 2015/6/17.
 */
public class CityPicker extends LinearLayout {
    private WheelView mWheelYear;
    private WheelView mWheelMonth;
    private WheelView mWheelDay;

    private String curWheelprovince="上海市";
    private String curWheelCity="上海市";
    private String curWheelDistrinc="宝山区";

	private int mCurrProvinceIndex = -1;
	private int mTemCityIndex = -1;
	private int mDistrincIndex = -1;

    Calendar calendar = Calendar.getInstance();
//    private int currentYear= Calendar.getInstance().get(Calendar.YEAR);
//    private int currentMonth= Calendar.getInstance().get(Calendar.MONTH);
//    private int currentDay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private String selectProvince="上海市";
    private String selectCity="上海市";
    private String selectDistrinc="宝山区";
	private Map<String, List<CityModel>> cityMap = new HashMap<>();
	private Map<String, List<DistrictModel>> disTrincMap = new HashMap<>();


    public CityPicker(Context context) {
        this(context, null);
    }

    public CityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getProvince(){
    	return selectProvince;
    }
    public String getCity(){
    	return selectCity;
    }
    public String getDistrinc(){
    	return selectDistrinc;
    }

    public void setDefaultLocation(String[]selectweight){
    	if(selectweight.length!=3){
    		mWheelYear.setDefault(0);
    		mWheelMonth.setDefault(0);
    		mWheelDay.setDefault(0);
    	}else{
//    		mWheelYear.setDefault(Integer.parseInt(selectweight[0])-1);
//    		mWheelMonth.setDefault(Integer.parseInt(selectweight[1])-1);
//    		mWheelDay.setDefault(Integer.parseInt(selectweight[2])-1);
//    		this.selectYear=selectweight[0];
//    		this.selectMonth=selectweight[1];
//    		this.selectDay=selectweight[2];
    	}
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);
        mWheelYear = (WheelView) findViewById(R.id.year);
        mWheelMonth = (WheelView) findViewById(R.id.month);
        mWheelDay = (WheelView) findViewById(R.id.day);

        mWheelYear.setData(getProvinceData());
        mWheelYear.setDefault(23);
        mWheelMonth.setData(getCityData(curWheelprovince));
        mWheelMonth.setDefault(0);
        mWheelDay.setData(getDisTrincData(curWheelCity));
        mWheelDay.setDefault(0);
        mWheelYear.setOnSelectListener(new OnSelectListener() {

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub

			}

			@Override
			public void endSelect(int id, String text) {
				 if (text.equals("") || text == null)
	                    return;
	                if (mCurrProvinceIndex != id) {
						mCurrProvinceIndex = id;
	                    selectProvince = mWheelYear.getSelectedText();
	                    if (selectProvince == null || selectProvince.equals(""))
	                        return;
	                    mWheelMonth.resetData(getCityData(selectProvince));
						mWheelMonth.setDefault(0);
						selectCity = mWheelMonth.getSelectedText();
	                    mWheelDay.resetData(getDisTrincData(selectCity));
						mWheelDay.setDefault(0);
						selectDistrinc = mWheelDay.getSelectedText();
	                }

				 }
		});
        mWheelMonth.setOnSelectListener(new OnSelectListener() {

			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub

			}

			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null)
                    return;
                if (mTemCityIndex != id) {
					mTemCityIndex = id;
	                	selectProvince = mWheelYear.getSelectedText();
						selectCity = mWheelMonth.getSelectedText();
						selectDistrinc = mWheelDay.getSelectedText();
	                    if (selectCity == null || selectCity.equals(""))
	                        return;
	                    mWheelDay.resetData(getDisTrincData(selectCity));
						mWheelDay.setDefault(0);
                	}

				}
		});
        mWheelDay.setOnSelectListener(new OnSelectListener() {
			
			@Override
			public void selecting(int id, String text) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null)
                    return;
                if (mDistrincIndex != id) {
					mDistrincIndex = id;
					selectProvince = mWheelYear.getSelectedText();
					selectCity = mWheelMonth.getSelectedText();
					selectDistrinc = mWheelDay.getSelectedText();
	                if (selectDistrinc == null || selectDistrinc.equals(""))
	                     return;

                	}
				
			}
		});
    }

    private ArrayList<String> getProvinceData() {
        ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < MemoryData.getInstance().provinceModel.size(); i++) {
			list.add(MemoryData.getInstance().provinceModel.get(i).getName());
			cityMap.put(MemoryData.getInstance().provinceModel.get(i).getName(), MemoryData.getInstance().provinceModel.get(i).getCityList());
		}
        return list;
    }

    private ArrayList<String> getCityData(String province) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < cityMap.get(province).size(); i++) {
            list.add(cityMap.get(province).get(i).getName());
			disTrincMap.put(cityMap.get(province).get(i).getName(), cityMap.get(province).get(i).getDistrictList());
        }
        return list;
    }

    private ArrayList<String> getDisTrincData(String city) {
        //ignore condition
    	
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < disTrincMap.get(city).size(); i++) {
            list.add(disTrincMap.get(city).get(i).getName());
        }
        return list;
    }
}
