package com.zantong.mobile.user.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zantong.mobile.common.Config;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.UserApiClient;
import com.zantong.mobile.base.activity.BaseMvpActivity;
import com.zantong.mobile.base.interf.IBaseView;
import com.tzly.annual.base.bean.Result;
import com.zantong.mobile.user.dto.FeedbackDTO;
import com.zantong.mobile.presenter.HelpPresenter;
import com.tzly.annual.base.util.ToastUtils;

import butterknife.Bind;

/**
 * 帮助与反馈
 */
public class FeedbackActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements View.OnClickListener, IBaseView {

    @Bind(R.id.mine_feedback_content)
    EditText mInputContent;
    @Bind(R.id.mine_feedback_commit)
    Button mCommit;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_feedback_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mine_feedback_commit) {
            String content = mInputContent.getText().toString().trim();
            if ("".equals(content)) {
                ToastUtils.toastShort("内容不能为空");
                return;
            }
            showDialogLoading();
            FeedbackDTO dto = new FeedbackDTO();
            dto.setReqcontent(content);
            dto.setUsrid(MemoryData.getInstance().userID);
            UserApiClient.commitFeedback(this, dto, new CallBack<Result>() {
                @Override
                public void onSuccess(Result result) {
                    hideDialogLoading();
                    if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())){
                        FeedbackActivity.this.finish();
                    }
                }

                @Override
                public void onError(String errorCode, String msg) {
                    super.onError(errorCode, msg);
                    hideDialogLoading();
                }
            });

        }

    }

    @Override
    public void initView() {
        setTitleText("意见反馈");
        mCommit.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }
}
