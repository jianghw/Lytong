package com.zantong.mobilecttx.widght;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.zantong.mobilecttx.R;
import cn.qqtheme.framework.util.ui.DensityUtils;

/**
 * 自定义拍照扫描框
 *
 * @author fangcm 2012-09-06
 */
public class ViewfinderView extends View {

    private int width, height;
    private Paint paint;
    private Context mContext;
    private int mWidth, mHeight;
    private float lineLeft, lineRight, lineTop, lineBottom;
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
    private float scanY1, scanY2;

    private int mLeft = 0;
    private int mTop = 0;
    private int mRight = 0;
    private int mBottom = 0;

    boolean l = false, r = false, t = false, b = false, L = false;
    Drawable mImg;//图片地址
    int mPostion;//图片位置  0左下角 1右下角

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray array = mContext.obtainStyledAttributes(attrs,
                R.styleable.ViewfinderViewImg);
        mImg = array.getDrawable(R.styleable.ViewfinderViewImg_path);
        mPostion = array.getInteger(R.styleable.ViewfinderViewImg_position,0);
        array.recycle();
    }


    public ViewfinderView(Context context) {
        super(context);
        this.mContext = context;
    }

    public ViewfinderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    /**
     *
     * @param pWidth
     * @param pHeight
     * @param mHandler
     */
    public void initFinder(int pWidth, int pHeight, Handler mHandler) {

        m_nImageWidth = pWidth;
        m_nImageHeight = pHeight;


        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        Log.d("tag", "-1-------->>" + width);

        marginT = mContext.getResources().getDimension(R.dimen.ds_30);

        marginW = (float) ((width - pWidth) / 2.0);
        marginH = (float) ((height - pHeight) / 2.0);

        m_nImageWidth = width / 2;
        m_nImageHeight = height / 2;

        float g = height - marginT * 2;
        float k = g * 1.58f;
        float x = 10.0f;
        while (k > pWidth) {
            x--;
            k = k * (x / 10.0f);
            g = g * (x / 10.0f);
        }
        lineLeft = (float) (m_nImageWidth - k / 2.0);
        lineRight = (float) (m_nImageWidth + k / 2.0);
        lineTop = (float) (m_nImageHeight - g / 2.0);
        lineBottom = (float) (m_nImageHeight + g / 2.0);


        int nDisplayWidth = display.getWidth();
        int nDisplayHeight = display.getHeight();

        int nImageWidth = m_nImageWidth;
        int nImageHeight = m_nImageHeight;
        double nFitWidth;
        double nFitHeight;
        double nUseWidth = 0;
        double nUseHeight = 0;
        double dRealRegionWidth = 0;
//		double dRealRegionHeight = 0;
        if (nImageWidth * nDisplayHeight < nDisplayWidth * nImageHeight) {
            nFitHeight = nDisplayHeight;
            nFitWidth = (nImageWidth / (double) nImageHeight) * nFitHeight;
        } else {
            nFitWidth = nDisplayWidth;
            nFitHeight = nFitWidth * (nImageHeight / (double) nImageWidth);
        }
        if (nFitWidth / nFitHeight >= 4 / 3) {
            nUseHeight = nFitHeight;
            nUseWidth = 4 * nUseHeight / 3.0f;
        } else {
            nUseWidth = nFitWidth;
            nUseHeight = 3 * nUseWidth / 4.0f;
        }
        dRealRegionWidth = nUseWidth / 480.0f * 420.0f;

        paint = new Paint();
        dLineWidth = (int) dRealRegionWidth / 28; //30
        dLineWidth = 4;
        paint.setStrokeWidth(dLineWidth);
        dLen = (int) dRealRegionWidth / 6; //160

        scanY1 = mHeight - mHeight / 4;
        scanY2 = mHeight + mHeight / 4;

        mWidth = DensityUtils.getScreenWidth(mContext);
        mHeight = DensityUtils.getScreenHeight(mContext);

        if (mHeight > mWidth) {
            int tmp = mHeight;
            mHeight = mWidth;
            mWidth = tmp;
        }
        paint.setStrokeWidth(4f);
        paint.setColor(Color.WHITE);
        mLeft = mWidth * 12 / 100;
        mRight = mWidth * 88 / 100;
        mTop = mHeight * 10 / 100;
        mBottom = mHeight * 90 / 100;
    }

    public void initFinder(int w, int h, int d) {

    }

    public Rect getFinder() {
        return new Rect((int) (lineLeft - marginW), (int) (lineTop - marginH), (int) (lineRight + marginW), (int) (lineBottom + marginH));
    }

    public void setLineRect(int model) {
        lineModel = model;
        invalidate();
    }


    public void scanInit() {
        this.scan = true;
        startX = lineLeft;
    }

    public void scan() {
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        if (scan) {
//            startX++;
//            startX = startX + 5;
//            if (startX >= lineRight) {
//                startX = lineLeft;
//            }
//            canvas.drawLine(startX, scanY1, startX, scanY2, paint);
//        }

        paint.setColor(Color.WHITE);
        canvas.drawLine(mLeft, mTop, mRight, mTop, paint);
        canvas.drawLine(mLeft, mTop, mLeft, mBottom, paint);
        canvas.drawLine(mRight, mTop, mRight, mBottom, paint);
        canvas.drawLine(mLeft, mBottom, mRight, mBottom, paint);
        paint.setColor(mContext.getResources().getColor(R.color.colorQueryOtherCar));
        canvas.drawRect(mLeft - 2, mTop - 2, mLeft + 120, mTop + 12, paint);
        canvas.drawRect(mLeft - 2, mTop - 2, mLeft + 12, mTop + 120, paint);
        canvas.drawRect(mRight - 120, mTop - 2, mRight + 2, mTop + 12, paint);
        canvas.drawRect(mRight - 10, mTop - 2, mRight + 2, mTop + 120, paint);
        canvas.drawRect(mLeft - 2, mBottom - 12, mLeft + 120, mBottom + 2, paint);
        canvas.drawRect(mLeft + -2, mBottom - 120, mLeft + 12, mBottom + 2, paint);
        canvas.drawRect(mRight - 120, mBottom - 12, mRight + 2, mBottom + 2, paint);
        canvas.drawRect(mRight - 12, mBottom - 120, mRight + 2, mBottom + 2, paint);

//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.img_jiasz_ocr_bg);
        BitmapDrawable bd = (BitmapDrawable) mImg;
        Bitmap bitmap = bd.getBitmap();
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        /* 放大变量 */
        double scale = 1.5;
        float scaleWidth = 1;
        float scaleHeight = 1;
        /* 放大以后的宽高，一定要强制转换为float型的 */
        scaleWidth = (float) (scaleWidth * scale);
        scaleHeight = (float) (scaleHeight * scale);

        /* 产生resize后的Bitmap对象 */
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight,
                matrix, true);

//        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, 288, 344);
        if (mPostion == 0){//左下角
            canvas.drawBitmap(resizeBmp, mLeft + 90, mBottom - bitmap.getHeight() - 180, paint);
        }else if (mPostion == 1){//右下角
            canvas.drawBitmap(resizeBmp, mRight - bitmap.getWidth() - 180, mBottom - bitmap.getHeight() - 180, paint);
        }
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(48);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("请将证件置于框内",mWidth / 2,mTop + 90 ,paint);
    }


    /* big method */
    private void big() {


    }
}
