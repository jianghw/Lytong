package com.zantong.mobilecttx.user.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.DialogUtils;

import butterknife.OnClick;

/**
 * 问题反馈
 * Created by zhoujie on 2016/12/23.
 */

public class ProblemFeedbackActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Override
    public void initView() {
        setTitleText("联系客服");
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.problem_feedback_tel,})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.problem_feedback_tel: {
                tel();
            }
        }
    }

    private void tel() {
        DialogUtils.telDialog(this, "温馨提示", "是否要拨打工商银行客服热线?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhone();
            }
        });
    }

    public void takePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1001);
        } else {
            playTel();
        }
    }

    private void playTel() {
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作
        Uri data = Uri.parse("tel:" + "95588"); // 设置数据
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                playTel();
            } else {
                Toast.makeText(this, "权限未开启", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_problem_feedback;
    }
}
