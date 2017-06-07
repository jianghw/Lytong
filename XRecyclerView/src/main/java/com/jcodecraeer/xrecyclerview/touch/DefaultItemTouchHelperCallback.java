package com.jcodecraeer.xrecyclerview.touch;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by jianghw on 2017/5/2.
 */
public class DefaultItemTouchHelperCallback extends ItemTouchHelper.Callback {


    private OnItemMovementListener onItemMovementListener;
    private OnItemMoveListener onItemMoveListener;
    private boolean isItemViewSwipeEnabled;

    /**
     * item 移动监听
     *
     * @param onItemMovementListener
     */
    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        this.onItemMovementListener = onItemMovementListener;
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        this.onItemMoveListener = onItemMoveListener;
    }

    public void setItemViewSwipeEnabled(boolean canSwipe) {
        this.isItemViewSwipeEnabled = canSwipe;
    }

    /**
     * 是否可以滑动
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isItemViewSwipeEnabled;
    }

    /**
     * 移动状态设置
     *
     * @param recyclerView
     * @param targetViewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder) {
        if (onItemMovementListener != null) {
            int dragFlags = onItemMovementListener.onDragFlags(recyclerView, targetViewHolder);
            int swipeFlags = onItemMovementListener.onSwipeFlags(recyclerView, targetViewHolder);
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            //适用GridLayoutManager类型和LinearLayoutManager类型。
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                //拖拽的方向。
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //侧滑的方向：left right。
                return makeMovementFlags(dragFlags, swipeFlags);
            } else if (layoutManager instanceof LinearLayoutManager) {
                //线性方向分为：水平和垂直方向。
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    //拖拽的方向。
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    //侧滑的方向：left right。
                    int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }
        }
        return makeMovementFlags(0, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targetHolder) {
        if (viewHolder.getItemViewType() == targetHolder.getItemViewType()) {// 相同类型的允许切换。
            if (onItemMoveListener == null) return false;
            //回调刷新数据及界面。
//            return onItemMoveListener.onItemMove(
//                    viewHolder.getAdapterPosition(), targetHolder.getAdapterPosition());
            return false;
        }
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //回调刷新数据及界面。
        if (onItemMoveListener != null)
            onItemMoveListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
