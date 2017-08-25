package com.zantong.mobilecttx.utils.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zantong.mobilecttx.R;


public class SurePayPopupWindows extends PopupWindow {
	private View view;

	public SurePayPopupWindows(final Context mContext, View parent, final OnClickListener btn1, final OnClickListener btn2) {

		view = View
				.inflate(mContext, R.layout.sure_pay_popupwindows, null);
		view.startAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
//		ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
//				R.anim.left_to_right));
//		ll_popup.setAnimation(AnimationUtils.loadAnimation(mContext,
//				R.style.popwin_anim_style));
//		ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
//				R.anim.left_to_right));

		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();

		RelativeLayout pay_type_choose_rl = (RelativeLayout) view
				.findViewById(R.id.pay_type_choose_rl);
		Button next_btn = (Button) view
				.findViewById(R.id.next_btn);
//		Button bt2 = (Button) view
//				.findViewById(R.id.next_btn);
//		Button bt3 = (Button) view
//				.findViewById(R.id.item_popupwindows_cancel);
//
//		if(ConnData.userSex){
//			line_bg.setBackgroundColor(mContext.getResources().getColor(R.color.appmain));
//		}else{
//			line_bg.setBackgroundColor(mContext.getResources().getColor(R.color.appNmain));
//		}
		pay_type_choose_rl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				btn1.onClick(v);
				dismiss();
			}
		});
		next_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				btn2.onClick(v);
				dismiss();
			}
		});
//		bt3.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				dismiss();
//			}
//		});
//
//		view.setOnTouchListener(new OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//            	int height = view.findViewById(R.id.ll_popup).getTop();
//                int y=(int) event.getY();
//                if(event.getAction()== MotionEvent.ACTION_UP){
//                    if(y<height){
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

	}
}
