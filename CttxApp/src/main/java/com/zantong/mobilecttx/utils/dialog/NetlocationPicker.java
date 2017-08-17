package com.zantong.mobilecttx.utils.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.daijia.bean.DistrictModel;
import com.zantong.mobilecttx.map.bean.NetLocationBean;
import com.zantong.mobilecttx.utils.dialog.WheelView.OnSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.util.log.LogUtils;

/**
 * container 3 wheelView implement timePicker
 * Created by JiangPing on 2015/6/17.
 */
public class NetlocationPicker extends LinearLayout {
    private WheelView mWheelYear;
    private WheelView mWheelMonth;
//    private WheelView mWheelDay;

    private String curWheelprovince = "徐汇区";
    private String curWheelCity = "习勤路支行";
    private String curWheelDistrinc = "宝山区";

    private int mCurrProvinceIndex = -1;
    private int mTemCityIndex = -1;
    private int mDistrincIndex = -1;

    private String selectProvince = "上海市长宁区";
    private String selectCity = "上海市长宁区延安西路支行";
    private String selectDistrinc = "宝山区";

    private Map<String, List<NetLocationBean.NetLocationElement.NetQuBean>> cityMap = new HashMap<>();
    private NetLocationBean.NetLocationElement.NetQuBean mNetQuBean;
    private Map<String, List<DistrictModel>> disTrincMap = new HashMap<>();
    private List<NetLocationBean.NetLocationElement.NetQuBean> dataList = new ArrayList<>();


    public NetlocationPicker(Context context) {
        this(context, null);
    }

    public NetlocationPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getProvince() {
        return selectProvince;
    }

    public String getCity() {
        return selectCity;
    }

    public String getDistrinc() {
        return selectDistrinc;
    }

    public NetLocationBean.NetLocationElement.NetQuBean getDataBean() {
        return mNetQuBean;
    }

    public void setDefaultLocation(String[] selectweight) {
        if (selectweight.length != 3) {
            mWheelYear.setDefault(0);
            mWheelMonth.setDefault(0);
//    		mWheelDay.setDefault(0);
        } else {
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

        LayoutInflater.from(getContext()).inflate(R.layout.netlocation_picker, this);
        mWheelYear = (WheelView) findViewById(R.id.year);
        mWheelMonth = (WheelView) findViewById(R.id.month);
//        mWheelDay = (WheelView) findViewById(R.id.day);

        curWheelprovince = getProvinceData().get(0);
        mWheelYear.setData(getProvinceData());
        mWheelYear.setDefault(0);
        mWheelMonth.setData(getCityData(curWheelprovince));
        selectProvince = curWheelprovince;
        selectCity = getCityData(curWheelprovince).get(0);
        dataList = cityMap.get(selectProvince);
        mNetQuBean = cityMap.get(selectProvince).get(0);
        mWheelMonth.setDefault(0);

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
                    dataList = cityMap.get(selectProvince);
                    mWheelMonth.setDefault(0);
                    selectCity = mWheelMonth.getSelectedText();
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
                    mNetQuBean = dataList.get(id);
                    if (selectCity == null || selectCity.equals(""))
                        return;
                }
            }
        });
//        mWheelDay.setOnSelectListener(new OnSelectListener() {
//
//			@Override
//			public void selecting(int id, String text) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void endSelect(int id, String text) {
//				if (text.equals("") || text == null)
//                    return;
//                if (mDistrincIndex != id) {
//					mDistrincIndex = id;
//					selectProvince = mWheelYear.getSelectedText();
//					selectCity = mWheelMonth.getSelectedText();
//					selectDistrinc = mWheelDay.getSelectedText();
//	                if (selectDistrinc == null || selectDistrinc.equals(""))
//	                     return;
//
//                	}
//
//			}
//		});
    }

    private ArrayList<String> getProvinceData() {
        ArrayList<String> list = new ArrayList<>();
        if (PublicData.getInstance().mNetLocationBean.getNetLocationlist() != null) {
            for (int i = 0; i < PublicData.getInstance().mNetLocationBean.getNetLocationlist().size(); i++) {

                list.add(PublicData.getInstance().mNetLocationBean.getNetLocationlist().get(i).getNetLocationQu());

                cityMap.put(PublicData.getInstance().mNetLocationBean.getNetLocationlist().get(i).getNetLocationQu(),
                        PublicData.getInstance().mNetLocationBean.getNetLocationlist().get(i).getListNet());
            }
        } else {
            LogUtils.i("网点数据是空");
        }

        return list;
    }

    private ArrayList<String> getCityData(String province) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < cityMap.get(province).size(); i++) {
            list.add(cityMap.get(province).get(i).getNetLocationName());
        }
        return list;
    }

    private ArrayList<String> getDisTrincData(String city) {

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < disTrincMap.get(city).size(); i++) {
            list.add(disTrincMap.get(city).get(i).getName());
        }
        return list;
    }
}
