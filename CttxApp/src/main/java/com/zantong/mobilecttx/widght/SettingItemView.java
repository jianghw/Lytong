package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;

/**
 * Created by zhoujie on 2016/10/20.
 */

public class SettingItemView extends RelativeLayout {

    private TextView title;
    private TextView titleHint;
    private RelativeLayout layout;

    private String strTitle;
    private String strTitleHint;

    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.widget_setting_item, this, true);
        layout = (RelativeLayout) view.findViewById(R.id.setting_item_layout);
        title = (TextView) view.findViewById(R.id.setting_item_title);
        titleHint = (TextView) view.findViewById(R.id.setting_item_title_hint);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        strTitle = typedArray.getString(R.styleable.SettingItemView_titleS);
        strTitleHint = typedArray.getString(R.styleable.SettingItemView_titleHintS);
        title.setTextColor(typedArray.getColor(R.styleable.SettingItemView_titleSC,
                getResources().getColor(R.color.colorAddCarGrayNumberFont)));
        titleHint.setTextColor(typedArray.getColor(R.styleable.SettingItemView_titleHintC,
                getResources().getColor(R.color.gray_b2)));
        typedArray.recycle();

        if(TextUtils.isEmpty(strTitle)){
            strTitle = "";
        }
        title.setText(strTitle);
        if(TextUtils.isEmpty(strTitleHint)){
            strTitleHint = "";
        }
        titleHint.setText(strTitleHint);
    }

    public void setRightText(String rightText){
        titleHint.setText(rightText);
    }

    public String getRightText(){
        return titleHint.getText().toString().trim();
    }

    public void setLeftTextColor(int leftTextColor){
        title.setTextColor(leftTextColor);
    }

    public void setRightTextColor(int rightTextColor){
        titleHint.setTextColor(rightTextColor);
    }

    public void setItemBackgroundColor(int backgroundColor){
        layout.setBackgroundColor(backgroundColor);
    }

}
