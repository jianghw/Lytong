package com.zantong.mobilecttx.home.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.contract.ModelView;
import com.zantong.mobilecttx.presenter.CodeQueryPresenter;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.ViolationDetails;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 输入编码查询违章页面
 */
public class Codequery extends BaseJxActivity implements ModelView {

    @Bind(R.id.citation_notice_image)
    ImageView citationNoticeImage;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.pay_code_number)
    EditText pay_code_number;
    @Bind(R.id.img_camera)
    ImageView imgCamera;

    private CodeQueryPresenter mCodeQueryPresenter;

    private Dialog mDialog;
    private String mType;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getStringExtra(GlobalConstant.putExtra.common_extra);
        }
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

        if (!TextUtils.isEmpty(mType) && mType.equals("type=finish")) {
            pay_code_number.setText(mType);
        }
        mCodeQueryPresenter = new CodeQueryPresenter(this);
    }

    @Override
    protected void DestroyViewAndThing() {
        hideProgress();
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
                    mCodeQueryPresenter.loadView(1);
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

    @Override
    public void showProgress() {
        mDialog = DialogUtils.showLoading(this);
    }

    /**
     * 查询成功
     */
    @Override
    public void updateView(Object object, int index) {
        PublicData.getInstance().mHashMap.put("ViolationDetailsStr", getEditNumber());

        Act.getInstance().lauchIntent(this, ViolationDetails.class);

        if (!TextUtils.isEmpty(mType) && mType.equals("type=finish")) {
            finish();
        }
    }

    @Override
    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        if (!TextUtils.isEmpty(mType) && mType.equals("type=finish")) {
            ToastUtils.toastShort("违章编号查询失败");
            finish();
        }

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

    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        Act.getInstance().gotoIntent(this, CaptureActivity.class);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("相机权限被拒绝，请手机设置中打开");
    }
}
