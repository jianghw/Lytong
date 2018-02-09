package com.zantong.mobile.home.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.primission.PermissionFail;
import com.tzly.annual.base.util.primission.PermissionGen;
import com.tzly.annual.base.util.primission.PermissionSuccess;
import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.utils.DialogMgr;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.weizhang.activity.ViolationDetails;

import butterknife.Bind;
import butterknife.OnClick;

import static com.tzly.annual.base.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 输入编码查询违章页面
 */
public class Codequery extends BaseJxActivity {

    @Bind(R.id.citation_notice_image)
    ImageView citationNoticeImage;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.pay_code_number)
    EditText pay_code_number;
    @Bind(R.id.img_camera)
    ImageView imgCamera;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
    }

    @Override
    protected int getContentResId() {
        return R.layout.code_query;
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("扫描单查询");
    }

    @Override
    protected void initViewStatus() {
    }

    @Override
    protected void DestroyViewAndThing() {
    }

    @OnClick({R.id.citation_notice_image, R.id.next_btn, R.id.img_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.citation_notice_image:
                new DialogMgr(Codequery.this, R.mipmap.cod_number_location);
                break;
            case R.id.next_btn:
                if (TextUtils.isEmpty(getEditNumber())) {
                    ToastUtils.toastShort("请输入正确罚单编号");
                } else {
                    Act.getInstance().gotoIntent(this, ViolationDetails.class, getEditNumber());
                }
                break;
            case R.id.img_camera:
                takeCapture();
                break;
            default:
                break;
        }
    }

    public String getEditNumber() {
        return pay_code_number.getText().toString().trim();
    }

    /**
     * 违章单扫描
     */
    public void takeCapture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}
            );
        } else {
            Act.getInstance().gotoIntent(this, CaptureActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        Act.getInstance().gotoIntent(this, CaptureActivity.class);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("相机权限被拒绝，请手机设置中打开");
    }
}