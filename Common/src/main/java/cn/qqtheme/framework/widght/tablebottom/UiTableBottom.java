package cn.qqtheme.framework.widght.tablebottom;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.qqtheme.framework.R;

/**
 * Created by jianghw on 2017/6/26.
 * Description: 自定义控件导航栏
 * Update by:
 * Update day:
 */

public class UiTableBottom extends LinearLayout implements View.OnClickListener {
    private Context context;
    private int colorClick;
    private int colorUnClick;
    //子控件
    private ArrayMap<Integer, UITableItem> itemHashMap;

    private int index = 100;
    private int currentPosition;
    private OnUITabChangListener changeListener; //ui Tab 改变监听器

    public UiTableBottom(Context context) {
        this(context, null, 0);
    }

    public UiTableBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UiTableBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setUiViewPager(OnUITabChangListener changeListener,
                               int currentPosition, ArrayMap<Integer, Integer[]> hashMap) {
        this.changeListener = changeListener;
        this.currentPosition = currentPosition;

        init(hashMap);
        invalidate();
    }

    public void setTipOfNumber(int position, int number) {
        for (int j = 0; j < itemHashMap.size(); j++) {
            if (j == position) {
                itemHashMap.get(j).msgView.setVisibility(number > 0 ? View.VISIBLE : View.INVISIBLE);
            }
        }
        invalidate();
    }

    /**
     * UITab 改变监听器
     */
    private void init(ArrayMap<Integer, Integer[]> hashMap) {
//        colorClick = getResources().getColor(R.color.color_theme_green);
//        colorUnClick = getResources().getColor(R.color.color_gray);

        /*拿控件的高度*/
        int tableBottomHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        //设置父类控件的方向
        setOrientation(LinearLayout.HORIZONTAL);
        /*创建子控件，并标记*/
        itemHashMap = new ArrayMap<>();
        for (int i = 0; i < hashMap.size(); i++) {
            UITableItem item = getChileItem(tableBottomHeight, i, hashMap);
            itemHashMap.put(i, item);
        }
        selectTab(currentPosition);
    }

    /**
     * 建立布局子控件Item
     *
     * @param tableBottomHeight 父类控件高度
     * @param i                 子控件位号
     * @param hashMap
     * @return zi控件对象
     */
    private UITableItem getChileItem(int tableBottomHeight, int i, ArrayMap<Integer, Integer[]> hashMap) {
        UITableItem table = newChildItem(i);
        //用于建立控件，所用参数
        LayoutParams layoutParams = new LayoutParams(0, tableBottomHeight);
        layoutParams.weight = 1;

//        if (i == currentPosition) {
//            table.labelView.setTextColor(colorClick);
//        } else {
//            table.labelView.setTextColor(colorUnClick);
//        }
//        table.labelView.setText(context.getString(hashMap.get(i)[2]));

        table.iconView.initBitmap(hashMap.get(i)[0], hashMap.get(i)[1]);

        /*加入到父控件中*/
        addView(table.parent, layoutParams);
        return table;
    }

    /**
     * Button控件 子item包含组件
     *
     * @param i 标记位号
     * @return
     */
    private UITableItem newChildItem(int i) {
        UITableItem tableItem = new UITableItem();
        tableItem.parent = LayoutInflater.from(context).inflate(R.layout.custom_table_bottom, null);
        tableItem.iconView = (UiItemImage) tableItem.parent.findViewById(R.id.img_bottom);
        tableItem.msgView = (ImageView) tableItem.parent.findViewById(R.id.img_msg);

        tableItem.parent.setTag(i);
        tableItem.parent.setOnClickListener(this);
        return tableItem;
    }

    private class UITableItem {
        View parent; //父控件
        UiItemImage iconView; //图片
        ImageView msgView; //图片
    }

    /**
     * OnPagerChangListener 时会被调用
     */
    public void selectTab(int i) {
        if (index == i) {
            return;
        }
        index = i;
        //页面
        if (changeListener != null) {
            changeListener.onTabChang(index);
        }
        //图像颜色
        for (int j = 0; j < itemHashMap.size(); j++) {
            itemHashMap.get(j).iconView.setUiAlpha(0);
        }

        itemHashMap.get(i).iconView.setUiAlpha(255);
    }

    @Override
    public void onClick(View v) {
        int i = (Integer) v.getTag();
        //跳转到ViewPager的指定页面
        selectTab(i);
    }

    public interface OnUITabChangListener {
        void onTabChang(int index);
    }

    public OnUITabChangListener getChangeListener() {
        return changeListener;
    }

    public void setChangeListener(OnUITabChangListener changeListener) {
        this.changeListener = changeListener;
    }

}
