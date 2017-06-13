package com.zantong.mobilecttx.user.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.user.dto.PersonInfoDTO;
import com.zantong.mobilecttx.user.dto.UpdateUserHeadImgDTO;
import com.zantong.mobilecttx.eventbus.BenDiCarInfoEvent;
import com.zantong.mobilecttx.api.FileUploadApi;
import com.zantong.mobilecttx.presenter.LogoutPresenter;
import com.zantong.mobilecttx.utils.DateService;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.InspectService;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.popwindow.IOSpopwindow;
import com.zantong.mobilecttx.interf.ILoginView;
import com.zantong.mobilecttx.widght.UISwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cn.qqtheme.framework.picker.DatePicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends BaseMvpActivity<ILoginView, LogoutPresenter> implements View.OnClickListener, ILoginView {

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
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final String PHOTO_FILE_NAME = "cttx_photo_head.jpg";
    private static final String PHOTO_CROP_FILE_NAME = "cttx_crop_photo_head.jpg";
    private File cttxFile;
    DatePicker picker;

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
            SPUtils.getInstance(SettingActivity.this).setWeizhangPush(false);
            SPUtils.getInstance(SettingActivity.this).setJifenPush(false);
        }
        mBreakRulesNotice.setChecked(SPUtils.getInstance(SettingActivity.this).getWeizhangPush());
        mScoreNotice.setChecked(SPUtils.getInstance(SettingActivity.this).getJifenPush());

        mBreakRulesNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (PublicData.getInstance().loginFlag) {
                    SPUtils.getInstance(SettingActivity.this).setWeizhangPush(isChecked);
                    mBreakRulesNotice.setChecked(isChecked);
                    if (!isChecked) {
                        ToastUtils.showShort(SettingActivity.this, "违章主动通知已关闭");
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
                    SPUtils.getInstance(SettingActivity.this).setJifenPush(isChecked);
                    PublicData.getInstance().updateMsg = isChecked;
                    if (isChecked) {
                        UserInfoRememberCtrl.saveObject(SettingActivity.this, PublicData.getInstance().NOTICE_STATE, true);//已开启
                        startService(intent);
                    } else {
                        UserInfoRememberCtrl.saveObject(SettingActivity.this, PublicData.getInstance().NOTICE_STATE, false);//已关闭
                        stopService(intent);
                        ToastUtils.showShort(SettingActivity.this, "记分周期提醒已关闭");
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
    protected int getContentResId() {
        presenter.attach(this);
        return R.layout.activity_setting;
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
                Act.getInstance().lauchIntentToLogin(this, FeedbackActivity.class);
                break;
            case R.id.activity_about:
                Act.getInstance().gotoIntent(this, AboutActivity.class);
                break;
            case R.id.setting_date_text://选择领证日期
                if (PublicData.getInstance().loginFlag && !"".equals(PublicData.getInstance().userID)) {
                    DialogUtils.createDialog(this, getResources().getString(R.string.dialog_select_date_hint), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            openMyChooseDialog();

                            String temp = mSelDate.getText().toString().trim();
                            String[] temps = null;
                            if ("" != temp && temp.contains("-")) {
                                temps = temp.split("-");
                                for (String tempStr : temps) {
                                    try {
                                        if (Integer.parseInt(tempStr) < 10) {
                                            tempStr = "0" + tempStr;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                LogUtils.i("temps---" + temps.toString());
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
                chooseHeadImg();
                break;
            case R.id.user_info_name_rl:
                Act.getInstance().lauchIntent(this, UpdateNickName.class);
                break;
        }
    }

    /**
     * 选择图片
     */
    private void chooseHeadImg() {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "CTTXHEAD");
        else
            cacheDir = this.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        IOSpopwindow menuWindow = new IOSpopwindow(this, content_all, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhone();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhone();
            }
        });
    }

    public void takePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        } else {
            camera();
        }
    }

    public void choosePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1002);
        } else {
            gallery();
        }
    }

    /**
     * 从相机获取
     */
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            Toast.makeText(this, "未找到存储卡，无法存储照片！1", Toast.LENGTH_SHORT).show();
            Uri mImageCaptureUri;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {//如果是7.0android系统
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, new File(
                        Environment.getExternalStorageDirectory() + "/CTTXHEAD/", PHOTO_FILE_NAME).getAbsolutePath());
                mImageCaptureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                mImageCaptureUri = Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory() + "/CTTXHEAD/", PHOTO_FILE_NAME));
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    /**
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                String img_path = getPath(this, uri);
                cttxFile = new File(img_path);
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                if (0 == resultCode) {
                    return;
                }
                cttxFile = new File(Environment.getExternalStorageDirectory() + "/CTTXHEAD/",
                        PHOTO_FILE_NAME);
                Uri mImageCaptureUri;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {//如果是7.0android系统
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, cttxFile.getAbsolutePath());
                    mImageCaptureUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                } else {
                    mImageCaptureUri = Uri.fromFile(cttxFile);
                }
                crop(mImageCaptureUri);
            } else {
                Toast.makeText(SettingActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT && resultCode == this.RESULT_OK) {
            try {
                Map<String, RequestBody> params = new HashMap<>();
                File imagFile = new File(Environment
                        .getExternalStorageDirectory() + "/CTTXHEAD/" + PHOTO_CROP_FILE_NAME);
                RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), imagFile);
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
                        try {
                            String gsonStr = response.body().string();
                            JSONObject json = new JSONObject(gsonStr);
                            String url = json.get("url").toString();
                            updateUserImg(url);
//                            if (Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getPortrait())) {
//
//                            }else{
//                                ToastUtils.showShort(SettingActivity.this, "上传头像成功");
//                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        ToastUtils.showShort(SettingActivity.this, Config.getErrMsg("1"));
                        Log.e("why", t.toString());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                    UserInfoRememberCtrl.saveObject(SettingActivity.this, PublicData.getInstance().mLoginInfoBean);
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
                    PublicData.getInstance().clearData(SettingActivity.this);
                    SPUtils.getInstance(SettingActivity.this).clear();
                    mLogout.setVisibility(View.GONE);
                    Intent intent = new Intent(SettingActivity.this, InspectService.class);
                    stopService(intent);
//                    stopService(getInspectService());
                    EventBus.getDefault().post(new BenDiCarInfoEvent(true, null));
                    finish();
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
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

    /**
     * 4.4的系统有些bug,需要以下方法来获取文件的路径
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }


            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
