package com.zantong.mobilecttx.weizhang.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.CommonListActivity;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.utils.AllCapTransformationMethod;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.popwindow.KeyWordPop;
import com.zantong.mobilecttx.widght.UISwitchButton;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 违法查询页面
 */
public class ViolationQueryFragment extends BaseRefreshJxFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView mImgCamera;
    /**
     * 沪
     */
    private TextView mTvProvince;
    private RelativeLayout mLayoutProvince;
    /**
     * 车牌号
     */
    private EditText mEditPlate;
    private ImageView mImgCartypeDesc;
    /**
     * 请输入发动机号后5位
     */
    private EditText mEditEngine;
    private TextView mTvTypeTitle;
    private ImageView mImgTypeDesc;
    private TextView mTvType;
    private ImageView mImgType;
    /**
     * 请选择
     */
    private TextView mTvDate;
    private ImageView mImgDate;
    /**
     * 请选择
     */
    private TextView mTvBrand;
    private ImageView mImgBrand;
    /**
     * 请选择
     */
    private TextView mTvCarSeries;
    private ImageView mImgCarSeries;
    /**
     * 请选择
     */
    private TextView mTvVehicle;
    private ImageView mImgVehicle;
    private UISwitchButton mCustomSwitchBtn;
    /**
     * 查询
     */
    private Button mBtnQuery;


    public static ViolationQueryFragment newInstance() {
        return new ViolationQueryFragment();
    }

    public static ViolationQueryFragment newInstance(String param1, String param2) {
        ViolationQueryFragment fragment = new ViolationQueryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_violation_query;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);
//车牌号
        mEditPlate.setTransformationMethod(new AllCapTransformationMethod());
        mEditPlate.setSelection(mEditPlate.getText().toString().length());
    }

    @Override
    protected void onFirstDataVisible() {

    }

    @Override
    protected void DestroyViewAndThing() {

    }

    public void initView(View view) {
        mImgCamera = (ImageView) view.findViewById(R.id.img_camera);
        mImgCamera.setOnClickListener(this);

        mTvProvince = (TextView) view.findViewById(R.id.tv_province);
        mLayoutProvince = (RelativeLayout) view.findViewById(R.id.layout_province);
        mLayoutProvince.setOnClickListener(this);
        mEditPlate = (EditText) view.findViewById(R.id.edit_plate);

        mImgCartypeDesc = (ImageView) view.findViewById(R.id.img_cartype_desc);
        mImgCartypeDesc.setOnClickListener(this);
        mEditEngine = (EditText) view.findViewById(R.id.edit_engine);

        mTvTypeTitle = (TextView) view.findViewById(R.id.tv_type_title);
        mImgTypeDesc = (ImageView) view.findViewById(R.id.img_type_desc);
        mImgTypeDesc.setOnClickListener(this);
        mTvType = (TextView) view.findViewById(R.id.tv_type);
        mTvType.setOnClickListener(this);

        mImgType = (ImageView) view.findViewById(R.id.img_type);
        mImgType.setOnClickListener(this);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvDate.setOnClickListener(this);
        mImgDate = (ImageView) view.findViewById(R.id.img_date);
        mImgDate.setOnClickListener(this);
        mTvBrand = (TextView) view.findViewById(R.id.tv_brand);
        mTvBrand.setOnClickListener(this);
        mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
        mImgBrand.setOnClickListener(this);
        mTvCarSeries = (TextView) view.findViewById(R.id.tv_car_series);
        mTvCarSeries.setOnClickListener(this);
        mImgCarSeries = (ImageView) view.findViewById(R.id.img_car_series);
        mImgCarSeries.setOnClickListener(this);
        mTvVehicle = (TextView) view.findViewById(R.id.tv_vehicle);
        mTvVehicle.setOnClickListener(this);
        mImgVehicle = (ImageView) view.findViewById(R.id.img_vehicle);
        mImgVehicle.setOnClickListener(this);
        mCustomSwitchBtn = (UISwitchButton) view.findViewById(R.id.custom_switch_btn);
        mCustomSwitchBtn.setOnClickListener(this);
        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_camera:
                takePhoto();
                break;
            case R.id.layout_province://车牌
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                new KeyWordPop(getActivity(), v, new KeyWordPop.KeyWordLintener() {
                    @Override
                    public void onKeyWordLintener(String cityStr) {
                        mTvProvince.setText(cityStr);
                    }
                });
                break;
            case R.id.img_cartype_desc://发动机
                break;
            case R.id.img_type_desc:
                new DialogMgr(getActivity(), R.mipmap.engine_number_image);
                break;
            case R.id.tv_type://车牌类型
                PublicData.getInstance().commonListType = 3;
                Intent intent = new Intent(getActivity(), CommonListActivity.class);
                startActivityForResult(intent, GlobalConstant.requestCode.violation_query_plate);
                break;
            case R.id.img_type:
                ToastUtils.toastShort("xxxxxxxxxxxxxxxxx");
                break;
            case R.id.tv_date:
                break;
            case R.id.img_date:
                break;
            case R.id.tv_brand:
                break;
            case R.id.img_brand:
                break;
            case R.id.tv_car_series:
                break;
            case R.id.img_car_series:
                break;
            case R.id.tv_vehicle:
                break;
            case R.id.img_vehicle:
                break;
            case R.id.custom_switch_btn:
                break;
            case R.id.btn_query:
                break;
        }
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
        } else {
            goToCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 拍照前权限调用
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        goToCamera();
    }

    protected void goToCamera() {
        Intent intentOcr = new Intent(getActivity(), OcrCameraActivity.class);
        intentOcr.putExtra(GlobalConstant.putExtra.ocr_camera_extra, 0);
//        startActivityForResult(intentOcr, 1201);
        startActivityForResult(intentOcr, GlobalConstant.requestCode.violation_query_camera);
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("您已关闭摄像头权限,请设置中打开");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//车牌类型
        if (requestCode == GlobalConstant.requestCode.violation_query_plate
                && resultCode == GlobalConstant.resultCode.common_list_fty) {

        }
    }

}
