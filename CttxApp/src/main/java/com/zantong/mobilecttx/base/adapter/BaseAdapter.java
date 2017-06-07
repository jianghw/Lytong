package com.zantong.mobilecttx.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zantong.mobilecttx.base.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类adapter
 * @author Sandy
 * create at 16/6/2 下午2:14
 */
public abstract class BaseAdapter<ItemDataType> extends
        RecyclerView.Adapter<BaseRecyclerViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public ArrayList<ItemDataType> mItemDataList = new ArrayList<ItemDataType>();

    public BaseAdapter() {
    }

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
        if (itemDataTypes.size() > 0) {
            for (ItemDataType itemDataType : itemDataTypes) {
                mItemDataList.add(itemDataType);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 替换全部数据
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void replace(List<ItemDataType> itemDataTypes) {
        mItemDataList.clear();
        if (itemDataTypes.size() > 0) {
            mItemDataList.addAll(itemDataTypes);
            notifyDataSetChanged();
        }
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

    public ArrayList<ItemDataType> getAll(){
        return mItemDataList;
    }
    /**
     * 移除一条数据
     *
     * @param item
     */
    public void remove(ItemDataType item) {
        mItemDataList.remove(item);
        notifyDataSetChanged();
    }

    /**
     * 移除所有数据
     */
    public void removeAll() {
        mItemDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItemDataList.size();
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder viewHolder, int i) {
        showData(viewHolder, i, mItemDataList.get(i));
        viewHolder.itemView.setTag(mItemDataList.get(i));
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = createView(viewGroup, i);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        BaseRecyclerViewHolder holder = createViewHolder(view,i);
        return holder;
    }

    /**
     * 显示数据抽象函数
     *
     * @param viewHolder 基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param position   位置
     * @param data      数据集合
     */
    public abstract void showData(BaseRecyclerViewHolder viewHolder, int position, ItemDataType data);

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
    public abstract BaseRecyclerViewHolder createViewHolder(View view,int itemType);


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (ItemDataType) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //默认接口
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }
}