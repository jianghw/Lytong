package com.tzly.ctcyh.router.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by jianghw on 17-12-29.
 */

public class GridItemManager extends GridLayoutManager {

    public GridItemManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridItemManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public GridItemManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
