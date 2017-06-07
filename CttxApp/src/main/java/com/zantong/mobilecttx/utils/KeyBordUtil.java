package com.zantong.mobilecttx.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.R;

/**
 * Created by Administrator on 2016/5/16.
 */
public class KeyBordUtil {

    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private TextView tv;

    public KeyBordUtil(Activity at, Context mContext, TextView tv){
        this.tv = tv;
        mKeyboard = new Keyboard(mContext, R.xml.province);
        mKeyboardView = (KeyboardView) at.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(true);
        mKeyboardView.setOnKeyboardActionListener(listener);
    }
    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
//            Log.e("why","1");
//            Editable ed = tv.getText();
//            int start = tv.getSelectionStart();
//            if(primaryCode == 133){
//                tv.setText("");
//            }else{
//                ed.clear();
//                ed.insert(start, Character.toString((char) primaryCode));
////                tv.setText(Character.toString((char) primaryCode));
//            }

        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
//            Editable ed = tv.getText();
            int start = tv.getSelectionStart();
            if(primaryCode == 133){
                tv.setText("");
            }else{
                tv.setText(Tools.getProvinceStr(primaryCode));
//                ed.clear();
//                ed.insert(0, Character.toString((char) primaryCode));
//                tv.setText(Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    };

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

}
