package com.zantong.mobilecttx.common.activity;

import android.content.Intent;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.bean.CommonProblem;
import com.zantong.mobilecttx.presenter.HelpPresenter;

import butterknife.Bind;

/**
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemDetailActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.common_problem_detail)
    TextView mDetail;
    @Bind(R.id.common_problem_title)
    TextView mTitle;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            CommonProblem commonProblem = (CommonProblem) intent.getSerializableExtra("commonproblem");
            setTitleText(commonProblem.getProblemTitle());
            mTitle.setText(commonProblem.getProblemChileTitle());
            mDetail.setText(commonProblem.getProblemDetail());
        }
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_common_problem_detail;
    }

}
