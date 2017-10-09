package com.tzly.annual.base.custom.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.tzly.annual.base.R;
import com.tzly.annual.base.custom.dialog.WheelView.OnSelectListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * container 3 wheelView implement timePicker
 * Created by JiangPing on 2015/6/17.
 */
public class TimePicker extends LinearLayout {
    private WheelView mWheelYear;
    private WheelView mWheelMonth;
    private WheelView mWheelDay;

    private int curWheelYear = -1;
    private int curWheelMonth = -1;
    private int curWheelDay = -1;

    Calendar calendar = Calendar.getInstance();
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private String selectYear = 1990 + "";
    private String selectMonth = currentMonth + 1 + "";
    private String selectDay = currentDay + "";


    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public String getYear() {
        return selectYear;
    }

    public String getMonth() {
        return selectMonth;
    }

    public String getDay() {
        return selectDay;
    }

    public void setDefaultLocation(String[] selectweight) {
        if (selectweight.length != 3) {
            mWheelYear.setDefault(0);
            selectYear = currentYear + "";
            mWheelMonth.setDefault(currentMonth);
            mWheelDay.setDefault(currentDay - 1);
        } else {
            mWheelYear.setDefault(currentYear - Integer.parseInt(selectweight[0]));
            mWheelMonth.setDefault(Integer.parseInt(selectweight[1]) - 1);
            mWheelDay.setDefault(Integer.parseInt(selectweight[2]) - 1);
            this.selectYear = selectweight[0];
            this.selectMonth = selectweight[1];
            this.selectDay = selectweight[2];
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater.from(getContext()).inflate(R.layout.custom_wheel_time, this);
        mWheelYear = (WheelView) findViewById(R.id.year);
        mWheelMonth = (WheelView) findViewById(R.id.month);
        mWheelDay = (WheelView) findViewById(R.id.day);

        mWheelYear.setData(getYearData());
        mWheelMonth.setData(getMonthData());
        mWheelDay.setData(getDayData(currentYear + "", currentMonth + ""));
        mWheelYear.setOnSelectListener(new OnSelectListener() {

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub

            }

            @Override
            public void endSelect(int id, String text) {
                if (text == null || text.equals(""))
                    return;
                if (curWheelYear != id) {
                    curWheelYear = id;
                    selectYear = mWheelYear.getSelectedText();
                    selectMonth = mWheelMonth.getSelectedText();
                    selectDay = mWheelDay.getSelectedText();
                    if (selectYear == null || selectYear.equals(""))
                        return;
                    mWheelMonth.setData(getMonthData());
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
                if (text == null || text.equals(""))
                    return;
                if (curWheelMonth != id) {
                    curWheelMonth = id;
                    selectYear = mWheelYear.getSelectedText();
                    selectMonth = mWheelMonth.getSelectedText();
                    selectDay = mWheelDay.getSelectedText();
                    if (selectMonth == null || selectMonth.equals(""))
                        return;
                }
            }
        });
        mWheelDay.setOnSelectListener(new WheelView.OnSelectListener() {

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub
            }

            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                if (curWheelDay != id) {
                    curWheelDay = id;
                    selectYear = mWheelYear.getSelectedText();
                    selectMonth = mWheelMonth.getSelectedText();
                    selectDay = mWheelDay.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                }
            }
        });
    }

    private ArrayList<String> getYearData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i > 0; i--) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private ArrayList<String> getDayData(String year, String month) {
        //ignore condition

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i <= calDayByYearAndMonth(year, month) + 1; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    private int calDayByYearAndMonth(String dyear, String dmouth) {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM", Locale.SIMPLIFIED_CHINESE);
        Calendar rightNow = Calendar.getInstance();
        try {
            rightNow.setTime(simpleDate.parse(dyear + "/" + dmouth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数
    }
}
