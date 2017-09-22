package com.zantong.mobile.utils.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zantong.mobile.R;
import com.zantong.mobile.utils.Tools;

/**
 * 作者：王海洋
 * 时间：2016/6/7 09:39
 */
public class IOSpopwindow extends PopupWindow{

    public IOSpopwindow(final Context mContext, View parent) {

        View view = View
                .inflate(mContext, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in));

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        TextView line_bg = (TextView) view
                .findViewById(R.id.line_bg);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);

        line_bg.setBackgroundColor(mContext.getResources().getColor(R.color.appmain));
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public IOSpopwindow(final Context mContext, View parent, final View.OnClickListener btn1, final View.OnClickListener btn2) {

        final View view = View
                .inflate(mContext, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        final LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in));

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        TextView line_bg = (TextView) view
                .findViewById(R.id.line_bg);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);

        line_bg.setBackgroundColor(mContext.getResources().getColor(R.color.appmain));
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn1.onClick(v);
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn2.onClick(v);
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = ll_popup.getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
    public IOSpopwindow(final Context mContext, View parent, String btnText1, String btnText2, final View.OnClickListener btn1, final View.OnClickListener btn2) {

        final View view = View
                .inflate(mContext, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        final LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in));

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        TextView line_bg = (TextView) view
                .findViewById(R.id.line_bg);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        LinearLayout btll1 = (LinearLayout) view
                .findViewById(R.id.item_popupwindows_camera_ll);
        LinearLayout btll2 = (LinearLayout) view
                .findViewById(R.id.item_popupwindows_Photo_ll);

        line_bg.setBackgroundColor(mContext.getResources().getColor(R.color.appmain));
        bt1.setText(btnText1);
        bt2.setText(btnText2);
        if(btn2 == null){
            btll2.setVisibility(View.GONE);
        }
        if(btn1 == null){
            btll1.setVisibility(View.GONE);
        }
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn1.onClick(v);
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn2.onClick(v);
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = ll_popup.getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
    public IOSpopwindow(final Context mContext, View parent, String btnText1, String btnText2, final View.OnClickListener btn1, final View.OnClickListener btn2, String notice) {

        final View view = View
                .inflate(mContext, R.layout.item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        final LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in));

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        TextView line_bg = (TextView) view
                .findViewById(R.id.line_bg);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_notice);
        Button bt4 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        LinearLayout btll1 = (LinearLayout) view
                .findViewById(R.id.item_popupwindows_camera_ll);
        LinearLayout btll2 = (LinearLayout) view
                .findViewById(R.id.item_popupwindows_Photo_ll);
        LinearLayout noticell = (LinearLayout) view
                .findViewById(R.id.item_popupwindows_notice_ll);

        line_bg.setBackgroundColor(mContext.getResources().getColor(R.color.appmain));
        bt1.setText(btnText1);
        bt2.setText(btnText2);
        if(btn2 == null){
            btll2.setVisibility(View.GONE);
        }
        if(!Tools.isStrEmpty(notice)){
            bt3.setText(notice);
            noticell.setVisibility(View.VISIBLE);
        }else{
            noticell.setVisibility(View.GONE);
        }
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn1.onClick(v);
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn2.onClick(v);
                dismiss();
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = ll_popup.getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public IOSpopwindow(final Context mContext, View parent, final View.OnClickListener btn1) {

        final View view = View
                .inflate(mContext, R.layout.item_nomal_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
        final LinearLayout ll_popup = (LinearLayout) view
                .findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.push_bottom_in));

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        TextView line_bg = (TextView) view
                .findViewById(R.id.line_bg);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);

        line_bg.setBackgroundColor(mContext.getResources().getColor(R.color.appmain));
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn1.onClick(v);
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = ll_popup.getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }


}
