package com.zantong.mobile.widght;

import android.os.CountDownTimer;
import android.widget.TextView;

public class DanceWageTimer extends CountDownTimer {

	private TextView textView;
	private int totalWage;
	private int startNum = 0;//从多少开始累加
	private int decimalFlag = 0;//记录小数部分的累加

	public DanceWageTimer(long millisInFuture, long countDownInterval, TextView textView, int totalWage) {
		super(millisInFuture, countDownInterval);
		this.textView = textView;
		this.totalWage = totalWage;
		if (totalWage > 20){
			startNum = totalWage - 20;
		}
	}

	@Override
	public void onFinish() {
		textView.setText(String.valueOf(totalWage));
	}

	@Override
	public void onTick(long arg0) {
		if (decimalFlag < totalWage - startNum){
			decimalFlag++;
			textView.setText(String.valueOf(startNum + decimalFlag));
		}


	}

}
