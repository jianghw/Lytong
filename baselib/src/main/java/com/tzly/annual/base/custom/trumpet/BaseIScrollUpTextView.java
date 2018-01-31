package com.tzly.annual.base.custom.trumpet;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tzly.annual.base.R;
import com.tzly.annual.base.bean.HomeNotice;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 京东快报 自动向上滚动的广告基类
 * 内部包含TextView的自动向上滚动
 *
 * <pre>
 * @author 顾林海
 */
public abstract class BaseIScrollUpTextView<T> extends ListView implements IScrollTextData<T> {

    /**
     * 数据源
     */
    private ArrayList<T> mDataList = new ArrayList<>();

    /**
     * 字体大小
     */
    private float mSize = 12;

    /**
     * 数据总数
     */
    private int mMaxItemCount;

    private int position = -1;

    /**
     * 向上滚动距离
     */
    private int scroll_Y;
    /**
     * 设置固定的滚动距离
     */
    private int mScrollHeight;

    /**
     * 适配器
     */
    private AutoScrollAdapter mAutoScrollAdapter = new AutoScrollAdapter();

    /**
     * 监听器
     */
    private TrumpetItemListener mTrumpetItemListener;

    private Context mContext;

    private int mTimer = 3000;
    /**
     * 标记是否在滚动
     */
    private boolean turning = false;

    private TimeHandler handler = new TimeHandler(this);

    Runnable runnable = new Runnable() {
        @Override
        public void run() {// 开启轮播
            switchItem();
            handler.postDelayed(this, mTimer);
        }
    };

    private static class TimeHandler extends Handler {

        private final WeakReference<ViewGroup> weakReference;

        public TimeHandler(ViewGroup viewGroup) {
            weakReference = new WeakReference<>(viewGroup);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseIScrollUpTextView group = (BaseIScrollUpTextView) weakReference.get();
            if (group == null) {
                super.handleMessage(msg);
                return;
            }
            if (msg.what == 100) {
                group.startUpItem();
            }
        }
    }

    /**
     * 是否正在跑
     */
    public boolean isRunning() {
        return turning;
    }

    /**
     * 开启轮播
     */
    public void start() {
        //如果是正在翻页的话先停掉
        if (isRunning()) {
            stop();
        }
        turning = true;
        handler.sendEmptyMessageDelayed(100, 1000);
    }

    /**
     * 关闭轮播
     */
    public void stop() {
        turning = false;
        handler.removeCallbacksAndMessages(null);
    }

    public void startUpItem() {
        switchItem();
        handler.sendEmptyMessageDelayed(100, mTimer);
    }

    public BaseIScrollUpTextView(Context context) {
        this(context, null);
    }

    public BaseIScrollUpTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseIScrollUpTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.mContext = context;
        mScrollHeight = getTrumpetHeight();
        initDefault();
    }

    /**
     * 获取高度
     */
    protected abstract int getTrumpetHeight();

    /**
     * 默认样式设置
     */
    private void initDefault() {
        this.setDivider(null);
        this.setFastScrollEnabled(false);
        this.setDividerHeight(0);
        this.setEnabled(false);
    }

    /**
     * dp-->px
     */
    private int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 开始轮播
     */
    private void switchItem() {
        if (position == -1) {
            scroll_Y = 0;
        } else {
            scroll_Y = mScrollHeight;
        }
        smoothScrollBy(scroll_Y, mTimer);
        setSelection(position);
        position++;
    }

    /**
     * 添加数据
     */
    public void setData(List<T> tList) {
        if (!mDataList.isEmpty()) mDataList.clear();
        mDataList.addAll(tList);

        mMaxItemCount = mDataList == null ? 0 : mDataList.size();
        this.setAdapter(mAutoScrollAdapter);
        mAutoScrollAdapter.notifyDataSetChanged();
    }

    /**
     * 广告条适配器
     */
    private class AutoScrollAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            final int count = mDataList == null ? 0 : mDataList.size();
            return count > 2 ? Integer.MAX_VALUE : count;
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position % mMaxItemCount);
        }

        @Override
        public long getItemId(int position) {
            return position % mMaxItemCount;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_auto_scroll_up, null);
                viewHolder.mInfoView = (TextView) convertView.findViewById(R.id.tv_info);
                viewHolder.mImgMsg = (ImageView) convertView.findViewById(R.id.img_label);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            T data = mDataList.get(position % mMaxItemCount);

            viewHolder.mInfoView.setTextSize(mSize);
            viewHolder.mInfoView.setText(getTextInfo(data));

            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mTrumpetItemListener != null)
                        mTrumpetItemListener.onItemClick(position % mMaxItemCount);
                }
            });
            return convertView;
        }

    }

    private static class ViewHolder {
        TextView mInfoView;// 内容
        ImageView mImgMsg;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    public List<T> getList() {
        return mDataList;
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float _size) {
        this.mSize = _size;
    }

    /**
     * 设置监听事件
     */
    public void setTrumpetItemListener(TrumpetItemListener _listener) {
        this.mTrumpetItemListener = _listener;
    }

    /**
     * 设置轮播间隔时间
     *
     * @param _time 毫秒单位
     */
    public void setTimer(int _time) {
        this.mTimer = _time;
    }

}
