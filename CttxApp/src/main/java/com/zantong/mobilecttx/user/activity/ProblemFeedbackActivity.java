package com.zantong.mobilecttx.user.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.DialogUtils;

/**
 * 问题反馈
 */

public class ProblemFeedbackActivity extends AbstractBaseActivity implements View.OnClickListener, View.OnLongClickListener {

    /**
     * phone_num
     */
    private TextView mProblemFeedbackTel;
    private ImageView mImgCode;

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
        intent.setAction(Intent.ACTION_DIAL); // 设置动作
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
                ToastUtils.toastShort("请开启电话权限");
            }
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_problem_feedback;
    }


    @Override
    protected void bundleIntent(Intent intent) {

    }

    @Override
    protected void bindFragment() {
        titleContent("联系客服");
    }

    @Override
    protected void initContentData() {
        initView();
    }

    public void initView() {
        mProblemFeedbackTel = (TextView) findViewById(R.id.problem_feedback_tel);
        mProblemFeedbackTel.setOnClickListener(this);
        mImgCode = (ImageView) findViewById(R.id.img_code);
        mImgCode.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.problem_feedback_tel:
                tel();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager service = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", "畅通车友会");
        // 将ClipData内容放到系统剪贴板里。
        if (service == null) return false;
        service.setPrimaryClip(mClipData);
        ToastUtils.toastShort("畅通车友会 复制成功");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("weixin://wap/pay?"));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
