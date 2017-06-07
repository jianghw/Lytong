package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;

/**
 * 办卡进度查询的进度图
 * @author zyb
 *
 *
 *    *  *   *  *
 *  *      *      *
 *  *             *
 *   *           *
 *      *     *
 *         *
 *
 *
 * create at 17/5/10 上午10:40
 */
public class CustomFlowView extends ImageView {

	private Paint mPaint = null;

	public void setValue(int i){
		invalidate();
	}
	public CustomFlowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);//左上右下
		mPaint.setColor(Color.WHITE);
		mPaint.setAntiAlias(true);
		canvas.drawCircle(60,34,16,mPaint);
		canvas.drawCircle(60,174,16,mPaint);
		canvas.drawCircle(60,314,16,mPaint);
		canvas.drawCircle(60,454,16,mPaint);

		canvas.drawLine(60,30,60,454,mPaint);

		mPaint.setTextSize(32);
		canvas.drawText("已受理",120,45,mPaint);
		canvas.drawText("审批中（2-3工作日）",150,115,mPaint);
		canvas.drawText("审批通过",120,185,mPaint);
		canvas.drawText("开户成功（2-3工作日）",150,255,mPaint);
		canvas.drawText("已制卡",120,325,mPaint);
		canvas.drawText("已邮寄或待领取（1-2工作日）",150,395,mPaint);
		canvas.drawText("已到达领卡网点",120,465,mPaint);

		mPaint.setPathEffect(null);
		Resources resources = getResources();
		mPaint.setColor(resources.getColor(R.color.red));
		canvas.drawCircle(60,34,12,mPaint);
		canvas.drawCircle(60,174,12,mPaint);
		canvas.drawCircle(60,314,12,mPaint);
		canvas.drawCircle(60,454,12,mPaint);


	}


	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (hasWindowFocus) {
			invalidate();
		}
		super.onWindowFocusChanged(hasWindowFocus);
	}
	@Override
	public void invalidate() {
		initView();
		super.invalidate();
	}

	private void initView() {
		mPaint = new Paint();
		mPaint.setStrokeWidth(4f);
		mPaint.setColor(Color.WHITE);
	}

}