package com.zantong.mobilecttx.utils;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/5/17.
 */
public class TextPopupKeyboardUtil {

    private Activity mActivity;

    private KeyboardView keyboardView;
    private Keyboard keyboard;// 全键盘包括数字和字母

    private View editText1;

    public TextPopupKeyboardUtil(Activity mActivity) {
        this.mActivity = mActivity;
        this.keyboard = new Keyboard(mActivity, R.xml.province);
//        this.keyboard = new Keyboard(mActivity, R.xml.small_keyboard);
    }

    public void attachTo(View editText, boolean isAuto) {
        this.editText1 = editText;
        if(editText instanceof EditText){
            hideSystemSofeKeyboard((EditText) this.editText1);
            setAutoShowOnFocs(isAuto);
        }else{
            setAutoShowOnClike(isAuto);
        }

    }

    public void setAutoShowOnFocs(boolean enable) {
        if (editText1 == null)
            return;
        if (enable)
            editText1.setOnFocusChangeListener(onFocusChangeListener1);
        else
            editText1.setOnFocusChangeListener(null);
    }

    public void setAutoShowOnClike(boolean enable){
        if (editText1 == null)
            return;
        if (enable){
            editText1.setOnClickListener(onClickListener);
            editText1.setOnFocusChangeListener(onFocusChangeListener1);
        }
        else{
            editText1.setOnClickListener(null);
            editText1.setOnFocusChangeListener(null);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            showSoftKeyboard();
//            editText1.setFocusable(true);

        }
    };
    View.OnFocusChangeListener onFocusChangeListener1 = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus)
                showSoftKeyboard();
            else
                hideSoftKeyboard();
        }
    };

    View viewContainer;

    public void showSoftKeyboard() {

        if (viewContainer == null) {
            viewContainer = mActivity.getLayoutInflater().inflate(R.layout.keyboardview_layout, null);
        } else {
            if (viewContainer.getParent() != null)
                return;
        }

        FrameLayout frameLayout = (FrameLayout) mActivity.getWindow().getDecorView();
        KeyboardView keyboardView = (KeyboardView) viewContainer.findViewById(R.id.keyboard_view);
        this.keyboardView = keyboardView;
        this.keyboardView.setKeyboard(keyboard);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener2);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        frameLayout.addView(viewContainer, lp);
        //viewContainer.setVisibility(View.GONE);
        viewContainer.setAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.down_to_up));
    }


    public void hideSoftKeyboard() {
        if (viewContainer != null && viewContainer.getParent() != null) {
            ((ViewGroup) viewContainer.getParent()).removeView(viewContainer);
        }
    }

    public boolean isShowing() {
        if (viewContainer == null)
            return false;
        return viewContainer.getVisibility() == View.VISIBLE;
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
    }

    private KeyboardView.OnKeyboardActionListener listener2 = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (editText1 != null) {
                if(editText1 instanceof EditText){
                    keyCode_delect(primaryCode, (EditText) editText1);
                }else if(editText1 instanceof TextView){
                    keyCodeDo(primaryCode, editText1);
                }
            }
            keyboardView.postInvalidate();
        }
    };


    private void keyCodeDo(int primaryCode, View edText){

        if(primaryCode >= 133 && (edText instanceof TextView)){
//            ((TextView) edText).setText("");
        }else{
            ((TextView) edText).setText(Tools.getProvinceStr(primaryCode));
        }
    }

    /**
     * 判断回退键 和大小写切换
     *
     * @param primaryCode
     * @param edText
     */
    private void keyCode_delect(int primaryCode, EditText edText) {

        Editable editable = edText.getText();
        int start = edText.getSelectionStart();
        if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
            if (edText.hasFocus()) {
                if (!TextUtils.isEmpty(editable)) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            }

        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
            keyboardView.setKeyboard(keyboard);
        } else {
            if (edText.hasFocus() && primaryCode < 132) {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    }

}
