package com.zantong.mobile.base.adapter.superadapter;

import android.content.Context;

/**
 * 数据控制器
 * ----------
 * 将ItemData和ViewHolder进行绑定，并设置监听
 */
public interface DataHolder<T> {

    void bind(Context context, SuperViewHolder holder, T item, int position);
}
