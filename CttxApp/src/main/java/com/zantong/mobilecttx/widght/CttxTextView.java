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

/**
 * Created by zhoujie on 2016/10/20.
 */

public class CttxTextView extends LinearLayout {

    private TextView mTitle;
    private TextView mContent;
    private ImageView mImgFlag;
    private LinearLayout layout;

    private String strTitle;
    private String strContent;
    private String strContentHint;

    public CttxTextView(Context context) {
        super(context);
    }

    public CttxTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CttxTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_textview, this, true);
        layout = (LinearLayout) view.findViewById(R.id.view_textview_layout);
        mTitle = (TextView) view.findViewById(R.id.view_textview_title);
        mContent = (TextView) view.findViewById(R.id.view_textview_content);
        mImgFlag = (ImageView) view.findViewById(R.id.view_textview_flag);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CttxEditText);
        strTitle = typedArray.getString(R.styleable.CttxEditText_titletext);
        strContent = typedArray.getString(R.styleable.CttxEditText_contenttext);
        strContentHint = typedArray.getString(R.styleable.CttxEditText_hinttext);

        //设置输入类型
        int inputType = typedArray.getInt(R.styleable.CttxEditText_contentinputtype,-1);
        String inputStr = "";
        if (inputType == 0){//不限制
            mContent.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if (inputType == 1){//数字
            mContent.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if (inputType == 2){//字母或数字
            mContent.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
            inputStr = "1234567890qwertyuiopasdfghjklzxcvbnm";
//            mContent.setKeyListener(DigitsKeyListener.getInstance("1234567890qwertyuiopasdfghjklzxcvbnm"));
        }else if (inputType == 3){//数字或X（身份证号）
            mContent.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
//            mContent.setKeyListener(DigitsKeyListener.getInstance("1234567890Xx"));
        }else if (inputType == 4){//纯字母
            mContent.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
//            mContent.setKeyListener(DigitsKeyListener.getInstance("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLXCVBNM"));
        }
        //设置限制字符、
        mContent.setFilters(new InputFilter[] { new InputFilter.LengthFilter(typedArray.getInt(R.styleable.CttxEditText_contentlength,16)) });



        boolean isVisibleImg =  typedArray.getBoolean(R.styleable.CttxEditText_imgflag,false);
        mImgFlag.setVisibility(isVisibleImg ? View.VISIBLE : View.GONE);
        typedArray.recycle();

        if(TextUtils.isEmpty(strTitle)){
            strTitle = "";
        }
        mTitle.setText(strTitle);

        if(TextUtils.isEmpty(strContentHint)){
            strContentHint = "";
        }
        if(TextUtils.isEmpty(strContent)){
            strContent = "";
        }
        mContent.setText(strContent);
    }

    public void setImgFlag(boolean isVisible){
        mImgFlag.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置输入框内容
     * @param content
     */
    public void setContentText(String content){
        mContent.setText(content);
    }

    /**
     * 获取输入内容
     * @return
     */
    public String getContentText(){
        return mContent.getText().toString();
    }


}
