package com.zantong.mobilecttx.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zantong.mobilecttx.R;


/**
 * 自定义dialog
 *
 * @author Wanghy
 */
public class CityDialog extends Dialog {
    //定义回调事件，用于dialog的点击事件
    public interface OnChooseDialogListener {
        public void back(String[] name);
    }

    private String[] name;
    private OnChooseDialogListener chooseDialogListener;
    EditText etName;
    private int layou;
    private CityPicker mCityPicker;

    public CityDialog(Context context, String[] name, OnChooseDialogListener chooseDialogListener) {
        super(context);
        this.name = name;
        this.chooseDialogListener = chooseDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citypickers);
//	                setTitle(name);
        mCityPicker = (CityPicker) findViewById(R.id.citypicker);
        Button btn_choose = (Button) findViewById(R.id.btn_choose);
        if (name != null){
            mCityPicker.setDefaultLocation(name);
        }
        btn_choose.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {

//                int month = Integer.parseInt(mCityPicker.getMonth());
//                int day = Integer.parseInt(mCityPicker.getDay());
//                String tempMonth = String.valueOf(month);
//                String tempDay = String.valueOf(day);
//                if (month < 10) {
//                    tempMonth = "0" + tempMonth;
//                }
//                if (day < 10) {
//                    tempDay = "0" + tempDay;
//                }
                String[] data = new String[3];
                data[0] = mCityPicker.getProvince();
                data[1] = mCityPicker.getCity();
                data[2] = mCityPicker.getDistrinc();
                chooseDialogListener.back(data);
                CityDialog.this.dismiss();
            } catch (Exception e) {

            }

        }
    };

}
