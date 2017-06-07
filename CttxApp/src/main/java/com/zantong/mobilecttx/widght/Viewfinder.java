package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;

public class Viewfinder extends ImageView {
	
	private Paint mPaint = null;
	private int mWidth = 0;
	private int mHeight = 0;
	private int mLeft = 0;
	private int mTop = 0;
	private int mRight = 0;
	private int mBottom = 0;
	private int maskColor = -1;
	private int titleColor = -1;
	private Bitmap mDrivingImg = null;
	private Bitmap mDriverImg = null;
	private String mTitle;

	private Rect mDriverSrcRect, mDriverDestRect;
	private Rect mDrivingSrcRect, mDrivingDestRect;

	int mOcrType;//拍照扫描的类型

	public void setValue(int i){
		mOcrType = i;
		invalidate();
	}
	public Viewfinder(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	private void initView() {
		mHeight = getHeight();
		mWidth = getWidth();
		if (mHeight > mWidth) {
			int tmp = mHeight;
			mHeight = mWidth;
			mWidth = tmp;
		}
		mPaint = new Paint();
		mPaint.setStrokeWidth(4f);
		mPaint.setColor(Color.WHITE);
		mLeft = mWidth * 10 / 100;
		mRight = mWidth * 90 / 100;
		mTop = mHeight * 8 / 100;
		mBottom = mHeight * 92 / 100;

		Resources resources = getResources();
		maskColor = resources.getColor(R.color.colorQueryOtherCar);
		titleColor = resources.getColor(R.color.white);
		mDrivingImg = BitmapFactory.decodeResource(resources, R.mipmap.img_xingsz_ocr_bg,null);
		mDriverImg = BitmapFactory.decodeResource(resources, R.mipmap.img_jiasz_ocr_bg,null);
		mTitle = "请将证件置于框内";
	}
	

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);//左上右下
		mPaint.setColor(Color.WHITE);
		canvas.drawLine(mLeft, mTop, mRight, mTop, mPaint);
		canvas.drawLine(mLeft, mTop, mLeft, mBottom, mPaint);
		canvas.drawLine(mRight, mTop, mRight, mBottom, mPaint);
		canvas.drawLine(mLeft, mBottom, mRight, mBottom, mPaint);
		mPaint.setPathEffect(null);
		mPaint.setColor(maskColor);
		canvas.drawRect(mLeft- 2, mTop - 2, mLeft + 120, mTop + 12,mPaint);
		canvas.drawRect(mLeft- 2, mTop - 2, mLeft+ 12, mTop + 120, mPaint);
		canvas.drawRect(mRight - 120, mTop - 2, mRight + 2, mTop+ 12, mPaint);
		canvas.drawRect(mRight - 10, mTop - 2, mRight + 2, mTop + 120, mPaint);
		canvas.drawRect(mLeft - 2, mBottom - 12, mLeft + 120, mBottom + 2, mPaint);
		canvas.drawRect(mLeft - 2, mBottom - 120, mLeft + 12, mBottom + 2, mPaint);
		canvas.drawRect(mRight - 120, mBottom - 12, mRight + 2, mBottom + 2, mPaint);
		canvas.drawRect(mRight - 12, mBottom  - 120, mRight + 2, mBottom + 2, mPaint);
		mPaint.setTextSize(32);
		float strWidth = mPaint.measureText(mTitle);
		mPaint.setColor(titleColor);

		if (mOcrType == 0){
			canvas.drawText("请将行驶证置于框内",(mWidth - strWidth)/ 2, mTop - 16, mPaint);
			int mDrivingImgWidthWidth = mDrivingImg.getWidth();
			int mDrivingImgHeight = mDrivingImg.getHeight();
			mDrivingSrcRect = new Rect(0, 0, mDrivingImgWidthWidth , mDrivingImgHeight);//第一个Rect 代表要绘制的bitmap 区域，
			mDrivingDestRect = new Rect(mLeft + 30, mBottom  - mDrivingImgHeight * 2 - 30, mLeft + 30 + mDrivingImgWidthWidth * 2 , mBottom - 30 );//第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
			canvas.drawBitmap(mDrivingImg,mDrivingSrcRect, mDrivingDestRect, mPaint);
		}else{
			canvas.drawText("请将驾驶证置于框内",(mWidth - strWidth)/ 2, mTop - 16, mPaint);
			int mDriverImgWidthWidth = mDriverImg.getWidth();
			int mDriverImgHeight = mDriverImg.getHeight();
			mDriverSrcRect = new Rect(0, 0, mDriverImgWidthWidth , mDriverImgHeight);//第一个Rect 代表要绘制的bitmap 区域，
			mDriverDestRect = new Rect(mRight - 30 - mDriverImgWidthWidth * 2,  mBottom  - mDriverImgHeight * 2 - 30, mRight - 30, mBottom - 30);//第二个 Rect 代表的是要将bitmap 绘制在屏幕的什么地方
			canvas.drawBitmap(mDriverImg, mDriverSrcRect, mDriverDestRect, mPaint);
		}

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
	
}