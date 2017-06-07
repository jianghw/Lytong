package com.zantong.mobilecttx.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.ToastUtils;


/**
 * 自定义dialog
 *
 * @author Wanghy
 */
public class MyChooseDialog extends Dialog {
    //定义回调事件，用于dialog的点击事件
    public interface OnChooseDialogListener {
        public void back(String name);
    }

    Context context;
    private String[] name;
    private OnChooseDialogListener chooseDialogListener;
    EditText etName;
    private int layou;
    private TimePicker timePicker;

    public MyChooseDialog(Context context, String[] name, OnChooseDialogListener chooseDialogListener) {
        super(context);
        this.name = name;
        this.chooseDialogListener = chooseDialogListener;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepickers);
//	                setTitle(name);
        timePicker = (TimePicker) findViewById(R.id.timepicker);
        Button btn_choose = (Button) findViewById(R.id.btn_choose);
        if (name != null){
            timePicker.setDefaultLocation(name);
        }
        btn_choose.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int currentDate = 0;
            int selDate =  0;
            try {

                int month = Integer.parseInt(timePicker.getMonth());
                int day = Integer.parseInt(timePicker.getDay());
                String tempMonth = String.valueOf(month);
                String tempDay = String.valueOf(day);
                if (month < 10) {
                    tempMonth = "0" + tempMonth;
                }
                if (day < 10) {
                    tempDay = "0" + tempDay;
                }
                currentDate = Integer.valueOf(DateUtils.getEndDate());
                selDate = Integer.valueOf(timePicker.getYear() + tempMonth + tempDay);
                if (currentDate < selDate){
                    ToastUtils.showShort(context,"初次领证日期错误");
                }else{
                    chooseDialogListener.back(String.valueOf(timePicker.getYear() + "-" + tempMonth + "-" + tempDay));
                }
                MyChooseDialog.this.dismiss();
            } catch (Exception e) {

            }

        }
    };

}
