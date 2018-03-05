package com.zantong.mobilecttx.user.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tzly.ctcyh.java.response.BankResponse;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.dto.FeedbackDTO;

import butterknife.Bind;

/**
 * 帮助与反馈
 *
 * @author Sandy
 *         create at 16/6/2 下午12:26
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
            dto.setUsrid(MainRouter.getUserID());
            UserApiClient.commitFeedback(this, dto, new CallBack<BankResponse>() {
                @Override
                public void onSuccess(BankResponse bankResponse) {
                    hideDialogLoading();
                    if (Config.OK.equals(bankResponse.getSYS_HEAD().getReturnCode())){
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
