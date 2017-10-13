/**
 * Copyright 2017 andy (https://github.com/andyxialm)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jianghw.multi.state.layout;

import android.support.annotation.LayoutRes;

/**
 * Description: MultiStateLayout default settings
 */
public class MultiStateConfiguration {

    public static class Builder {

        private int mLoadingResId = -1;
        private int mEmptyResId = -1;
        private int mErrorResId = -1;
        private int mNetErrorResId = -1;

        private int mAnimDuration = 300;
        private boolean mAnimEnable;

        public Builder() {
        }

        @SuppressWarnings("unused")
        public Builder setDefaultLoadingLayout(@LayoutRes int resId) {
            mLoadingResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setDefaultEmptyLayout(@LayoutRes int resId) {
            mEmptyResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setDefaultErrorLayout(@LayoutRes int resId) {
            mErrorResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setDefaultNetErrorLayout(@LayoutRes int resId) {
            mNetErrorResId = resId;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setAnimDuration(int duration) {
            mAnimDuration = duration;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setAnimEnable(boolean animEnable) {
            mAnimEnable = animEnable;
            return this;
        }

        @SuppressWarnings("unused")
        public int getDefaultEmptyLayout() {
            return mEmptyResId;
        }

        @SuppressWarnings("unused")
        public int getDefaultLoadingLayout() {
            return mLoadingResId;
        }

        @SuppressWarnings("unused")
        public int getDefaultErrorLayout() {
            return mErrorResId;
        }

        @SuppressWarnings("unused")
        public int getDefaultNetErrorLayout() {
            return mNetErrorResId;
        }

        @SuppressWarnings("unused")
        public int getAnimDuration() {
            return mAnimDuration;
        }

        @SuppressWarnings("unused")
        public boolean isAnimEnable() {
            return mAnimEnable;
        }

    }
}
