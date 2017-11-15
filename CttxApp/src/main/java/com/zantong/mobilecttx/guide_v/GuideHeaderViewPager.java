package com.zantong.mobilecttx.guide_v;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home.bean.StartPicBean;

import java.util.ArrayList;
import java.util.List;

import com.tzly.ctcyh.router.custom.image.ImageOptions;

public class GuideHeaderViewPager extends FrameLayout {
    /**
     * 图片地址
     */
    private List<Integer> imageUrls;
    // 放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    // 放圆点的View的list
    private List<View> dotViewsList;
    private LinearLayout mDotsLayout;
    private ViewPager viewPager;
    private MyPagerAdapter mAdapter;
    private Context context;
    private GuideInterface guideInterface;

    public GuideHeaderViewPager(Context context) {
        this(context, null);
    }

    public GuideHeaderViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideHeaderViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initData();
        initUI(context);
    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        imageUrls = new ArrayList<Integer>();
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
    }

    public int getImageCount() {
        return imageUrls.size();
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_header_ct_viewpager, this, true);

        viewPager = (ViewPager) findViewById(R.id.header_viewpager_images);
        mDotsLayout = (LinearLayout) findViewById(R.id.header_viewpager_dots);
        viewPager.setFocusable(true);
        mAdapter = new MyPagerAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 输入图片地址
     * 1、mList.size() > 0 ? mList.size(): imageUrls.size()
     * 2、
     */
    public void setImageUrls(List<Integer> list, ImageView.ScaleType scaleType,
                             GuideInterface guideInterface, List<StartPicBean> mList) {
        if (list != null && list.size() > 0) {
            this.guideInterface = guideInterface;
            imageUrls.addAll(list);

            int len = mList != null ? mList.size() > 0 ? mList.size() : imageUrls.size() : imageUrls.size();
            for (int i = 0; i < len; i++) {
                ImageView view = new ImageView(context);
                view.setScaleType(scaleType);
                if (mList != null && mList.size() > 0) {
                    if (mList.get(i) != null && !TextUtils.isEmpty(mList.get(i).getPicUrl())) {
                        ImageLoader.getInstance().displayImage(
                                mList.get(i).getPicUrl(),
                                view,
                                ImageOptions.getDefaultOptions());
                    } else {
                        view.setBackgroundResource(imageUrls.get(i));
                    }
                } else {
                    view.setBackgroundResource(imageUrls.get(i));
                }
                imageViewsList.add(view);
            }
            mAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(len > 1 ? 0 : 0);
            setDots(len);
        }
    }

    private void setDots(int len) {
        if (len > 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            layoutParams.setMargins(10, 0, 10, 12);
            mDotsLayout.removeAllViews();
            dotViewsList.clear();
            for (int i = 0; i < len; i++) {
                ImageView dot = new ImageView(context);
                dot.setLayoutParams(layoutParams);
                if (i == 0) {
                    dot.setBackgroundResource(R.mipmap.icon_guide_select);
                } else {
                    dot.setBackgroundResource(R.mipmap.icon_guide_unselect);
                }
                dotViewsList.add(dot);
                mDotsLayout.addView(dot);
            }
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            if (imageViewsList.get(position).getParent() != null) {
                ((ViewPager) container).removeView(imageViewsList.get(position));
            }
            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int pos) {
            int len = dotViewsList.size();

            if (len != 0) {
                int position = 0;
                if (len > 1) {
                    position = pos % len;
                }

                for (int i = 0; i < len; i++) {
                    if (i == position) {
                        dotViewsList.get(position).setBackgroundResource(R.mipmap.icon_guide_select);
                    } else {
                        dotViewsList.get(i).setBackgroundResource(R.mipmap.icon_guide_unselect);
                    }
                }
            }

            guideInterface.getIndex(len - pos);
        }
    }

    public interface GuideInterface {
        void getIndex(int index);
    }
}
