package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 王海洋 on 2016/5/10.
 * 自定义recycle 分割线
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration{

    private Paint mPaint;
    private Drawable mDrawable;//分割线的样式
    private Context mContext;
    private int mDividerHigh = 2;//分割线的高度
    private int mOrientation;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    /**
     * 默认分割线：高度为2px，颜色为灰色
     * @param mContext
     * @param mOrientation
     */
    public RecycleViewDivider(Context mContext, int mOrientation){

        if(mOrientation != LinearLayoutManager.VERTICAL && mOrientation != LinearLayoutManager.HORIZONTAL){
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        this.mOrientation = mOrientation;
        final TypedArray mTypedArray = mContext.obtainStyledAttributes(ATTRS);
        mDrawable = mTypedArray.getDrawable(0);
        mTypedArray.recycle();

    }

    /**
     * 用户定义布局的分割线
     * @param mContext
     * @param mOrientation
     * @param drawbleID
     */
    public RecycleViewDivider(Context mContext, int mOrientation, int drawbleID){
        this(mContext, mOrientation);
        mDrawable = ContextCompat.getDrawable(mContext, drawbleID);
        mDividerHigh = mDrawable.getIntrinsicHeight();
    }

    public RecycleViewDivider(Context mContext, int mOrientation, int mDividerHigh, int dividerColor){
        this(mContext, mOrientation);
        this.mDividerHigh = mDividerHigh;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHigh);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(mOrientation == LinearLayoutManager.VERTICAL){
//            d
        }else{

        }
    }

    private void drawHorizontal(Canvas mCanvas, RecyclerView parent){
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth()-parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++){
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams mLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom()+mLayoutParams.bottomMargin;
            final int bottom = top + mDividerHigh;
            if(mDrawable != null){
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(mCanvas);
            }
            if(mPaint != null){
                mCanvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    private void drawVertical(Canvas mCanvas, RecyclerView parent){
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight()-parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for(int i = 0; i < childSize; i++){
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams mLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight()+mLayoutParams.rightMargin;
            final int right = left + mDividerHigh;

            if(mDrawable != null){
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(mCanvas);
            }
            if(mPaint != null){
                mCanvas.drawRect(left, top, right, bottom, mPaint);
            }
        }


    }

}
