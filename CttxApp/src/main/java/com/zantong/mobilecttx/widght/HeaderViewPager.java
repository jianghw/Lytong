package com.zantong.mobilecttx.widght;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.huodong.activity.HundredPlanActivity;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.ImageOptions;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.LogUtils;

public class HeaderViewPager extends FrameLayout {

    int lastX = -1;
    int lastY = -1;

    /**
     * 延时启动
     */
    private final static long INITIAL_DELAY = 2000;
    /**
     * 是否可以自动播放 ,true 自动播放，false 不播放
     */
    private boolean isAutoPlay = true;
    /**
     * 图片地址
     */
    private List<HomeAdvertisement> imageUrls;
    // 放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    // 放圆点的View的list
    private List<View> dotViewsList;
    private LinearLayout mDotsLayout;
    private ViewPager viewPager;
    private MyPagerAdapter mAdapter;
    private Context context;

    public HeaderViewPager(Context context) {
        this(context, null);
    }

    public HeaderViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        new Thread(networkTask).start();
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

    Thread change = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {
                if (isAutoPlay && mAdapter.getCount() > 1) {
//                    handler.obtainMessage().sendToTarget();
                    handler.sendEmptyMessage(0);
                }
                try {
                    Thread.sleep(INITIAL_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    // Handler
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (hasMessages(0)) {
                removeMessages(0);
            }
            if (mAdapter != null && mAdapter.getCount() > 1 && imageViewsList.size() > 1 && isAutoPlay) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                setDots();
            }
        }
    };

