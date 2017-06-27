package com.zantong.mobilecttx.home.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.activity.ViolationDetails;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;
import com.zantong.mobilecttx.presenter.CodeQueryPresenter;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.StateBarSetting;
import com.zantong.mobilecttx.utils.TitleSetting;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.interf.ModelView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 输入编码查询违章页面
 * Created by Administrator on 2016/5/2.
 */
public class Codequery extends Activity implements ModelView{
    @Bind(R.id.citation_notice_image)
    ImageView citationNoticeImage;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.pay_code_number)
    EditText pay_code_number;
    private CodeQueryPresenter mCodeQueryPresenter;
    private ViolationDetailsBean mViolationDetailsBean;
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_query);
        ButterKnife.bind(this);
        StateBarSetting.settingBar(this, CaptureActivity.class, true);
        TitleSetting.getInstance().initTitle(this, "编码查询", 0, "取消", null, "扫罚单");
        mCodeQueryPresenter = new CodeQueryPresenter(this);
    }

    public HashMap<String, String> mapData(){
        HashMap<String, String>  mHashMap = new HashMap<>();
        mHashMap.put("violationnum", pay_code_number.getText().toString());
        return mHashMap;
    }

    @OnClick({R.id.citation_notice_image, R.id.next_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.citation_notice_image:
                new DialogMgr(Codequery.this, R.mipmap.cod_number_location);
                break;
            case R.id.next_btn:
//                PublicData.getInstance().mHashMap.put("ViolationDetailsStr", pay_code_number.getText().toString());
//                Act.getInstance().lauchIntent(Codequery.this, ViolationDetails.class);
                if(TextUtils.isEmpty(pay_code_number.getText().toString())){
                    ToastUtils.showShort(this, "请输入罚单编号");
                }else{
                    mCodeQueryPresenter.loadView(1);
                }
                break;
        }
    }

    @Override
    public void showProgress() {
        mDialog = DialogUtils.showLoading(this);
    }

    @Override
    public void updateView(Object object, int index) {
        mViolationDetailsBean = (ViolationDetailsBean) object;
        PublicData.getInstance().mHashMap.put("ViolationDetailsStr", pay_code_number.getText().toString());
        Act.getInstance().lauchIntent(Codequery.this, ViolationDetails.class);
    }

    @Override
    public void hideProgress() {
        if(mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
