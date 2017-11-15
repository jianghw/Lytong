package com.zantong.mobilecttx.car.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tzly.ctcyh.router.bean.BankResponse;
import com.tzly.ctcyh.router.bean.BaseResponse;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.car.bean.CarBrandBean;
import com.zantong.mobilecttx.car.bean.CarStyleInfoBean;
import com.zantong.mobilecttx.car.bean.CarXiBean;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.car.dto.LiYingCarManageDTO;
import com.zantong.mobilecttx.card.bean.XingShiZhengBean;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.common.activity.CommonListActivity;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrBean;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.eventbus.AddCarInfoEvent;
import com.zantong.mobilecttx.eventbus.EditCarInfoEvent;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.utils.AllCapTransformationMethod;
import com.zantong.mobilecttx.utils.CarBrandParser;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.UiHelpers;
import com.zantong.mobilecttx.utils.VehicleTypeTools;
import com.zantong.mobilecttx.utils.dialog.MyChooseDialog;
import com.zantong.mobilecttx.utils.popwindow.KeyWordPop;
import com.zantong.mobilecttx.weizhang.activity.ViolationListActivity;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;
import com.zantong.mobilecttx.widght.SettingItemView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;



public class AddCarActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements IBaseView {

    @Bind(R.id.add_car_layout)
    View mLayout;//车牌号FFFFB700
    @Bind(R.id.add_car_plate)
    EditText mPlateNum;//车牌号
    @Bind(R.id.add_car_province_text)
    TextView mProvince;//省份
    @Bind(R.id.add_car_province_layout)
    View mProvinceSelBtn;//选择按钮

    @Bind(R.id.add_car_engine_instruction)
    ImageView mEngineInsBtn;//发动机说明按钮
    @Bind(R.id.add_car_engine_num)
    EditText mEngineNum;

    @Bind(R.id.add_car_type_layout)
    View mTypeSelBtn;//选择车型
    @Bind(R.id.add_car_type_text)
    TextView mType;
    @Bind(R.id.add_car_name)
    EditText mPersonName;
    @Bind(R.id.add_car_pos)
    EditText mPosition;
    @Bind(R.id.add_car_property)
    EditText mUseProperty;
    @Bind(R.id.add_car_brand)
    EditText mBrand;
    @Bind(R.id.add_car_code)
    EditText mCode;
    @Bind(R.id.add_car_date_text)
    TextView mRegisterDate;
    @Bind(R.id.add_car_date_layout)
    View mDateSelBtn;
    @Bind(R.id.add_car_commit)
    Button mCommit;
    @Bind(R.id.add_car_remove)
    Button mRemove;
    @Bind(R.id.add_car_item_brand)
    SettingItemView mCarBrand;         //品牌
    @Bind(R.id.add_car_item_xi)
    SettingItemView mCarXi;         //车系
    @Bind(R.id.add_car_item_xing)
    SettingItemView mCarXing;         //车型
    @Bind(R.id.add_car_img)
    ImageView mOcrImg;         //ocr缩略图
    @Bind(R.id.add_car_img_layout)
    View mOcrImgLayout;         //ocr缩略图

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    public static final int PHOTO_OCR = 4;// 智能扫描
    private static final String PHOTO_FILE_NAME = "cttx_photo_head.jpg";
    private static final String PHOTO_CROP_FILE_NAME = "cttx_crop_photo_head.jpg";
    private static final String SHOWCASE_ID = "cttx_addcaractivity";
    public static boolean isFrom = false;


    /* 新接口传值*/
    BindCarDTO params = new BindCarDTO();
    /* 老接口传值*/
    CarInfoDTO dto = new CarInfoDTO();

    private File cttxFile;
    private String ocrImgPath = "";

    CarInfoDTO infoBean;
    CarInfoDTO tempBean;
    int pos = 1;

    private String carNum = "";
    private String carEngineNum = "";

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_add_car;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void initView() {
        mPlateNum.setTransformationMethod(new AllCapTransformationMethod());
        mPlateNum.setSelection(mPlateNum.getText().toString().length());

        UiHelpers.setTextViewIcon(this, getEnsureView(), R.mipmap.icon_add_car_camera,
                R.dimen.ds_60,
                R.dimen.ds_48, UiHelpers.DRAWABLE_RIGHT);

        setCarItemCanClickT(mCarBrand);
        setCarItemNoClick(mCarXi);
        setCarItemNoClick(mCarXing);
    }


