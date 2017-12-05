package com.tzly.ctcyh.cargo.vehicle.views;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 识别图像显示View
 * 
 * @author Иεtwμ
 * 
 */
public class RecoImageView extends ImageView {


	public RecoImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RecoImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RecoImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	public void setImagePath(String path, int width, int height) {
		// TODO Auto-generated method stub
	}
	
	
	/**
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
	         int minSideLength, int maxNumOfPixels) {
	     int initialSize = computeInitialSampleSize(options, minSideLength,
	             maxNumOfPixels);
	 
	     int roundedSize;
	     if (initialSize <= 8) {
	         roundedSize = 1;
	         while (roundedSize < initialSize) {
	             roundedSize <<= 1;
	         }
	     } else {
	         roundedSize = (initialSize + 7) / 8 * 8;
	     }
	 
	     return roundedSize;
	 }
	 /**
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
	         int minSideLength, int maxNumOfPixels) {
	     double w = options.outWidth;
	     double h = options.outHeight;
	 
	     int lowerBound = (maxNumOfPixels == -1) ? 1 :
	             (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
	     int upperBound = (minSideLength == -1) ? 512 :
	             (int) Math.min(Math.floor(w / minSideLength),
	             Math.floor(h / minSideLength));
	 
	     if (upperBound < lowerBound) {
	         // return the larger one when there is no overlapping zone.
	         return lowerBound;
	     }
	 
	     if ((maxNumOfPixels == -1) &&
	             (minSideLength == -1)) {
	         return 1;
	     } else if (minSideLength == -1) {
	         return lowerBound;
	     } else {
	         return upperBound;
	     }
	 } 

}
