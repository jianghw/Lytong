package com.tzly.ctcyh.cargo.cc.vehicle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.tzly.ctcyh.cargo.R;

/**
 * ��Ƶȡ�����ؼ�
 * 
 * @author fangcm 2012-09-06
 *
 */
public class ViewfinderView extends View{
	
	private int width , height;
	private Paint paint;
	private Context mContext;
	private int mWidth,mHeight;
	private float lineLeft,lineRight,lineTop,lineBottom;
	private int lineModel = 0;
	private float marginW = 0f;
	private float marginH = 0f;
	private float marginT = 0f;
	private int dLineWidth = 12;
	private int dLen = 60;
	private int m_nImageWidth;
	private int m_nImageHeight;
	private boolean scan = false;
	private float startX = 0;
	private float scanY1,scanY2;
	
	boolean l = false, r = false, t = false, b = false , L = false;
	
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}
	
	
	public ViewfinderView(Context context){
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public ViewfinderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}
	
	/**
	 * ��С  85.6*54 
	 * ���ǿ��1.58��
	 * @param pWidth
	 * @param pHeight
	 */
	public void initFinder(int pWidth,int pHeight,Handler mHandler){
		m_nImageWidth = pWidth;
		m_nImageHeight = pHeight;
		WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		Log.d("tag", "-1-------->>"+width);
		
		marginT = mContext.getResources().getDimension(R.dimen.public_30_dp);
		
		marginW = (float) ((width - pWidth)/2.0);
		marginH = (float) ((height - pHeight)/2.0);
		
		mWidth = width/2;
		mHeight = height/2;
		
		float g = height - marginT * 2;
		float k = g* 1.58f;
		float x = 10.0f;
		Log.d("ocr", k+"<<--k---�߶�----g--1---->>"+g);
		while(k > pWidth){
			x --;
			k = k * (x/10.0f);
			g = g * (x/10.0f);
		}
		Log.d("ocr", k+"<<--k---�߶�----g--2---->>"+g);
		lineLeft = (float) (mWidth - k/2.0);
		lineRight = (float) (mWidth + k/2.0);
		lineTop = (float) (mHeight - g/2.0);
		lineBottom = (float) (mHeight + g/2.0);
		
		
		
		
		int nDisplayWidth = display.getWidth();
		int nDisplayHeight = display.getHeight();
		
		int nImageWidth = m_nImageWidth;
		int nImageHeight  = m_nImageHeight;
		double nFitWidth;
		double nFitHeight;
		double nUseWidth = 0;
		double nUseHeight = 0;
		double dRealRegionWidth = 0;
//		double dRealRegionHeight = 0;
		if(nImageWidth*nDisplayHeight < nDisplayWidth*nImageHeight){
			nFitHeight = nDisplayHeight;
			nFitWidth = (nImageWidth/(double)nImageHeight)*nFitHeight;
		}else{
			nFitWidth = nDisplayWidth;
			nFitHeight = nFitWidth*(nImageHeight/(double)nImageWidth);
		}
		if(nFitWidth/nFitHeight >= 4/3){
			nUseHeight = nFitHeight;
			nUseWidth = 4*nUseHeight/3.0f;
		}else{
			nUseWidth = nFitWidth;
			nUseHeight = 3*nUseWidth/4.0f;
		}
		dRealRegionWidth = nUseWidth/480.0f*420.0f;
//		dRealRegionHeight = nUseHeight/360.0f*270.0f;
		
//		lineLeft = (int)((nDisplayWidth - dRealRegionWidth)/2.0f);//- (nDisplayWidth - nFitWidth)/2.0f);
//		lineRight = (int)( nDisplayWidth - lineLeft);//- (nDisplayWidth - nFitWidth) );
//		lineTop = (int)(nDisplayHeight - dRealRegionHeight)/2.0f;
//		lineBottom = nDisplayHeight - lineTop;
		
		
		paint = new Paint();
		dLineWidth = (int)dRealRegionWidth/28; //30
		dLineWidth = 4;
		paint.setStrokeWidth(dLineWidth);
		dLen = (int)dRealRegionWidth/6; //160
		
		scanY1 = mHeight - mHeight/4;
		scanY2 = mHeight + mHeight/4;
	}
	
	
	public void initFinder(int w,int h,int d){}
	
	public Rect getFinder(){
		return new Rect((int)(lineLeft - marginW), (int)(lineTop - marginH), (int)(lineRight + marginW), (int)(lineBottom + marginH));
	}
	
	
	public void setLineRect(int model){
		lineModel = model;
		invalidate();
	}
	
	
	public void scanInit(){
		this.scan = true;
		startX = lineLeft;
	}
	
	public void scan(){
		invalidate();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		paint.setColor(Color.GREEN);
		
			if(scan){
//				startX ++;
				startX = startX + 5;
				if(startX >= lineRight){
					startX = lineLeft;
				}
				canvas.drawLine(startX, scanY1, startX, scanY2, paint);
			}
//			
			
			
			
			
			
			canvas.drawLine(lineLeft - dLineWidth/2, lineTop, lineLeft + dLen, lineTop, paint);
			canvas.drawLine(lineLeft, lineTop- dLineWidth/2, lineLeft, lineTop + dLen, paint);
			
			canvas.drawLine(lineRight, lineTop -  dLineWidth/2, lineRight, lineTop + dLen, paint);
			canvas.drawLine(lineRight +  dLineWidth/2, lineTop, lineRight - dLen, lineTop, paint);
			
			canvas.drawLine(lineLeft, lineBottom +  dLineWidth/2, lineLeft, lineBottom - dLen, paint);
			canvas.drawLine(lineLeft -  dLineWidth/2, lineBottom, lineLeft + dLen, lineBottom, paint);
			
			canvas.drawLine(lineRight +  dLineWidth/2, lineBottom, lineRight - dLen, lineBottom, paint);
			canvas.drawLine(lineRight, lineBottom +  dLineWidth/2, lineRight, lineBottom - dLen, paint);
			
			
			
			switch (lineModel) {
			case 0:
				
				break;
			case 1://��߿���
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				break;
			case 2://�ұ߿���
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				break;
			case 3://����
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				break;
			case 4://�ϱ߿�
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				break;
			case 5://����
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				break;
			case 6://����
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				break;
			case 7://������
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				break;
			case 8://�±߿�
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				break;
			case 9://����
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				break;
			case 10://���±߿�
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				break;
			case 11://������
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				break;
			case 12://����
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				break;
			case 13://������
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				break;
			case 14://������
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				break;
			case 15://ȫ
				canvas.drawLine(lineLeft, lineTop, lineLeft, lineBottom, paint);
				canvas.drawLine(lineRight, lineTop, lineRight, lineBottom, paint);
				canvas.drawLine(lineLeft, lineTop, lineRight, lineTop, paint);
				canvas.drawLine(lineLeft, lineBottom, lineRight, lineBottom, paint);
				break;
	
			default:
				
				break;
			}
			paint.setColor(Color.BLACK);
			paint.setAlpha(100);
//			canvas.drawRect(lineLeft + dLineWidth / 2, lineTop + dLineWidth / 2, lineRight - dLineWidth / 2, lineBottom - dLineWidth / 2, paint);
			
			//������
//			canvas.drawRect(0, 0, width, lineTop - dLineWidth / 2, paint);
//			
//			canvas.drawRect(0, lineTop - dLineWidth / 2, lineLeft - dLineWidth / 2, lineBottom + dLineWidth / 2, paint);
//			
//			canvas.drawRect(0, lineBottom + dLineWidth / 2, width, height, paint);
//			
//			canvas.drawRect(lineRight + dLineWidth / 2, lineTop - dLineWidth / 2, width, lineBottom + dLineWidth / 2, paint);
		
		
	}


	
}