    @Override
    public void initData() {

        infoBean = (CarInfoDTO) LoginData.getInstance().mHashMap.get("ConsummateInfo");
        if (infoBean != null) {
            tempBean = (CarInfoDTO) LoginData.getInstance().mHashMap.get("ConsummateInfo");
            mPlateNum.setText(infoBean.getCarnum().substring(1, infoBean.getCarnum().length()));
            mPlateNum.setFocusable(false);
            mProvinceSelBtn.setFocusable(false);
            mProvinceSelBtn.setEnabled(false);
            mEngineNum.setText(infoBean.getEnginenum());
            mProvince.setText(infoBean.getCarnum().substring(0, 1));

            mCommit.setText("保存");
            setTitleText("编辑车辆");
            mRemove.setVisibility(View.VISIBLE);
            try {
                pos = Integer.parseInt(infoBean.getCarnumtype());

                mType.setText(this.getResources().getStringArray(R.array.car_type)[pos - 1]);
            } catch (Exception e) {

            }
            mRegisterDate.setText(infoBean.getInspectdate());
            LoginData.getInstance().mHashMap.remove("ConsummateInfo");
        } else {
            setTitleText("添加车辆");
            mCommit.setText("保存并查询");
        }
    }

    @OnClick({R.id.add_car_commit, R.id.add_car_remove, R.id.add_car_province_layout, R.id.add_car_type_layout,
            R.id.add_car_date_layout, R.id.add_car_engine_instruction, R.id.add_car_date_instruction,
            R.id.add_car_item_brand, R.id.add_car_item_xi, R.id.add_car_item_xing, R.id.add_car_img_layout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_car_commit://提交车辆信息
                commitCarInfo();
                break;
            case R.id.add_car_remove://删除车辆信息
                DialogUtils.delDialog(this, "删除提示", "您确定要删除该车辆吗？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!MainRouter.isUserLogin()) {
                            delLocalCarInfo();
                        } else {
                            dialogForDelCar();
                        }
                    }
                });
                LoginData.getInstance().isDelCar = true;
                break;
            case R.id.add_car_province_layout://选择省
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                KeyWordPop mKeyWordPop = new KeyWordPop(AddCarActivity.this, v, new KeyWordPop.KeyWordLintener() {
                    @Override
                    public void onKeyWordLintener(String cityStr) {
                        mProvince.setText(cityStr);
                    }
                });
                break;
            case R.id.add_car_type_layout://选车型

                LoginData.getInstance().commonListType = 3;
                startActivityForResult(new Intent(this, CommonListActivity.class), JxGlobal.requestCode.add_car_aty);
                break;
            case R.id.add_car_date_layout://选日期
                String temp = mRegisterDate.getText().toString().trim();
                String[] temps = null;
                if ("" != temp) {
                    temps = temp.split("-");
                }
                MyChooseDialog dialog = new MyChooseDialog(AddCarActivity.this, temps, new MyChooseDialog.OnChooseDialogListener() {

                    @Override
                    public void back(String name) {
                        mRegisterDate.setText(name);

                    }
                });
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
            case R.id.add_car_engine_instruction://发动机说明书
                new DialogMgr(AddCarActivity.this, R.mipmap.engine_number_image);
                break;
            case R.id.add_car_date_instruction://日期说明
                new DialogMgr(AddCarActivity.this, 0);
                break;
            case R.id.add_car_item_brand://品牌
                startActivityForResult(new Intent(this, CarBrandActivity.class), CarBrandActivity.REQUEST_CODE);
                break;
            case R.id.add_car_item_xi://车系
                Intent intentL = new Intent(this, CarChooseActivity.class);
                intentL.putExtra("type", 1);
                intentL.putExtra("id", carBrandId);
                startActivityForResult(intentL, CarChooseActivity.REQUEST_L_CODE);
                break;
            case R.id.add_car_item_xing://车型
                Intent intentX = new Intent(this, CarChooseActivity.class);
                intentX.putExtra("type", 2);
                intentX.putExtra("id", carSeriesId);
                intentX.putExtra("idB", carBrandId);
                startActivityForResult(intentX, CarChooseActivity.REQUEST_X_CODE);
                break;
            case R.id.add_car_img_layout://ocr照片预览

                break;
        }
    }

    /* 获取请求参数*/
    private void getCarInfoDto() {

        /* 老接口传值*/
        dto.setCarnum(mProvince.getText().toString() + mPlateNum.getTransformationMethod().getTransformation(mPlateNum.getText(), mPlateNum).toString());
        dto.setUsrid(MainRouter.getUserID(false));

        dto.setCarnumtype(VehicleTypeTools.switchVehicleCode(mType.getText().toString()));

        dto.setEnginenum(mEngineNum.getText().toString());
        dto.setIspaycar("0");
        dto.setDefaultflag("0");
        dto.setInspectflag("0");
        dto.setViolationflag("0");
        dto.setCarmodel("");
        dto.setInspectdate("");
        if (infoBean != null) {
            dto.setIspaycar(infoBean.getIspaycar());
            dto.setCarmodel(infoBean.getCarmodel());
            dto.setInspectdate(infoBean.getInspectdate());
        }
    }


    /**
     * 保存车辆
     */
    private void commitCarInfo() {
        String plateNum = mPlateNum.getTransformationMethod().getTransformation(mPlateNum.getText(), mPlateNum).toString();
        String engineNum = mEngineNum.getText().toString();
        getCarInfoDto();

        if (plateNum.length() < 4) {
            ToastUtils.toastShort("请输入正确的车牌号");
            return;
        }
        if (Tools.isStrEmpty(engineNum) || engineNum.length() != 5) {
            ToastUtils.toastShort("请输入正确的发动机号");
            return;
        }

        params.setPlateNo(mProvince.getText().toString() + plateNum);
        carNum = mProvince.getText().toString() + plateNum;
        carEngineNum = engineNum;

        params.setFileNum("");
        params.setVehicleType(String.valueOf(pos + 1));

        LogUtils.i("老接口选中的位置" + (pos + 1));
        params.setAddress(mPosition.getText().toString());
        params.setUseCharacter(mUseProperty.getText().toString());
        params.setCarModel(mBrand.getText().toString());
        params.setVin(mCode.getText().toString());
        params.setEngineNo(mEngineNum.getText().toString());

        if (mRegisterDate.getText().toString().contains("请")) {
            params.setRegisterDate("");
            params.setIssueDate("");
        } else {
            params.setRegisterDate(mRegisterDate.getText().toString());
            params.setIssueDate(mRegisterDate.getText().toString());
        }

        if (SPUtils.getInstance().getXinnengyuanFlag() && plateNum.length() >= 7 && (!dto.getCarnumtype().equals("51") ||
                !dto.getCarnumtype().equals("52"))) {
            SPUtils.getInstance().setXinnengyuanFlag(false);
            DialogUtils.createDialog(this, "温馨提示", "如果您的汽车为新能源汽车,车牌类型请选择新能源汽车", "取消", "继续查询", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            //未登录时直接提交到新服务器,登录时先提交旧服务器,再提交新服务器
            if (!MainRouter.isUserLogin()) {
                List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
                if (isFrom) {//修改
                    SPUtils.getInstance().getCarsInfo().remove(tempBean);
                    SPUtils.getInstance().getCarsInfo().add(dto);
                } else {
                    if (list.size() > 2) {
                        list.remove(0);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getCarnum().equals(dto.getCarnum())) {
                            ToastUtils.toastShort("该车辆已存在，请不要重复添加");
                            return;
                        }
                    }
                    mCommit.setClickable(false);
                    showDialogLoading();

                    LoginData.getInstance().mCarNum++;
                    LoginData.getInstance().mHashMap.put("carnum", dto.getCarnum());
                    LoginData.getInstance().mHashMap.put("enginenum", dto.getEnginenum());
                    LoginData.getInstance().mHashMap.put("carnumtype", dto.getCarnumtype());
                    LoginData.getInstance().mHashMap.put("IllegalViolationName", dto.getCarnum());//标题
                    SPUtils.getInstance().getCarsInfo().add(dto);

                    ViolationDTO dto1 = new ViolationDTO();

                    dto1.setCarnum(RSAUtils.strByEncryption(dto.getCarnum(), true));
                    dto1.setEnginenum(RSAUtils.strByEncryption(dto.getEnginenum(), true));
                    dto1.setCarnumtype(dto.getCarnumtype());

                    Intent intent = new Intent(this, ViolationListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("params", dto1);
                    intent.putExtras(bundle);
                    intent.putExtra("plateNum", dto.getCarnum());
                    startActivity(intent);
                    finish();
                }
                mCommit.setClickable(true);
                hideDialogLoading();

                hideKeyBord();
            } else {
                LogUtils.i("老服务器");
                params.setUsrnum(MainRouter.getUserID(false));
                if (isFrom) {
                    editOldCarInfo();
                    showDialogLoading();
                    mCommit.setClickable(false);
                } else {
                    List<UserCarInfoBean> list1 = LoginData.getInstance().mServerCars;
                    if (list1.size() > 0) {
                        for (int i = 0; i < list1.size(); i++) {
                            if (list1.get(i).getCarnum().equals(dto.getCarnum())) {
                                ToastUtils.toastShort("该车辆已存在，请不要重复添加");
                                return;
                            }
                        }
                    }
                    showDialogLoading();
                    mCommit.setClickable(true);
                    commitCarInfoToOldServer();
                }
            }
        }
    }


    //提交老服务器
    private void commitCarInfoToOldServer() {
        UserApiClient.addCarInfo(this, dto, new CallBack<BankResponse>() {
            @Override
            public void onSuccess(BankResponse bankResponse) {
                mCommit.setClickable(true);
                hideDialogLoading();
                if (bankResponse.getSYS_HEAD().getReturnCode().equals("000000")) {


                    commitCarInfoToNewServer();

                    LoginData.getInstance().mHashMap.put("carnum", carNum);
                    LoginData.getInstance().mHashMap.put("enginenum", carEngineNum);
                    LoginData.getInstance().mHashMap.put("carnumtype", dto.getCarnumtype());
                    LoginData.getInstance().mHashMap.put("IllegalViolationName", carNum);//标题

                    LoginData.getInstance().mCarNum++;//车辆数+1

                    dto.setCarnum(carNum);
                    dto.setEnginenum(carEngineNum);

                    EventBus.getDefault().post(new AddCarInfoEvent(true, dto));
                    hideKeyBord();

                    ViolationDTO dto1 = new ViolationDTO();
                    dto1.setCarnum(RSAUtils.strByEncryption(carNum, true));
                    dto1.setEnginenum(RSAUtils.strByEncryption(dto.getEnginenum(), true));
                    dto1.setCarnumtype(dto.getCarnumtype());

                    Intent intent = new Intent(AddCarActivity.this, ViolationListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("params", dto1);
                    intent.putExtras(bundle);
                    intent.putExtra("plateNum", carNum);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                hideDialogLoading();
                mCommit.setClickable(true);
                super.onError(errorCode, msg);
            }
        });
    }

    //提交新服务器
    private void commitCarInfoToNewServer() {
        LogUtils.i(params.getEngineNo() + "-----" + params.getVehicleType());
        CarApiClient.commitCar(this, params, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {

            }
        });
    }


    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private int carBrandId;  //车品牌id
    private int carSeriesId; //车系id

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {

                // 得到图片的全路径
                Uri uri = data.getData();
                if (null == uri) {
                    return;
                }
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor.getString(actual_image_column_index);
                cttxFile = new File(img_path);
                try {
                    if (!cttxFile.exists()) {
                        throw new FileNotFoundException(img_path);
                    }
                    toByteArray(cttxFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                if (0 == resultCode) {
                    return;
                }
                cttxFile = new File(Environment.getExternalStorageDirectory() + "/CTTX/",
                        PHOTO_FILE_NAME);
            } else {
                Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                File imagFile = new File(Environment.getExternalStorageDirectory() + "/CTTX/",
                        PHOTO_CROP_FILE_NAME);
                toByteArray(imagFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == JxGlobal.requestCode.add_car_aty
                && resultCode == JxGlobal.resultCode.common_list_fty) {
            if (data != null) {
                switch (LoginData.getInstance().commonListType) {
                    case 3://车辆类型
                        mType.setText(data.getStringExtra(JxGlobal.putExtra.common_list_extra));
                        String[] strs = this.getResources().getStringArray(R.array.car_type);
                        for (int i = 0; i < strs.length; i++) {
                            if (strs[i].equals(data.getStringExtra(JxGlobal.putExtra.common_list_extra))) {
                                pos = i;
                            }
                        }
                        break;
                }

            }
        }
        if (requestCode == CarBrandActivity.REQUEST_CODE
                && resultCode == CarBrandActivity.RESULT_CODE
                && data != null) {
            CarBrandBean carBrandBean = (CarBrandBean) data.getSerializableExtra(CarBrandActivity.CAR_BRAND_BEAN);
            mCarBrand.setRightText(carBrandBean.getBrandName());
            carBrandId = carBrandBean.getId();
            params.setBrandId(String.valueOf(carBrandId));
            setCarItemCanClickT(mCarXi);
            setCarItemNoClick(mCarXing);
            setCarItemCanClick(mCarBrand);
        }

        if (requestCode == CarChooseActivity.REQUEST_L_CODE
                && resultCode == CarChooseActivity.RESULT_L_CODE
                && data != null) {
            CarXiBean carBrandBean = (CarXiBean) data.getSerializableExtra(CarChooseActivity.CAR_LINE_BEAN);
            mCarXi.setRightText(carBrandBean.getSeriesName());
            carSeriesId = carBrandBean.getSeriesId();
            params.setSeriesId(String.valueOf(carSeriesId));
            setCarItemCanClick(mCarXi);
            setCarItemCanClickT(mCarXing);
        }

        if (requestCode == CarChooseActivity.REQUEST_X_CODE
                && resultCode == CarChooseActivity.RESULT_X_CODE
                && data != null) {
            CarStyleInfoBean carBrandBean = (CarStyleInfoBean) data.getSerializableExtra(CarChooseActivity.CAR_XING_BEAN);
            params.setCarModelId(String.valueOf(carBrandBean.getCarModelId()));
            mCarXing.setRightText(carBrandBean.getCarModelName());
            setCarItemCanClick(mCarXing);
        }

        if (requestCode == 1201 && resultCode == 1202) {
            if (data != null) {
                getDrivingCardInfo();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setCarItemNoClick(SettingItemView settingItemView) {
        settingItemView.setClickable(false);
        settingItemView.setRightText("");
        settingItemView.setLeftTextColor(getResources().getColor(R.color.lowhintColor));
        settingItemView.setRightTextColor(getResources().getColor(R.color.colorQueryNumberFont));
    }

    private void setCarItemCanClickT(SettingItemView settingItemView) {
        settingItemView.setClickable(true);
        settingItemView.setRightText("");
        settingItemView.setLeftTextColor(getResources().getColor(R.color.colorAddCarGrayNumberFont));
        settingItemView.setRightTextColor(getResources().getColor(R.color.colorAddCarGrayNumberFont));
    }

    private void setCarItemCanClick(SettingItemView settingItemView) {
        settingItemView.setClickable(true);
        settingItemView.setLeftTextColor(getResources().getColor(R.color.colorAddCarGrayNumberFont));
        settingItemView.setRightTextColor(getResources().getColor(R.color.gray_25));
    }

    byte[] buffer;


    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        Intent intentOcr = new Intent(this, OcrCameraActivity.class);
        intentOcr.putExtra("ocr_resource", 0);
        startActivityForResult(intentOcr, 1201);
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        ToastUtils.toastShort("您已关闭摄像头权限");
    }

    private void toByteArray(File file) throws IOException {
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
        }
        FileInputStream fi = new FileInputStream(file);
        buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideDialogLoading();
    }

    /**
     * 设置值在view上
     */
    private void setValueToView(XingShiZhengBean vehicleInfo) {
        if (vehicleInfo.getCardNo() != null) {
            String province = vehicleInfo.getCardNo();
            mProvince.setText(province.substring(0, 1));
            mPlateNum.setText(province.substring(1, province.length()));
        }
        if (vehicleInfo.getEnginePN() != null) {//取后五位
            try {
                mEngineNum.setText(vehicleInfo.getEnginePN().substring(vehicleInfo.getEnginePN().length() - 5));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (vehicleInfo.getVehicleType() != null) {
            mType.setText(vehicleInfo.getVehicleType());
        }
        if (vehicleInfo.getName() != null) {
            mPersonName.setText(vehicleInfo.getName());
        }
        if (vehicleInfo.getAddr() != null) {
            mPosition.setText(vehicleInfo.getAddr());
        }
        if (vehicleInfo.getUseCharacte() != null) {
            mUseProperty.setText(vehicleInfo.getUseCharacte());
        }
        if (!TextUtils.isEmpty(vehicleInfo.getModel())) {
            mBrand.setText(matchingBrand(vehicleInfo.getModel()));
        }
        if (vehicleInfo.getVin() != null) {
            mCode.setText(vehicleInfo.getVin());
        }
        if (vehicleInfo.getRegisterDate() != null) {
            String regDate = vehicleInfo.getRegisterDate();
            String year = regDate.substring(0, 4);
            String month = regDate.substring(4, 6);
            String day = regDate.substring(6, 8);
            mRegisterDate.setText(year + "-" + month + "-" + day);
        }
    }

    private String matchingBrand(String brandName) {
        String str = "";
        List<CarBrandBean> sourceDateList = new ArrayList<CarBrandBean>();
        try {
            sourceDateList = CarBrandParser.parse(getAssets().open("car_brand.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (CarBrandBean carBrandBean : sourceDateList) {
            if (carBrandBean.getBrandName().contains(brandName)) {
                str = carBrandBean.getBrandName();
                break;
            }
        }
        return str;
    }


    @Override
    protected void baseGoEnsure() {
        super.baseGoEnsure();
        PermissionGen.needPermission(this, 100,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                });
    }

    //删除本地车辆
    private void delLocalCarInfo() {
        List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
        list.remove(infoBean);
        LoginData.getInstance().mCarNum--;
        hideKeyBord();
    }

    private void dialogForDelCar() {
        carNum = mProvince.getText().toString() + mPlateNum.getText().toString();
        String signCar = SPUtils.getInstance().getSignCarPlate();
        if ("1".equals(infoBean.getIspaycar())) {
            DialogUtils.createDialog(this, "该车辆为可缴费车辆不能进行删除操作", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    return;
                }
            });
        } else {
            if (carNum.equals(signCar)) {
                DialogUtils.delDialog(this, "温馨提醒", "该车辆为活动车辆，请勿删除!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delOldCarInfo();
                    }
                });
            } else {
                delOldCarInfo();
            }
        }
    }

    //删除老服务器车辆
    private void delOldCarInfo() {
        getCarInfoDto();
        String plateNum = mPlateNum.getText().toString();
        String engineNum = mEngineNum.getText().toString();
        if (TextUtils.isEmpty(plateNum)) {
            ToastUtils.toastShort("请输入正确的车牌号");
            return;
        }
        if (Tools.isStrEmpty(engineNum)) {
            ToastUtils.toastShort("请输入发动机号");
            return;
        }
        dto.setOpertype("3");
        editAndDelCarInfo();
    }

    //修改老服务器车辆
    private void editOldCarInfo() {
        getCarInfoDto();
        String plateNum = mPlateNum.getText().toString();
        String engineNum = mEngineNum.getText().toString();
        if (TextUtils.isEmpty(plateNum)) {
            ToastUtils.toastShort("请输入正确的车牌号");
            return;
        }
        if (Tools.isStrEmpty(engineNum)) {
            ToastUtils.toastShort("请输入发动机号");
            return;
        }
        dto.setOpertype("2");
        editAndDelCarInfo();
    }

    //修改、删除服务器车辆请求
    private void editAndDelCarInfo() {
        LogUtils.i("getOpertype:" + dto.getOpertype());

        UserApiClient.editCarInfo(this, dto, new CallBack<BankResponse>() {
            @Override
            public void onSuccess(BankResponse bankResponse) {
                mCommit.setClickable(true);
                hideDialogLoading();
                if (bankResponse.getSYS_HEAD().getReturnCode().equals("000000")) {
                    dto.setCarnum(carNum);
                    dto.setEnginenum(carEngineNum);
                    if (dto.getOpertype().equals("2")) {

                        EventBus.getDefault().post(new EditCarInfoEvent(true, tempBean, dto));
                    } else {
                        EventBus.getDefault().post(
                                new EditCarInfoEvent(true, dto, null));
                    }
                    if (dto.getOpertype().equals("3")) {
                        liYingCarDel(dto.getCarnumtype());
                    }
                }
                hideKeyBord();
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                mCommit.setClickable(true);
                hideDialogLoading();
            }
        });

    }

    private void liYingCarDel(String carType) {
        carNum = mProvince.getText().toString() + mPlateNum.getText().toString();
        LiYingCarManageDTO liYingCarManageDTO = new LiYingCarManageDTO();
        liYingCarManageDTO.setPlateNo(carNum);
        liYingCarManageDTO.setVehicleType(carType);
        liYingCarManageDTO.setCreOrdel("2");
        CarApiClient.liYingCarManage(this, liYingCarManageDTO, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
                LoginData.getInstance().mCarNum--;
            }
        });
    }

    /**
     * 添加 、删除 、修改成功后自动隐藏键盘
     */
    private void hideKeyBord() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && this.getCurrentFocus() != null) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        finish();
    }

    //    @Subscribe(threadMode = ThreadMode.MAIN)
    //    public void onDataSynEvent(OcrDrivingEvent event) {
    //        if (event != null && event.getStatus()){
    //            event.setStatus(false);
    //            showDialogLoading();
    //            CarApiClient.uploadDrivingImg(this, event.getFile(), new CallBack<DrivingOcrResult>() {
    //                @Override
    //                public void onSuccess(DrivingOcrResult result) {
    //                    hideDialogLoading();
    //                    if ("OK".equals(result.getStatus())) {
    //                        String cardNo = result.getContent().getCardNo();
    //                        String province = cardNo.substring(0,1);
    //                        String plateNum = cardNo.substring(1,cardNo.length());
    //                        String provinces = "沪浙苏皖京藏川鄂甘赣贵桂黑吉冀津晋辽鲁蒙闽宁青琼陕湘新渝豫粤云";
    //                        if (provinces.contains(province)){
    //                            mProvince.setText(province);
    //                        }
    //                        mPlateNum.setText(plateNum);
    //                        String engineNum = result.getContent().getEnginePN();
    //                        engineNum = engineNum.substring(engineNum.length() - 5,engineNum.length());
    //                        mEngineNum.setText(engineNum);
    //                        mType.setText(result.getContent().getVehicleType());
    //                        dto.setCarnumtype(VehicleTypeTools.switchVehicleCode(mType.getText().toString()));
    //                    } else {
    //                        ToastUtils.showShort(AddCarActivity.this, "解析失败，请重试");
    //                    }
    //                }
    //
    //                @Override
    //                public void onError(String errorCode, String msg) {
    //                    super.onError(errorCode, msg);
    //                    hideDialogLoading();
    //                }
    //            });
    //        }
    //    }

    /**
     * ocr获取行驶证信息
     */
    private void getDrivingCardInfo() {
        showDialogLoading();
        if (OcrCameraActivity.file == null) {
            hideDialogLoading();
            ToastUtils.toastShort("照片获取失败");
            return;
        }
        CarApiClient.uploadDrivingImg(this, OcrCameraActivity.file, new CallBack<DrivingOcrResult>() {
            @Override
            public void onSuccess(DrivingOcrResult result) {
                hideDialogLoading();
                if ("OK".equals(result.getStatus())) {
                    DrivingOcrBean bean = result.getContent();
                    if (bean != null) {

                        if (bean.getCardNo() != null) {
                            String cardNo = bean.getCardNo();
                            String province = cardNo.substring(0, 1);
                            String plateNum = cardNo.substring(1, cardNo.length());
                            String provinces = "沪浙苏皖京藏川鄂甘赣贵桂黑吉冀津晋辽鲁蒙闽宁青琼陕湘新渝豫粤云";
                            if (provinces.contains(province)) {
                                mProvince.setText(province);
                            }
                            mPlateNum.setText(plateNum);
                        }
                        if (bean.getEnginePN() != null) {
                            String engineNum = bean.getEnginePN();
                            engineNum = engineNum.substring(engineNum.length() - 5, engineNum.length());
                            mEngineNum.setText(engineNum);
                        }
                        if (bean.getVehicleType() != null) {
                            mType.setText(bean.getVehicleType());
                        }
                        dto.setCarnumtype(VehicleTypeTools.switchVehicleCode(mType.getText().toString()));
                    }
                } else {
                    ToastUtils.toastShort("解析失败，请重试");
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
