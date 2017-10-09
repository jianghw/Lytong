/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tzly.annual.base.custom;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 */
public class ScrollSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mScrollUpChild;

    public ScrollSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return ViewCompat.canScrollVertically(mScrollUpChild, -1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * true-->onTouchEvent
     * false,super--> 下层处理
     * 下滑为正slideY 上滑为负
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(x - mLastMotionX);
                float deltaY = Math.abs(y - mLastMotionY);

                if (deltaX < deltaY-6) return true;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    float mLastMotionY = 0;
    float mLastMotionX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //        float x = event.getX();
        //        float y = event.getY();
        //
        //        switch (event.getAction()) {
        //            case MotionEvent.ACTION_DOWN:
        //                mLastMotionX = x;
        //                mLastMotionY = y;
        //                break;
        //            case MotionEvent.ACTION_MOVE:
        //                float deltaX = Math.abs(x - mLastMotionX);
        //                float deltaY = Math.abs(y - mLastMotionY);
        //
        //                if (deltaX - 6 > deltaY) return false;
        //            case MotionEvent.ACTION_UP:
        //                break;
        //            default:
        //                break;
        //        }
        return super.onTouchEvent(event);
    }
}
