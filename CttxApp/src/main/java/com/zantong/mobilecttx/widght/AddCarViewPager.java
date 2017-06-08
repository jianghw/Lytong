package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseFragment;
import com.zantong.mobilecttx.utils.BaseFragmentPagerAdapter;
import com.zantong.mobilecttx.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class AddCarViewPager extends FrameLayout {

    private List<BaseFragment> fragmentList = new ArrayList<>();
    // 放圆点的View的list
    private List<View> dotViewsList;
    private LinearLayout mDotsLayout;
    private ViewPager viewPager;
    private MyPagerAdapter mAdapter;
    private Context context;

    int lastX = -1;
    int lastY = -1;

    public AddCarViewPager(Context context) {
        this(context, null);
    }

    public AddCarViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddCarViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initData();
        initUI(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                LogUtils.i("dealtX:=" + dealtX);
                LogUtils.i("dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        dotViewsList = new ArrayList<View>();
    }

    public int getImageCount() {
        return fragmentList.size();
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_header_viewpager,
                this, true);
        viewPager = (ViewPager) findViewById(R.id.header_viewpager_images);
        mDotsLayout = (LinearLayout) findViewById(R.id.header_viewpager_dots);
        viewPager.setFocusable(true);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());

    }

    public void setFragmentList(List<BaseFragment> list, FragmentManager fm) {
        try {
            mAdapter = new MyPagerAdapter(fm);
            viewPager.setAdapter(mAdapter);
            if (list != null && list.size() > 0) {
//			Collections.reverse(list); // 倒序排列
                fragmentList = list;
                int len = fragmentList.size();
                mAdapter.notifyDataSetChanged();
                if (mAdapter != null && mAdapter.getCount() > 1 && fragmentList.size() > 1) {
                    viewPager.setCurrentItem(0);
                    setDots(len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDots(int len) {
        if (len > 1) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    60, 6);
            layoutParams.setMargins(8, 4, 10, 6);
            mDotsLayout.removeAllViews();
            dotViewsList.clear();
            for (int i = 0; i < len; i++) {
                ImageView dot = new ImageView(context);
                dot.setLayoutParams(layoutParams);
                if (i == 0) {
                    dot.setBackgroundResource(R.mipmap.icon_square_sel);
                } else {
                    dot.setBackgroundResource(R.mipmap.icon_square_nor);
                }
                dotViewsList.add(dot);
                mDotsLayout.addView(dot);
            }
        } else {
            mDotsLayout.removeAllViews();
            dotViewsList.clear();
        }
    }

    public void refreshData(List<BaseFragment> fragmentLists) {
        if (!fragmentList.isEmpty()) fragmentList.clear();
        fragmentList.addAll(fragmentLists);
//		Collections、reverse(fragmentList);
        mAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(0);
        setDots(mAdapter.getCount());
    }

    public int getCount() {
        return mAdapter.getCount();
    }


    private class MyPagerAdapter extends BaseFragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
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
                        dotViewsList.get(position).setBackgroundResource(
                                R.mipmap.icon_square_sel);
                    } else {
                        dotViewsList.get(i).setBackgroundResource(
                                R.mipmap.icon_square_nor);
                    }
                }
            }

        }
    }
}
