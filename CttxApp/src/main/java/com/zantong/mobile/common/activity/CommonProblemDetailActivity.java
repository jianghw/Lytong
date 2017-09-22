package com.zantong.mobile.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.common.bean.CommonProblem;

import butterknife.Bind;

/**
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemDetailActivity extends BaseJxActivity {

    @Bind(R.id.common_problem_detail)
    TextView mDetail;
    @Bind(R.id.common_problem_title)
    TextView mTitle;

    private CommonProblem mCommonProblem;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mCommonProblem = (CommonProblem) intent.getSerializableExtra("commonproblem");
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_common_problem_detail;
    }

    @Override
    protected void initFragmentView(View view) {
    }

    protected boolean isNeedKnife() {
        return true;
    }

    protected void initViewStatus() {
        initTitleContent(mCommonProblem != null ? mCommonProblem.getProblemTitle() : "出错");

        mTitle.setText(mCommonProblem != null ? mCommonProblem.getProblemChileTitle() : "出错");
        mDetail.setText(mCommonProblem != null ? mCommonProblem.getProblemDetail() : "出错");
    }

    @Override
    protected void DestroyViewAndThing() {
    }

}
