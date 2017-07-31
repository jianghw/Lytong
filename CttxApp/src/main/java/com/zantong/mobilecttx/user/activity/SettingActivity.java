package com.zantong.mobilecttx.user.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.FileUploadApi;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.BenDiCarInfoEvent;
import com.zantong.mobilecttx.contract.ILoginView;
import com.zantong.mobilecttx.presenter.LogoutPresenter;
import com.zantong.mobilecttx.user.dto.PersonInfoDTO;
import com.zantong.mobilecttx.user.dto.UpdateUserHeadImgDTO;
import com.zantong.mobilecttx.utils.DateService;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.InspectService;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.popwindow.IOSpopwindow;
import com.zantong.mobilecttx.widght.UISwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.CleanUtils;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static cn.qqtheme.framework.util.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 设置页面
 */
public class SettingActivity extends BaseMvpActivity<ILoginView, LogoutPresenter>
        implements View.OnClickListener, ILoginView {

    @Bind(R.id.activity_help)
    View mHelp;
    @Bind(R.id.activity_about)
    View mAbout;
    @Bind(R.id.activity_logout)
    View mLogout;
    @Bind(R.id.setting_date_text)
    TextView mSelDate;
    @Bind(R.id.setting_breakrules_notice)
    UISwitchButton mBreakRulesNotice;
    @Bind(R.id.setting_score_notice)
    UISwitchButton mScoreNotice;

    @Bind(R.id.user_info_phone_text)
    TextView user_info_phone_text;
    @Bind(R.id.user_info_name_text)
    TextView user_info_name_text;
    @Bind(R.id.user_info_name_rl)
    RelativeLayout userInfoNameRl;
    @Bind(R.id.user_info_change_pwd)
    RelativeLayout userChangePwdRl;
    @Bind(R.id.user_info_head_rl)
    RelativeLayout userInfoRl;
    @Bind(R.id.content_all)
    LinearLayout content_all;

    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final String PHOTO_CROP_FILE_NAME = "cttx_crop_photo_head.jpg";

    DatePicker picker;

    private static final int REQ_TAKE_PHOTO = 100;// 拍照
    private static final int REQ_ALBUM_1 = 101;
    private static final int REQ_ALBUM_2 = 102;
    private static final int REQ_ZOOM = 103;

    @Override
    protected int getContentResId() {
        presenter.attach(this);
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        picker = new DatePicker(SettingActivity.this);
        setTitleText("设置");

        if (Tools.isStrEmpty(PublicData.getInstance().userID)) {
            mLogout.setVisibility(View.GONE);
        } else {
            String date = PublicData.getInstance().mLoginInfoBean.getGetdate();
            try {
                if (date.contains("-")) {
                    mSelDate.setText(date);
                } else {
                    mSelDate.setText(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getNickname())) {
            user_info_name_text.setText(PublicData.getInstance().mLoginInfoBean.getNickname());
        } else {
            user_info_name_text.setText(PublicData.getInstance().mLoginInfoBean.getPhoenum().substring(7));
        }
        String phone = StringUtils.getEncrypPhone(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        user_info_phone_text.setText(phone);
    }

    @Override
    public void initData() {
        mHelp.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mSelDate.setOnClickListener(this);
        userInfoNameRl.setOnClickListener(this);
        userChangePwdRl.setOnClickListener(this);
        userInfoRl.setOnClickListener(this);

        if (!PublicData.getInstance().loginFlag) {
            SPUtils.getInstance().setWeizhangPush(false);
            SPUtils.getInstance().setJifenPush(false);
        }
        mBreakRulesNotice.setChecked(SPUtils.getInstance().getWeizhangPush());
        mScoreNotice.setChecked(SPUtils.getInstance().getJifenPush());

        mBreakRulesNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (PublicData.getInstance().loginFlag) {
                    SPUtils.getInstance().setWeizhangPush(isChecked);
                    mBreakRulesNotice.setChecked(isChecked);
                    if (!isChecked) {
                        ToastUtils.showShort(getApplicationContext(), "违章主动通知已关闭");
                    }
                } else {
                    mBreakRulesNotice.setChecked(false);
                    Intent intent2 = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent2);
                }
            }
        });
        mScoreNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Intent intent = new Intent(SettingActivity.this, DateService.class);
                if (PublicData.getInstance().loginFlag && !"".equals(PublicData.getInstance().userID)) {
                    SPUtils.getInstance().setJifenPush(isChecked);
                    PublicData.getInstance().updateMsg = isChecked;
                    if (isChecked) {
                        UserInfoRememberCtrl.saveObject(PublicData.getInstance().NOTICE_STATE, true);//已开启
                        startService(intent);
                    } else {
                        UserInfoRememberCtrl.saveObject(PublicData.getInstance().NOTICE_STATE, false);//已关闭
                        stopService(intent);
                        ToastUtils.showShort(getApplicationContext(), "记分周期提醒已关闭");
                    }
                } else {
                    Intent intent2 = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    mScoreNotice.setChecked(false);
                }
            }
        });
    }

    @Override
    public LogoutPresenter initPresenter() {
        return new LogoutPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_help:
                Act.getInstance().gotoIntentLogin(this, FeedbackActivity.class);
                break;
            case R.id.activity_about:
                Act.getInstance().gotoIntent(this, AboutActivity.class);
                break;
            case R.id.setting_date_text://选择领证日期
                if (PublicData.getInstance().loginFlag && !"".equals(PublicData.getInstance().userID)) {
                    showLicenseDateDialog();
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.activity_logout:
                logout();
                break;
            case R.id.setting_breakrules_notice: //违章主动通知
                break;
            case R.id.user_info_change_pwd:
                Act.getInstance().lauchIntent(this, ChangePwdActivity.class);
                break;
            case R.id.user_info_head_rl:
                MobclickAgent.onEvent(this, Config.getUMengID(23));
                chooseHeadImge();
                break;
            case R.id.user_info_name_rl:
                Act.getInstance().lauchIntent(this, UpdateNickName.class);
                break;
            default:
                break;
        }
    }

    private void showLicenseDateDialog() {
        DialogUtils.createDialog(this, getResources().getString(R.string.dialog_select_date_hint), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = mSelDate.getText().toString().trim();
                if (!TextUtils.isEmpty(temp) && temp.contains("-")) {
                    String[] temps = temp.split("-");
                    for (String tempStr : temps) {
                        try {
                            if (Integer.parseInt(tempStr) < 10) {
                                tempStr = "0" + tempStr;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                picker.setRangeStart(DateUtils.getYear() - 100, DateUtils.getMonth(), DateUtils.getDay());
                picker.setRangeEnd(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
                try {
                    String date = PublicData.getInstance().mLoginInfoBean.getGetdate();
                    if (!"".equals(date)) {
                        date = date.replace("-", "");
                        picker.setSelectedItem(Integer.valueOf(date.substring(0, 4)), Integer.valueOf(date.substring(4, 6)), Integer.valueOf(date.substring(6, 8)));
                    } else {
                        picker.setSelectedItem(DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        commitGetCardDate(year + "-" + month + "-" + day);
                    }
                });
                picker.show();
            }
        });
    }

    /**
     * 选择图片
     */
    private void chooseHeadImge() {
        new IOSpopwindow(this, content_all, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });
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
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请拍照运行时权限
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        openCamera();
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
    }

    /**
     * 拍照
     */
    private void openCamera() {
        // 指定调用相机拍照后照片的储存路径
        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.CAMERA_DIR);
        File mCameraFile = new File(ImgPath);
        Uri cameraUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //如果是7.0或以上
            cameraUri = getUriForFileByN(mCameraFile);

            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            cameraUri = Uri.fromFile(mCameraFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);

        startActivityForResult(intent, REQ_TAKE_PHOTO);
    }

    /**
     * N 7.0系统封装
     *
     * @param mCameraFile
     * @return
     */
    private Uri getUriForFileByN(File mCameraFile) {
        try {
            return FileProvider.getUriForFile(getApplicationContext(),
                    getApplication().getPackageName() + ".fileprovider", mCameraFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.fromFile(mCameraFile);
        }
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.GALLERY_DIR);
        File mGalleryFile = new File(ImgPath);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = getUriForFileByN(mGalleryFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(intent, REQ_ALBUM_1);
        } else {
            startActivityForResult(intent, REQ_ALBUM_2);
        }
    }

    /**
     * 照相回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK://调用图片选择处理成功
                switch (requestCode) {
                    case REQ_TAKE_PHOTO:// 拍照后在这里回调
                        reqTakePhoto();
                        break;
                    case REQ_ALBUM_1:// 选择相册中的图片>7.0
                        if (data != null) {
                            startPhotoZoomByN(data);
                        }
                        break;
                    case REQ_ALBUM_2:// 选择相册中的图片
                        if (data != null) {
                            Uri uri = data.getData();
                            FileUtils.startPhotoZoom(uri, this, REQ_ZOOM);
                        }
                        break;
                    case REQ_ZOOM://裁剪后回调
                        if (data != null) {
                            reqZoom();
                        } else {
                            ToastUtils.showShort(getApplicationContext(), "选择图片发生错误，图片可能已经移位或删除");
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 拍照回调
     */
    private void reqTakePhoto() {
        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.CAMERA_DIR);
        File mCameraFile = new File(ImgPath);
        Uri inputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            inputUri = getUriForFileByN(mCameraFile);
        } else {
            inputUri = Uri.fromFile(mCameraFile);
        }

        FileUtils.startPhotoZoom(inputUri, this, REQ_ZOOM);
    }

    /**
     * 选择相册中的图片>7.0
     *
     * @param data
     */
    private void startPhotoZoomByN(Intent data) {

        String url = FileUtils.getPath(getApplicationContext(), data.getData());
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showShort(getApplicationContext(), "选择图片发生错误，图片可能已经移位或删除");
            return;
        }
        File imgUri = new File(url);
        Uri dataUri = getUriForFileByN(imgUri);

        if (dataUri == null) {
            ToastUtils.showShort(getApplicationContext(), "选择图片发生错误，图片可能已经移位或删除");
        } else {
            FileUtils.startPhotoZoom(dataUri, this, REQ_ZOOM);
        }
    }

    /**
     * 剪切回调
     */
    private void reqZoom() {
        //如果是拍照的
        String ImgPath = FileUtils.photoImagePath(getApplicationContext(), FileUtils.CROP_DIR);
        File mCropFile = new File(ImgPath);
        if (!mCropFile.exists()) {
            ToastUtils.showShort(getApplicationContext(), "头像图片可能未生成或删除");
            return;
        }

        Map<String, RequestBody> params = new HashMap<>();
        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), mCropFile);
        String imagFileName = "";
        String[] imageUrls = PublicData.getInstance().mLoginInfoBean.getPortrait().split("\\/");

        if (Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getPortrait())) {
            imagFileName = PublicData.getInstance().userID + ".jpg";
        } else {
            imagFileName = imageUrls[imageUrls.length - 1];
        }

        params.put("image\"; filename=\"" + imagFileName + "", body);

        Retrofit2Utils retrofit2Utils = new Retrofit2Utils();
        FileUploadApi api = retrofit2Utils
                .getRetrofitHttps(BuildConfig.ImageLoadUrl)
                .create(FileUploadApi.class);

        api.uploadImage(params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.isSuccessful()) {
                    ResponseBody mBody = response.body();
                    if (mBody != null) {
                        String url = null;
                        try {
                            JSONObject json = new JSONObject(mBody.string());
                            url = json.get("url").toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateUserImg(url);
                    }
                } else if (response != null) {
                    ToastUtils.showShort(getApplicationContext(), response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.showShort(getApplicationContext(), Config.getErrMsg("1"));
            }
        });
    }

    /**
     * 更新用户头像信息
     *
     * @param strUrl
     */
    private void updateUserImg(final String strUrl) {
        showDialogLoading();
        getBaseBack().setEnabled(false);
        UpdateUserHeadImgDTO updateUserHeadImgDTO = new UpdateUserHeadImgDTO();
        updateUserHeadImgDTO.setPortrait(strUrl);
        updateUserHeadImgDTO.setUsrid(PublicData.getInstance().userID);
        updateUserHeadImgDTO.setDevicetoken(PublicData.getInstance().imei);
        updateUserHeadImgDTO.setPushswitch("0");
        UserApiClient.updateUserHeadImg(this, updateUserHeadImgDTO, new CallBack<Result>() {
            @Override
            public void onSuccess(Result result) {
                hideDialogLoading();
                ImageLoader.getInstance().clearMemoryCache();
                getBaseBack().setEnabled(true);
                if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
                    PublicData.getInstance().mLoginInfoBean.setPortrait(strUrl);
                    ToastUtils.showShort(SettingActivity.this, "修改头像成功");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                getBaseBack().setEnabled(true);
                hideDialogLoading();
            }
        });
    }

    @Override
    public String getPhoenum() {
        return "13521396353";
    }

    @Override
    public String getCaptcha() {
        return "123123";
    }

    /**
     * 提交初次领证日期
     *
     * @param date
     */
    private void commitGetCardDate(final String date) {
        if (PublicData.getInstance().mLoginInfoBean.getGetdate().equals(date)) {
            return;
        }
        PersonInfoDTO dto = new PersonInfoDTO();
        dto.setGetdate(date.replace("-", ""));
        showDialogLoading();
        UserApiClient.commitPersonInfo(SettingActivity.this, dto, new CallBack<Result>() {
            @Override
            public void onSuccess(Result result) {
                mSelDate.setText(date);
                hideDialogLoading();
                if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                    picker.dismiss();
                    PublicData.getInstance().mLoginInfoBean.setGetdate(date);
                    UserInfoRememberCtrl.saveObject(PublicData.getInstance().mLoginInfoBean);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
            }
        });
    }

    /**
     * 退出
     */
    private void logout() {
        showDialogLoading();

        UserApiClient.logout(this, new CallBack<Result>() {
            @Override
            public void onSuccess(Result result) {
                hideDialogLoading();

                if ("000000".equals(result.getSYS_HEAD().getReturnCode())) {

                    PublicData.getInstance().clearData(ContextUtils.getContext());
                    SPUtils.getInstance().clear();
                    CleanUtils.cleanCustomCache(FileUtils.photoImageDirectory(getApplicationContext()));

                    mLogout.setVisibility(View.GONE);

                    Intent intent = new Intent(SettingActivity.this, InspectService.class);
                    stopService(intent);
                    EventBus.getDefault().post(new BenDiCarInfoEvent(true, null));

                    finish();
                } else {
                    ToastUtils.toastShort("退出失败");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                hideDialogLoading();
                ToastUtils.toastShort(msg + "退出失败");
            }
        });
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1001);
        intent.putExtra("aspectY", 1000);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("scale", true);
        // 图片格式
        Uri mImageCaptureUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {//如果是7.0android系统
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, new File(Environment
                    .getExternalStorageDirectory() + "/CTTXHEAD/", PHOTO_CROP_FILE_NAME).getAbsolutePath());
            mImageCaptureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            mImageCaptureUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory() + "/CTTXHEAD/", PHOTO_CROP_FILE_NAME));
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);//人脸识别
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

}