    /**
     * 初始化相关Data
     */
    private void initData() {
        imageUrls = new ArrayList<HomeAdvertisement>();
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
    }

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_header_viewpager, this, true);

        viewPager = (ViewPager) findViewById(R.id.header_viewpager_images);
        mDotsLayout = (LinearLayout) findViewById(R.id.header_viewpager_dots);
        viewPager.setFocusable(true);
        mAdapter = new MyPagerAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());


    }

    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }

    public void setImageUrls(List<HomeAdvertisement> list, ImageView.ScaleType scaleType) {
        imageViewsList.clear();
        imageUrls.clear();
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                imageUrls = list;
            } else {
                imageUrls.add(list.get(list.size() - 1));
                imageUrls.addAll(list);
                imageUrls.add(list.get(0));
            }

            int len = imageUrls.size();
            for (int i = 0; i < len; i++) {
                ImageView view = new ImageView(context);
                view.setScaleType(scaleType);
                ImageLoader.getInstance().displayImage(
                        imageUrls.get(i).getUrl(), view,
                        ImageOptions.getDefaultOptions());
                imageViewsList.add(view);
            }
            mAdapter.notifyDataSetChanged();
            if (mAdapter != null && mAdapter.getCount() > 1) {
//                viewPager.setCurrentItem(len > 1 ? len * 100 : len);
//                if (len > 1) {
//                    setDots(0);
//                }
                createDots();
                if (isAutoPlay && !change.isAlive()) {
                    change.start();
                }
            }

        }

    }

    public void clear() {
        imageUrls.clear();
//		imageViewsList.clear();
//		dotViewsList.clear();
    }

    public void close() {
        change.interrupt();
    }

    private void createDots() {
        if (mAdapter.getCount() > 1) {
            mDotsLayout.removeAllViews();
            dotViewsList.clear();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    12, 12);
            layoutParams.setMargins(8, 4, 8, 6);
            for (int i = 0; i < mAdapter.getCount() - 2; i++) {
                ImageView dot = new ImageView(context);
                dot.setLayoutParams(layoutParams);
                if (i == 0) {
                    dot.setBackgroundResource(R.mipmap.icon_dot_sel);
                } else {
                    dot.setBackgroundResource(R.mipmap.icon_dot_nor);
                }
                dotViewsList.add(dot);
                mDotsLayout.addView(dot);
            }
        }
    }

    private void setDots() {
        for (int i = 0; i < mAdapter.getCount() - 2; i++) {
            if (i == viewPager.getCurrentItem() - 1) {
                dotViewsList.get(i).setBackgroundResource(R.mipmap.icon_dot_sel);
            } else {
                dotViewsList.get(i).setBackgroundResource(R.mipmap.icon_dot_nor);
            }
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //移除目录
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            int len = imageViewsList.size();
            final int currentPosition;
            if (len > 1) {
                currentPosition = position % len;
            } else {
                currentPosition = position;
            }
            if (imageViewsList.get(currentPosition).getParent() != null) {
                ((ViewPager) container).removeView(imageViewsList.get(currentPosition));
            }
            ((ViewPager) container)
                    .addView(imageViewsList.get(currentPosition));
            imageViewsList.get(currentPosition).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    MobclickAgent.onEvent(context, Config.getUMengID(10));
                    PublicData.getInstance().webviewUrl = imageUrls.get(currentPosition).getAdvertisementSkipUrl();
                    PublicData.getInstance().mHashMap.put("htmlUrl", PublicData.getInstance().webviewUrl);
                    PublicData.getInstance().webviewTitle = "广告";
                    PublicData.getInstance().isCheckLogin = false;

                    if (PublicData.getInstance().webviewUrl.contains("discount") || PublicData.getInstance().webviewUrl.contains("happysend")) {
//                        Act.getInstance().gotoIntent(context, CustomCordovaActivity.class);
                    } else if (PublicData.getInstance().webviewUrl.contains("localActivity")) {
                        if (PublicData.getInstance().loginFlag) {
                            getSignStatus();
                        } else {
                            Act.getInstance().gotoIntent(context, LoginActivity.class);
                        }
                    } else {
                        Act.getInstance().gotoIntent(context, BrowserActivity.class);
                        CarApiClient.commitAdClick(context, imageUrls.get(currentPosition).getId(), new CallBack<BaseResult>() {
                            @Override
                            public void onSuccess(BaseResult result) {
//							ToastUtils.showShort(context,"已统计");
                            }
                        });
                    }
                }
            });
            return imageViewsList.get(currentPosition);
        }

        @Override
        public int getCount() {
            int size = imageViewsList.size();
            return size;
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
            setDots();
            int currentPosition = pos;
            if (currentPosition == 0) {
                currentPosition = mAdapter.getCount() - 2;
                viewPager.setCurrentItem(currentPosition, false);
            } else if (currentPosition == mAdapter.getCount() - 1) {
                currentPosition = 1;
                viewPager.setCurrentItem(currentPosition, false);

            }
        }
    }

    private void getSignStatus() {
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(PublicData.getInstance().userID);
        CarApiClient.getActivityCar(context, activityCarDTO, new CallBack<ActivityCarResult>() {
            @Override
            public void onSuccess(ActivityCarResult result) {

                //报名结束时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
                long endTm = 0;
                try {
                    endTm = sdf.parse(DateUtils.START_TIME).getTime();//毫秒
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (currentTm == -1) {
                    ToastUtils.showShort(context, "拉取网络时间失败");
                    return;
                }

                if (result.getResponseCode() == 2000 && !TextUtils.isEmpty(result.getData().getPlateNo())) {
                    SPUtils.getInstance(context).setSignStatus(true);
                    SPUtils.getInstance(context).setSignCarPlate(result.getData().getPlateNo());

                    if (currentTm < endTm) {//4.17号之前
                        MobclickAgent.onEvent(context, Config.getUMengID(22));
                        Act.getInstance().lauchIntentToLogin(context, HundredPlanActivity.class);

                    } else {//4.17号之后
                        PublicData.getInstance().webviewUrl = Config.HUNDRED_PLAN_HOME;
                        PublicData.getInstance().webviewTitle = "百日无违章";
                        Act.getInstance().lauchIntentToLogin(context, BrowserActivity.class);
                    }

                } else if (result.getResponseCode() == 4000) {
                    SPUtils.getInstance(context).setSignStatus(false);
                    if (currentTm < endTm) {//4.17号之前
                        MobclickAgent.onEvent(context, Config.getUMengID(19));
                        Act.getInstance().lauchIntentToLogin(context, HundredPlanActivity.class);

                    } else {//4.17号之后
                        PublicData.getInstance().webviewUrl = Config.HUNDRED_PLAN_DEADLINE;
                        PublicData.getInstance().webviewTitle = "百日无违章";
                        Act.getInstance().lauchIntentToLogin(context, BrowserActivity.class);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }

    long currentTm = -1;

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 取得资源对象
            URL url = null;
            URLConnection uc = null;
            try {
                url = new URL("http://www.baidu.com");
                // 生成连接对象
                uc = url.openConnection();
                uc.setConnectTimeout(1000);
                uc.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long time = uc.getDate();

            Message msg = new Message();
            Bundle data = new Bundle();
            data.putLong("webTm", time);
            msg.setData(data);
            handler2.sendMessage(msg);
        }
    };

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            currentTm = data.getLong("webTm");
        }
    };

}