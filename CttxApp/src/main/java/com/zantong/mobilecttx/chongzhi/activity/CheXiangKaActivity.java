/*
 * Copyright (C) 2008 ZXing authors
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

package com.zantong.mobilecttx.chongzhi.activity;

import android.view.View;
import android.widget.EditText;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import cn.qqtheme.framework.util.ToastUtils;

import butterknife.Bind;

/**
 * 中石油 - 车享卡
 * @author zyb
 *
 *
 *    *  *   *  *
 *  *      *      *
 *  *             *
 *   *           *
 *      *     *
 *         *
 *
 *
 * create at 17/1/4 下午4:51
 */
public final class CheXiangKaActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements IBaseView {

    @Bind(R.id.home_chexiangka_edit)
    EditText mEditText;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.home_chexiangka_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void initView() {
        setTitleText("选择车享卡");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.home_chexiangka_commit:
                if (mEditText.getText().toString().length() < 6){
                    ToastUtils.showShort(this,"位数不对");
                    return;
                }
                break;
        }
    }
}
