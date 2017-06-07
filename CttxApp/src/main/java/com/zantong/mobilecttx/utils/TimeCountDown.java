package com.zantong.mobilecttx.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * 倒计时工具类
 * 
 * @author Kyle
 * @date 2015-5-5 下午7:17:16
 */
public class TimeCountDown extends CountDownTimer {
	private View mCountView;
	private static final long MILLIS_IN_FUTURE = 3000;
	private static final long COUNT_DOWN_INTERVAL = 1000;
	private long countDownInterval;
	Animation mAnimation;
	Animation mAnimation2;

	/**
	 * 构造函数
	 * 
	 * @param mCountView
	 *            倒计时的View
	 * @param millisInFuture
	 *            总时长
	 * @param countDownInterval
	 *            倒计时间隔事件
	 */
	public TimeCountDown(View mCountView, long millisInFuture,
						 long countDownInterval) {
		super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		this.mCountView = mCountView;
		this.countDownInterval = countDownInterval;
	}

	/**
	 * 构造函数
	 * 
	 * @param mCountView
	 *            倒计时的View
	 */
	public TimeCountDown(View mCountView,Animation animation,Animation animation2) {
		this(mCountView, MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL);
		this.mAnimation = animation;
		this.mAnimation2 = animation2;
	}

	@Override
	public void onFinish() {
		// 计时完毕时触发
		if (mCountView instanceof TextView) {
			((TextView) mCountView).setClickable(true);
		}
	}

	@Override
	public void onTick(long millisUntilFinished) {
		// 计时过程显示
		if (millisUntilFinished % MILLIS_IN_FUTURE == 1){
			if (mCountView instanceof TextView) {
				mCountView.setAnimation(mAnimation);
			}
		}else if(millisUntilFinished % MILLIS_IN_FUTURE == 0){
			if (mCountView instanceof TextView) {
				mCountView.setAnimation(mAnimation2);
			}
		}

	}
}