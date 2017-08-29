package com.jcodecraeer.xrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.touch.OnSwipeMenuItemClickListener;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenu;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenuCreator;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenuLayout;
import com.jcodecraeer.xrecyclerview.touch.SwipeMenuView;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类adapter
 *
 * @author Sandy
 *         create at 16/6/2 下午2:14
 */
public abstract class BaseAdapter<ItemDataType> extends
        RecyclerView.Adapter<BaseRecyclerViewHolder> implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private ArrayList<ItemDataType> mItemDataList = new ArrayList<>();
    /**
     * Swipe menu click listener。
     */
    private OnSwipeMenuItemClickListener mSwipeMenuItemClickListener = null;
    private SwipeMenuCreator mSwipeMenuCreator = null;

    /**
     * 动态增加一条数据
     *
     * @param itemDataType 数据实体类对象
     */
    public void append(ItemDataType itemDataType) {
        if (itemDataType != null) {
            mItemDataList.add(itemDataType);
            notifyDataSetChanged();
        }
    }

    /**
     * 动态增加一组数据集合
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void append(List<ItemDataType> itemDataTypes) {
        mItemDataList.addAll(itemDataTypes);
        notifyDataSetChanged();
    }

    public void appendOnly(List<ItemDataType> itemDataTypes) {
        mItemDataList.addAll(itemDataTypes);
    }

    /**
     * 替换全部数据
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void replace(List<ItemDataType> itemDataTypes) {
        if (!mItemDataList.isEmpty()) mItemDataList.clear();
        mItemDataList.addAll(itemDataTypes);
        notifyDataSetChanged();
    }

    public void replaceOnly(List<ItemDataType> itemDataTypes) {
        if (!mItemDataList.isEmpty()) mItemDataList.clear();
        mItemDataList.addAll(itemDataTypes);
    }

    /**
     * 移除一条数据集合
     *
     * @param position
     */
    public void remove(int position) {
        mItemDataList.remove(position);
        notifyDataSetChanged();
    }

    public ArrayList<ItemDataType> getAll() {
        return mItemDataList;
    }

    /**
     * 移除一条数据
     *
     * @param item
     */
    public void remove(ItemDataType item) {
        if (mItemDataList != null) mItemDataList.remove(item);
        notifyDataSetChanged();
    }

    /**
     * 移除所有数据
     */
    public void removeAllOnly() {
        if (mItemDataList != null) mItemDataList.clear();
    }

    public void removeAll() {
        if (mItemDataList != null) mItemDataList.clear();
        notifyDataSetChanged();
    }

    public void cleanListData() {
        if (mItemDataList != null) mItemDataList.clear();
    }

    @Override
    public int getItemCount() {
        return mItemDataList.size();
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View contentView = createView(viewGroup, viewType);

        if (mSwipeMenuCreator != null) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.item_swipe_menu_default, viewGroup, false);
            SwipeMenu swipeLeftMenu = new SwipeMenu(swipeMenuLayout, viewType);
            SwipeMenu swipeRightMenu = new SwipeMenu(swipeMenuLayout, viewType);
            mSwipeMenuCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);

            int leftMenuCount = swipeLeftMenu.getMenuItems().size();
            if (leftMenuCount > 0) {
                SwipeMenuView swipeLeftMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.lv_swipe_left);
                // noinspection WrongConstant
                swipeLeftMenuView.setOrientation(swipeLeftMenu.getOrientation());
                swipeLeftMenuView.bindMenu(swipeLeftMenu, XRecyclerView.LEFT_DIRECTION);
                swipeLeftMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }

            int rightMenuCount = swipeRightMenu.getMenuItems().size();
            if (rightMenuCount > 0) {
                SwipeMenuView swipeRightMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.lv_swipe_right);
                // noinspection WrongConstant
                swipeRightMenuView.setOrientation(swipeRightMenu.getOrientation());
                swipeRightMenuView.bindMenu(swipeRightMenu, XRecyclerView.RIGHT_DIRECTION);
                swipeRightMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }
            if (leftMenuCount > 0 || rightMenuCount > 0) {
                ViewGroup viewById = (ViewGroup) swipeMenuLayout.findViewById(R.id.lv_swipe_content);
                viewById.addView(contentView);
                contentView = swipeMenuLayout;
            }
        }
        //将创建的View注册点击事件
        contentView.setOnClickListener(this);
        return createViewHolder(contentView, viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder viewHolder, int position) {
        View itemView = viewHolder.itemView;
        if (itemView instanceof SwipeMenuLayout) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) itemView;
            int childCount = swipeMenuLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = swipeMenuLayout.getChildAt(i);
                if (childView instanceof SwipeMenuView) {
                    ((SwipeMenuView) childView).bindAdapterViewHolder(viewHolder);
                }
            }
        }

        bindViewData(viewHolder, position, mItemDataList.get(position));
        viewHolder.itemView.setTag(mItemDataList.get(position));
    }

    /**
     * 加载item的view,直接返回加载的view即可
     *
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
     * @param i
     * @return item 的 view
     */
    public abstract View createView(ViewGroup viewGroup, int i);

    /**
     * 加载两个个ViewHolder,为BaseRecyclerViewHolder子类,直接返回子类的对象即可
     *
     * @param view item 的view
     * @return BaseRecyclerViewHolder 基类ViewHolder
     */
    public abstract BaseRecyclerViewHolder createViewHolder(View view, int itemType);

    /**
     * 显示数据抽象函数
     *
     * @param viewHolder 基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param position   位置
     * @param data       数据集合
     */
    public abstract void bindViewData(BaseRecyclerViewHolder viewHolder, int position, ItemDataType data);

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //默认接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    /**
     * 侧滑用添加
     */
    void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSwipeMenuCreator = swipeMenuCreator;
    }

    /**
     * Set to click menu listener.
     */
    void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        this.mSwipeMenuItemClickListener = swipeMenuItemClickListener;
    }
}