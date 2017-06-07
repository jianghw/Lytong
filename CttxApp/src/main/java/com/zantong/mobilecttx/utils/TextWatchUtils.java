package com.zantong.mobilecttx.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;

/**
 * Created by zhengyingbing on 16/6/22.
 */
public class TextWatchUtils implements TextWatcher{

    private Button mBtn;

    public TextWatchUtils(Button btn){
        this.mBtn = btn;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString())){
            mBtn.setEnabled(true);
        }else{
            mBtn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
