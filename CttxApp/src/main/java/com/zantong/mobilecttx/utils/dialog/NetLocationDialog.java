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
public class NetLocationDialog extends Dialog {
    //定义回调事件，用于dialog的点击事件
    public interface OnChooseDialogListener {
        void back(String[] name);
    }

    private String[] name;
    private OnChooseDialogListener chooseDialogListener;
    EditText etName;
    private int layou;
    private NetlocationPicker mNetlocationPicker;

    public NetLocationDialog(Context context, String[] name, OnChooseDialogListener chooseDialogListener) {
        super(context);
        this.name = name;
        this.chooseDialogListener = chooseDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.netlocationpickers);
//	                setTitle(name);
        mNetlocationPicker = (NetlocationPicker) findViewById(R.id.citypicker);
        Button btn_choose = (Button) findViewById(R.id.btn_choose);
        if (name != null) {
            mNetlocationPicker.setDefaultLocation(name);
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
                String[] data = new String[5];
                data[0] = mNetlocationPicker.getProvince();
                data[1] = mNetlocationPicker.getDataBean().getNetLocationCode();
                data[2] = mNetlocationPicker.getCity();
                data[3] = mNetlocationPicker.getDataBean().getNetLocationXiang();
                data[4] = mNetlocationPicker.getDataBean().getNetLocationNumber();
//                data[2] = mCityPicker.getDistrinc();
                if (chooseDialogListener != null) chooseDialogListener.back(data);
                NetLocationDialog.this.dismiss();
            } catch (Exception e) {

            }

        }
    };

}
