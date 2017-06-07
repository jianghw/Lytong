package com.zantong.mobilecttx.widght;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.interf.IBasePopWindow;
import com.zantong.mobilecttx.utils.UiHelpers;

/**
 * 菜单控件头部，封装了下拉动画
 *
 * @author Sandy
 *         create at 16/6/7 下午4:54
 */

public class ExpandTabView extends LinearLayout implements OnDismissListener {

    private ToggleButton selectedButton;
    private ToggleButton mToggleButton;
    private RelativeLayout mView;
    private Context mContext;
    private final int SMALL = 0;
    private int displayWidth;
    private int displayHeight;
    private PopupWindow popupWindow;

    public ExpandTabView(Context context) {
        super(context);
        init(context);
    }

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        displayHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 根据选择的位置设置tabitem显示的值
     */
    public void setTitle(String valueText) {
        mToggleButton.setText(valueText);
    }

    /**
     * 根据选择的位置获取tabitem显示的值
     */
    public String getTitle() {
        return mToggleButton.getText().toString();
    }

    /**
     * 设置tabitem的个数和初始值
     */
    public void setValue(String text, View view) {
        if (mContext == null) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);

        mView = new RelativeLayout(mContext);
        int maxHeight = (int) (displayHeight * 0.6);
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, maxHeight);
        rl.topMargin = 1;
        mView.addView(view, rl);
        mView.setTag(SMALL);
        mToggleButton = (ToggleButton) inflater.inflate(R.layout.view_toggle_button, this, false);
        addView(mToggleButton);
        UiHelpers.setTextViewIcon(mContext, mToggleButton, R.mipmap.icon_vio_down, R.dimen.ds_52, R.dimen.ds_32, UiHelpers.DRAWABLE_RIGHT);
        mToggleButton.setText(text);
        mToggleButton.setPadding(30,0,30,0);
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPressBack();
            }
        });

        //展开后的背景色
        mView.setBackgroundColor(mContext.getResources().getColor(R.color.popup_main_background));
        mToggleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // initPopupWindow();
                ToggleButton tButton = (ToggleButton) view;

                if (selectedButton != null && selectedButton != tButton) {
                    selectedButton.setChecked(false);
                    changeTextState(0);
                }
                selectedButton = tButton;
                startAnimation();
                if (mOnButtonClickListener != null && tButton.isChecked()) {
                    mOnButtonClickListener.onClick();
                }
            }
        });
    }

    private void startAnimation() {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(mView, displayWidth, displayHeight);
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
        }


        if (selectedButton.isChecked()) {
            changeTextState(1);
            if (!popupWindow.isShowing()) {
                showPopup();
            } else {
                popupWindow.setOnDismissListener(this);
                popupWindow.dismiss();
                hideView();
            }
        } else {
            changeTextState(0);
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                hideView();
            }
        }
    }

    private void changeTextState(int state) {
        if (selectedButton == null) {
            return;
        }
         switch (state) {
            case 0:
                selectedButton.setTextColor(getResources().getColor(R.color.gray_33));
                break;
            case 1:
                selectedButton.setTextColor(getResources().getColor(R.color.gray_66));
                break;
        }

    }

    private void showPopup() {
        View tView = mView.getChildAt(0);
        if (tView instanceof IBasePopWindow) {
            IBasePopWindow f = (IBasePopWindow) tView;
            f.show();
        }
        if (popupWindow.getContentView() != mView) {
            popupWindow.setContentView(mView);
        }
        popupWindow.showAsDropDown(this, 0, 0);
    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     */
    public boolean onPressBack() {
        if (popupWindow == null) {
            return false;
        }
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            hideView();
            if (selectedButton != null) {
                changeTextState(0);
                selectedButton.setChecked(false);
            }
            return true;
        } else {
            return false;
        }

    }

    private void hideView() {
        View tView = mView.getChildAt(0);
        if (tView instanceof IBasePopWindow) {
            IBasePopWindow f = (IBasePopWindow) tView;
            f.hide();
        }
    }



    @Override
    public void onDismiss() {
        showPopup();
        popupWindow.setOnDismissListener(null);
    }

    private OnButtonClickListener mOnButtonClickListener;

    /**
     * 设置tabitem的点击监听事件
     */
    public void setOnButtonClickListener(OnButtonClickListener l) {
        mOnButtonClickListener = l;
    }

    /**
     * 自定义tabitem点击回调接口
     */
    public interface OnButtonClickListener {
        public void onClick();
    }

}
