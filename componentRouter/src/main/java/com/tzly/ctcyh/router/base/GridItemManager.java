package com.tzly.ctcyh.router.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by jianghw on 17-12-29.
 */

public class GridItemManager extends GridLayoutManager {

    public GridItemManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
