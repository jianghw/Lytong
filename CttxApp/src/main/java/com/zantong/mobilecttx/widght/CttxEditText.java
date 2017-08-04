package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;

import cn.qqtheme.framework.util.ViewUtils;

/**
 * 办卡输入框样式
 */
public class CttxEditText extends LinearLayout {

    private TextView mTitle;
    private EditText mContent;
    private ImageView mImgFlag;
    private LinearLayout layout;

    private String strTitle;
    private String strContent;
    private String strContentHint;

    public CttxEditText(Context context) {
        super(context);
    }

    public CttxEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CttxEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_edittext, this, true);
        layout = (LinearLayout) view.findViewById(R.id.view_edittext_layout);
        mTitle = (TextView) view.findViewById(R.id.view_edittext_title);
        mContent = (EditText) view.findViewById(R.id.view_edittext_content);
        mImgFlag = (ImageView) view.findViewById(R.id.view_edittext_flag);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CttxEditText);
        strTitle = typedArray.getString(R.styleable.CttxEditText_titletext);
        strContent = typedArray.getString(R.styleable.CttxEditText_contenttext);
        strContentHint = typedArray.getString(R.styleable.CttxEditText_hinttext);

        //设置输入类型
        int inputType = typedArray.getInt(R.styleable.CttxEditText_contentinputtype, -1);

        if (inputType == 0) {//不限制
            mContent.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (inputType == 1) {//数字
            mContent.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (inputType == 2) {//字母或数字
            mContent.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        } else if (inputType == 3) {//数字或X（身份证号）
            mContent.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
        } else if (inputType == 4) {//纯字母
            mContent.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        }
        //设置限制字符、
        mContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(typedArray.getInt(R.styleable.CttxEditText_contentlength, 16))});

        boolean isVisibleImg = typedArray.getBoolean(R.styleable.CttxEditText_imgflag, false);
        mImgFlag.setVisibility(isVisibleImg ? View.VISIBLE : View.GONE);

        boolean editEnable = typedArray.getBoolean(R.styleable.CttxEditText_edit_enable, true);
        if (!editEnable) {
            mContent.setFocusable(false);
            mContent.setFocusableInTouchMode(false);
        }

        typedArray.recycle();

        if (TextUtils.isEmpty(strTitle)) {
            strTitle = "";
        }
        mTitle.setText(strTitle);

        if (TextUtils.isEmpty(strContentHint)) {
            strContentHint = "";
        }
        mContent.setHint(strContentHint);
        if (TextUtils.isEmpty(strContent)) {
            strContent = "";
        }
        mContent.setText(strContent);

        ViewUtils.editTextInputSpace(mContent);
    }

    public void setImgFlag(boolean isVisible) {
        mImgFlag.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置输入框内容
     */
    public void setContentText(String content) {
        mContent.setText(content);
    }

    /**
     * 获取输入内容
     */
    public String getContentText() {
        return mContent.getText().toString().trim();
    }


}
