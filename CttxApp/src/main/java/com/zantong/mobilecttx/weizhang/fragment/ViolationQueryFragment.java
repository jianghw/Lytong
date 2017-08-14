package com.zantong.mobilecttx.weizhang.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import cn.qqtheme.framework.contract.bean.BaseResult;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.car.activity.CarBrandActivity;
import com.zantong.mobilecttx.car.activity.CarChooseActivity;
import com.zantong.mobilecttx.car.bean.CarBrandBean;
import com.zantong.mobilecttx.car.bean.CarStyleInfoBean;
import com.zantong.mobilecttx.car.bean.CarXiBean;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.CommonListActivity;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.contract.IViolationQueryFtyContract;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrBean;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.eventbus.AddCarInfoEvent;
import com.zantong.mobilecttx.eventbus.UpdateCarInfoEvent;
import com.zantong.mobilecttx.presenter.weizhang.ViolationQueryFtyPresenter;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.utils.AllCapTransformationMethod;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.VehicleTypeTools;
import com.zantong.mobilecttx.utils.dialog.MyChooseDialog;
import com.zantong.mobilecttx.utils.popwindow.KeyWordPop;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.ViolationListActivity;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;
import com.zantong.mobilecttx.widght.UISwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 违法查询页面
 */
public class ViolationQueryFragment extends BaseRefreshJxFragment
        implements View.OnClickListener, IViolationQueryFtyContract.IViolationQueryFtyView {

    private static final String ARG_PARAM1 = "param1";
    /**
     * 用于编辑或删除的标记
     */
    private VehicleLicenseBean mParam1 = null;

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
    private Button mBtnDelete;
    private RelativeLayout mLayoutBrand;
    private RelativeLayout mLayoutCarSeries;
    private RelativeLayout mLayoutVehicle;
    private RelativeLayout mLayDate;
    private RelativeLayout mLayoutSave;

    /**
     * P
     */
    private IViolationQueryFtyContract.IViolationQueryFtyPresenter mPresenter;
    /**
     * 车品牌id
     */
    private int carBrandId;
    /**
     * 车系id
     */
    private int carSeriesId;
    private BindCarDTO mBindCarDTO = new BindCarDTO();
    /**
     * 老接口
     */
    private CarInfoDTO mCarInfoDTO = new CarInfoDTO();


    public static ViolationQueryFragment newInstance() {
        return new ViolationQueryFragment();
    }

    public static ViolationQueryFragment newInstance(VehicleLicenseBean param1) {
        ViolationQueryFragment fragment = new ViolationQueryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    protected boolean isRefresh() {
        return false;
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

        ViolationQueryFtyPresenter mPresenter = new ViolationQueryFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
//车牌号
        //小写转化为大写
        mEditPlate.setTransformationMethod(new AllCapTransformationMethod());
        ////设置光标位置在文本框末尾
        int plateText = mEditPlate.getText().toString().trim().length();
        mEditPlate.setSelection(plateText);
//选择项目
        initLayEnable(true, mLayoutBrand);
        initLayEnable(false, mLayoutCarSeries);
        initLayEnable(false, mLayoutVehicle);
    }

    @Override
    public void setPresenter(IViolationQueryFtyContract.IViolationQueryFtyPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 是否可以点击
     */
    private void initLayEnable(boolean enabled, RelativeLayout layoutBrand) {
        if (layoutBrand != null) layoutBrand.setEnabled(enabled);
    }

    @Override
    protected void onFirstDataVisible() {
//        if (!SPUtils.getInstance().getGuideXingShiZheng()) {
//            PublicData.getInstance().GUIDE_TYPE = 1;
//            Act.getInstance().gotoIntent(getActivity(), GuideActivity.class);
//        }

//可添加控件操作
        int size;
        if (PublicData.getInstance().loginFlag)
            size = PublicData.getInstance().mServerCars.size();
        else
            size = SPUtils.getInstance().getCarsInfo().size();

        mCustomSwitchBtn.setChecked(size < 3);
        mCustomSwitchBtn.setEnabled(size < 3);

        if (mParam1 != null) initBundleData();
    }

    /**
     * 用于删除 编辑
     */
    private void initBundleData() {

        mBtnDelete.setVisibility(mParam1 != null ? View.VISIBLE : View.GONE);
        mBtnQuery.setText(mParam1 != null ? "编辑保存" : "查  询");

        mCustomSwitchBtn.setChecked(true);
        mCustomSwitchBtn.setEnabled(false);

        String carNum = Des3.decode(mParam1.getPlateNo());

        setTvProvince(carNum.substring(0, 1));
        setEditPlate(carNum.substring(1, carNum.length()));

        mLayoutProvince.setEnabled(false);
        mEditPlate.setEnabled(false);

        setEditEngine(Des3.decode(mParam1.getEngineNo()));
        setTvType(VehicleTypeTools.switchVehicleType(mParam1.getVehicleType()));
        setTvData(mParam1.getIssueDate());
//是否可缴费
        mBindCarDTO.setIsPay(mParam1.getIsPayable());
//品牌
        String brand = mParam1.getBrand();
        if (!TextUtils.isEmpty(brand)) {
            mTvBrand.setText(brand);
            carBrandId = Integer.valueOf(mParam1.getBrandId());
            mBindCarDTO.setBrandId(mParam1.getBrandId());
            initLayEnable(true, mLayoutCarSeries);
        }
//车系
        String series = mParam1.getSeries();
        if (!TextUtils.isEmpty(series)) {
            mTvCarSeries.setText(series);
            carSeriesId = Integer.valueOf(mParam1.getSeriesId());
            mBindCarDTO.setSeriesId(mParam1.getSeriesId());
            initLayEnable(true, mLayoutVehicle);
        }
//车型
        String carModel = mParam1.getCarModel();
        if (!TextUtils.isEmpty(carModel)) {
            mTvVehicle.setText(carModel);
            mBindCarDTO.setCarModelId(mParam1.getCarModelId());
        }
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();

        mCarInfoDTO = null;
        mBindCarDTO = null;
        mParam1 = null;
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

        mLayDate = (RelativeLayout) view.findViewById(R.id.lay_date);
        mLayDate.setOnClickListener(this);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mImgDate = (ImageView) view.findViewById(R.id.img_date);

        mLayoutBrand = (RelativeLayout) view.findViewById(R.id.lay_brand);
        mLayoutBrand.setOnClickListener(this);
        mTvBrand = (TextView) view.findViewById(R.id.tv_brand);
        mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
        mLayoutCarSeries = (RelativeLayout) view.findViewById(R.id.lay_car_series);
        mLayoutCarSeries.setOnClickListener(this);
        mTvCarSeries = (TextView) view.findViewById(R.id.tv_car_series);

        mImgCarSeries = (ImageView) view.findViewById(R.id.img_car_series);
        mLayoutVehicle = (RelativeLayout) view.findViewById(R.id.lay_vehicle);
        mLayoutVehicle.setOnClickListener(this);
        mTvVehicle = (TextView) view.findViewById(R.id.tv_vehicle);

        mLayoutSave = (RelativeLayout) view.findViewById(R.id.lay_save);
        mImgVehicle = (ImageView) view.findViewById(R.id.img_vehicle);
        mCustomSwitchBtn = (UISwitchButton) view.findViewById(R.id.custom_switch_btn);

//用于编辑删除功能作用时
        mBtnDelete = (Button) view.findViewById(R.id.btn_delete);
        mBtnDelete.setOnClickListener(this);

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
                new DialogMgr(getActivity(), R.mipmap.engine_number_image);
                break;
            case R.id.img_type_desc:
                ToastUtils.toastShort("请新能源车辆选择新能源汽车车牌");
                break;
            case R.id.tv_type://车牌类型
                PublicData.getInstance().commonListType = 3;
                Intent intent = new Intent(getActivity(), CommonListActivity.class);
                startActivityForResult(intent, GlobalConstant.requestCode.violation_query_plate);
                break;
            case R.id.lay_date://日期
                String temp = mTvDate.getText().toString().trim();
                String[] temps = null;
                if (!TextUtils.isEmpty(temp)) temps = temp.split("-");
                MyChooseDialog dialog = new MyChooseDialog(
                        getActivity(),
                        temps,
                        new MyChooseDialog.OnChooseDialogListener() {

                            @Override
                            public void back(String name) {
                                mTvDate.setText(name);
                            }
                        });
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
            case R.id.lay_brand://品牌
                startActivityForResult(
                        new Intent(getActivity(), CarBrandActivity.class),
                        CarBrandActivity.REQUEST_CODE);
                break;
            case R.id.lay_car_series://车系
                Intent intentL = new Intent(getActivity(), CarChooseActivity.class);
                intentL.putExtra("type", 1);
                intentL.putExtra("id", carBrandId);
                startActivityForResult(intentL, CarChooseActivity.REQUEST_L_CODE);
                break;
            case R.id.lay_vehicle://车型
                Intent intentX = new Intent(getActivity(), CarChooseActivity.class);
                intentX.putExtra("type", 2);
                intentX.putExtra("id", carSeriesId);
                intentX.putExtra("idB", carBrandId);
                startActivityForResult(intentX, CarChooseActivity.REQUEST_X_CODE);
                break;
            case R.id.btn_query://提交
                dataFormValidation();
                updateVehicle();
                break;
            case R.id.btn_delete://删除
                dataFormValidation();
                deleteCar();
                break;
            default:
                break;
        }
    }

    protected void deleteCar() {
        if (mParam1 != null && mParam1.getIsPayable() == 1) {
            DialogUtils.createDialog(getActivity(),
                    "该车辆为可缴费车辆不能进行删除操作",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
        } else {
            DialogUtils.delDialog(getActivity(),
                    "删除提示",
                    "您确定要删除该车辆吗？",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mPresenter != null) mPresenter.removeVehicleLicense();
                        }
                    });
        }
    }

    /**
     * 数据表单验证
     */
    private void dataFormValidation() {
        String plate = mEditPlate.getTransformationMethod().getTransformation(getEditPlate(), mEditPlate).toString();

        String engine = getEditEngine();
        String carType = getTvType();

        if (TextUtils.isEmpty(plate)) {
            ToastUtils.toastShort("请输入正确的车牌号");
            return;
        }
        if (TextUtils.isEmpty(plate) || engine.length() != 5) {
            ToastUtils.toastShort("请输入正确的发动机号");
            return;
        }

        if (TextUtils.isEmpty(carType)) {
            ToastUtils.toastShort("请选择车辆类型");
            return;
        }

        String vehicleCode = VehicleTypeTools.switchVehicleCode(carType);
        if (plate.length() >= 7 && (vehicleCode.equals("51") || vehicleCode.equals("52"))) {
            DialogUtils.createDialog(getActivity(),
                    "温馨提示",
                    "如果您的汽车为新能源汽车,车牌类型请选择新能源汽车",
                    "取消",
                    "继续查询",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
        }

        String carNum = getTvProvince() + plate;
        initCarInfoDto(carNum, engine, vehicleCode);
        initBindCarDTO(carNum, engine, vehicleCode);
    }

    private void updateVehicle() {
        if (mParam1 != null) {//编辑
            if (mPresenter != null) mPresenter.updateVehicleLicense();
        } else {
            GlobalConfig.getInstance().eventIdByUMeng(13);
            submitData();
        }
    }

    /**
     * 未登录时直接提交到新服务器
     * 登录时先提交旧服务器,再提交新服务器
     */
    private void submitData() {
        //登陆状态
        if (PublicData.getInstance().loginFlag) {
            if (mCustomSwitchBtn.isChecked()) {
                List<UserCarInfoBean> serverCars = PublicData.getInstance().mServerCars;
                if (serverCars != null && serverCars.size() > 0) {
                    int size = serverCars.size();
                    if (size >= 3) {
                        ToastUtils.toastShort("当前车辆已超过3俩，请不要再保存");
                        return;
                    }
                    for (int i = 0; i < size; i++) {
                        if (serverCars.get(i).getCarnum().equals(mCarInfoDTO.getCarnum())) {
                            ToastUtils.toastShort("该车辆已存在，请不要重复添加");
                            return;
                        }
                    }
                }
                commitCarInfoToServer();
            } else {
                doQueryVehicle();
            }
        }
        //未登录状态
        if (!PublicData.getInstance().loginFlag) {
            if (mCustomSwitchBtn.isChecked()) {
                List<CarInfoDTO> infoDTOList = SPUtils.getInstance().getCarsInfo();
                if (infoDTOList != null && infoDTOList.size() > 0) {
                    int size = infoDTOList.size();
                    if (size >= 3) {
                        ToastUtils.toastShort("当前车辆已超过3俩，请不要再保存");
                        return;
                    }
                    for (int i = 0; i < size; i++) {
                        if (infoDTOList.get(i).getCarnum().equals(mCarInfoDTO.getCarnum())) {
                            ToastUtils.toastShort("该车辆已存在，请不要重复添加");
                            return;
                        }
                    }
                }
                SPUtils.getInstance().getCarsInfo().add(getCarInfoDTO());
                PublicData.getInstance().mCarNum++;//车辆数+1
                EventBus.getDefault().post(new UpdateCarInfoEvent(true));
                EventBus.getDefault().post(new AddCarInfoEvent(true, getCarInfoDTO()));
            }
            doQueryVehicle();
        }
    }

    private void commitCarInfoToServer() {
        if (mPresenter != null) mPresenter.addVehicleLicense();

        doQueryVehicle();
    }

    private void doQueryVehicle() {

        PublicData.getInstance().mHashMap.put("carnum", mCarInfoDTO.getCarnum());
        PublicData.getInstance().mHashMap.put("enginenum", mCarInfoDTO.getEnginenum());
        PublicData.getInstance().mHashMap.put("carnumtype", mCarInfoDTO.getCarnumtype());
        PublicData.getInstance().mHashMap.put("IllegalViolationName", mCarInfoDTO.getCarnum());//标题

        ViolationDTO violationDTO = new ViolationDTO();
        violationDTO.setCarnum(RSAUtils.strByEncryption(mCarInfoDTO.getCarnum(), true));
        violationDTO.setEnginenum(RSAUtils.strByEncryption(mCarInfoDTO.getEnginenum(), true));
        violationDTO.setCarnumtype(mCarInfoDTO.getCarnumtype());

        Intent intent = new Intent(getActivity(), ViolationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("params", violationDTO);
        intent.putExtras(bundle);
        intent.putExtra("plateNum", mCarInfoDTO.getCarnum());
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * cip.cfc.u005.01 添加
     * cip.cfc.c001.01 修改
     * 参数名	必选	类型	说明
     * usrid	是	string	用户ID
     * carnum	是	string	车牌号 加密
     * carmodel	否	string	车辆型号
     * ispaycar	是	string	是否可缴费车辆
     * inspectdate	否	string	年检日期
     * defaultflag	是	string	默认标识
     * inspectflag	是	string	年检提醒标识
     * carnumtype	是	string	车牌类型
     * violationflag	是	string	违章提醒标识
     * enginenum	是	string	发动机号 加密
     */
    private void initCarInfoDto(String carNum, String engine, String vehicleCode) {
        mCarInfoDTO.setUsrid(PublicData.getInstance().userID);
        mCarInfoDTO.setCarnum(carNum);
        mCarInfoDTO.setEnginenum(engine);
        //数字
        mCarInfoDTO.setCarnumtype(vehicleCode);

        mCarInfoDTO.setCarmodel("");
        mCarInfoDTO.setIspaycar("0");
        mCarInfoDTO.setInspectdate(getTvData());
        mCarInfoDTO.setDefaultflag("0");
        mCarInfoDTO.setInspectflag("0");
        mCarInfoDTO.setViolationflag("0");
    }

    @Override
    public CarInfoDTO getCarInfoDTO() {
        return mCarInfoDTO;
    }

    /**
     * 添加保存 老接口 失败
     */
    @Override
    public void commitCarInfoToOldServerError(String message) {
        ToastUtils.toastShort("保存车辆失败" + message);
    }

    @Override
    public void commitCarInfoToOldServerSucceed(Result responseBean) {
        PublicData.getInstance().mCarNum++;//车辆数+1
        EventBus.getDefault().post(new UpdateCarInfoEvent(true));
        EventBus.getDefault().post(new AddCarInfoEvent(true, getCarInfoDTO()));

        doQueryVehicle();
    }

    /**
     * 删除车辆 失败
     */
    @Override
    public void removeVehicleLicenseSucceed(BaseResult responseBean) {
        getActivity().finish();
    }

    @Override
    public void removeVehicleLicenseError(String message) {
        hideLoadingProgress();
        ToastUtils.toastShort(message);
    }

    @Override
    public BindCarDTO getBindCarDTO() {
        return mBindCarDTO;
    }

    @Override
    public void commitCarInfoToNewServerError(String message) {
        ToastUtils.toastShort(message);
    }

    /**
     * 48.绑定行驶证接口
     * 参数名	必选	类型	说明
     * plateNo	是	string	车牌号
     * engineNo	是	string	发动机号
     * vehicleType	是	string	车辆类型
     * usrnum	是	string	安盛id
     * issueDate	否	string	初次领证日期
     */
    private void initBindCarDTO(String carNum, String engine, String vehicleCode) {
        mBindCarDTO.setPlateNo(carNum);
        mBindCarDTO.setEngineNo(engine);
        mBindCarDTO.setVehicleType(vehicleCode);
        mBindCarDTO.setUsrnum(PublicData.getInstance().userID);
        mBindCarDTO.setIssueDate(getTvData());

//        mBindCarDTO.setFileNum("");
//        mBindCarDTO.setAddress(mPosition.getText().toString());
//        mBindCarDTO.setUseCharacter(mUseProperty.getText().toString());
//        mBindCarDTO.setCarModel(mBrand.getText().toString());
//        mBindCarDTO.setVin(mCode.getText().toString());
    }

    public String getTvProvince() {
        return mTvProvince.getText().toString().trim();
    }

    public void setTvProvince(String province) {
        if (mTvProvince != null) mTvProvince.setText(province);
    }

    public String getEditPlate() {
        return mEditPlate.getText().toString().trim();
    }

    public void setEditPlate(String editPlate) {
        if (mEditPlate != null) mEditPlate.setText(editPlate);
    }

    public String getEditEngine() {
        return mEditEngine.getText().toString().trim();
    }

    public void setEditEngine(String editEngine) {
        if (mEditEngine != null) mEditEngine.setText(editEngine);
    }

    public String getTvType() {
        return mTvType.getText().toString();
    }

    public void setTvType(String tvType) {
        if (mTvType != null) mTvType.setText(tvType);
    }

    public String getTvData() {
        return mTvDate.getText().toString();
    }

    public void setTvData(String tvData) {
        if (mTvDate != null) mTvDate.setText(tvData);
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
                && resultCode == GlobalConstant.resultCode.common_list_fty && data != null) {
            setTvType(data.getStringExtra(GlobalConstant.putExtra.common_list_extra));
        }
//拍照回调
        if (requestCode == GlobalConstant.requestCode.violation_query_camera
                && resultCode == GlobalConstant.resultCode.ocr_camera_license) {

            if (OcrCameraActivity.file == null)
                ToastUtils.toastShort("照片获取失败");
            else if (mPresenter != null)
                mPresenter.uploadDrivingImg();
        }
//品牌
        if (requestCode == CarBrandActivity.REQUEST_CODE
                && resultCode == CarBrandActivity.RESULT_CODE && data != null) {
            CarBrandBean carBrandBean = (CarBrandBean) data.getSerializableExtra(CarBrandActivity.CAR_BRAND_BEAN);
            if (carBrandBean != null) {
                mTvBrand.setText(carBrandBean.getBrandName());

                carBrandId = carBrandBean.getId();
                mBindCarDTO.setBrandId(String.valueOf(carBrandId));

                initLayEnable(true, mLayoutCarSeries);
//滞空处理
                mTvCarSeries.setText("");
                mBindCarDTO.setSeriesId("");
                mTvVehicle.setText("");
                mBindCarDTO.setCarModelId("");
            }
        }
//车系
        if (requestCode == CarChooseActivity.REQUEST_L_CODE
                && resultCode == CarChooseActivity.RESULT_L_CODE && data != null) {
            CarXiBean carBrandBean = (CarXiBean) data.getSerializableExtra(CarChooseActivity.CAR_LINE_BEAN);
            if (carBrandBean != null) {
                mTvCarSeries.setText(carBrandBean.getSeriesName());

                carSeriesId = carBrandBean.getSeriesId();
                mBindCarDTO.setSeriesId(String.valueOf(carSeriesId));

                initLayEnable(true, mLayoutVehicle);
//滞空处理
                mTvVehicle.setText("");
                mBindCarDTO.setCarModelId("");
            }
        }
//车型
        if (requestCode == CarChooseActivity.REQUEST_X_CODE
                && resultCode == CarChooseActivity.RESULT_X_CODE && data != null) {
            CarStyleInfoBean carBrandBean = (CarStyleInfoBean) data.getSerializableExtra(CarChooseActivity.CAR_XING_BEAN);
            if (carBrandBean != null) {
                mTvVehicle.setText(carBrandBean.getCarModelName());
                mBindCarDTO.setCarModelId(String.valueOf(carBrandBean.getCarModelId()));
            }
        }
    }

    @Override
    public void loadingProgress() {
        showDialogLoading();
    }

    @Override
    public void hideLoadingProgress() {
        hideDialogLoading();
    }

    /**
     * 55.行驶证扫描接口
     */
    @Override
    public void uploadDrivingImgSucceed(DrivingOcrResult result) {
        DrivingOcrBean bean = result.getContent();
        if (bean != null) {
            String cardNo = bean.getCardNo();
            String provinces = "沪浙苏皖京藏川鄂甘赣贵桂黑吉冀津晋辽鲁蒙闽宁青琼陕湘新渝豫粤云";

            if (!TextUtils.isEmpty(cardNo) && cardNo.length() >= 1 && mParam1 == null) {
                String province = cardNo.substring(0, 1);
                String plateNum = cardNo.substring(1, cardNo.length());

                if (provinces.contains(province)) mTvProvince.setText(province);
                mEditPlate.setText(plateNum);
            }

            String engineNum = bean.getEnginePN();
            if (!TextUtils.isEmpty(engineNum) && engineNum.length() > 5) {
                String engine = engineNum.substring(engineNum.length() - 5, engineNum.length());
                mEditEngine.setText(engine);
            }
            mTvType.setText(bean.getVehicleType() != null ? bean.getVehicleType() : "");

            mCarInfoDTO.setCarnumtype(VehicleTypeTools.switchVehicleCode(mTvType.getText().toString()));
//TODO RegisterDate 注册日期
            String registerDate = bean.getRegisterDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
            try {
                Date data = simpleDateFormat.parse(registerDate);
                String dataString = sdf.format(data);
                mTvDate.setText(dataString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            ToastUtils.toastShort("行驶证图片解析失败(55)，请重试");
        }
    }

    @Override
    public void uploadDrivingImgError(String message) {
        hideLoadingProgress();
        ToastUtils.toastShort(message);
    }
}
