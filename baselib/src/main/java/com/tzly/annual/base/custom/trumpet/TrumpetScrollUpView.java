package com.tzly.annual.base.custom.trumpet;

import android.content.Context;
import android.util.AttributeSet;

import com.tzly.annual.base.bean.HomeNotice;

/**
 * <pre>
 * 制作主页的向上广告滚动条
 * AdvertisementObject是主页的数据源，假如通过GSON或FastJson获取的实体类
 */
public class TrumpetScrollUpView extends BaseIScrollUpTextView<HomeNotice> {

    private int mHeight;

    public TrumpetScrollUpView(Context context) {
        super(context);
    }

    public TrumpetScrollUpView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrumpetScrollUpView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public String getTextInfo(HomeNotice data) {
        return data.getDesc();
    }

    /**
     * 这里面的高度应该和你的xml里设置的高度一致
     */
    public void setTrumpetHeight(int height) {
        this.mHeight = height;
    }

    @Override
    public int getTrumpetHeight() {
        return mHeight;
    }

}
